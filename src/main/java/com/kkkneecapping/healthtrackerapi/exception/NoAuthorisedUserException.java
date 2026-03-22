package com.kkkneecapping.healthtrackerapi.exception;

import org.springframework.http.HttpStatus;

public class NoAuthorisedUserException extends ApiException {
  public NoAuthorisedUserException() {
    super("No authorised user for this request", ErrorCode.NOT_AUTHORISED, HttpStatus.UNAUTHORIZED);
  }
}
