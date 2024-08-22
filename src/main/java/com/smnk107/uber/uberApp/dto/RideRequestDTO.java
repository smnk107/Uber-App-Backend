package com.smnk107.uber.uberApp.dto;

import com.smnk107.uber.uberApp.entities.enums.PaymentMethod;
import com.smnk107.uber.uberApp.entities.enums.RideStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestDTO {
    private Long id;
    private PointDTO pickupLocation;
    private PointDTO dropOffLocation;

    private LocalDateTime createdTime;
    private RiderDTO rider;

    private PaymentMethod paymentMethod;

    private RideStatus rideStatus;
    private Double fare;
}
