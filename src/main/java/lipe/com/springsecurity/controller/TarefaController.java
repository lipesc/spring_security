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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lipe.com.springsecurity.dto.TarefaDTO;
import lipe.com.springsecurity.model.Tarefa;
import lipe.com.springsecurity.model.Usuario;
import lipe.com.springsecurity.repository.UsuarioRepository;
import lipe.com.springsecurity.service.AuthService;
import lipe.com.springsecurity.service.TarefaService;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import io.swagger.v3.oas.annotations.Operation;

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
  
  @Operation(summary = "Criar Tarefa endpoint, precisa do token gerado no login", security = @SecurityRequirement(name = "bearerAuth"))
  @PostMapping
  public ResponseEntity<Tarefa> criarTarefa(@Validated @RequestBody TarefaDTO dto) {
    try {
      Tarefa tarefa = tarefaService.criTarefa(dto);
      return ResponseEntity.status(HttpStatus.CREATED).body(tarefa);
    } catch (RuntimeException e) {
      // TODO: handle exception
      logger.warn(e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }
  @Operation(summary = "Listar Tarefas endpoint, precisa do token gerado no login", security = @SecurityRequirement(name = "bearerAuth"))
  @GetMapping
  public ResponseEntity<List<Tarefa>> listarTarefas() {
    try {
      List<Tarefa> tarefas = tarefaService.listarTarefasAutenticado();
      return ResponseEntity.ok(tarefas);
    } catch (RuntimeException e) {
      logger.warn(e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

}