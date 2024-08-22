package com.smnk107.uber.uberApp.services.impl;

import com.smnk107.uber.uberApp.dto.RideRequestDTO;
import com.smnk107.uber.uberApp.entities.Driver;
import com.smnk107.uber.uberApp.entities.Ride;
import com.smnk107.uber.uberApp.entities.RideRequest;
import com.smnk107.uber.uberApp.entities.Rider;
import com.smnk107.uber.uberApp.entities.enums.RideRequestStatus;
import com.smnk107.uber.uberApp.entities.enums.RideStatus;
import com.smnk107.uber.uberApp.exceptions.ResourceNotFoundException;
import com.smnk107.uber.uberApp.repository.RideRepository;
import com.smnk107.uber.uberApp.repository.RiderRepository;
import com.smnk107.uber.uberApp.services.RideRequestService;
import com.smnk107.uber.uberApp.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRequestService rideRequestService;
    private final ModelMapper modelMapper;
    private final RideRepository rideRepository;

    @Override
    public Ride getRideById(Long rideId) {

        return rideRepository.findById(rideId)
                .orElseThrow(()->new ResourceNotFoundException("Ride with given Id not found"));
    }

    @Override
    public void matchWithDrivers(RideRequestDTO rideRequestDto) {

    }

    @Override
    @Transactional
    public Ride createNewRide(RideRequest rideRequest, Driver driver) {

        rideRequest.setRideRequestStatus(RideRequestStatus.CONFIRMED);

        Ride ride = modelMapper.map(rideRequest,Ride.class);
        ride.setRideStatus(RideStatus.CONFIRMED);
        ride.setDriver(driver);
        ride.setOtp(generateRandomOTP());
        ride.setId(null);

        rideRequestService.update(rideRequest);

        return rideRepository.save(ride);
    }

    @Override
    public Ride updateRideStatus(Long rideId, RideStatus rideStatus) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(()->new ResourceNotFoundException("Cannot update, as the ride not found"));

        ride.setRideStatus(rideStatus);
        return rideRepository.save(ride);
    }

    @Override
    public Page<Ride> getAllRidesOfRider(Rider rider, PageRequest pageRequest) {


        return rideRepository.findByRider(rider,pageRequest.of(0,10));

        //return null;
    }

    @Override
    public Page<Ride> getAllRidesOfDriver(Driver driver, PageRequest pageRequest) {

        return rideRepository.findByDriver(driver,pageRequest.of(0,10));


    }

    private String generateRandomOTP() {
        Random random = new Random();
        int otpInt = random.nextInt(10000);  //0 to 9999
        return String.format("%04d", otpInt);
    }
}
