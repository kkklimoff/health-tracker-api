package com.kkkneecapping.healthtrackerapi.exception;

import org.springframework.http.HttpStatus;

public class UsernameDuplicationException extends ApiException {
  public UsernameDuplicationException() {
    super(
        "Provided username is already registered",
        ErrorCode.USERNAME_ALREADY_IN_USE,
        HttpStatus.CONFLICT);
  }
}
