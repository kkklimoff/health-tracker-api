package com.kkkneecapping.healthtrackerapi.exception;

import java.util.Optional;
import lombok.Getter;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

  @NonNull private final ErrorCode errorCode;
  @NonNull private final HttpStatus status;

  public ApiException(String message, @Nullable ErrorCode errorCode, @Nullable HttpStatus status) {
    super(message);
    this.errorCode = Optional.ofNullable(errorCode).orElse(ErrorCode.BAD_REQUEST);
    this.status = Optional.ofNullable(status).orElse(HttpStatus.BAD_REQUEST);
  }
}
