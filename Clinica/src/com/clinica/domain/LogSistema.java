package com.clinica.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class LogSistema extends GenericDomain{

	@ManyToOne
	private Administrador usuario;
	
	private String acao;
	
	private String descricao;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHora;

	public Administrador getUsuario() {
		return usuario;
	}

	public void setUsuario(Administrador usuario) {
		this.usuario = usuario;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}
	
	
}
