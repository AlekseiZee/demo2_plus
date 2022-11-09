package com.example.demo2;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public enum PersistenceManager {

	INSTANCE;
	
	private EntityManagerFactory emfactory;
	
	private PersistenceManager() {
		emfactory = Persistence.createEntityManagerFactory("eclipselink");
	}
	
	public EntityManager getEntityManager() {
		return emfactory.createEntityManager();
	}

	public void close() {
		emfactory.close();
	}
}

