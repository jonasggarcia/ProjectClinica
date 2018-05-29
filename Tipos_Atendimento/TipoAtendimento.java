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
		return new String[] { "descricao", "ativo" };
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format("%s", this.getDescricao());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericDomain other = (GenericDomain) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
}
