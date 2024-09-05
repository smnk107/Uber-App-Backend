package com.smnk107.uber.uberApp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    String accessToken;
}
