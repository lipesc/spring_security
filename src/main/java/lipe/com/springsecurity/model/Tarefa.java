package lipe.com.springsecurity.model;

import java.time.LocalDateTime;

// import org.antlr.v4.runtime.misc.NotNull;
// import org.springframework.lang.NonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Tarefa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String titulo;

  private String descricao;

  @Enumerated(EnumType.STRING)
  private StatusTarefa status = StatusTarefa.Pendente;

  @Column(name = "data_criacao", nullable = false, updatable = false)
  private LocalDateTime dateCriacao;

  protected void onCreate() {
    this.dateCriacao = LocalDateTime.now();
  }

  public Tarefa() {
  }

  public Tarefa(Long id, String titulo, String descricao, StatusTarefa status, LocalDateTime dateCriacao) {
    this.id = id;
    this.titulo = titulo;
    this.descricao = descricao;
    this.status = status;
    this.dateCriacao = dateCriacao;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public void setStatus(StatusTarefa status) {
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public String getTitulo() {
    return titulo;
  }

  public String getDescricao() {
    return descricao;
  }

  public StatusTarefa getStatus() {
    return status;
  }

  public LocalDateTime getDateCriacao() {
    return dateCriacao;
  }

  public enum StatusTarefa {
    Pendente,
    em_andamento,
    concluida
  }
}
