package lipe.com.springsecurity.dto;

import lipe.com.springsecurity.model.Tarefa.StatusTarefa;

public class TarefaDTO {
  private Long id;
  private String titulo;
  private String descrisao;
  private StatusTarefa status;

  public TarefaDTO(Long id, String titulo, String descrisao, StatusTarefa status) {
    this.id = id;
    this.titulo = titulo;
    this.descrisao = descrisao;
    this.status = status;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getDescrisao() {
    return descrisao;
  }

  public void setDescrisao(String descrisao) {
    this.descrisao = descrisao;
  }

  public StatusTarefa getStatus() {
    return status;
  }

  public void setStatus(StatusTarefa status) {
    this.status = status;
  }

  public Long getId() {
    return id;
  }

}
