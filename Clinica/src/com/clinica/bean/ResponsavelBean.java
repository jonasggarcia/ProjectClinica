package com.clinica.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import com.clinica.dao.PacienteDAO;
import com.clinica.dao.ResponsavelDAO;
import com.clinica.domain.Responsavel;

@ManagedBean(name = "MBResponsavel")
public class ResponsavelBean {

	private Responsavel responsavel;

	private List<Responsavel> lista;

	private ResponsavelDAO dao;

	@PostConstruct
	public void iniciar() {
		dao = new ResponsavelDAO();
		lista = dao.loadAllSimple();
	}

	public void novo() {
		responsavel = new Responsavel();
	}

	public void salvar() {
		responsavel.setAtivo("Sim");
		dao.salvar(responsavel, true);
		lista = dao.loadAllSimple();
		novo();
	}

	public void editar() {
		dao.editar(responsavel, true);
		lista = dao.loadAllSimple();
	}

	public void remover() {
		if (new PacienteDAO().loadByResponsavel(responsavel) != null) {
			if (new PacienteDAO().loadByResponsavel(responsavel).size() > 0) {
				dao.deletar(responsavel.getId(), true);
				lista = dao.loadAllSimple();
			} else {
				Messages.addGlobalWarn("Este responsável eatá vinculado com Pacientes!");
			}
		} else {
			Messages.addGlobalWarn("Este responsável eatá vinculado com Pacientes!");
		}
	}

	public void onSelect(Responsavel resp, String types, String indexes) {
		this.responsavel = resp;
	}

	public void selectItemInfo(ActionEvent ev) {
		responsavel = (Responsavel) ev.getComponent().getAttributes().get("selected");
	}

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	public List<Responsavel> getLista() {
		return lista;
	}

	public void setLista(List<Responsavel> lista) {
		this.lista = lista;
	}

	public ResponsavelDAO getDao() {
		return dao;
	}

	public void setDao(ResponsavelDAO dao) {
		this.dao = dao;
	}
}
