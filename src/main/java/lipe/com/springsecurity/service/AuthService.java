package lipe.com.springsecurity.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lipe.com.springsecurity.exception.CustomServetLogin;
import lipe.com.springsecurity.exception.CustomServetRegistrar;
import lipe.com.springsecurity.model.Usuario;
import lipe.com.springsecurity.repository.UsuarioRepository;

@Service
public class AuthService implements UserDetailsService {
    String secretKey = System.getenv("JWT_SECRET");
    // private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado"));

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRoles().toArray(new String[0]))
                .build();
    }

    public Usuario resgistrarUsuario(Usuario usuario) {
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            throw new CustomServetRegistrar("Username ja cadastrado, escolha outro nome.");
        }

        usuario.setUsername(usuario.getUsername());
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        if (usuario.getRoles() == null || usuario.getRoles().isEmpty()) {
            usuario.setRoles(List.of("USER"));
        }

        return usuarioRepository.save(usuario);
    }

    public String login(String username, String password) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new CustomServetLogin("Usuario nao encontrado"));
        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new CustomServetLogin("Senha errada");
        }

        return genJWT(usuario.getUsername(), usuario.getRoles().toArray(new String[0]));
    }

    private String genJWT(String username, String[] roles) {
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalStateException("secretKey env nao criada ou encontrada");
        }

        Algorithm algorithm = Algorithm.HMAC512(secretKey);
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
                .withClaim("roles", String.join(",", roles))
                .sign(algorithm);
    }
}