package com.kkkneecapping.healthtrackerapi.service;

import com.kkkneecapping.healthtrackerapi.entity.Measurement;
import com.kkkneecapping.healthtrackerapi.entity.User;
import com.kkkneecapping.healthtrackerapi.repository.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MeasurementService {

  private final MeasurementRepository measurementRepository;

  @Transactional
  public void create(Measurement measurement, User user) {
    measurement.setUserId(user.getId());
    measurementRepository.save(measurement);
  }
}
