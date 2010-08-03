package net.todd.biblestudy.db;

import net.java.ao.Entity;

interface Driver extends Entity {
	String getName();

	void setName(String name);

	void setCar(Car car);

	Car getCar();
}