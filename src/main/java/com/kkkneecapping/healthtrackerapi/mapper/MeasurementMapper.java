package com.kkkneecapping.healthtrackerapi.mapper;

import com.kkkneecapping.healthtrackerapi.dto.MeasurementValueResponse;
import com.kkkneecapping.healthtrackerapi.dto.MeasurementWithValuesRequest;
import com.kkkneecapping.healthtrackerapi.dto.MeasurementWithValuesResponse;
import com.kkkneecapping.healthtrackerapi.entity.Measurement;
import com.kkkneecapping.healthtrackerapi.entity.MeasurementValue;
import org.springframework.stereotype.Component;

@Component
public class MeasurementMapper {
  public Measurement fromDto(MeasurementWithValuesRequest measurementWithValuesRequest) {
    Measurement measurement = new Measurement();
    measurement.setType(
        Measurement.MeasurementType.valueOf(measurementWithValuesRequest.getType()));
    measurement.setDetails(measurementWithValuesRequest.getDetails());
    measurement.setRecordedAt(measurementWithValuesRequest.getRecordedAt());
    measurementWithValuesRequest
        .getValues()
        .forEach(
            (key, value) -> {
              MeasurementValue measurementValue = new MeasurementValue();
              measurementValue.setAspect(MeasurementValue.MeasurementAspect.valueOf(key));
              measurementValue.setValue(value);
              measurement.addValue(measurementValue);
            });
    return measurement;
  }

  public MeasurementWithValuesResponse toDto(Measurement measurement) {
    MeasurementWithValuesResponse measurementWithValuesResponse =
        new MeasurementWithValuesResponse();
    measurementWithValuesResponse.setDetails(measurement.getDetails());
    measurementWithValuesResponse.setRecordedAt(measurement.getRecordedAt());
    measurementWithValuesResponse.setType(measurement.getType().toString());
    measurementWithValuesResponse.setId(measurement.getPublicId());
    measurement
        .getValues()
        .forEach(
            val -> {
              MeasurementValueResponse measurementValueResponse = new MeasurementValueResponse();
              measurementValueResponse.setAspect(val.getAspect().toString());
              measurementValueResponse.setValue(val.getValue());
              measurementValueResponse.setId(val.getPublicId());
              measurementValueResponse.setCreatedAt(val.getCreatedAt());
              measurementWithValuesResponse.getValues().add(measurementValueResponse);
            });
    return measurementWithValuesResponse;
  }
}
