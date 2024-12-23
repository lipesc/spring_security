package lipe.com.springsecurity.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

  private final UsuarioRepository usuarioRepository;
  private final PasswordEncoder passwordEncoder;

  public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
    this.usuarioRepository = usuarioRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario = usuarioRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));

    return User.builder()
        .username(usuario.getUsername())
        .password(usuario.getPassword())
        .roles(usuario.getRoles().toArray(new String[0]))
        .build();
  }

  public Usuario resgistrarUsuario(Usuario usuario) {
    Optional<Usuario> existingUser = usuarioRepository.findByUsername(usuario.getUsername());
    if (existingUser.isPresent()) {
      throw new IllegalArgumentException("Username já cadastrado, escolha outro nome");
    }

    usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

    if (usuario.getRoles() == null || usuario.getRoles().isEmpty()) {
      usuario.setRoles(List.of("USER"));
    }

    return usuarioRepository.save(usuario);
  }

  public String login(String username, String password) {
    Usuario usuario = usuarioRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));
    if (!passwordEncoder.matches(password, usuario.getPassword())) {
      throw new IllegalArgumentException("Senha errada");
    }
    return genJWT(usuario.getUsername(), usuario.getRoles().toArray(new String[0]));
  }

  private String genJWT(String username, String[] roles) {
    String secret = System.getenv("JWT_SECRET");
    if (secret == null || secret.isEmpty()) {
      throw new IllegalStateException("JWT_SECRET env nao criada");
    }

    Algorithm algorithm = Algorithm.HMAC512(secret);
    return JWT.create()
        .withSubject(username)
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + 3600000)) // 1 hour expiry
        .withArrayClaim("roles", roles)
        .sign(algorithm);
  }
}