package com.smnk107.uber.uberApp.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
//@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Rider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Double rating;
}
