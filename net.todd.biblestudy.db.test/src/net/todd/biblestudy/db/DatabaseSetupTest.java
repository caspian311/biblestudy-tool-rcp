package net.todd.biblestudy.db;

import static org.junit.Assert.*;

import java.util.UUID;

import net.java.ao.EntityManager;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DatabaseSetupTest {
	@BeforeClass
	public static void setupDatabase() {
		new DatabaseSetup().setupDatabase();
	}

	private EntityManager entityManager;

	@Before
	public void setUp() {
		entityManager = EntityManagerProvider.getEntityManager();
	}

	@Test
	public void addingAndRemovingCars() throws Exception {
		String carName = UUID.randomUUID().toString();
		Car newCar = entityManager.create(Car.class);
		newCar.setName(carName);
		newCar.save();

		Car[] allCars = entityManager.find(Car.class);
		assertEquals(1, allCars.length);
		assertEquals(carName, allCars[0].getName());

		entityManager.delete(allCars[0]);

		assertEquals(0, entityManager.find(Car.class).length);
	}

	@Test
	public void addingAndRemovingDriversToCars() throws Exception {
		String carName = UUID.randomUUID().toString();
		Car newCar = entityManager.create(Car.class);
		newCar.setName(carName);
		newCar.save();

		Driver driver1 = entityManager.create(Driver.class);
		String driverToCarName1 = UUID.randomUUID().toString();
		driver1.setName(driverToCarName1);
		driver1.setCar(newCar);
		driver1.save();

		Driver driver2 = entityManager.create(Driver.class);
		String driverToCarName2 = UUID.randomUUID().toString();
		driver2.setName(driverToCarName2);
		driver2.setCar(newCar);
		driver2.save();

		Car[] allCars = entityManager.find(Car.class);
		assertEquals(2, allCars[0].getDrivers().length);
		assertEquals(driverToCarName1, allCars[0].getDrivers()[0].getName());
		assertEquals(driverToCarName2, allCars[0].getDrivers()[1].getName());

		for (Driver Driver : newCar.getDrivers()) {
			entityManager.delete(Driver);
		}
		entityManager.delete(newCar);

		assertEquals(0, entityManager.find(Car.class).length);
		assertEquals(0, entityManager.find(Driver.class).length);
	}

	@Test
	public void dataPersistenceWhenDatabaseStartupCalledMultipleTimes() throws Exception {
		new DatabaseSetup().setupDatabase();
		new DatabaseSetup().setupDatabase();

		EntityManager entityManager = EntityManagerProvider.getEntityManager();
		Car Car = entityManager.create(Car.class);
		Car.setName(UUID.randomUUID().toString());
		Car.save();

		new DatabaseSetup().setupDatabase();
		new DatabaseSetup().setupDatabase();

		EntityManager entityManager2 = EntityManagerProvider.getEntityManager();
		assertEquals(1, entityManager2.find(Car.class).length);
	}
}
