package net.todd.biblestudy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DataTest {
	@Test
	public void testAddDatum() {
		SearchableData data = new SearchableData();
		data.addDatum(SearchableData.Type.ID, "bar");

		assertEquals("bar", data.getValue(SearchableData.Type.ID));
	}
}
