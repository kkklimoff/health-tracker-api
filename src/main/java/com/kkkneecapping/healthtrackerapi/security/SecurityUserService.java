package com.kkkneecapping.healthtrackerapi.security;

import com.kkkneecapping.healthtrackerapi.entity.User;
import com.kkkneecapping.healthtrackerapi.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public @NonNull SecurityUser loadUserByUsername(@NonNull String username)
      throws UsernameNotFoundException {
    User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return new SecurityUser(user);
  }

  public @NonNull SecurityUser loadUserByPublicId(@NonNull UUID id)
      throws UsernameNotFoundException {
    User user =
        userRepository
            .findByPublicId(id)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return new SecurityUser(user);
  }
}
