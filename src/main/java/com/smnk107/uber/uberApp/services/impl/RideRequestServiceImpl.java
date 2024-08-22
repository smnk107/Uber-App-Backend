package com.smnk107.uber.uberApp.services.impl;

import com.smnk107.uber.uberApp.entities.RideRequest;
import com.smnk107.uber.uberApp.exceptions.ResourceNotFoundException;
import com.smnk107.uber.uberApp.repository.RideRequestRepository;
import com.smnk107.uber.uberApp.services.RideRequestService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Data
public class RideRequestServiceImpl implements RideRequestService {

    private final RideRequestRepository rideRequestRepository ;

    @Override
    public RideRequest findRideRequestById(Long rideRequestId) {
        RideRequest rideRequest = rideRequestRepository.findById(rideRequestId)
                    .orElseThrow(()->new ResourceNotFoundException("RideRequest not found with id "+rideRequestId));

        return rideRequest;
    }

    @Override
    public void update(RideRequest rideRequest) {
        rideRequestRepository.save(rideRequest);
    }
}
