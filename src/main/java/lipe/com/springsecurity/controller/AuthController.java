package lipe.com.springsecurity.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.ServletException;
import lipe.com.springsecurity.dto.JwtResponse;
import lipe.com.springsecurity.dto.LoginUsers;
import lipe.com.springsecurity.exception.CustomServetLogin;
import lipe.com.springsecurity.exception.CustomServetRegistrar;
import lipe.com.springsecurity.model.Usuario;
import lipe.com.springsecurity.service.AuthService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/registrar")
  public ResponseEntity<Usuario> registrar(@RequestBody Usuario novoUsuario) throws IOException, ServletException {
    authService.resgistrarUsuario(novoUsuario);
    return ResponseEntity.ok(novoUsuario);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginUsers loginUsers) {
    String token = authService.login(loginUsers.getUsername(), loginUsers.getPassword());
    return ResponseEntity.ok(new JwtResponse(token));
  }

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