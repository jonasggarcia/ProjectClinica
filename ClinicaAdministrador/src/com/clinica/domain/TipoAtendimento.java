package com.clinica.domain;

import javax.persistence.Entity;


@SuppressWarnings("serial")
@Entity
public class TipoAtendimento extends GenericDomain {

	private String descricao;
	
	private String ativo;

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}
	
	public String getAtivo() {
		return ativo;
	}

	public static String[] getFilters() {
		return new String[] { "id", "descricao","ativo" };
	}
}
