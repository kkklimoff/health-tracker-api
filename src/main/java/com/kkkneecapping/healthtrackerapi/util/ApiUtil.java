package com.kkkneecapping.healthtrackerapi.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiUtil {

  private ApiUtil() {}

  public static ResponseEntity<Void> responseOk() {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  public static <T> ResponseEntity<T> responseOk(T body) {
    return new ResponseEntity<>(body, HttpStatus.OK);
  }
}
