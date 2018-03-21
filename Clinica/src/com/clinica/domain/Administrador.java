package com.clinica.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class Administrador extends GenericDomain {

	private String nome;

	private String cpf;

	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	private String telefone;

	private String celular;

	private String endereco;

	private String nomeUsuario;

	private String senha;

	private Character tipo;

	private String ativo;

	private String tipoTexto;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Character getTipo() {
		return tipo;
	}

	public void setTipo(Character tipo) {
		if(tipo=='1'){
			this.tipoTexto = "Master";
		}else if(tipo=='2'){
			this.tipoTexto = "Geral";
		}else if(tipo=='3'){
			this.tipoTexto = "Atendente";
		}else{
			this.tipoTexto = "Erro";
		}
		this.tipo = tipo;
	}

	public String getTipoTexto() {
		return tipoTexto;
	}

	public void setTipoTexto(String tipoTexto) {
		this.tipoTexto = tipoTexto;
	}

}
