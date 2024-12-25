package lipe.com.springsecurity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lipe.com.springsecurity.dto.TarefaDTO;
import lipe.com.springsecurity.model.Tarefa;
import lipe.com.springsecurity.model.Usuario;
import lipe.com.springsecurity.repository.UsuarioRepository;
import lipe.com.springsecurity.service.AuthService;
import lipe.com.springsecurity.service.TarefaService;

import java.util.Optional;

@RestController
@RequestMapping("/auth/tarefas")
public class TarefaController {

  private static final Logger logger = LoggerFactory.getLogger(TarefaController.class);

  private final TarefaService tarefaService;
  private final UsuarioRepository usuarioRepository;
  private final AuthService authService;

  public TarefaController(TarefaService tarefaService, UsuarioRepository usuarioRepository, AuthService authService) {
    this.tarefaService = tarefaService;
    this.usuarioRepository = usuarioRepository;
    this.authService = authService;
  }

  @PostMapping
  public ResponseEntity<Tarefa> criarTarefa(@Validated @RequestBody TarefaDTO dto,
      @AuthenticationPrincipal UserDetails userDetails) {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    userDetails = authService.loadUserByUsername(username);

    if (userDetails == null) {
      logger.warn("Usuario nao autenticado");
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    logger.info("Usuario autenticado: " + userDetails.getUsername());

    Long usersId = usuarioRepository.findByUsername(userDetails.getUsername()).get().getId();

    Tarefa tarefa = tarefaService.criTarefa(dto, usersId);

    return ResponseEntity.status(HttpStatus.CREATED).body(tarefa);
  }

}