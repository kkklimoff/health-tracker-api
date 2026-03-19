package com.kkkneecapping.healthtrackerapi.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Table(name = "users")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Builder.Default
  @Column(name = "public_id", unique = true, nullable = false, updatable = false)
  private UUID publicId = UUID.randomUUID();

  @Column(unique = true, nullable = false, length = 50)
  private String username;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String passwordHash;

  @Column(length = 100, nullable = false)
  private String firstName;

  @Column(length = 100)
  private String lastName;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 50)
  @Builder.Default
  private UserStatus status = UserStatus.PENDING;

  @Column(nullable = false)
  @CreationTimestamp
  private OffsetDateTime createdAt;

  @Column(nullable = false)
  @UpdateTimestamp
  private OffsetDateTime updatedAt;

  @Column private OffsetDateTime lastLoginAt;

  public enum UserStatus {
    PENDING,
    ACTIVE,
    BLOCKED
  }
}
