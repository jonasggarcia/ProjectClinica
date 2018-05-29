package com.clinica.bean;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.omnifaces.util.Messages;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import com.clinica.dao.AgendamentoDAO;
import com.clinica.domain.Agendamento;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.SimpleFileResolver;

@ManagedBean(name = "MBAgendamento")
@ViewScoped
public class AgendamentoBean {

	private Long idOpen;

	private Date dateSeted;

	private String strDateCalendar;

	private Agendamento agendamento;

	private List<Agendamento> lista;

	private List<Agendamento> agendamentosMedico;

	private AgendamentoDAO dao;

	private String jsonConsultas;

	@PostConstruct
	public void iniciar() {
		dao = new AgendamentoDAO();
		dateSeted = new Date();
		setarDataCalendar();
		gerarJSON();
	}

	public void loadAgendamento() {
		agendamento = dao.selectById(idOpen);
	}

	public void setarDataCalendar() {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		strDateCalendar = fmt.format(dateSeted);
		System.out.println(strDateCalendar);
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
			if(ag.getStatus().equals("Desmarcado")) {
				obj.put("color", "red");
			}else if(ag.getStatus().equals("Consulta Realizada")){
				obj.put("color", "green");
			}
			array.put(obj);
		}
		setJsonConsultas(array.toString());
	}

	public void editar(String opt) {
		if (opt.equals("status")) {
			agendamento.setStatus("Desmarcado");
		}
		dao.editar(agendamento, true);
		gerarJSON();
		setarDataCalendar();
		agendamento = null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public void chamarRelatorio() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			String reportsDirPath = facesContext.getExternalContext().getRealPath("/") + "WEB-INF/reports";
			File reportsDir = new File(reportsDirPath);
			HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			InputStream reportStream = facesContext.getExternalContext()
					.getResourceAsStream("/WEB-INF/reports/" + "agendamentos" + ".jasper");
			HashMap parameters = new HashMap();
			parameters.put("usuario", "Pedro");
			parameters.put(JRParameter.REPORT_FILE_RESOLVER, new SimpleFileResolver(reportsDir));
			ServletOutputStream servletOutputStream = response.getOutputStream();
			response.setContentType("application/pdf");
			facesContext.responseComplete();
			JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, parameters,
					new JRBeanCollectionDataSource(lista));
			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			Messages.addGlobalError("Erro ao gerar o relatório!");
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

	public void setAgendamentosMedico(List<Agendamento> agendamentosMedico) {
		this.agendamentosMedico = agendamentosMedico;
	}

	public List<Agendamento> getAgendamentosMedico() {
		return agendamentosMedico;
	}

	public void setDateSeted(Date dateSeted) {
		this.dateSeted = dateSeted;
	}

	public Date getDateSeted() {
		return dateSeted;
	}

	public void setStrDateCalendar(String strDateCalendar) {
		this.strDateCalendar = strDateCalendar;
	}

	public String getStrDateCalendar() {
		return strDateCalendar;
	}
}
