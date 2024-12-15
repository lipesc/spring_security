package lipe.com.springsecurity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lipe.com.springsecurity.model.Tarefa;
import lipe.com.springsecurity.model.Tarefa.StatusTarefa;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
  List<Tarefa> findByStatus(StatusTarefa status);
}
