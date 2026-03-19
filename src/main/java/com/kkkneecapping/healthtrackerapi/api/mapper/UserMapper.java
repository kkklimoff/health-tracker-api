package com.kkkneecapping.healthtrackerapi.api.mapper;

import com.kkkneecapping.healthtrackerapi.dto.RegistrationRequest;
import com.kkkneecapping.healthtrackerapi.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

  private final PasswordEncoder passwordEncoder;

  public User fromDto(RegistrationRequest registrationRequest) {
    return User.builder()
        .username(registrationRequest.getUsername())
        .passwordHash(passwordEncoder.encode(registrationRequest.getPassword()))
        .email(registrationRequest.getEmail())
        .firstName(registrationRequest.getFirstName())
        .lastName(registrationRequest.getLastName())
        .build();
  }
}
