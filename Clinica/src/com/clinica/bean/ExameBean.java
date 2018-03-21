package com.clinica.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


import com.clinica.dao.ExameDAO;
import com.clinica.domain.Exame;

@ManagedBean(name = "MBExame")
@ViewScoped
public class ExameBean {

	private List<Exame> exames;

	private Exame exame;

	private ExameDAO dao;

	@PostConstruct
	public void iniciar() {
		dao = new ExameDAO();
		exames = dao.loadAllSimple();
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
	
	public void remover(){
		dao.deletar(exame.getId(), true);
		exames = dao.loadAllSimple();
	}
	
	public void onSelect(Exame exame, String typeOfSelection, String indexes) {
		this.exame = exame;
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

}
