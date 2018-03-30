package com.clinica.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import com.clinica.dao.AdministradorDAO;
import com.clinica.domain.Administrador;

@ManagedBean(name = "MBAdministrador")
@ViewScoped
public class AdministradorBean {

	private Administrador administrador;

	private List<Administrador> lista;

	private AdministradorDAO dao;

	@PostConstruct
	public void iniciar() {
		dao = new AdministradorDAO();
		lista = dao.loadAllSimple();
	}

	public void novo() {
		administrador = new Administrador();
	}

	public void salvar() {
		administrador.setAtivo("Sim");
		dao.salvar(administrador, true);
		lista = dao.loadAllSimple();
		novo();
	}

	public void editar() {
		dao.editar(administrador, true);
		lista = dao.loadAllSimple();
	}

	public void desativar() {
		administrador.setAtivo("N�o");
		dao.editar(administrador, true);
		lista = dao.loadAllSimple();
	}

	public void selectItemInfo(ActionEvent ev) {
		administrador = (Administrador) ev.getComponent().getAttributes().get("selected");
	}

	public void onSelect(Administrador admin, String typeSeleciton, String indexes) {
		this.administrador = admin;
	}

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}

	public List<Administrador> getLista() {
		return lista;
	}

	public void setLista(List<Administrador> lista) {
		this.lista = lista;
	}
}