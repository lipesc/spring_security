package lipe.com.springsecurity.service;

import lipe.com.springsecurity.exception.CustomServetLogin;
import lipe.com.springsecurity.exception.CustomServetRegistrar;
import lipe.com.springsecurity.model.Usuario;
import lipe.com.springsecurity.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegistrarUsuario_UsernameAlreadyExists() {
        Usuario usuario = new Usuario();
        usuario.setUsername("existingUser");

        when(usuarioRepository.findByUsername("existingUser")).thenReturn(Optional.of(usuario));

        assertThrows(CustomServetRegistrar.class, () -> authService.resgistrarUsuario(usuario));

        verify(usuarioRepository, times(1)).findByUsername("existingUser");
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        when(usuarioRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authService.loadUserByUsername("nonExistentUser"));

        verify(usuarioRepository, times(1)).findByUsername("nonExistentUser");
    }

    @Test
    public void testRegistrarUsuario_Success() {
        Usuario usuario = new Usuario();
        usuario.setUsername("newUser");
        usuario.setPassword("password");

        when(usuarioRepository.findByUsername("newUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario result = authService.resgistrarUsuario(usuario);

        assertNotNull(result);
        assertEquals("newUser", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());

        verify(usuarioRepository, times(1)).findByUsername("newUser");
        verify(usuarioRepository, times(1)).save(usuario);
        verify(passwordEncoder, times(1)).encode("password");
    }
}