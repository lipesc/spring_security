package lipe.com.springsecurity.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lipe.com.springsecurity.dto.TarefaDTO;
import lipe.com.springsecurity.model.Tarefa;
import lipe.com.springsecurity.model.Usuario;
// import lipe.com.springsecurity.model.Tarefa.StatusTarefa;
import lipe.com.springsecurity.repository.TarefaRepository;
import lipe.com.springsecurity.repository.UsuarioRepository;

@Service
public class TarefaService {

  private final TarefaRepository tarefaRepository;
  private final UsuarioRepository usuarioRepository;

  public TarefaService(TarefaRepository tarefaRepository, UsuarioRepository usuarioRepository) {
    this.tarefaRepository = tarefaRepository;
    this.usuarioRepository = usuarioRepository;
  }

  public Tarefa criTarefa(TarefaDTO dto, Long userId) {

    Tarefa tarefa = new Tarefa();
    tarefa.setTitulo(dto.getTitulo());
    tarefa.setDescricao(dto.getDescrisao());
    tarefa.setStatus(dto.getStatus());
    tarefa.setUserId(userId);

    return tarefaRepository.save(tarefa);

  }

  public List<Tarefa> listarTarefas(String userName) {

    Usuario usuario = usuarioRepository.findByUsername(userName).orElseThrow();

    return tarefaRepository.findByUserId(usuario.getId());

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
