package com.clinica.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.omnifaces.util.Messages;
import org.primefaces.model.SortOrder;

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
	public List<Entidade> loadCriteriaWithFilter(int pageSize, int first, Map<String, String> filters, SortOrder order,
			String fieldOrder) {
			try{
				StringBuffer buf = new StringBuffer();
				for (String f : filters.keySet()) {
					buf.append("l.");
					buf.append(f + " ");
					buf.append("LIKE '%");
					buf.append(filters.get(f) + "%' ");
					buf.append("AND ");
				}
				if(buf.toString().length()>0){
					String fmtBuf = buf.toString().substring(0, buf.toString().length() - 4);
					String sql = null;
					sql = "SELECT l FROM "+classe.getSimpleName()+" l WHERE " + fmtBuf;
					if(order!=null){
						if (order == SortOrder.ASCENDING) {
							// menor pro maior
							sql = sql + " ORDER BY l." + fieldOrder + " ASC";
						} else {
							sql = sql + " ORDER BY l." + fieldOrder + " DESC";
						}
					}
					System.out.println(sql);
					Query q = Factory.getEntityManager().createQuery(sql, classe);
					q.setFirstResult(first);
					q.setMaxResults(pageSize);
					Factory.getEntityManager().close();
					return q.getResultList();
				}else{
					//sem filtro
					return loadCriteria(pageSize, first, order, fieldOrder);
				}
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
	}
	
	@SuppressWarnings("unchecked")
	public List<Entidade> loadCriteria(int pageSize, int first, SortOrder order, String fieldOrder) {
		try {
			String sql = null;
			sql = "SELECT l from "+classe.getSimpleName()+" l";
			if(order != null) {
				if (order == SortOrder.ASCENDING) {
					// menor pro maior
					sql = sql + " ORDER BY l." + fieldOrder + " ASC";
				} else {
					sql = sql + " ORDER BY l." + fieldOrder + " DESC";
				}
			}else{
				sql = sql + " ORDER BY l.id ASC";
			}
			Query q = Factory.getEntityManager().createQuery(sql,classe);
			q.setFirstResult(first);
			q.setMaxResults(pageSize);
			Factory.getEntityManager().close();
			return q.getResultList();
		} catch (Exception e) {
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
