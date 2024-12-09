package lipe.com.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lipe.com.springsecurity.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
  List<Tarefa> findByStatus(StatusTarefa status);

  List<Tarefa> findByUsuario(Usuario usuario);
}
