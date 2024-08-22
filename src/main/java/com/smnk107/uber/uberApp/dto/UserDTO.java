package com.smnk107.uber.uberApp.dto;

import com.smnk107.uber.uberApp.entities.enums.Roles;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    String name;
    String email;
    Set<Roles> roles;
}
