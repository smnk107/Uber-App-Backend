package com.smnk107.uber.uberApp.strategies.impl;

import com.smnk107.uber.uberApp.dto.RideRequestDTO;
import com.smnk107.uber.uberApp.entities.RideRequest;
import com.smnk107.uber.uberApp.services.DistanceService;
import com.smnk107.uber.uberApp.strategies.RideFareCalculationStrategy;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Data
public class RideFareSurgePricingFareCalculationStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;
    //@Value("${RIDE_FARE_MULTIPLIER}")
    Double RIDE_FARE_MULTIPLIER = 10.00;
    Double SURGE_FACTOR = 2.0;

    @Override
    public double calculateFare(RideRequest rideRequest) {

        Double distance = distanceService.calculateDistance(rideRequest.getPickupLocation(),
                rideRequest.getDropOffLocation());


        return distance*RIDE_FARE_MULTIPLIER*SURGE_FACTOR;
    }
}
