package net.todd.biblestudy.common;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class ResourceProviderTest {
	private ResourceProvider testObject;

	@Before
	public void setUp() {
		testObject = new ResourceProvider();
	}

	@Test
	public void resourcesExistAndHaveCorrectValues() throws Exception {
		List<Resource> resources = testObject.getResources();

		Resource resource1 = findResourceByName(resources, "resource1");
		Resource resource2 = findResourceByName(resources, "resource2");

		String resourceValue1 = FileUtils.readFileToString(resource1.getResourceFile());
		String resourceValue2 = FileUtils.readFileToString(resource2.getResourceFile());

		assertEquals("this is resource1", resourceValue1);
		assertEquals("this is resource2", resourceValue2);
	}

	private Resource findResourceByName(List<Resource> resources, String name) {
		Resource target = null;
		for (Resource resource : resources) {
			if (resource.getName().equals(name)) {
				target = resource;
				break;
			}
		}
		return target;
	}
}
