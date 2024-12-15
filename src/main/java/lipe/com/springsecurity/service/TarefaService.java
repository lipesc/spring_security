package lipe.com.springsecurity.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lipe.com.springsecurity.dto.TarefaDTO;
import lipe.com.springsecurity.model.Tarefa;
// import lipe.com.springsecurity.model.Tarefa.StatusTarefa;
import lipe.com.springsecurity.repository.TarefaRepository;

@Service
public class TarefaService {

  private final TarefaRepository tarefaRepository;

  public TarefaService(TarefaRepository tarefaRepository) {
    this.tarefaRepository = tarefaRepository;
  }

  public Tarefa criTarefa(TarefaDTO dto) {

    Tarefa tarefa = new Tarefa();
    tarefa.setTitulo(dto.getTitulo());
    tarefa.setDescricao(dto.getDescrisao());
    tarefa.setStatus(dto.getStatus());

    return tarefaRepository.save(tarefa);

  }

  public List<Tarefa> listarTarefas() {

    List<Tarefa> tarefas = tarefaRepository.findAll();

    return tarefas;

  }

  public Tarefa atualizarStatus(Long id, TarefaDTO updateStatus) {

    Tarefa status = tarefaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Id não encontrato"));

    status.setStatus(updateStatus.getStatus());

    return tarefaRepository.save(status);
  }

  public void deletarTarefa(Long id, TarefaDTO dtoDeletar) {

    tarefaRepository.deleteById(id);

  }
}
