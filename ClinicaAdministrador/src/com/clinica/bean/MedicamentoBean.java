package com.clinica.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.clinica.dao.MedicamentoDAO;
import com.clinica.domain.Medicamento;

@ManagedBean(name="MBMedicamento")
@ViewScoped
public class MedicamentoBean {

	private String valueSearch;
	
	private Boolean hasSelected;
	
	private List<Medicamento> lista;
	
	private Medicamento medicamento;
	
	private MedicamentoDAO dao;

	@PostConstruct
	public void iniciar(){
		dao = new MedicamentoDAO();
		lista = dao.loadAllSimple();
		valueSearch = "";
		hasSelected = false;
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
	
	public void buscar(){
		lista = dao.loadAllSimpleFiltered(valueSearch, Medicamento.getFilters());
	}
	
	public void onSelect(SelectEvent event) {
		hasSelected = true;
	}

	public void onUnselect(UnselectEvent event) {
		hasSelected = false;
	}
	
	public String getValueSearch() {
		return valueSearch;
	}

	public void setValueSearch(String valueSearch) {
		this.valueSearch = valueSearch;
	}

	public Boolean getHasSelected() {
		return hasSelected;
	}

	public void setHasSelected(Boolean hasSelected) {
		this.hasSelected = hasSelected;
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
