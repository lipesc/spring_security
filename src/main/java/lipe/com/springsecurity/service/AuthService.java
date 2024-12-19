package lipe.com.springsecurity.service;

// import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lipe.com.springsecurity.model.Usuario;
import lipe.com.springsecurity.repository.UsuarioRepository;

@Service
public class AuthService implements UserDetailsService {
  private UsuarioRepository usuarioRepository;
  private PasswordEncoder passwordEncoder;

  public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
    this.usuarioRepository = usuarioRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario = usuarioRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("usuario não encontrado"));

    return User.builder()
        .username(usuario.getUsername())
        .password(usuario.getPassword())
        .roles(usuario.getRoles().toArray((new String[0])))
        .build();
  }

  public Usuario resgistrarUsuario(Usuario usuario) {

    if (usuarioRepository.findByUsername(usuario.getUsername()) != null) {
      throw new Error("Username ja cadastrado, escolha outro nome");
    }

    usuario.setUsername((usuario.getUsername()));
    usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

    if (usuario.getRoles() == null || usuario.getRoles().isEmpty()) {
      usuario.setRoles(List.of("USER"));
    }

    return usuarioRepository.save(usuario);
  }

  public String login(String username, String password) {
    Usuario usuario = usuarioRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("usuario não encontrado"));
    if (!passwordEncoder.matches(password, usuario.getPassword())) {
      throw new IllegalArgumentException("senha errada");
    }
    return genJWT(usuario.getUsername());
  }

  private String genJWT(String username) {
    String secret = System.getenv("JWT_SECRET");

    Algorithm algorithm = Algorithm.HMAC512(secret);
    return JWT.create()
        .withSubject(username)
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
        .withClaim("roles", "USER")
        .sign(algorithm);
  }

}
