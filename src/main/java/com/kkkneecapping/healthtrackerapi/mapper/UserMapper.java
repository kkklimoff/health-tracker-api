package com.kkkneecapping.healthtrackerapi.mapper;

import com.kkkneecapping.healthtrackerapi.dto.RegistrationRequest;
import com.kkkneecapping.healthtrackerapi.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

  public User fromDto(RegistrationRequest registrationRequest) {
    return User.builder()
        .username(registrationRequest.getUsername())
        .email(registrationRequest.getEmail())
        .firstName(registrationRequest.getFirstName())
        .lastName(registrationRequest.getLastName())
        .build();
  }
}
