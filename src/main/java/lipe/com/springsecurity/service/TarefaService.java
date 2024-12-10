package lipe.com.springsecurity.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lipe.com.springsecurity.dto.TarefaDTO;
import lipe.com.springsecurity.model.Tarefa;
// import lipe.com.springsecurity.model.Tarefa.StatusTarefa;
import lipe.com.springsecurity.repository.TarefaRepository;

@Service
public class TarefaService {

  private final TarefaRepository tarefaRepository;

  private static final Logger logger = LoggerFactory.getLogger(TarefaService.class);

  public TarefaService(TarefaRepository tarefaRepository) {
    this.tarefaRepository = tarefaRepository;
  }

  public Tarefa criTarefa(TarefaDTO dto) {
    try {
      logger.info("Criando tarefa: -> {}", dto.getTitulo());

      Tarefa tarefa = new Tarefa();
      tarefa.setTitulo(dto.getTitulo());
      tarefa.setDescricao(dto.getDescrisao());
      tarefa.setStatus(dto.getStatus());

      logger.debug("*** tarefa criada: ID -> {}", dto.getId());

      return tarefaRepository.save(tarefa);

    } catch (Exception e) {
      // TODO: handle exception
      logger.error("*** -> Erro ao criar tarefa", e);
      throw e;
    }

  }

  public List<Tarefa> listarTarefas() {
    try {
      logger.info("Buscando tarefas");

      List<Tarefa> tarefas = tarefaRepository.findAll();
      logger.debug("*** -> tarefas listadas");

      return tarefas;

    } catch (Exception e) {
      logger.error("*** Erro tarefa não encontrada", e);
      throw e;
    }
  }

  public Tarefa atualizarStatus(Long id, TarefaDTO updateStatus) {
    try {
      logger.info("Atualizar status da tarefa: -> {}", updateStatus.getStatus());

      Tarefa status = tarefaRepository.findById(id)
          .orElseThrow(() -> new RuntimeException("Id não encontrato"));

      status.setStatus(updateStatus.getStatus());

      return tarefaRepository.save(status);

    } catch (Exception e) {
      // TODO: handle exception
      logger.error("*** Erro tarefa não encontrada", e);
      throw e;
    }
  }

  public void deletarTarefa(Long id, TarefaDTO dtoDeletar) {

    try {
      // Tarefa tarefa = tarefaRepository.findById(id)
      //     .orElseThrow(() -> new RuntimeException("Tarefa não encontrato"));

      logger.info("*** Deletar tarefa -> ID: {}, Titulo: -> {}", id, dtoDeletar.getTitulo());

      tarefaRepository.deleteById(id);

      logger.debug("*** trarefa deletada ID: ->{}", id);

    } catch (Exception e) {
      // TODO: handle exception
      logger.error("*** Erro tarefa não encontrada", e);
      throw e;
    }
  }
}
