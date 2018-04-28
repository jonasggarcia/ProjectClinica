package com.clinica.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.clinica.domain.Agendamento;
import com.clinica.domain.Medico;
import com.clinica.factory.Factory;

public class AgendamentoDAO extends GenericDAO<Agendamento> {

	@SuppressWarnings("unchecked")
	public List<Agendamento> loadAgendamentosBetweenDatesMedico(Medico medico, Date dtInicial, Date dtFinal) {
		EntityManager em = Factory.getEntityManager();
		try {
			String sql = "SELECT ag from Agendamento ag WHERE ag.medico=:medico AND ag.dataHoraInicio<=:dtInicial AND ag.dataHoraFim>=:dtFinal";
			Query q = em.createQuery(sql, Agendamento.class);
			q.setParameter("dtInicial", dtInicial);
			q.setParameter("dtFinal", dtFinal);
			q.setParameter("medico", medico);
			return q.getResultList();
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}
}
