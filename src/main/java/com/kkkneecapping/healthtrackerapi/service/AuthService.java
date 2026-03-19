package com.kkkneecapping.healthtrackerapi.service;

import com.kkkneecapping.healthtrackerapi.entity.User;
import com.kkkneecapping.healthtrackerapi.repository.UserRepository;
import com.kkkneecapping.healthtrackerapi.security.JwtUtil;
import com.kkkneecapping.healthtrackerapi.security.SecurityUser;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;

  public String loginByCredentials(String username, String password) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password));
    SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
    if (securityUser == null) {
      throw new IllegalStateException("No authenticated user!");
    }
    recordLoginDate(securityUser.getUser());
    return jwtUtil.generateToken(securityUser.getPublicId());
  }

  private void recordLoginDate(User user) {
      user.setLastLoginAt(OffsetDateTime.now());
      userRepository.save(user);
  }
}
