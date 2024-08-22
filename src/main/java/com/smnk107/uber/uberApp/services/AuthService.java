package com.smnk107.uber.uberApp.services;

import com.smnk107.uber.uberApp.dto.DriverDTO;
import com.smnk107.uber.uberApp.dto.SignupDTO;
import com.smnk107.uber.uberApp.dto.UserDTO;

public interface AuthService {

    void login(String userName, String password);
    UserDTO signup(SignupDTO signupDTO);
    DriverDTO onboardNewDriver(Long userId,String vehicleId);
}
