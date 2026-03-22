package com.kkkneecapping.healthtrackerapi.service;

import com.kkkneecapping.healthtrackerapi.entity.User;
import com.kkkneecapping.healthtrackerapi.exception.EmailDuplicationException;
import com.kkkneecapping.healthtrackerapi.exception.UsernameDuplicationException;
import com.kkkneecapping.healthtrackerapi.repository.UserRepository;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Transactional
  public void create(User user) {
    if (userRepository.existsByEmail(user.getEmail())) {
      throw new EmailDuplicationException();
    }
    if (userRepository.existsByUsername(user.getUsername())) {
      throw new UsernameDuplicationException();
    }
    // TODO
    user.setStatus(User.UserStatus.ACTIVE);
    userRepository.save(user);
  }

  @Transactional
  public void updateLastLogin(User user) {
    user.setLastLoginAt(OffsetDateTime.now());
    userRepository.save(user);
  }
}
