package net.todd.biblestudy.reference.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ReferenceDataSourceTest
{
	
	@Test
	public void testSearchWithReference()
	{
		ReferenceDataSource dataSource = new ReferenceDataSource()
		{
			@Override
			protected IBibleDao getBibleDao()
			{
				return new IBibleDao()
				{
					public List<BibleVerse> keywordLookup(String keyword)
					{
						List<BibleVerse> list = new ArrayList<BibleVerse>();
						BibleVerse verse = new BibleVerse();
						verse.setText("keyword result");
						list.add(verse);
						return list ;
					}

					public List<BibleVerse> referenceLookup(Reference reference)
					{
						List<BibleVerse> list = new ArrayList<BibleVerse>();
						
						if (reference.getBook().equalsIgnoreCase("john"))
						{
							BibleVerse verse = new BibleVerse();
							verse.setText("reference result");
							list.add(verse);
						}
						return list ;
					}
					
				};
			}

			@Override
			public String getId()
			{
				return null;
			}

			@Override
			public String getShortName()
			{
				return null;
			}
		};
		
		List<BibleVerse> referenceResults = dataSource.search("john 3:16");
		assertNotNull(referenceResults);
		assertEquals(1, referenceResults.size());
		assertEquals("reference result", referenceResults.get(0).getText());
		
		List<BibleVerse> keywordResults = dataSource.search("love");
		assertNotNull(keywordResults);
		assertEquals(1, keywordResults.size());
		assertEquals("keyword result", keywordResults.get(0).getText());
	}
}
