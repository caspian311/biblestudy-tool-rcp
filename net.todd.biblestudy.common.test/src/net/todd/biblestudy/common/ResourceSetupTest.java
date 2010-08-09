package net.todd.biblestudy.common;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.util.Arrays;

import net.todd.biblestudy.common.DirectoryProvider;
import net.todd.biblestudy.common.Resource;
import net.todd.biblestudy.common.ResourceProvider;
import net.todd.biblestudy.common.ResourceSetup;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ResourceSetupTest {
	@Mock
	private ResourceProvider resourcesProvider;
	@Mock
	private DirectoryProvider directoryProvider;

	private ResourceSetup testObject;

	private File destinationDirectory;
	private File sourceFile1;
	private File sourceFile2;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		testObject = new ResourceSetup(resourcesProvider, directoryProvider);
	}

	@Before
	public void setupTempDirectory() throws Exception {
		destinationDirectory = createTemporaryDirectory();

		sourceFile1 = File.createTempFile(getClass().getName(), "_datafile");
		FileUtils.writeStringToFile(sourceFile1, "source file 1");

		sourceFile2 = File.createTempFile(getClass().getName(), "_datafile");
		FileUtils.writeStringToFile(sourceFile2, "source file 2");
	}

	@After
	public void clearTempDirectory() throws Exception {
		FileUtils.deleteDirectory(destinationDirectory);
	}

	private File createTemporaryDirectory() throws Exception {
		File temp = File.createTempFile(getClass().getName(), "resourcesDirectory");
		String tempDirectoryLocation = temp.getAbsolutePath();
		FileUtils.forceDelete(temp);
		temp.mkdir();
		return new File(tempDirectoryLocation);
	}

	@Test
	public void settingUpResourcesCopyResourcesFromTheProviderToTheResourceDirectory() throws Exception {
		doReturn(destinationDirectory).when(directoryProvider).getDirectory(DirectoryProvider.RESOURCE_FILES);
		Resource resource1 = mock(Resource.class);
		doReturn(sourceFile1).when(resource1).getResourceFile();
		Resource resource2 = mock(Resource.class);
		doReturn(sourceFile2).when(resource2).getResourceFile();
		doReturn(Arrays.asList(resource1, resource2)).when(resourcesProvider).getResources();

		testObject.setupResources();

		assertEquals(2, destinationDirectory.list().length);
		assertEquals("source file 1", FileUtils.readFileToString(new File(destinationDirectory, sourceFile1.getName())));
		assertEquals("source file 2", FileUtils.readFileToString(new File(destinationDirectory, sourceFile2.getName())));
	}

	@Test
	public void settingUpResourcesDoesNotCopyResourcesIfTheyAlreadyExist() throws Exception {
		File existingSourceFile2 = new File(destinationDirectory, sourceFile2.getName());
		FileUtils.writeStringToFile(existingSourceFile2, "different content");

		doReturn(destinationDirectory).when(directoryProvider).getDirectory(DirectoryProvider.RESOURCE_FILES);
		Resource resource1 = mock(Resource.class);
		doReturn(sourceFile1).when(resource1).getResourceFile();
		Resource resource2 = mock(Resource.class);
		doReturn(sourceFile2).when(resource2).getResourceFile();
		doReturn(Arrays.asList(resource1, resource2)).when(resourcesProvider).getResources();

		testObject.setupResources();

		assertEquals(2, destinationDirectory.list().length);
		assertEquals("source file 1", FileUtils.readFileToString(new File(destinationDirectory, sourceFile1.getName())));
		assertEquals("different content", FileUtils.readFileToString(existingSourceFile2));
	}
}
