package com.clinica.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import com.clinica.dao.PacienteDAO;
import com.clinica.domain.Paciente;
import com.clinica.domain.Responsavel;

@ManagedBean(name = "MBPaciente")
@ViewScoped
public class PacienteBean {

	private String search;

	private String hasResponsavel;

	private Paciente paciente;

	private List<Paciente> listaPacientes;

	private PacienteDAO daoPaciente;

	@PostConstruct
	public void iniciar() {
		daoPaciente = new PacienteDAO();
		listaPacientes = daoPaciente.loadAllSimple();
	}

	public void novo() {
		paciente = new Paciente();
		paciente.setResponsavel(new Responsavel());
	}

	public void salvar() {
		if (paciente.getResponsavel().getId() == null) {
			paciente.setResponsavel(null);
		}
		paciente.setAtivo("Sim");
		daoPaciente.salvar(paciente, true);
		listaPacientes = daoPaciente.loadAllSimple();
		novo();
	}

	public void editar() {
		daoPaciente.editar(paciente, true);
		listaPacientes = daoPaciente.loadAllSimple();
	}

	public void desativar() {
		paciente.setAtivo("Não");
		daoPaciente.editar(paciente, true);
		listaPacientes = daoPaciente.loadAllSimple();
	}

	public void putRespToPatient(ActionEvent e) {
		this.paciente.setResponsavel((Responsavel) e.getComponent().getAttributes().get("selected"));
		System.out.println(paciente.getResponsavel().getNome());
	}

	public void changeHasResponsavel() {
		if (hasResponsavel.equals("0")) {
			paciente.setResponsavel(null);
		} else {
			paciente.setResponsavel(new Responsavel());
		}
	}

	public void onSelect(Paciente selected, String type, String indexes) {
		this.paciente = selected;
		if (paciente.getResponsavel() == null) {
			hasResponsavel = "0";
		} else {
			hasResponsavel = "1";
		}
	}
	
	public void onSearch() {
		listaPacientes = daoPaciente.loadAllSimpleFiltered(search, Paciente.getFilters());
	}

	public void selectItemInfo(ActionEvent e) {
		paciente = (Paciente) e.getComponent().getAttributes().get("selected");
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public List<Paciente> getListaPacientes() {
		return listaPacientes;
	}

	public void setListaPacientes(List<Paciente> listaPacientes) {
		this.listaPacientes = listaPacientes;
	}

	public void setHasResponsavel(String hasResponsavel) {
		this.hasResponsavel = hasResponsavel;
	}

	public String getHasResponsavel() {
		return hasResponsavel;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getSearch() {
		return search;
	}
}
