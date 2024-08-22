package com.smnk107.uber.uberApp.services.impl;

import com.smnk107.uber.uberApp.dto.DriverDTO;
import com.smnk107.uber.uberApp.dto.RateDTO;
import com.smnk107.uber.uberApp.dto.RiderDTO;
import com.smnk107.uber.uberApp.entities.Driver;
import com.smnk107.uber.uberApp.entities.Rating;
import com.smnk107.uber.uberApp.entities.Ride;
import com.smnk107.uber.uberApp.entities.Rider;
import com.smnk107.uber.uberApp.exceptions.ResourceNotFoundException;
import com.smnk107.uber.uberApp.repository.DriverRepository;
import com.smnk107.uber.uberApp.repository.RatingRepository;
import com.smnk107.uber.uberApp.repository.RiderRepository;
import com.smnk107.uber.uberApp.services.RatingService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Data
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final RiderRepository riderRepository;
    private final DriverRepository driverRepository;

    private final ModelMapper modelMapper;

    @Override
    public RiderDTO rateRider(Ride ride, Integer rating) {

        Rider rider = ride.getRider();
        Rating ratingObj = ratingRepository.findByRide(ride)
                .orElseThrow(()->new ResourceNotFoundException("Ride not found while rating the rider"));

        ratingObj.setRiderRating(rating);
        ratingRepository.save(ratingObj);

        Double newRating = ratingRepository.findByRider(rider)
                .stream()
                .mapToDouble(Rating::getDriverRating)
                .average()
                .orElse(0.0);

        rider.setRating(newRating);
        Rider savedRider = riderRepository.save(rider);

        return modelMapper.map(savedRider,RiderDTO.class);

    }

    @Override
    public DriverDTO rateDriver(Ride ride, Integer rating) {

        Driver driver = ride.getDriver();
        Rating ratingObj = ratingRepository.findByRide(ride)
                .orElseThrow(()->new ResourceNotFoundException("Ride not found while rating the driver"));

        ratingObj.setDriverRating(rating);
        ratingRepository.save(ratingObj);

        Double newRating = ratingRepository.findByDriver(driver)
                .stream()
                .mapToDouble(Rating::getDriverRating)
                .average()
                .orElse(0.0);

        driver.setRating(newRating);
        Driver savedDriver = driverRepository.save(driver);

        return modelMapper.map(savedDriver,DriverDTO.class);
    }

    @Override
    public void createNewRating(Ride ride) {

        Rider rider = ride.getRider();
        Driver driver = ride.getDriver();

        Rating rating = Rating.builder()
                .ride(ride)
                .rider(rider)
                .driver(driver)
                .build();

        ratingRepository.save(rating);
    }
}
