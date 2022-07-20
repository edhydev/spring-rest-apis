package com.openwebinars.rest.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductoNotFoundException extends RuntimeException {
  public ProductoNotFoundException(long id) {
    super("No se pudo encontrar el producto con la ID: " + id);
  }
}
