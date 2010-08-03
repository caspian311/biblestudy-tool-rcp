package net.todd.biblestudy.db;

import net.java.ao.Entity;
import net.java.ao.OneToMany;

interface Car extends Entity {
	String getName();

	void setName(String name);

	@OneToMany
	Driver[] getDrivers();
}