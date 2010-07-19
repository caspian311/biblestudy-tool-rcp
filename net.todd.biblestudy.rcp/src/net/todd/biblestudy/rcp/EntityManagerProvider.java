package net.todd.biblestudy.rcp;

import net.java.ao.EntityManager;

public class EntityManagerProvider {
	public EntityManager getEntityManager() {
		String uri = "jdbc:mysql://localhost/test";
		String username = "root";
		String password = "root";
		return new EntityManager(uri, username, password, getClass()
				.getClassLoader());
	}
}
