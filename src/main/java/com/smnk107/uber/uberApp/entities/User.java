package com.smnk107.uber.uberApp.entities;

import com.smnk107.uber.uberApp.entities.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "app_user",
indexes = {@Index(name = "idx_user_email" , columnList = "email")})
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @Email
    @Column(unique = true)
    private String email;

    @Column(name = "password")
    private String passWord;

    @ElementCollection
    @Enumerated(value = EnumType.STRING)
    private Set<Roles> roles;

}
