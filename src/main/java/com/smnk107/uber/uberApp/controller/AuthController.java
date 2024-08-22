package com.smnk107.uber.uberApp.controller;

import com.smnk107.uber.uberApp.dto.DriverDTO;
import com.smnk107.uber.uberApp.dto.OnboardDriverDTO;
import com.smnk107.uber.uberApp.dto.SignupDTO;
import com.smnk107.uber.uberApp.dto.UserDTO;
import com.smnk107.uber.uberApp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    UserDTO signUp(@RequestBody SignupDTO signupDTO) {
        return authService.signup(signupDTO);
    }

    @PostMapping("/onboardDriver")
    DriverDTO onBoardNewDriver(@RequestBody OnboardDriverDTO onboardDriverDTO)
    {
        return authService.onboardNewDriver(onboardDriverDTO.getUserId(),onboardDriverDTO.getVehicleId());
    }

}