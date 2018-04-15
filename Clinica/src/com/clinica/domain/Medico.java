package com.clinica.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class Medico extends GenericDomain {

	private String nome;

	@ManyToMany
	private List<Especialidade> especialidades;

	private String crm;

	private String telefone;

	private String celular;

	private String cidade;

	private String nomeUsuario;

	private String senha;

	private String ativo;

	@Transient
	private String especialidadesAux;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
		StringBuffer buf = new StringBuffer();
		for (Especialidade e : especialidades) {
			buf.append(e.getDescricao());
			buf.append(",");
		}
		setEspecialidadesAux(buf.toString().substring(0, buf.toString().length() - 1));
	}

	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public String getCrm() {
		return crm;
	}

	public void setCrm(String crm) {
		this.crm = crm;
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

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
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

	public void setEspecialidadesAux(String especialidadesAux) {
		this.especialidadesAux = especialidadesAux;
	}

	public String getEspecialidadesAux() {
		return especialidadesAux;
	}

	public static String[] getFilters() {
		return new String[] { "nome", "crm", "ativo" };
	}
}
