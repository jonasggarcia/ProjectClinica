package com.clinica.bean;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.omnifaces.util.Messages;

import com.clinica.dao.PacienteDAO;
import com.clinica.domain.Paciente;
import com.clinica.domain.Responsavel;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.SimpleFileResolver;

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

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public void chamarRelatorio() {
		try {
			if(listaPacientes!=null && listaPacientes.size()>=1) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			String reportsDirPath = facesContext.getExternalContext().getRealPath("/") + "WEB-INF/reports";
			File reportsDir = new File(reportsDirPath);
			HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			InputStream reportStream = facesContext.getExternalContext()
					.getResourceAsStream("/WEB-INF/reports/" + "pacientes" + ".jasper");
			HashMap parameters = new HashMap();
			parameters.put("usuario", "Pedro");
			parameters.put(JRParameter.REPORT_FILE_RESOLVER, new SimpleFileResolver(reportsDir));
			ServletOutputStream servletOutputStream = response.getOutputStream();
			response.setContentType("application/pdf");
			facesContext.responseComplete();
			JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, parameters,
					new JRBeanCollectionDataSource(listaPacientes));
			servletOutputStream.flush();
			servletOutputStream.close();
			}else
				//JOptionPane.showMessageDialog(null, "Não há dados para serem exibidos no relatório!");
				Messages.addGlobalError("Erro ao gerar o relatório!");
		} catch (Exception e) {
			e.printStackTrace();
			Messages.addGlobalError("Erro ao gerar o relatório!");
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
