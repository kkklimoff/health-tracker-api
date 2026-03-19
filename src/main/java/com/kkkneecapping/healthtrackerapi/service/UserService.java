package com.kkkneecapping.healthtrackerapi.service;

import com.kkkneecapping.healthtrackerapi.entity.User;
import com.kkkneecapping.healthtrackerapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public void create(User user) {
    if (userRepository.findByUsername(user.getUsername()).isPresent()) {
      throw new IllegalStateException("User already exists");
    }
    // TODO
    user.setStatus(User.UserStatus.ACTIVE);
    userRepository.save(user);
  }
}
