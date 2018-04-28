package com.clinica.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import com.clinica.dao.AgendamentoDAO;
import com.clinica.dao.MedicoDAO;
import com.clinica.dao.PacienteDAO;
import com.clinica.dao.TipoAtendimentoDAO;
import com.clinica.domain.Agendamento;
import com.clinica.domain.Medico;
import com.clinica.domain.Paciente;
import com.clinica.domain.TipoAtendimento;

@SuppressWarnings("serial")
@ManagedBean(name = "MBAgendamento")
@ViewScoped
public class AgendamentoBean implements Serializable {

	private Long idOpen;

	private Agendamento agendamento;

	private List<Agendamento> lista;

	private List<Agendamento> agendamentosMedico;

	private List<Paciente> pacientes;

	private List<Medico> medicos;

	private List<TipoAtendimento> tiposAtendimento;

	private AgendamentoDAO dao;

	private String jsonConsultas;

	@PostConstruct
	public void iniciar() {
		dao = new AgendamentoDAO();
		gerarJSON();
		pacientes = new PacienteDAO().loadAllSimple();
		medicos = new MedicoDAO().loadAllSimple();
		tiposAtendimento = new TipoAtendimentoDAO().loadAllSimple();
	}

	public void novo() {
		agendamento = new Agendamento();
		agendamento.setTipoAtendimento(new TipoAtendimento());
	}

	public void loadAgendamento() {
		agendamento = dao.selectById(idOpen);
	}

	private void gerarJSON() {
		lista = dao.loadAllSimple();
		JSONArray array = new JSONArray();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Agendamento ag : lista) {
			String dtInicio = fmt.format(ag.getDataHoraInicio());
			String dtFim = fmt.format(ag.getDataHoraFim());
			String[] inicio = dtInicio.split(" ");
			String[] fim = dtFim.split(" ");
			JSONObject obj = new JSONObject();
			obj.put("id", ag.getId());
			obj.put("title", ag.getMedico().getNome() + " - " + ag.getTipoAtendimento().getDescricao());
			obj.put("start", inicio[0] + "T" + inicio[1]);
			obj.put("end", fim[0] + "T" + fim[1]);
			array.put(obj);
		}
		setJsonConsultas(array.toString());
	}

	public void selectPaciente(ActionEvent ev) {
		this.agendamento.setPaciente((Paciente) ev.getComponent().getAttributes().get("selected"));
	}

	public void selectMedico(ActionEvent ev) {
		this.agendamento.setMedico((Medico) ev.getComponent().getAttributes().get("selected"));
	}

	public void buscaConsultasMedico() {
		System.out.println(
				"Inicio: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(agendamento.getDataHoraInicio()));
		System.out.println("Fim: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(agendamento.getDataHoraFim()));
		agendamentosMedico = dao.loadAgendamentosBetweenDatesMedico(agendamento.getMedico(),
				agendamento.getDataHoraInicio(), agendamento.getDataHoraFim());
		System.out.println("Tamanho da lista: " + agendamentosMedico.size());
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setLista(List<Agendamento> lista) {
		this.lista = lista;
	}

	public List<Agendamento> getLista() {
		return lista;
	}

	public void setJsonConsultas(String jsonConsultas) {
		this.jsonConsultas = jsonConsultas;
	}

	public String getJsonConsultas() {
		return jsonConsultas;
	}

	public void setIdOpen(Long idOpen) {
		this.idOpen = idOpen;
	}

	public Long getIdOpen() {
		return idOpen;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setMedicos(List<Medico> medicos) {
		this.medicos = medicos;
	}

	public List<Medico> getMedicos() {
		return medicos;
	}

	public void setAgendamentosMedico(List<Agendamento> agendamentosMedico) {
		this.agendamentosMedico = agendamentosMedico;
	}

	public List<Agendamento> getAgendamentosMedico() {
		return agendamentosMedico;
	}

	public void setTiposAtendimento(List<TipoAtendimento> tiposAtendimento) {
		this.tiposAtendimento = tiposAtendimento;
	}

	public List<TipoAtendimento> getTiposAtendimento() {
		return tiposAtendimento;
	}
}
