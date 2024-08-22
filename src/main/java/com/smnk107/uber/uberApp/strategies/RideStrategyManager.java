package com.smnk107.uber.uberApp.strategies;

import com.smnk107.uber.uberApp.strategies.impl.DriverMatchingHighestRatedDriverStrategy;
import com.smnk107.uber.uberApp.strategies.impl.DriverMatchingNearestDriverStrategy;
import com.smnk107.uber.uberApp.strategies.impl.RideFareSurgePricingFareCalculationStrategy;
import com.smnk107.uber.uberApp.strategies.impl.RiderFareDefaultFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RideStrategyManager {

    private final DriverMatchingHighestRatedDriverStrategy driverMatchingHighestRatedDriverStrategy;
    private final DriverMatchingNearestDriverStrategy driverMatchingNearestDriverStrategy;
    private final RideFareSurgePricingFareCalculationStrategy rideFareSurgePricingFareCalculationStrategy;
    private final RiderFareDefaultFareCalculationStrategy riderFareDefaultFareCalculationStrategy;
    public RideFareCalculationStrategy rideFareCalculationStrategy()
    {
        LocalTime surgeStartTime = LocalTime.of(18,00);
        LocalTime surgeEndTime = LocalTime.of(6,00);
        LocalTime currentTime = LocalTime.now();

        Boolean isSurgeTime = currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);

        if(isSurgeTime) return rideFareSurgePricingFareCalculationStrategy;
        return riderFareDefaultFareCalculationStrategy;
    }

    public DriverMatchingStrategy driverMatchingStrategy(double riderRating)
    {
        //if(riderRating>=4.5)
            //return driverMatchingHighestRatedDriverStrategy;
        //else
         return driverMatchingNearestDriverStrategy;
    }
}
