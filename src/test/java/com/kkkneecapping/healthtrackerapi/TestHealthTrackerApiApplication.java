package com.kkkneecapping.healthtrackerapi;

import org.springframework.boot.SpringApplication;

public class TestHealthTrackerApiApplication {

  static void main(String[] args) {
    SpringApplication.from(HealthTrackerApiApplication::main)
        .with(TestcontainersConfiguration.class)
        .run(args);
  }
}
