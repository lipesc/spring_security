package lipe.com.springsecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lipe.com.springsecurity.dto.JwtResponse;
import lipe.com.springsecurity.dto.LoginUsers;
import lipe.com.springsecurity.model.Usuario;
import lipe.com.springsecurity.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/registrar")
  public ResponseEntity<Usuario> registrar(@RequestBody Usuario novoUsuario) {
    authService.resgistrarUsuario(novoUsuario);

    return ResponseEntity.ok(novoUsuario);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginUsers loginUsers) {
    String token = authService.login(loginUsers.getUsername(), loginUsers.getPassword());
    return ResponseEntity.ok(new JwtResponse(token));
  }
}
