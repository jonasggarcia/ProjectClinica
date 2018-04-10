package com.clinica.bean;

import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

import com.clinica.dao.AgendamentoDAO;
import com.clinica.domain.Agendamento;

@ManagedBean(name = "MBAgendamento")
@ViewScoped
public class AgendamentoBean {

	private ScheduleModel agendamentos;

	private Agendamento agendamento;

	private List<Agendamento> lista;

	private AgendamentoDAO dao;

	@PostConstruct
	public void iniciar() {
		agendamentos = new DefaultScheduleModel();
	}

	public void atualizar() {
		agendamentos = new DefaultScheduleModel();
		lista = dao.loadAllSimple();
		for (Agendamento a : lista) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(a.getDataHoraInicio());
			cal.add(Calendar.DATE, -1);
			a.setDataHoraInicio(cal.getTime());
			cal.setTime(a.getDataHoraFim());
			cal.add(Calendar.DATE, -1);
			a.setDataHoraFim(cal.getTime());
			agendamentos.addEvent(new DefaultScheduleEvent(a.getId() + " - " + a.getMedico().getNome(),
					a.getDataHoraInicio(), a.getDataHoraFim()));
		}
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

	public void setAgendamentos(ScheduleModel agendamentos) {
		this.agendamentos = agendamentos;
	}

	public ScheduleModel getAgendamentos() {
		return agendamentos;
	}
}
