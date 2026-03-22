package com.kkkneecapping.healthtrackerapi.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Table(name = "measurement_values")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeasurementValue {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Builder.Default
  @Column(name = "public_id", unique = true, nullable = false, updatable = false)
  private UUID publicId = UUID.randomUUID();

  @Column(nullable = false)
  @CreationTimestamp
  private OffsetDateTime createdAt;

  @Column(nullable = false)
  @UpdateTimestamp
  private OffsetDateTime updatedAt;

  @Column(nullable = false)
  private Float value;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private MeasurementAspect aspect;

  @ManyToOne
  @JoinColumn(name = "measurement_id", nullable = false)
  private Measurement measurement;

  @Getter
  public enum MeasurementAspect {
    SYSTOLIC(Measurement.MeasurementType.CARDIO, Unit.NUMERIC),
    DIASTOLIC(Measurement.MeasurementType.CARDIO, Unit.NUMERIC),
    HEART_RATE(Measurement.MeasurementType.CARDIO, Unit.BPM),
    OXYGEN(Measurement.MeasurementType.CARDIO, Unit.PERCENT),
    INTENSITY(Measurement.MeasurementType.HEADACHE, Unit.BASE10),
    DURATION(Measurement.MeasurementType.HEADACHE, Unit.HOURS),
    ;

    private final Measurement.MeasurementType parentType;
    private final Unit unit;

    MeasurementAspect(Measurement.MeasurementType parentType, Unit unit) {
      this.parentType = parentType;
      this.unit = unit;
    }
  }

  public enum Unit {
    NUMERIC,
    BASE10,
    HOURS,
    PERCENT,
    BPM
  }
}
