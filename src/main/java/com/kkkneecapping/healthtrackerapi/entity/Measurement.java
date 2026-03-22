package com.kkkneecapping.healthtrackerapi.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

@Table(name = "measurements")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Measurement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Builder.Default
  @Column(name = "public_id", unique = true, nullable = false, updatable = false)
  private UUID publicId = UUID.randomUUID();

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  private OffsetDateTime recordedAt;

  @Column(nullable = false)
  @CreationTimestamp
  private OffsetDateTime createdAt;

  @Column(nullable = false)
  @UpdateTimestamp
  private OffsetDateTime updatedAt;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(nullable = false, columnDefinition = "jsonb")
  private Map<String, Object> details;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  private MeasurementType type;

  @OneToMany(mappedBy = "measurement", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<MeasurementValue> values = new HashSet<>();

  public enum MeasurementType {
    CARDIO,
    HEADACHE
  }

  public void addValue(MeasurementValue measurementValue) {
    values.add(measurementValue);
    measurementValue.setMeasurement(this);
  }
}
