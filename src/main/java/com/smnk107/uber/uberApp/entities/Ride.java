package com.smnk107.uber.uberApp.entities;

import com.smnk107.uber.uberApp.entities.enums.PaymentMethod;
import com.smnk107.uber.uberApp.entities.enums.RideRequestStatus;
import com.smnk107.uber.uberApp.entities.enums.RideStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.geo.Point;

import java.time.LocalDateTime;

@Entity
@Data
@Table(indexes = {@Index(name = "idx_driver",columnList = "driver_id"),
                  @Index(name = "idx_rider",columnList = "rider_id")})
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point pickupLocation;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point dropOffLocation;

    @CreationTimestamp
    private LocalDateTime requestedTime;

    @CreationTimestamp
    private LocalDateTime startTime;
    @CreationTimestamp
    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Rider rider;

    @ManyToOne(fetch = FetchType.LAZY)
    private Driver driver;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private Double fare;
    private String otp;

    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;


}
