package com.kkkneecapping.healthtrackerapi.service;

import com.kkkneecapping.healthtrackerapi.dto.LoginRequest;
import com.kkkneecapping.healthtrackerapi.dto.RegistrationRequest;
import com.kkkneecapping.healthtrackerapi.entity.User;
import com.kkkneecapping.healthtrackerapi.mapper.UserMapper;
import com.kkkneecapping.healthtrackerapi.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  public SecurityUser login(LoginRequest loginRequest) {
    return authenticate(loginRequest.getUsername(), loginRequest.getPassword());
  }

  public void register(RegistrationRequest registrationRequest) {
    User user = userMapper.fromDto(registrationRequest);
    user.setPasswordHash(passwordEncoder.encode(registrationRequest.getPassword()));
    userService.create(user);
  }

  @NonNull
  private SecurityUser authenticate(String username, String password) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password));
    Object principal = authentication.getPrincipal();
    if (principal instanceof SecurityUser securityUser) {
      return securityUser;
    }
    throw new BadCredentialsException("Invalid username or password");
  }
}
