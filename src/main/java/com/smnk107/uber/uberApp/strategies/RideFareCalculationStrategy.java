package com.smnk107.uber.uberApp.strategies;

import com.smnk107.uber.uberApp.dto.RideRequestDTO;
import com.smnk107.uber.uberApp.entities.RideRequest;

public interface RideFareCalculationStrategy {
    double calculateFare(RideRequest rideRequest);

}
