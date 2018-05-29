package com.clinica.bean;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.omnifaces.util.Messages;

import com.clinica.dao.ExameDAO;
import com.clinica.domain.Exame;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.SimpleFileResolver;

@ManagedBean(name = "MBExame")
@ViewScoped
public class ExameBean {

	private String search;

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

	public void remover() {
		dao.deletar(exame.getId(), true);
		exames = dao.loadAllSimple();
	}

	public void onSelect(Exame exame, String typeOfSelection, String indexes) {
		this.exame = exame;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public void chamarRelatorio() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			String reportsDirPath = facesContext.getExternalContext().getRealPath("/") + "WEB-INF/reports";
			File reportsDir = new File(reportsDirPath);
			HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			InputStream reportStream = facesContext.getExternalContext()
					.getResourceAsStream("/WEB-INF/reports/" + "exames" + ".jasper");
			HashMap parameters = new HashMap();
			parameters.put("usuario", "Pedro");
			parameters.put(JRParameter.REPORT_FILE_RESOLVER, new SimpleFileResolver(reportsDir));
			ServletOutputStream servletOutputStream = response.getOutputStream();
			response.setContentType("application/pdf");
			facesContext.responseComplete();
			JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, parameters,
					new JRBeanCollectionDataSource(exames));
			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			Messages.addGlobalError("ERro ao Gerar Relatório!");
		}
	}
	
	public void onSearch() {
		exames = dao.loadAllSimpleFiltered(search, Exame.getFilters());
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

	public void setSearch(String search) {
		this.search = search;
	}

	public String getSearch() {
		return search;
	}
}
