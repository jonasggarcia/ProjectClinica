package com.clinica.dao;

import java.util.List;



import com.clinica.domain.Especialidade;
import javax.persistence.Query;
import com.clinica.domain.Medico;
import com.clinica.factory.Factory;

public class MedicoDAO extends GenericDAO<Medico> {

	@SuppressWarnings("unchecked")
	public List<Medico> loadByEspecialidade(Especialidade esp){
		try{
			Query q = Factory.getEntityManager().createQuery("select c from Medico c join c.especialidades e where e = :esp", Medico.class);
			q.setParameter("esp", esp);
			return q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
