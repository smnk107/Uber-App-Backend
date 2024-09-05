package com.smnk107.uber.uberApp.entities;

import com.smnk107.uber.uberApp.entities.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Data
@Table(name = "app_user",
indexes = {@Index(name = "idx_user_email" , columnList = "email")})
public class User implements UserDetails {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @Email
    @Column(unique = true)
    private String email;

    @Column(name = "password")
    private String passWord;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(value = EnumType.STRING )
    //@OneToMany(fetch = FetchType.EAGER)
    private Set<Roles> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return  roles.stream()
                .map(role ->new SimpleGrantedAuthority("ROLE_"+role.name()))
                .collect(Collectors.toSet());


    }



    //@Override
    public String getPassword() {
        return this.passWord;
    }

    //@Override
    public String getUsername() {
        return this.email;
    }
}
