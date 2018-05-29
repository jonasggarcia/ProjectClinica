package com.clinica.domain;

import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class Medicamento extends GenericDomain{
	
	private String nome;
	
	private String tipoUso;
	
	private String observacao;
	
	private String ativo;
	
	
	
	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getTipoUso() {
		return tipoUso;
	}



	public void setTipoUso(String tipoUso) {
		this.tipoUso = tipoUso;
	}



	public String getObservacao() {
		return observacao;
	}



	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}



	public String getAtivo() {
		return ativo;
	}



	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}



	public static String[] getFilters(){
		return new String[]{"id","nome","tipoUso","observacao"};
	}
}
