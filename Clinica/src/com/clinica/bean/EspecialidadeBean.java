package com.clinica.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import com.clinica.dao.EspecialidadeDAO;
import com.clinica.dao.MedicoDAO;
import com.clinica.domain.Especialidade;
import com.clinica.domain.Medico;

@ManagedBean(name = "MBEspecialidade")
@ViewScoped
public class EspecialidadeBean {

	private Especialidade especialidade;

	private List<Especialidade> lista;

	private EspecialidadeDAO dao;

	@PostConstruct
	public void iniciar() {
		dao = new EspecialidadeDAO();
		lista = dao.loadAllSimple();
	}

	public void novo() {
		especialidade = new Especialidade();
	}

	public void salvar() {
		dao.salvar(especialidade, true);
		lista = dao.loadAllSimple();
	}

	public void editar() {
		dao.editar(especialidade, true);
		lista = dao.loadAllSimple();
	}

	public void remover() {
		List<Medico> meds = new MedicoDAO().loadByEspecialidade(especialidade);
		if (meds != null && meds.size() > 0) {
			Messages.addGlobalWarn("Existem Médicos Cadastrados com esta Especialidade!");
			return;
		} else {
			dao.deletar(especialidade.getId(), true);
		}
	}

	public void onSelect(Especialidade esp, String type, String indexes) {
		this.especialidade = esp;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setLista(List<Especialidade> lista) {
		this.lista = lista;
	}

	public List<Especialidade> getLista() {
		return lista;
	}
}
