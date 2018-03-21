package com.clinica.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.omnifaces.util.Messages;

import com.clinica.factory.Factory;

public class GenericDAO<Entidade> {

	private Class<Entidade> classe;

	@SuppressWarnings("unchecked")
	public GenericDAO() {
		// TODO Auto-generated constructor stub
		this.classe = (Class<Entidade>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	public boolean salvar(Entidade entidade, boolean msg) {
		try {
			EntityManager s = Factory.getEntityManager();
			s.getTransaction().begin();
			s.persist(entidade);
			s.getTransaction().commit();
			s.close();
			if(msg)
				Messages.addGlobalInfo("Dados Salvos com Sucesso!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if(msg)
				Messages.addGlobalError("Erro ao Salvar Dados!");
			return false;
		}
	}

	public boolean editar(Entidade entidade, boolean msg) {
		try {
			EntityManager s = Factory.getEntityManager();
			s.getTransaction().begin();
			s.merge(entidade);
			s.getTransaction().commit();
			s.close();
			if(msg)
				Messages.addGlobalInfo("Alteração Realizada com Sucesso!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if(msg)
				Messages.addGlobalError("Erro ao Editar Dados!");
			return false;
		}
	}

	public boolean deletar(long id, boolean msg) {
		try {
			EntityManager s = Factory.getEntityManager();
			s.getTransaction().begin();
			String sql = "delete from "+classe.getSimpleName()+" where id = "+id;
			System.out.println(sql);
			Query q = s.createNativeQuery(sql, classe);
			q.executeUpdate();
			s.getTransaction().commit();
			s.close();
			if(msg)
				Messages.addGlobalInfo("Exclusão Realizada com Sucesso!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			if(msg)
				Messages.addGlobalError("Erro ao Deletar Dados!");
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Entidade selectById(Long id){
		try{
			Query q = Factory.getEntityManager().createQuery("select i from "+classe.getSimpleName()+" i where i.id=:id",classe);
			q.setParameter("id", id);
			q.setMaxResults(1);
			return (Entidade)q.getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}


	
	@SuppressWarnings("unchecked")
	public List<Entidade> loadAllSimpleFiltered(String value, String[]filters){
		StringBuffer buf = new StringBuffer();
		buf.append("select l from "+classe.getSimpleName()+" l where ");
		for(String s:filters){
			buf.append("l."+s+" like '%"+value+"%' or ");
		}
		try{
			String sql = buf.toString();
			sql = sql.substring(0, sql.length()-4);
			Query q = Factory.getEntityManager().createQuery(sql,classe);
			return q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Entidade> loadAllSimple(){
		try{
			String sql = "select l from "+classe.getSimpleName()+" l";
			Query q = Factory.getEntityManager().createQuery(sql,classe);
			return q.getResultList();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public Long count() {
		TypedQuery<Long> q = Factory.getEntityManager().createQuery("SELECT COUNT(id) FROM "+classe.getSimpleName()+" l", Long.class);
		Factory.getEntityManager().close();
		return q.getSingleResult();
	}
}
