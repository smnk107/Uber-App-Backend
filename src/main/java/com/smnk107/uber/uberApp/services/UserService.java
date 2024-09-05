package com.smnk107.uber.uberApp.services;

import com.smnk107.uber.uberApp.entities.User;
import com.smnk107.uber.uberApp.exceptions.ResourceNotFoundException;
import com.smnk107.uber.uberApp.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Data
@RequiredArgsConstructor
@Service
public final class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found with username :"+username));
    }

    public User getUserByUserId(Long userId)
    {
        return userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found with userId :"+userId));
    }
}
