package com.kkkneecapping.healthtrackerapi.api.controller;

import com.kkkneecapping.healthtrackerapi.api.TestApi;
import com.kkkneecapping.healthtrackerapi.dto.TestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController implements TestApi {

  @Override
  public ResponseEntity<TestResponse> testIdGet(Integer id) {
    return ResponseEntity.ok(new TestResponse().message("Test response message"));
  }
}
