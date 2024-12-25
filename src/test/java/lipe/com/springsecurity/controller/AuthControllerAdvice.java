package lipe.com.springsecurity.controller;

import lipe.com.springsecurity.exception.CustomServetLogin;
import lipe.com.springsecurity.exception.CustomServetRegistrar;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AuthControllerAdvice {

  @ExceptionHandler(CustomServetRegistrar.class)
  public ResponseEntity<Map<String, String>> handleErrorRegistrar(CustomServetRegistrar e) {
    Map<String, String> response = new HashMap<>();
    response.put("message", e.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
  }

  @ExceptionHandler(CustomServetLogin.class)
  public ResponseEntity<Map<String, String>> handlerErrorLogin(CustomServetLogin e) {
    Map<String, String> response = new HashMap<>();
    response.put("message", e.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
  }
}