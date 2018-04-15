package com.clinica.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import com.clinica.dao.TipoAtendimentoDAO;
import com.clinica.domain.TipoAtendimento;

@ManagedBean(name = "MBTipoAtendimento")
@ViewScoped
public class TipoAtendimentoBean {

	private String search;

	private List<TipoAtendimento> lista;

	private TipoAtendimento tipoAtendimento;

	private TipoAtendimentoDAO daoTipo;

	@PostConstruct
	public void iniciar() {
		daoTipo = new TipoAtendimentoDAO();
		lista = daoTipo.loadAllSimple();
	}

	public void novo() {
		tipoAtendimento = new TipoAtendimento();
	}

	public void selectRow(ActionEvent ev) {
		tipoAtendimento = (TipoAtendimento) ev.getComponent().getAttributes().get("rowSelected");
		System.out.println(tipoAtendimento.getDescricao());
	}

	public void salvar() {
		tipoAtendimento.setAtivo("Sim");
		daoTipo.salvar(tipoAtendimento, true);
		lista = daoTipo.loadAllSimple();
		novo();
	}

	public void editar() {
		daoTipo.editar(tipoAtendimento, true);
		lista = daoTipo.loadAllSimple();
	}

	public void remover() {
		daoTipo.deletar(tipoAtendimento.getId(), true);
		lista = daoTipo.loadAllSimple();
	}

	public void onSelect(TipoAtendimento tipoAtendimento, String typeOfSelection, String indexes) {
		this.tipoAtendimento = tipoAtendimento;
	}

	public void onSearch() {
		lista = daoTipo.loadAllSimpleFiltered(search, TipoAtendimento.getFilters());
	}

	public void setLista(List<TipoAtendimento> lista) {
		this.lista = lista;
	}

	public List<TipoAtendimento> getLista() {
		return lista;
	}

	public void setTipoAtendimento(TipoAtendimento tipoAtendimento) {
		this.tipoAtendimento = tipoAtendimento;
	}

	public TipoAtendimento getTipoAtendimento() {
		return tipoAtendimento;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getSearch() {
		return search;
	}
}
