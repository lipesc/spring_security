package lipe.com.springsecurity.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import lipe.com.springsecurity.model.Usuario;
import lipe.com.springsecurity.repository.UsuarioRepository;

@Service
public class AuthService implements UserDetailsService {

  private UsuarioRepository usuarioRepository;

  public AuthService(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  private PasswordEncoder passwordEncoder;

  public AuthService(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  private JwtDecoder jwtDecoder;

  public AuthService(JwtDecoder jwtDecoder) {
    this.jwtDecoder = jwtDecoder;
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

  public String login(String username, String password) {
    
    }
  

 
  /*
   * // Método para registrar novo usuário
   * public Usuario registrarNovoUsuario(Usuario usuario) {
   * // Criptografa senha antes de salvar
   * usuario.setPassword(
   * passwordEncoder.encode(usuario.getPassword())
   * );
   * return usuarioRepository.save(usuario);
   * }
   */
  public Usuario resgistrarUsuario(Usuario usuario) {
    usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
    return usuarioRepository.save(usuario);
  }

}
