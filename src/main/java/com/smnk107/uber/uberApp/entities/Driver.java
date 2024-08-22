package com.smnk107.uber.uberApp.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
//import org.springframework.data.geo.Point;
import org.locationtech.jts.geom.Point;
@Entity
@Data
@Builder
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Double rating;

    private Boolean available;

    @Column(columnDefinition = "Geometry(Point,4326)")
    private Point currentLocation;

    private String vehicleId;
}
