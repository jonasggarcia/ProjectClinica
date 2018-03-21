package com.clinica.factory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Factory {

	private static EntityManagerFactory emfLocal;

	// Conexão com a base de dados loccal
	public static EntityManager getEntityManager() {
		if (emfLocal == null)
			emfLocal = Persistence.createEntityManagerFactory("LocalDB");

		emfLocal.getCache().evictAll();

		return emfLocal.createEntityManager();
	}
	
}
