package com.clinica.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
public class Agendamento extends GenericDomain {

	@ManyToOne
	private TipoAtendimento tipoAtendimento;

	@ManyToOne
	private Medico medico;

	@ManyToOne
	private Paciente paciente;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraInicio;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraFim;

	@Column(length = 1000)
	private String observacoes;

	private String status;

	@Transient
	private String dataHoraInicioFmt;

	@Transient
	private String dataHoraFimFmt;

	public TipoAtendimento getTipoAtendimento() {
		return tipoAtendimento;
	}

	public void setTipoAtendimento(TipoAtendimento tipoAtendimento) {
		this.tipoAtendimento = tipoAtendimento;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(Date dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public Date getDataHoraFim() {
		return dataHoraFim;
	}

	public void setDataHoraFim(Date dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}

	public void setDataHoraFimFmt(String dataHoraFimFmt) {
		this.dataHoraFimFmt = dataHoraFimFmt;
	}

	public String getDataHoraFimFmt() {
		if (dataHoraFim != null)
			return new SimpleDateFormat("HH:mm:ss").format(dataHoraFim);

		return " - ";
	}

	public void setDataHoraInicioFmt(String dataHoraInicioFmt) {
		this.dataHoraInicioFmt = dataHoraInicioFmt;
	}

	public String getDataHoraInicioFmt() {
		if (dataHoraInicio != null)
			return new SimpleDateFormat("HH:mm:ss").format(dataHoraInicio);

		return " - ";
	}
}