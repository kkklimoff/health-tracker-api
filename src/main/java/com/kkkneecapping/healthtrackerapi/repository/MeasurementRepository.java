package com.kkkneecapping.healthtrackerapi.repository;

import com.kkkneecapping.healthtrackerapi.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {}
