package com.openwebinars.rest.error;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class ApiErrorAttributes extends DefaultErrorAttributes {
  @Override
  public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
    Map<String, Object> allErrorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
    Map<String, Object> errorAttributes = new HashMap<>();

    int statusCode = (int) allErrorAttributes.get("status");
    errorAttributes.put("estato", HttpStatus.valueOf(statusCode));
    errorAttributes.put("fecha", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

    String mensaje = "";
    Throwable throwable = getError(webRequest);

    if (throwable instanceof ResponseStatusException) {
      ResponseStatusException responseStatusException = (ResponseStatusException) throwable;
      mensaje = responseStatusException.getMessage() == null ? "" : responseStatusException.getReason();
    } else {
      if (throwable.getCause() != null) {
        mensaje = throwable.getCause().getMessage() == null ? "" : throwable.getCause().getMessage();
      }
    }
    errorAttributes.put("mensaje", mensaje);

    return errorAttributes;
  }
}
