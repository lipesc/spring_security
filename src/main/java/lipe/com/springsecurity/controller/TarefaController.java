package lipe.com.springsecurity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lipe.com.springsecurity.dto.TarefaDTO;
import lipe.com.springsecurity.model.Tarefa;
import lipe.com.springsecurity.model.Usuario;
import lipe.com.springsecurity.repository.UsuarioRepository;
import lipe.com.springsecurity.service.TarefaService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@RestController
public class TarefaController {

  private final TarefaService tarefaService;
  private final UsuarioRepository usuarioRepository;

  public TarefaController(TarefaService tarefaService, UsuarioRepository usuarioRepository) {
    this.tarefaService = tarefaService;
    this.usuarioRepository = usuarioRepository;
  }

  @PostMapping("/tarefas")
  public ResponseEntity<Tarefa> criarTarefa(@Validated @RequestBody TarefaDTO dto,
      @AuthenticationPrincipal UserDetails userDetails) {
    if (userDetails == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    Optional<Usuario> userOp = usuarioRepository.findByUsername(userDetails.getUsername());
    if (!userOp.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    Long userId = userOp.get().getId();
    Tarefa tarefa = tarefaService.criTarefa(dto, userId);

    return ResponseEntity.status(HttpStatus.CREATED).body(tarefa);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  }
}