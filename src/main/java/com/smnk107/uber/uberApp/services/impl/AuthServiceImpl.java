package com.smnk107.uber.uberApp.services.impl;

import com.smnk107.uber.uberApp.dto.DriverDTO;
import com.smnk107.uber.uberApp.dto.SignupDTO;
import com.smnk107.uber.uberApp.dto.UserDTO;
import com.smnk107.uber.uberApp.entities.Driver;
import com.smnk107.uber.uberApp.entities.User;
import com.smnk107.uber.uberApp.entities.enums.Roles;
import com.smnk107.uber.uberApp.exceptions.ResourceNotFoundException;
import com.smnk107.uber.uberApp.repository.DriverRepository;
import com.smnk107.uber.uberApp.repository.UserRepository;
import com.smnk107.uber.uberApp.services.AuthService;
import com.smnk107.uber.uberApp.services.RiderService;
import com.smnk107.uber.uberApp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private  final RiderService riderService;
    private final WalletService walletService;

    @Override
    public void login(String userName, String password) {

    }

    @Override
    public UserDTO signup(SignupDTO signupDTO) {

        User user = modelMapper.map(signupDTO,User.class);
        user.setRoles(Set.of(Roles.RIDER));
        User userPresent = userRepository.findByEmail(user.getEmail()).orElse(null);
        if(userPresent!= null)
            throw new RuntimeException("User with email "+ user.getEmail()+" already present");
        User savedUser = userRepository.save(user);


        //create user related entities
        riderService.createNewRider(savedUser);
        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser,UserDTO.class);

    }

    @Override
    public DriverDTO onboardNewDriver(Long userId,String vehicleId) {

        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User does not exist"));

        if(user.getRoles().contains(Roles.DRIVER))
            throw new RuntimeException("User is already listed as a Driver");

        user.getRoles().add(Roles.DRIVER);

        Driver newDriver = Driver.builder()
                                .user(user)
                                .rating(0.0)
                                .vehicleId(vehicleId)
                                .available(true)
                                .build();

        Driver savedDriver = driverRepository.save(newDriver);
        userRepository.save(user);

        return modelMapper.map(savedDriver,DriverDTO.class);
    }
}
