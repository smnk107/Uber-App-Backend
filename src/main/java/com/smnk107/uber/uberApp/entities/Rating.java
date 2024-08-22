package com.smnk107.uber.uberApp.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Table(indexes = {@Index(name ="idx_driver_rating",columnList = "driver_id"),
                  @Index(name = "idx_rider_rating",columnList = "rider_id")  })
@Builder
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Ride ride;

    @ManyToOne
    private Rider rider;
    @ManyToOne
    private Driver driver;

    private Integer driverRating;
    private Integer riderRating;
}
