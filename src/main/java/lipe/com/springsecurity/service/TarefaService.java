package lipe.com.springsecurity.service;

import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.threads.TaskThreadFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lipe.com.springsecurity.dto.TarefaDTO;
import lipe.com.springsecurity.model.Tarefa;
import lipe.com.springsecurity.model.Usuario;
import lipe.com.springsecurity.repository.TarefaRepository;
import lipe.com.springsecurity.repository.UsuarioRepository;

@Service
public class TarefaService {

  private final TarefaRepository tarefaRepository;
  private final UsuarioRepository usuarioRepository;
  private final AuthService authService;

  public TarefaService(TarefaRepository tarefaRepository, UsuarioRepository usuarioRepository,
      AuthService authService) {
    this.tarefaRepository = tarefaRepository;
    this.usuarioRepository = usuarioRepository;
    this.authService = authService;

  }

  public Tarefa criTarefa(TarefaDTO dto) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    UserDetails userDetails = authService.loadUserByUsername(username);

    if (userDetails == null) {
      throw new RuntimeException("nao autenticado");

    }

    Long userId = usuarioRepository.findByUsername(userDetails.getUsername()).get().getId();

    Tarefa tarefa = new Tarefa();
    tarefa.setTitulo(dto.getTitulo());
    tarefa.setDescricao(dto.getDescrisao());
    tarefa.setStatus(dto.getStatus());
    tarefa.setUserId(userId);

    return tarefaRepository.save(tarefa);

  }

  public List<Tarefa> listarTarefasAutenticado() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    UserDetails userDetails = authService.loadUserByUsername(username);

    if (userDetails == null) {
      throw new RuntimeException("Usuario nao autenticado");
    }

    Long userId = usuarioRepository.findByUsername(userDetails.getUsername()).get().getId();
    return tarefaRepository.findByUserId(userId);
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
