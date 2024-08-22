package com.smnk107.uber.uberApp.services;

import com.smnk107.uber.uberApp.dto.DriverDTO;
import com.smnk107.uber.uberApp.dto.RiderDTO;
import com.smnk107.uber.uberApp.entities.Rating;
import com.smnk107.uber.uberApp.entities.Ride;
import com.smnk107.uber.uberApp.entities.Rider;

public interface RatingService {

    public RiderDTO rateRider(Ride ride, Integer rating);
    public DriverDTO rateDriver(Ride ride, Integer rating);

    public void createNewRating(Ride ride);

}
