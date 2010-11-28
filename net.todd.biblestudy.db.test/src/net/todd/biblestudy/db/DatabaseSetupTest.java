package net.todd.biblestudy.db;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.java.ao.EntityManager;
import net.todd.biblestudy.common.BundleUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DatabaseSetupTest {
	private DataObjectProvider dataObjectProvider;
	private EntityManager entityManager;
	private File tempDirectory;
	private DatabaseSetup testObject;

	@Before
	public void setUp() throws Exception {
		tempDirectory = File.createTempFile(getClass().getName(), "directory");
		tempDirectory.delete();
		tempDirectory.mkdir();

		DataObject carDataObject = new DataObject(Car.class);
		File carsSqlFile = BundleUtils.getFileFromClass(getClass(), "/data/cars.sql");
		carDataObject.addSqlFile(carsSqlFile);

		DataObject driversDataObject = new DataObject(Driver.class);
		File driversSqlFile = BundleUtils.getFileFromClass(getClass(), "/data/drivers.sql");
		driversDataObject.addSqlFile(driversSqlFile);

		final List<DataObject> dataObjects = new ArrayList<DataObject>();
		dataObjects.add(carDataObject);
		dataObjects.add(driversDataObject);
		dataObjectProvider = new DataObjectProvider() {
			@Override
			public List<DataObject> getDataObjects() {
				return dataObjects;
			}
		};

		testObject = new DatabaseSetup(dataObjectProvider, tempDirectory);
		testObject.setupDatabase();

		entityManager = EntityManagerProvider.getEntityManager();
	}

	@After
	public void tearDown() throws Exception {
		BundleUtils.forceDelete(tempDirectory);
	}

	@Test
	public void viewingExistingData() throws Exception {
		try {
			Car[] cars = entityManager.find(Car.class);

			assertEquals(1, cars.length);
			assertEquals("Viper", cars[0].getName());
			assertEquals(1, cars[0].getDrivers().length);
			assertEquals("Matt", cars[0].getDrivers()[0].getName());
		} finally {
			entityManager.delete(entityManager.find(Driver.class));
			entityManager.delete(entityManager.find(Car.class));
		}
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
		testObject.setupDatabase();
		testObject.setupDatabase();

		EntityManager entityManager = EntityManagerProvider.getEntityManager();
		Car Car = entityManager.create(Car.class);
		Car.setName(UUID.randomUUID().toString());
		Car.save();

		testObject.setupDatabase();
		testObject.setupDatabase();

		EntityManager entityManager2 = EntityManagerProvider.getEntityManager();
		assertEquals(1, entityManager2.find(Car.class).length);
	}
}
