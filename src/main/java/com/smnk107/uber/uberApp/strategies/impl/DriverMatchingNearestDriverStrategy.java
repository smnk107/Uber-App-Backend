package com.smnk107.uber.uberApp.strategies.impl;

import com.smnk107.uber.uberApp.dto.RideRequestDTO;
import com.smnk107.uber.uberApp.entities.Driver;
import com.smnk107.uber.uberApp.entities.RideRequest;
import com.smnk107.uber.uberApp.repository.DriverRepository;
import com.smnk107.uber.uberApp.strategies.DriverMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DriverMatchingNearestDriverStrategy implements DriverMatchingStrategy {

    private final DriverRepository driverRepository;

    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {

        System.out.print("rideRequest-----------------");
        System.out.println(rideRequest);
        List<Driver> list = driverRepository.findNearByTopRatedDrivers(rideRequest.getPickupLocation());
        System.out.println(list);
        return list;
    }
}
