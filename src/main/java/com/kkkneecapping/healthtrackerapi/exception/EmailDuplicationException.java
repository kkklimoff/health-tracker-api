package com.kkkneecapping.healthtrackerapi.exception;

import org.springframework.http.HttpStatus;

public class EmailDuplicationException extends ApiException {
  public EmailDuplicationException() {
    super(
        "Provided email is already registered",
        ErrorCode.EMAIL_ALREADY_IN_USE,
        HttpStatus.CONFLICT);
  }
}
