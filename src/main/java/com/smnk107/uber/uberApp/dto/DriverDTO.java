package com.smnk107.uber.uberApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {
    private UserDTO user;
    private Double rating;
    private Boolean available;
    private String vehicleId;
}