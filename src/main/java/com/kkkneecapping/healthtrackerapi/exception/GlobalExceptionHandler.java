package com.kkkneecapping.healthtrackerapi.exception;

import com.kkkneecapping.healthtrackerapi.dto.ApiErrorResponse;
import com.kkkneecapping.healthtrackerapi.dto.ApiSubError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.metadata.ConstraintDescriptor;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler
  public ResponseEntity<ApiErrorResponse> handleApiException(ApiException e) {
    return ResponseEntity.status(e.getStatus())
        .body(getApiErrorResponse(e.getErrorCode(), e.getMessage()));
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status,
      @NonNull WebRequest request) {

    List<ApiSubError> subErrors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(this::mapFieldErrorToSubError)
            .collect(Collectors.toList());

    ApiErrorResponse response =
        ApiErrorResponse.builder()
            .code(ErrorCode.VALIDATION_FAILED.toString())
            .message("Invalid request parameters")
            .timestamp(OffsetDateTime.now())
            .details(subErrors)
            .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler
  ResponseEntity<ApiErrorResponse> handle(Exception e) {
    log.error("Unhandled exception", e);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(getApiErrorResponse(ErrorCode.INTERNAL_ERROR, "An unexpected error occurred"));
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      @NonNull Exception ex,
      Object body,
      @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode statusCode,
      @NonNull WebRequest request) {

    if (body instanceof ApiErrorResponse) {
      return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    String errorCode =
        ex.getClass()
            .getSimpleName()
            .replaceAll("Exception$", "")
            .replaceAll("([a-z])([A-Z]+)", "$1_$2")
            .toUpperCase();

    ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
    apiErrorResponse.setMessage(ex.getMessage());
    apiErrorResponse.setCode(errorCode);

    return super.handleExceptionInternal(ex, apiErrorResponse, headers, statusCode, request);
  }

  // ТА САМАЯ МАГИЯ РАСПАКОВКИ АННОТАЦИИ
  private ApiSubError mapFieldErrorToSubError(FieldError fieldError) {
    String validationCode = ErrorCode.INVALID_VALUE.toString();
    Map<String, Object> params = new HashMap<>();

    try {
      ConstraintViolation<?> violation = fieldError.unwrap(ConstraintViolation.class);
      ConstraintDescriptor<?> descriptor = violation.getConstraintDescriptor();

      // Получаем имя аннотации (например: "Size", "NotBlank", "Email")
      String annotationName = descriptor.getAnnotation().annotationType().getSimpleName();

      // Превращаем CamelCase в UPPER_SNAKE_CASE (Size -> SIZE, NotBlank -> NOT_BLANK)
      validationCode = annotationName.replaceAll("([a-z])([A-Z]+)", "$1_$2").toUpperCase();

      // Вытаскиваем все параметры аннотации (min, max, regexp и т.д.)
      Map<String, Object> attributes = descriptor.getAttributes();
      attributes.forEach(
          (key, value) -> {
            // Игнорируем системные поля аннотаций
            if (!List.of("message", "groups", "payload").contains(key)) {
              params.put(key, value);
            }
          });
    } catch (Exception e) {
      log.warn("Could not unwrap constraint violation for field: {}", fieldError.getField());
    }

    return ApiSubError.builder()
        .field(fieldError.getField())
        .code(validationCode)
        .message(fieldError.getDefaultMessage())
        .params(params.isEmpty() ? null : params)
        .build();
  }

  @NonNull
  private static ApiErrorResponse getApiErrorResponse(
      @NonNull ErrorCode errorCode, @Nullable String message) {
    return ApiErrorResponse.builder()
        .timestamp(OffsetDateTime.now())
        .code(errorCode.toString())
        .message(message)
        .build();
  }
}
