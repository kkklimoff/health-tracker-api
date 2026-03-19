package com.kkkneecapping.healthtrackerapi.api.controller;

import com.kkkneecapping.healthtrackerapi.api.AuthApi;
import com.kkkneecapping.healthtrackerapi.api.mapper.UserMapper;
import com.kkkneecapping.healthtrackerapi.dto.JwtResponse;
import com.kkkneecapping.healthtrackerapi.dto.LoginRequest;
import com.kkkneecapping.healthtrackerapi.dto.RegistrationRequest;
import com.kkkneecapping.healthtrackerapi.entity.User;
import com.kkkneecapping.healthtrackerapi.service.AuthService;
import com.kkkneecapping.healthtrackerapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

  private final UserMapper userMapper;
  private final UserService userService;
  private final AuthService authService;

  @Override
  public ResponseEntity<JwtResponse> login(LoginRequest loginRequest) {
    return ResponseEntity.ok(
        new JwtResponse()
            .token(
                authService.loginByCredentials(
                    loginRequest.getUsername(), loginRequest.getPassword())));
  }

  @Override
  public ResponseEntity<Void> register(RegistrationRequest registrationRequest) {
    User user = userMapper.fromDto(registrationRequest);
    userService.create(user);
    return ResponseEntity.ok().build();
  }
}
