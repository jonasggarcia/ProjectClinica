package com.clinica.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


import com.clinica.dao.MedicamentoDAO;
import com.clinica.domain.Medicamento;

@ManagedBean(name="MBMedicamento")
@ViewScoped
public class MedicamentoBean {
	
	private List<Medicamento> lista;
	
	private Medicamento medicamento;
	
	private MedicamentoDAO dao;

	@PostConstruct
	public void iniciar(){
		dao = new MedicamentoDAO();
		lista = dao.loadAllSimple();
	}
	
	public void novo(){
		medicamento = new Medicamento();
	}
	
	public void salvar(){
		medicamento.setAtivo("Sim");
		dao.salvar(medicamento, true);
		novo();
		lista = dao.loadAllSimple();
	}
	
	public void editar(){
		dao.editar(medicamento, true);
		lista = dao.loadAllSimple();
	}
	
	public void remover(){
		dao.deletar(medicamento.getId(), true);
		lista = dao.loadAllSimple();
	}
	
	public void onSelect(Medicamento med, String typeOfSelection, String indexes) {
		this.medicamento = med;
	}

	public List<Medicamento> getLista() {
		return lista;
	}

	public void setLista(List<Medicamento> lista) {
		this.lista = lista;
	}

	public Medicamento getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}
	
}
