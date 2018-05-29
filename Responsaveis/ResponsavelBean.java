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

import org.omnifaces.util.Messages;

import com.clinica.dao.PacienteDAO;
import com.clinica.dao.ResponsavelDAO;
import com.clinica.domain.Responsavel;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.SimpleFileResolver;

@ManagedBean(name = "MBResponsavel")
@ViewScoped
public class ResponsavelBean {
	
	private String search;

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
				Messages.addGlobalWarn("Este responsável está vinculado com Pacientes!");
				return;
			} else {
				dao.deletar(responsavel.getId(), true);
				lista = dao.loadAllSimple();
			}
		} else {
			dao.deletar(responsavel.getId(), true);
			lista = dao.loadAllSimple();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public void chamarRelatorio() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			String reportsDirPath = facesContext.getExternalContext().getRealPath("/") + "WEB-INF/reports";
			File reportsDir = new File(reportsDirPath);
			HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			InputStream reportStream = facesContext.getExternalContext()
					.getResourceAsStream("/WEB-INF/reports/" + "responsaveis" + ".jasper");
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
	public void onSelect(Responsavel resp, String types, String indexes) {
		this.responsavel = resp;
	}

	public void selectItemInfo(ActionEvent ev) {
		responsavel = (Responsavel) ev.getComponent().getAttributes().get("selected");
	}
	
	public void onSearch() {
		lista = dao.loadAllSimpleFiltered(search, Responsavel.getFilters());
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
	
	public void setSearch(String search) {
		this.search = search;
	}
	
	public String getSearch() {
		return search;
	}
}
