package com.clinica.dao;

import java.util.List;

import javax.persistence.Query;

import com.clinica.domain.Paciente;
import com.clinica.domain.Responsavel;
import com.clinica.factory.Factory;

public class PacienteDAO extends GenericDAO<Paciente>{

	@SuppressWarnings("unchecked")
	public List<Paciente> loadByResponsavel(Responsavel resp){
		try {
			Query q = Factory.getEntityManager().createQuery("select c from Paciente c where c.responsavel.id = :idResp",Paciente.class);
			q.setParameter("idResp", resp.getId());
			return q.getResultList();
		}catch(Exception e) {
			return null;
		}
	}
}
