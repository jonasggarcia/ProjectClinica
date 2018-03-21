package com.clinica.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import com.clinica.dao.PacienteDAO;
import com.clinica.domain.Paciente;

@ManagedBean(name = "MBPaciente")
@ViewScoped
public class PacienteBean {

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
	}

	public void salvar() {
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
	
	public void onSelect(Paciente selected,String type, String indexes) {
		this.paciente = selected;
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
}
