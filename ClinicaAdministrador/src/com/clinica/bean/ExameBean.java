package com.clinica.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.clinica.dao.ExameDAO;
import com.clinica.domain.Exame;

@ManagedBean(name = "MBExame")
@ViewScoped
public class ExameBean {

	private String valueSearch;

	private Boolean hasSelected;

	private List<Exame> exames;

	private Exame exame;

	private ExameDAO dao;

	@PostConstruct
	public void iniciar() {
		valueSearch = "";
		dao = new ExameDAO();
		exames = dao.loadAllSimple();
		hasSelected = false;
	}

	public void novo() {
		exame = new Exame();
	}

	public void salvar() {
		exame.setAtivo("Sim");
		dao.salvar(exame, true);
		novo();
		exames = dao.loadAllSimple();
	}

	public void editar() {
		dao.editar(exame, true);
		exames = dao.loadAllSimple();
	}

	public void buscar() {
		exames = dao.loadAllSimpleFiltered(valueSearch, Exame.getFilters());
	}

	public void onSelect(SelectEvent event) {
		hasSelected = true;
	}

	public void onUnselect(UnselectEvent event) {
		hasSelected = false;
		System.out.println("deselcionou");
	}

	public void setExames(List<Exame> exames) {
		this.exames = exames;
	}

	public List<Exame> getExames() {
		return exames;
	}

	public void setExame(Exame exame) {
		this.exame = exame;
	}

	public Exame getExame() {
		return exame;
	}

	public void setValueSearch(String valueSearch) {
		this.valueSearch = valueSearch;
	}

	public String getValueSearch() {
		return valueSearch;
	}

	public void setHasSelected(Boolean hasSelected) {
		this.hasSelected = hasSelected;
	}

	public Boolean getHasSelected() {
		return hasSelected;
	}
}
