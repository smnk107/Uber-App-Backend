package com.smnk107.uber.uberApp.services;

import com.smnk107.uber.uberApp.dto.DriverDTO;
import com.smnk107.uber.uberApp.dto.RideDTO;
import com.smnk107.uber.uberApp.dto.RideStartDTO;
import com.smnk107.uber.uberApp.dto.RiderDTO;
import com.smnk107.uber.uberApp.entities.Driver;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;


public interface DriverService {
    RideDTO acceptRide(Long rideId);

    RideDTO cancelRide(Long rideId);


    RideDTO startRide(RideStartDTO rideStartDTO);

    RideDTO endRide(Long rideId);

    RiderDTO rateRider(Long rideId, Integer rating);

    DriverDTO getMyProfile();

    Page<RideDTO> getAllMyRides(PageRequest pageRequest);

    Driver getCurrentDriver();

    void updateDriverAvailability(Long driverId, Boolean available);

    Driver getDriverById(Long driverId);
}
