package com.kkkneecapping.healthtrackerapi.api.controller;

import com.kkkneecapping.healthtrackerapi.api.AuthApi;
import com.kkkneecapping.healthtrackerapi.dto.JwtResponse;
import com.kkkneecapping.healthtrackerapi.dto.LoginRequest;
import com.kkkneecapping.healthtrackerapi.dto.RegistrationRequest;
import com.kkkneecapping.healthtrackerapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

  private final AuthService authService;

  @Override
  public ResponseEntity<JwtResponse> login(LoginRequest loginRequest) {
    String token = authService.login(loginRequest);
    return ResponseEntity.ok(new JwtResponse().token(token));
  }

  @Override
  public ResponseEntity<Void> register(RegistrationRequest registrationRequest) {
    authService.register(registrationRequest);
    return ResponseEntity.ok().build();
  }
}
