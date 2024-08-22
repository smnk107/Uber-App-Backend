package com.smnk107.uber.uberApp.entities;

import com.smnk107.uber.uberApp.entities.enums.PaymentMethod;
import com.smnk107.uber.uberApp.entities.enums.RideRequestStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;


import java.time.LocalDateTime;

@Entity
@Data
@Table(
        indexes = {@Index(name = "idx_ride_request_rider_id",columnList = "rider_id")}
)
public class RideRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point pickupLocation;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point dropOffLocation;

    @CreationTimestamp
    private LocalDateTime requestedTime;

    private Double Fare;
    @ManyToOne(fetch = FetchType.LAZY)
    private Rider rider;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private RideRequestStatus rideRequestStatus;
}
