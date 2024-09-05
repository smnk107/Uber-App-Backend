package com.smnk107.uber.uberApp.controller;

import com.smnk107.uber.uberApp.dto.*;
import com.smnk107.uber.uberApp.services.RiderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/rider")
@RequiredArgsConstructor
@Slf4j
@Secured("ROLE_RIDER")
public class RiderController {

    private final RiderService riderService;

    @PostMapping("/requestRide")
    public RideRequestDTO requestRide(@RequestBody RideRequestDTO rideRequestDTO)
    {
        System.out.println(rideRequestDTO.getRider()+"-in controller"+rideRequestDTO.getId());
        return riderService.requestRide(rideRequestDTO);
    }

    @PostMapping("/rateDriver")
    public DriverDTO rateDriver(@RequestBody RateDTO rateDTO)
    {
        return riderService.rateDriver(rateDTO.getRideId(),rateDTO.getRating());
    }

    @GetMapping("/myProfile")
    public RiderDTO getMyProfile()
    {
        return riderService.getMyProfile();
    }

    @GetMapping("/getAllMyRides")
    public Page<RideDTO> getAllMyRides(@RequestParam(defaultValue = "0") Integer pageOffset,
                                       @RequestParam(defaultValue = "10") Integer pageSize)
    {
        PageRequest pageRequest = PageRequest.of(pageOffset,pageSize, Sort.by(Sort.Direction.DESC,"requestedTime","id"));
        return riderService.getAllMyRides(pageRequest);
    }

    @PostMapping("/rateDriver/{rideId}/{rating}")
    public DriverDTO rateDriver(@PathVariable Long rideId, @PathVariable Integer rating)
    {
        return riderService.rateDriver(rideId,rating);
    }


}
