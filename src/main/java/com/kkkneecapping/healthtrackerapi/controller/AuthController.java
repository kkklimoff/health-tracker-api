package com.kkkneecapping.healthtrackerapi.controller;

import static com.kkkneecapping.healthtrackerapi.util.ApiUtil.responseOk;

import com.kkkneecapping.healthtrackerapi.api.AuthApi;
import com.kkkneecapping.healthtrackerapi.dto.JwtResponse;
import com.kkkneecapping.healthtrackerapi.dto.LoginRequest;
import com.kkkneecapping.healthtrackerapi.dto.RegistrationRequest;
import com.kkkneecapping.healthtrackerapi.security.JwtUtil;
import com.kkkneecapping.healthtrackerapi.security.SecurityUser;
import com.kkkneecapping.healthtrackerapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

  private final AuthService authService;
  private final JwtUtil jwtUtil;

  @Override
  public ResponseEntity<JwtResponse> login(LoginRequest loginRequest) {
    SecurityUser loggedInUser = authService.login(loginRequest);
    String token = jwtUtil.generateToken(loggedInUser.getPublicId());
    return responseOk(new JwtResponse(token));
  }

  @Override
  public ResponseEntity<Void> register(RegistrationRequest registrationRequest) {
    authService.register(registrationRequest);
    return responseOk();
  }
}
