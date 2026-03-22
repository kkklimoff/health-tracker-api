package com.kkkneecapping.healthtrackerapi.controller;

import static com.kkkneecapping.healthtrackerapi.util.ApiUtil.getCurrentUser;
import static com.kkkneecapping.healthtrackerapi.util.ApiUtil.responseOk;

import com.kkkneecapping.healthtrackerapi.api.MeasurementApi;
import com.kkkneecapping.healthtrackerapi.dto.MeasurementWithValuesRequest;
import com.kkkneecapping.healthtrackerapi.dto.MeasurementWithValuesResponse;
import com.kkkneecapping.healthtrackerapi.entity.Measurement;
import com.kkkneecapping.healthtrackerapi.mapper.MeasurementMapper;
import com.kkkneecapping.healthtrackerapi.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MeasurementController implements MeasurementApi {

  private final MeasurementMapper measurementMapper;
  private final MeasurementService measurementService;

  @Override
  public ResponseEntity<MeasurementWithValuesResponse> saveMeasurement(
      MeasurementWithValuesRequest measurementWithValuesRequest) {
    Measurement measurement = measurementMapper.fromDto(measurementWithValuesRequest);
    measurementService.create(measurement, getCurrentUser());
    return responseOk(measurementMapper.toDto(measurement));
  }
}
