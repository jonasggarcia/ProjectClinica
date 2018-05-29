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
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.clinica.dao.AdministradorDAO;
import com.clinica.domain.Administrador;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.SimpleFileResolver;

@ManagedBean(name = "MBAdministrador")
@ViewScoped
public class AdministradorBean {

	private String search;

	private Administrador administrador;

	private List<Administrador> lista;

	private AdministradorDAO dao;

	@PostConstruct
	public void iniciar() {
		dao = new AdministradorDAO();
		lista = dao.loadAllSimple();
	}

	public void novo() {
		administrador = new Administrador();
	}

	public void salvar() {
		administrador.setAtivo("Sim");
		dao.salvar(administrador, true);
		lista = dao.loadAllSimple();
		novo();
	}

	public void editar() {
		dao.editar(administrador, true);
		lista = dao.loadAllSimple();
	}

	public void desativar() {
		administrador.setAtivo("Não");
		dao.editar(administrador, true);
		lista = dao.loadAllSimple();
	}

	public void selectItemInfo(ActionEvent ev) {
		administrador = (Administrador) ev.getComponent().getAttributes().get("selected");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public void chamarRelatorio() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			String reportsDirPath = facesContext.getExternalContext().getRealPath("/") + "WEB-INF/reports";
			File reportsDir = new File(reportsDirPath);
			HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			InputStream reportStream = facesContext.getExternalContext()
					.getResourceAsStream("/WEB-INF/reports/" + "administradores" + ".jasper");
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
			Messages.addGlobalError("Erro ao Gerar Relatório!");
		}
	}

	public void onSelect(Administrador admin, String typeSeleciton, String indexes) {
		this.administrador = admin;
	}

	public void onSearch() {
		lista = dao.loadAllSimpleFiltered(search, Administrador.getFilters());
	}

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}

	public List<Administrador> getLista() {
		return lista;
	}

	public void setLista(List<Administrador> lista) {
		this.lista = lista;
	}

	public void onRowSelect(SelectEvent event) {
		this.administrador = (Administrador) event.getObject();
	}

	public void onRowUnselect(UnselectEvent event) {
		System.out.println("Descelecionou");
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getSearch() {
		return search;
	}
}
