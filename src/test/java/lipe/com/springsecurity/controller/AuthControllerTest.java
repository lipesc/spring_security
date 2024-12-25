package lipe.com.springsecurity.controller;

import lipe.com.springsecurity.dto.LoginUsers;
import lipe.com.springsecurity.model.Usuario;
import lipe.com.springsecurity.service.AuthService;
import lipe.com.springsecurity.exception.CustomServetRegistrar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class AuthControllerTest {

  @Mock
  private AuthService authService;

  @InjectMocks
  private AuthController authController;

  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(authController)
        .setControllerAdvice(new AuthControllerAdvice())
        .build();
  }

  @Test
  public void testRegistrarUsuario_UsernameAlreadyExists() throws Exception {
    String message = "Username ja cadastrado, escolha outro nome.";
    Usuario usuario = new Usuario();
    usuario.setUsername("existingUser");

    doThrow(new CustomServetRegistrar(message))
        .when(authService).resgistrarUsuario(any(Usuario.class));

    mockMvc.perform(post("/auth/registrar")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"username\": \"existingUser\", \"password\": \"password\"}"))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.message").value(message));
  }

  @Test
  public void testLogin_Success() throws Exception {
    String token = "fake-jwt-token";
    LoginUsers loginUsers = new LoginUsers("user", "password");

    when(authService.login(loginUsers.getUsername(), loginUsers.getPassword())).thenReturn(token);

    mockMvc.perform(post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"username\": \"user\", \"password\": \"password\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value(token));
  }

}