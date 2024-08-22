package com.smnk107.uber.uberApp.strategies.impl;

import com.smnk107.uber.uberApp.dto.RideRequestDTO;
import com.smnk107.uber.uberApp.entities.Driver;
import com.smnk107.uber.uberApp.entities.RideRequest;
import com.smnk107.uber.uberApp.strategies.DriverMatchingStrategy;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Data
public class DriverMatchingHighestRatedDriverStrategy implements DriverMatchingStrategy {
    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {
        return null;
    }
}
