package com.openwebinars.rest.error;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalControllerAdvice {
  @ExceptionHandler(ProductoNotFoundException.class)
  public ResponseEntity<ApiError> handleProductoNoEncontrado(ProductoNotFoundException ex) {
    ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
  }

  @ExceptionHandler(JsonMappingException.class)
  public ResponseEntity<ApiError> handleJsonMappingException(JsonMappingException ex) {
    ApiError apiError = new ApiError();
    apiError.setEstado(HttpStatus.BAD_REQUEST);
    apiError.setFecha(LocalDateTime.now());
    apiError.setMensaje(ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
  }
}
