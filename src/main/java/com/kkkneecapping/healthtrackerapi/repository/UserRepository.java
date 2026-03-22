package com.kkkneecapping.healthtrackerapi.repository;

import com.kkkneecapping.healthtrackerapi.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  @NonNull Optional<User> findByUsername(String username);

  @NonNull Optional<User> findByPublicId(UUID id);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);
}
