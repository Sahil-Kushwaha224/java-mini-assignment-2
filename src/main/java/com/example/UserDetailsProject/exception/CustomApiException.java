package com.example.UserDetailsProject.exception;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class CustomApiException extends RuntimeException {

  private final String message;
  private final HttpStatus status;
  private final LocalDateTime timestamp;

  public CustomApiException(String message, HttpStatus status) {
    this.message = message;
    this.status = status;
    this.timestamp = LocalDateTime.now();
  }
  public HttpStatus getStatus() {
    return status;
  }
}
