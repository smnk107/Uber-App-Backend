package com.smnk107.uber.uberApp.services.impl;

import com.smnk107.uber.uberApp.dto.DriverDTO;
import com.smnk107.uber.uberApp.dto.RideDTO;
import com.smnk107.uber.uberApp.dto.RideRequestDTO;
import com.smnk107.uber.uberApp.dto.RiderDTO;
import com.smnk107.uber.uberApp.entities.*;
import com.smnk107.uber.uberApp.entities.enums.RideRequestStatus;
import com.smnk107.uber.uberApp.entities.enums.RideStatus;
import com.smnk107.uber.uberApp.exceptions.ResourceNotFoundException;
import com.smnk107.uber.uberApp.repository.RideRequestRepository;
import com.smnk107.uber.uberApp.repository.RiderRepository;
import com.smnk107.uber.uberApp.services.DriverService;
import com.smnk107.uber.uberApp.services.RatingService;
import com.smnk107.uber.uberApp.services.RideService;
import com.smnk107.uber.uberApp.services.RiderService;
import com.smnk107.uber.uberApp.strategies.DriverMatchingStrategy;
import com.smnk107.uber.uberApp.strategies.RideFareCalculationStrategy;
import com.smnk107.uber.uberApp.strategies.RideStrategyManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Builder
//@AllArgsConstructor
public class RiderServiceImpl implements RiderService
{
    private final ModelMapper modelMapper;
    private final RideStrategyManager rideStrategyManager;
    private final RideRequestRepository rideRequestRepository;
    private  final RiderRepository riderRepository;
    private  final RideService rideService;
    private  final DriverService driverService;
    private final RatingService ratingService;
    @Override
    public RideRequestDTO requestRide(RideRequestDTO rideRequestDTO) {


        System.out.println(rideRequestDTO.getRider()+"-in service"+rideRequestDTO.getId());

        RideRequest rideRequest = modelMapper.map(rideRequestDTO,RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        Rider rider = getCurrentRider();

        rideRequest.setRider(rider);

        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);

        List<Driver> drivers = rideStrategyManager.driverMatchingStrategy(rider.getRating()).findMatchingDriver(rideRequest);
        //TODO : Send notification to all the drivers

        RideRequest savedRideRequest = rideRequestRepository.save(rideRequest);

        return modelMapper.map(savedRideRequest,RideRequestDTO.class);
    }

    @Override
    public RideDTO cancelRide(Long rideId) {

        Ride ride = rideService.getRideById(rideId);
        Rider rider = getCurrentRider();

        if(!rider.equals(ride.getRider()))
            throw new RuntimeException("rider is not authorized to cancel the ride !");

        if(ride.getRideStatus().equals(RideStatus.CONFIRMED))
            throw new RuntimeException("Ride cannot be cancelled");

        rideService.updateRideStatus(ride.getId(),RideStatus.CANCELLED);

        driverService.updateDriverAvailability(ride.getDriver().getId(),true);

        ride.setRideStatus(RideStatus.CANCELLED);

        return modelMapper.map(ride,RideDTO.class);
    }

    @Override
    public DriverDTO rateDriver(Long rideId, Integer rating)
    {

        Ride ride = rideService.getRideById(rideId);
        Rider rider = getCurrentRider();

        if(!rider.equals(ride.getRider()))
        {
            throw new RuntimeException("Rider is not authorised to rate the rider ");
        }

        if(!ride.getRideStatus().equals(RideStatus.ENDED))
        {
            throw new RuntimeException("You can rate the rider after the ends!");
        }

        DriverDTO driverDTO = ratingService.rateDriver(ride,rating);
        return driverDTO;
    }

    @Override
    public RiderDTO getMyProfile()
    {
        Rider rider = getCurrentRider();
        return modelMapper.map(rider,RiderDTO.class);

    }

    @Override
    public Page<RideDTO> getAllMyRides(PageRequest pageRequest) {
        Rider  rider  =  getCurrentRider() ;
        Page<Ride> rides = rideService .getAllRidesOfRider(rider,pageRequest);

        return rides .map( tride -> modelMapper.map(tride,RideDTO .class) );
    }

    @Override
    public Rider createNewRider(User user) {
        Rider rider = Rider.builder()
                           .user(user)
                           .rating(0.0)
                           .build();
        return rider;
    }

    @Override
    public Rider getCurrentRider() {
        //TODO: fetch current user with spring security;

        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return riderRepository.findByUser(user)
                .orElseThrow(()->new ResourceNotFoundException("Rider not found for te User"));

        //return riderRepository.findById(1L).orElseThrow(()-> new ResourceNotFoundException("Rider not found"));
    }

}
