package com.smnk107.uber.uberApp.strategies;

import com.smnk107.uber.uberApp.dto.RideRequestDTO;
import com.smnk107.uber.uberApp.entities.Driver;
import com.smnk107.uber.uberApp.entities.RideRequest;

import java.util.List;

public interface DriverMatchingStrategy {
    List<Driver> findMatchingDriver(RideRequest rideRequest);
}
