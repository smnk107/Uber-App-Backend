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
import com.smnk107.uber.uberApp.security.JwtService;
import com.smnk107.uber.uberApp.services.AuthService;
import com.smnk107.uber.uberApp.services.RiderService;
import com.smnk107.uber.uberApp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public String[] login(String userName, String password) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userName,password));

        User user = (User)authentication.getPrincipal();

        String accessToken = jwtService.createAccessToken(user);
        String refreshToken = jwtService.createRefreshToken(user);
        return new String[]{accessToken,refreshToken};
    }

    @Override
    public UserDTO signup(SignupDTO signupDTO) {

        User user = modelMapper.map(signupDTO,User.class);
        user.setRoles(Set.of(Roles.RIDER));
        User userPresent = userRepository.findByEmail(user.getEmail()).orElse(null);
        if(userPresent!= null)
            throw new RuntimeException("User with email "+ user.getEmail()+" already present");

        user.setPassWord(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);


        //create user related entities
        riderService.createNewRider(savedUser);
        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser,UserDTO.class);

    }

    @Override
    @Secured("ROLE_ADMIN")
    public DriverDTO onboardNewDriver(Long userId,String vehicleId) {

        //User user2;
//        System.out.println("getting context in ond");
////        if(SecurityContextHolder.getContext()!=null) {
////            user2 = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////            System.out.println(user2.getName());
////        }
//        System.out.println("getting context in ond 1");

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
