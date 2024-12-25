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
import jakarta.persistence.PrePersist;

@Entity
public class Tarefa {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  private String titulo;

  private String descricao;

  @Enumerated(EnumType.STRING)
  private StatusTarefa status = StatusTarefa.Pendente;

  @Column(name = "data_criacao", nullable = false, updatable = false)
  private LocalDateTime date_criacao;

  @PrePersist
  protected void onCreate() {
    this.date_criacao = LocalDateTime.now();
  }

  public Tarefa() {
  }

  public Tarefa(Long id, Long userId, String titulo, String descricao, StatusTarefa status,
      LocalDateTime date_criacao) {
    this.id = id;
    this.userId = userId;
    this.titulo = titulo;
    this.descricao = descricao;
    this.status = status;
    this.date_criacao = date_criacao;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public StatusTarefa getStatus() {
    return status;
  }

  public void setStatus(StatusTarefa status) {
    this.status = status;
  }

  public LocalDateTime getDate_criacao() {
    return date_criacao;
  }

  public void setDate_criacao(LocalDateTime date_criacao) {
    this.date_criacao = date_criacao;
  }

  public enum StatusTarefa {
    Pendente,
    em_andamento,
    concluida
  }
}
