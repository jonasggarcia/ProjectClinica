package com.clinica.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@SuppressWarnings("serial")
@Entity
public class Receita extends GenericDomain{

	@ManyToMany
	private List<Medicamento> medicamentos;

	public List<Medicamento> getMedicamentos() {
		return medicamentos;
	}

	public void setMedicamentos(List<Medicamento> medicamentos) {
		this.medicamentos = medicamentos;
	}
}
