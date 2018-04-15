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

import com.clinica.dao.MedicamentoDAO;
import com.clinica.domain.Medicamento;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.SimpleFileResolver;

@ManagedBean(name = "MBMedicamento")
@ViewScoped
public class MedicamentoBean {

	private String search;

	private List<Medicamento> lista;

	private Medicamento medicamento;

	private MedicamentoDAO dao;

	@PostConstruct
	public void iniciar() {
		dao = new MedicamentoDAO();
		lista = dao.loadAllSimple();
	}

	public void novo() {
		medicamento = new Medicamento();
	}

	public void salvar() {
		medicamento.setAtivo("Sim");
		dao.salvar(medicamento, true);
		novo();
		lista = dao.loadAllSimple();
	}

	public void editar() {
		dao.editar(medicamento, true);
		lista = dao.loadAllSimple();
	}

	public void remover() {
		dao.deletar(medicamento.getId(), true);
		lista = dao.loadAllSimple();
	}

	public void onSelect(Medicamento med, String typeOfSelection, String indexes) {
		this.medicamento = med;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public void chamarRelatorio() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			String reportsDirPath = facesContext.getExternalContext().getRealPath("/") + "WEB-INF/reports";
			File reportsDir = new File(reportsDirPath);
			HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			InputStream reportStream = facesContext.getExternalContext()
					.getResourceAsStream("/WEB-INF/reports/" + "medicamentos" + ".jasper");
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
			Messages.addGlobalError("ERro ao Gerar Relatório!");
		}
	}
	
	public void onSearch() {
		lista = dao.loadAllSimpleFiltered(search, Medicamento.getFilters());
	}

	public List<Medicamento> getLista() {
		return lista;
	}

	public void setLista(List<Medicamento> lista) {
		this.lista = lista;
	}

	public Medicamento getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getSearch() {
		return search;
	}

}
