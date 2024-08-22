package com.smnk107.uber.uberApp.services;

import com.smnk107.uber.uberApp.dto.DriverDTO;
import com.smnk107.uber.uberApp.dto.RideDTO;
import com.smnk107.uber.uberApp.dto.RideRequestDTO;
import com.smnk107.uber.uberApp.dto.RiderDTO;
import com.smnk107.uber.uberApp.entities.Rider;
import com.smnk107.uber.uberApp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface RiderService {
    RideRequestDTO requestRide(RideRequestDTO rideRequestDto);

    RideDTO cancelRide(Long rideId);

    DriverDTO rateDriver(Long rideId, Integer rating);

    RiderDTO getMyProfile();

    Page<RideDTO> getAllMyRides(PageRequest pageRequest);

    Rider createNewRider(User user);

    Rider getCurrentRider();
}
