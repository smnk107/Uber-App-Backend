package com.smnk107.uber.uberApp.services.impl;

import com.smnk107.uber.uberApp.dto.DriverDTO;
import com.smnk107.uber.uberApp.dto.RideDTO;
import com.smnk107.uber.uberApp.dto.RideStartDTO;
import com.smnk107.uber.uberApp.dto.RiderDTO;
import com.smnk107.uber.uberApp.entities.Driver;
import com.smnk107.uber.uberApp.entities.Ride;
import com.smnk107.uber.uberApp.entities.RideRequest;
import com.smnk107.uber.uberApp.entities.User;
import com.smnk107.uber.uberApp.entities.enums.RideRequestStatus;
import com.smnk107.uber.uberApp.entities.enums.RideStatus;
import com.smnk107.uber.uberApp.exceptions.ResourceNotFoundException;
import com.smnk107.uber.uberApp.repository.DriverRepository;
import com.smnk107.uber.uberApp.services.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    private final RideRequestService rideRequestService;
    private final RideService rideService;
    private final PaymentService paymentService;
    private final RatingService ratingService;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public RideDTO acceptRide(Long rideRequestId) {

        RideRequest rideRequest = rideRequestService.findRideRequestById(rideRequestId);

        if(!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING))
            throw new RuntimeException("The ride request cannot accepted, as the status is "+rideRequest.getRideRequestStatus());

        Driver currentDriver = getCurrentDriver();

        if(!currentDriver.getAvailable())
            throw new RuntimeException("Driver cannot accept ride due to unavailability");

        currentDriver.setAvailable(false);
        Driver savedDriver = driverRepository.save(currentDriver);

        Ride ride = rideService.createNewRide(rideRequest,savedDriver);
        //paymentService.createNewPayment(ride);

        return modelMapper.map(ride,RideDTO.class);
    }

    @Override
    @Transactional
    public RideDTO cancelRide(Long rideId) {

        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver()))
            throw new RuntimeException("Driver is not authorized to cancel the ride !");

        if(ride.getRideStatus().equals(RideStatus.CONFIRMED))
            throw new RuntimeException("Ride cannot be cancelled");

        rideService.updateRideStatus(ride.getId(),RideStatus.CANCELLED);

        driver.setAvailable(true);
        driverRepository.save(driver);

        ride.setRideStatus(RideStatus.CANCELLED);

        return modelMapper.map(ride,RideDTO.class);
    }

    @Override
    public RideDTO startRide(RideStartDTO rideStartDTO) {

        Long rideId = rideStartDTO.getRideId();
        String otp = rideStartDTO.getOtp();

        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver()))
        {
            throw new RuntimeException("Driver is not authorised to start the ride ");
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED))
        {
            throw new RuntimeException("Ride is not confirmed yet");
        }

        if(!otp.equals(ride.getOtp()))
        {
            throw new RuntimeException("Wrong OTP entered");
        }

        ride.setStartTime(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(rideId,RideStatus.ONGOING);

        paymentService.createNewPayment(savedRide);
        ratingService.createNewRating(savedRide);
        //modelMapper.map(savedRide,RideDTO.class);

        return modelMapper.map(savedRide,RideDTO.class);
    }

    @Override
    @Transactional
    public RideDTO endRide(Long rideId) {

        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver()))
        {
            throw new RuntimeException("Driver is not authorised to end the ride ");
        }

        if(!ride.getRideStatus().equals(RideStatus.ONGOING))
        {
            throw new RuntimeException("Ride is not ONGOING, hance cant be ended");
        }


        ride.setEndTime(LocalDateTime.now());
        Ride savedRide = rideService.updateRideStatus(rideId,RideStatus.ENDED);
        paymentService.processPayment(ride);
        modelMapper.map(savedRide,RideDTO.class);
        return modelMapper.map(savedRide,RideDTO.class);



    }

    @Override
    public RiderDTO rateRider(Long rideId, Integer rating) {

        Ride ride = rideService.getRideById(rideId);
        Driver driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver()))
        {
            throw new RuntimeException("Driver is not authorised to rate the rider ");
        }

        if(!ride.getRideStatus().equals(RideStatus.ENDED))
        {
            throw new RuntimeException("You can rate the rider after the ends!");
        }

        ratingService.rateRider(ride,rating);
        return null;
    }

    @Override
    public DriverDTO getMyProfile() {

        Driver driver = getCurrentDriver();
        return modelMapper.map(driver,DriverDTO.class);
    }

    @Override
    public Page<RideDTO> getAllMyRides(PageRequest pageRequest)
    {
        Driver  driver  =  getCurrentDriver() ;
        Page<Ride> rides = rideService .getAllRidesOfDriver(driver, pageRequest);

        return rides .map( tride -> modelMapper.map(tride,RideDTO .class) );
    }

    @Override
    public Driver getCurrentDriver() {

        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return driverRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Driver not found with " +
               "id "+user.getId()));
    }

    @Override
    public void updateDriverAvailability(Long driverId, Boolean available) {
        Driver driver = driverRepository.findById(driverId).orElseThrow(
                ()->new ResourceNotFoundException("Driver with id :"+driverId+" not found")
        );

        driver.setAvailable(available);
        driverRepository.save(driver);
    }

    @Override
    public Driver getDriverById(Long driverId) {
        return driverRepository.findById(driverId).orElseThrow(
                ()->new ResourceNotFoundException("Driver with id :"+driverId+" not found")
        );
    }
}
