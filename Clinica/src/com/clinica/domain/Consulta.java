package com.clinica.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class Consulta extends GenericDomain{
	
	@ManyToOne
	private Agendamento agendamento;
	
	@Column(length=5000)
	private String observacoes;
	
	@ManyToMany
	private List<Exame> exames;
	
	@ManyToOne
	private Receita receituario;
	
	@Column(length=5000)
	private String diagnostico;

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public List<Exame> getExames() {
		return exames;
	}

	public void setExames(List<Exame> exames) {
		this.exames = exames;
	}

	public Receita getReceituario() {
		return receituario;
	}

	public void setReceituario(Receita receituario) {
		this.receituario = receituario;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}
}
