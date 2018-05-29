package com.clinica.domain;

import javax.persistence.Entity;

@Entity
@SuppressWarnings("serial")
public class Exame extends GenericDomain {

	private String nome;
	private String ativo;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String status) {
		this.ativo = status;
	}

	public static String[] getFilters(){
		return new String[]{"nome","ativo"};
	}
}
