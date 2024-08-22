package com.smnk107.uber.uberApp.controller;

import com.smnk107.uber.uberApp.dto.*;
import com.smnk107.uber.uberApp.services.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;
    @PostMapping("/acceptRide/{rideRequestId}")
    public ResponseEntity<RideDTO> acceptRide(@PathVariable(name = "rideRequestId") Long rideRequestId)
    {
        return new ResponseEntity<>(driverService.acceptRide(rideRequestId), HttpStatus.OK);
    }

    @PostMapping("/startRide")
    public ResponseEntity<RideDTO> startRide(@RequestBody RideStartDTO rideStartDTO)
    {
        return new ResponseEntity<>(driverService.startRide(rideStartDTO), HttpStatus.OK);
    }

    @PostMapping("/endRide/{rideId}")
    public ResponseEntity<RideDTO> endRide(@PathVariable Long rideId)
    {
        return new ResponseEntity<>(driverService.endRide(rideId), HttpStatus.OK);
    }


    @PostMapping("/rateRider")
    public RiderDTO rateDriver(@RequestBody RateDTO rateDTO)
    {
        return driverService.rateRider(rateDTO.getRideId(),rateDTO.getRating());
    }

    @GetMapping("/myProfile")
    public DriverDTO getMyProfile()
    {
        return driverService.getMyProfile();
    }

    @GetMapping("/getAllMyRides")
    public Page<RideDTO> getAllMyRides(@RequestParam(defaultValue = "0") Integer pageOffset,
                                       @RequestParam(defaultValue = "10") Integer pageSize)
    {
        PageRequest pageRequest = PageRequest.of(pageOffset,pageSize, Sort.by(Sort.Direction.DESC,"requestedTime","id"));
        return driverService.getAllMyRides(pageRequest);
    }
}
