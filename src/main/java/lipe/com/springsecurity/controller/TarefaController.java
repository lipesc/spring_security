package lipe.com.springsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lipe.com.springsecurity.dto.TarefaDTO;
import lipe.com.springsecurity.model.Tarefa;
import lipe.com.springsecurity.service.TarefaService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

  private TarefaService tarefaService;

  public TarefaController(TarefaService tarefaService) {
    this.tarefaService = tarefaService;
  }

  @GetMapping
  public List<Tarefa> listarTarefas() {
    return tarefaService.listarTarefas();
  }

  @PostMapping
  public ResponseEntity<Tarefa> criarTarefa(@Validated @RequestBody TarefaDTO dto) {
    Tarefa tarefa = tarefaService.criTarefa(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(tarefa);
  }

}
