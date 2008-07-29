package net.todd.biblestudy.rcp.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SetupDatabaseModelSqlFileTest
{
	private SetupDatabaseModel model;

	@Before
	public void setUp()
	{
		model = new SetupDatabaseModel(null);
	}

	@Test
	public void testGetLinesFromSqlFile()
	{
		String contents = "test one two three;\nwoot\ntest\nwoot;\nblah blah;";
		List<String> statements = model.getLinesFromFileContents(contents);

		assertNotNull(statements);
		assertEquals(3, statements.size());
		assertEquals("test one two three;", statements.get(0));
		assertEquals("woot\ntest\nwoot;", statements.get(1));
		assertEquals("blah blah;", statements.get(2));
	}
}
