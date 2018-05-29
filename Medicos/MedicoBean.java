package com.clinica.bean;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
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

import com.clinica.dao.EspecialidadeDAO;
import com.clinica.dao.MedicoDAO;
import com.clinica.domain.Especialidade;
import com.clinica.domain.Medico;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.SimpleFileResolver;

@ManagedBean(name = "MBMedico")
@ViewScoped
public class MedicoBean{
	
	private String search;

	private Medico medico;

	private List<Medico> lista;

	private List<Especialidade> especialidades;

	private List<Especialidade> espAdd;

	private MedicoDAO dao;

	@PostConstruct
	public void iniciar() {
		dao = new MedicoDAO();
		lista = dao.loadAllSimple();
		especialidades = new EspecialidadeDAO().loadAllSimple();
	}

	public void novo() {
		medico = new Medico();
		espAdd = new ArrayList<>();
	}

	public void salvar() {
		medico.setEspecialidades(espAdd);
		medico.setAtivo("Sim");
		dao.salvar(medico, true);
		lista = dao.loadAllSimple();
		novo();
	}

	public void editar() {
		medico.setEspecialidades(espAdd);
		dao.editar(medico, true);
		lista = dao.loadAllSimple();
	}

	public void desativar() {
		medico.setAtivo("Não");
		dao.editar(medico, true);
		lista = dao.loadAllSimple();
	}

	public void selectInfo(ActionEvent ev) {
		medico = (Medico) ev.getComponent().getAttributes().get("selectedMedico");
	}

	public void selectAddEsp(ActionEvent ev) {
		Especialidade esp = (Especialidade) ev.getComponent().getAttributes().get("espSelected");
		if (!checkExistentEsp(esp)) {
			espAdd.add(esp);
		} else {
			Messages.addFlashGlobalWarn("Especialidade já Inserida!");
		}
	}

	public void selectRemEsp(ActionEvent e) {
		System.out.println("entrou");
		Especialidade esp = (Especialidade) e.getComponent().getAttributes().get("espSelected");
		removeByIdEsp(esp.getId());
		System.out.println("foi");
	}

	private void removeByIdEsp(Long id) {
		for (int i = 0; i < espAdd.size(); i++) {
			if (espAdd.get(i).getId() == id) {
				espAdd.remove(i);
				return;
			}
		}
	}

	private boolean checkExistentEsp(Especialidade e) {
		for (Especialidade esp : espAdd) {
			if (esp.getId() == e.getId()) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public void chamarRelatorio() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			String reportsDirPath = facesContext.getExternalContext().getRealPath("/") + "WEB-INF/reports";
			File reportsDir = new File(reportsDirPath);
			HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			InputStream reportStream = facesContext.getExternalContext()
					.getResourceAsStream("/WEB-INF/reports/" + "medicos" + ".jasper");
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
	
	public void onSearch() {
		lista = dao.loadAllSimpleFiltered(search, Medico.getFilters());
	}

	public void onSelect(Medico med, String type, String indexes) {
		this.medico = med;
		espAdd = medico.getEspecialidades();
	}
	
	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public List<Medico> getLista() {
		return lista;
	}

	public void setLista(List<Medico> lista) {
		this.lista = lista;
	}

	public void setEspAdd(List<Especialidade> espAdd) {
		this.espAdd = espAdd;
	}

	public List<Especialidade> getEspAdd() {
		return espAdd;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}
	
	public void setSearch(String search) {
		this.search = search;
	}
	
	public String getSearch() {
		return search;
	}
}
