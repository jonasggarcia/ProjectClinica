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

import com.clinica.dao.TipoAtendimentoDAO;
import com.clinica.domain.TipoAtendimento;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.SimpleFileResolver;

@ManagedBean(name = "MBTipoAtendimento")
@ViewScoped
public class TipoAtendimentoBean {

	private String search;

	private List<TipoAtendimento> lista;

	private TipoAtendimento tipoAtendimento;

	private TipoAtendimentoDAO daoTipo;

	@PostConstruct
	public void iniciar() {
		daoTipo = new TipoAtendimentoDAO();
		lista = daoTipo.loadAllSimple();
	}

	public void novo() {
		tipoAtendimento = new TipoAtendimento();
	}

	public void selectRow(ActionEvent ev) {
		tipoAtendimento = (TipoAtendimento) ev.getComponent().getAttributes().get("rowSelected");
		System.out.println(tipoAtendimento.getDescricao());
	}

	public void salvar() {
		tipoAtendimento.setAtivo("Sim");
		daoTipo.salvar(tipoAtendimento, true);
		lista = daoTipo.loadAllSimple();
		novo();
	}

	public void editar() {
		daoTipo.editar(tipoAtendimento, true);
		lista = daoTipo.loadAllSimple();
	}

	public void remover() {
		daoTipo.deletar(tipoAtendimento.getId(), true);
		lista = daoTipo.loadAllSimple();
	}

	public void onSelect(TipoAtendimento tipoAtendimento, String typeOfSelection, String indexes) {
		this.tipoAtendimento = tipoAtendimento;
	}

	public void onSearch() {
		lista = daoTipo.loadAllSimpleFiltered(search, TipoAtendimento.getFilters());
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public void chamarRelatorio() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			String reportsDirPath = facesContext.getExternalContext().getRealPath("/") + "WEB-INF/reports";
			File reportsDir = new File(reportsDirPath);
			HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			InputStream reportStream = facesContext.getExternalContext()
					.getResourceAsStream("/WEB-INF/reports/" + "tipoAtendimento" + ".jasper");
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
	
	public void setLista(List<TipoAtendimento> lista) {
		this.lista = lista;
	}

	public List<TipoAtendimento> getLista() {
		return lista;
	}

	public void setTipoAtendimento(TipoAtendimento tipoAtendimento) {
		this.tipoAtendimento = tipoAtendimento;
	}

	public TipoAtendimento getTipoAtendimento() {
		return tipoAtendimento;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getSearch() {
		return search;
	}
}
