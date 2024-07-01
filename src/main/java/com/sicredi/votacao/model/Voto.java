package com.sicredi.votacao.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Voto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Sessao sessao;

	private Long associadoId;
	private Boolean voto; 
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Sessao getSessao() {
		return sessao;
	}
	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}
	public Long getAssociadoId() {
		return associadoId;
	}
	public void setAssociadoId(Long associadoId) {
		this.associadoId = associadoId;
	}
	public Boolean getVoto() {
		return voto;
	}
	public void setVoto(Boolean voto) {
		this.voto = voto;
	}

    
}
