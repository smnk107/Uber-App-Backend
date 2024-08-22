package com.smnk107.uber.uberApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointDTO {

    private Double[] coordinates;
    private String type = "Point";

    public PointDTO(Double[] coo)
    {
        this.coordinates = coo;
    }
}
