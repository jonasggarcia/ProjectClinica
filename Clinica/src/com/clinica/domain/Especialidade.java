package com.clinica.domain;

import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class Especialidade extends GenericDomain {

	private String descricao;

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public static String[] getFilters() {
		return new String[] { "descricao" };
	}
}
