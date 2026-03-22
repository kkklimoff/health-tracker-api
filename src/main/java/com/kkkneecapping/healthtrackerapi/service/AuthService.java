package com.kkkneecapping.healthtrackerapi.service;

import com.kkkneecapping.healthtrackerapi.dto.LoginRequest;
import com.kkkneecapping.healthtrackerapi.dto.RegistrationRequest;
import com.kkkneecapping.healthtrackerapi.entity.User;
import com.kkkneecapping.healthtrackerapi.mapper.UserMapper;
import com.kkkneecapping.healthtrackerapi.security.JwtUtil;
import com.kkkneecapping.healthtrackerapi.security.SecurityUser;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final UserService userService;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  public String login(LoginRequest loginRequest) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));
    SecurityUser securityUser =
        (SecurityUser)
            Objects.requireNonNull(
                authentication.getPrincipal(),
                "Principal must not be null after successful authentication");
    userService.updateLastLogin(securityUser.getUser());
    return jwtUtil.generateToken(securityUser.getPublicId());
  }

  public void register(RegistrationRequest registrationRequest) {
    User user = userMapper.fromDto(registrationRequest);
    user.setPasswordHash(passwordEncoder.encode(registrationRequest.getPassword()));
    userService.create(user);
  }
}
