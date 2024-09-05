package com.smnk107.uber.uberApp.controller;

import com.smnk107.uber.uberApp.dto.*;
import com.smnk107.uber.uberApp.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public UserDTO signUp(@RequestBody SignupDTO signupDTO) {
        return authService.signup(signupDTO);
    }

    @PostMapping("/onboardDriver")
    public DriverDTO onBoardNewDriver(@RequestBody OnboardDriverDTO onboardDriverDTO)
    {
        return authService.onboardNewDriver(onboardDriverDTO.getUserId(),onboardDriverDTO.getVehicleId());
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletRequest request,
                                  HttpServletResponse response)
    {
        String[] tokens = authService.login(loginRequestDTO.getEmail(),loginRequestDTO.getPassword());

        Cookie cookie = new Cookie("RefreshToken",tokens[1]);
        cookie.setHttpOnly(true);
        //cookie.setSecure(true);
        response.addCookie(cookie);

        return LoginResponseDTO.builder().accessToken(tokens[0]).build();
    }

}