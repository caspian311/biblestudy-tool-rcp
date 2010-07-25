package net.todd.biblestudy.reference;

import static org.junit.Assert.assertEquals;

import java.util.List;

import net.todd.biblestudy.reference.BibleVerse;
import net.todd.biblestudy.reference.views.IReferenceView;
import net.todd.biblestudy.reference.views.ReferenceViewEvent;

import org.junit.Before;
import org.junit.Test;

public class ReferencePresenterEventHandlerTest
{
	private MockReferenceView referenceView;

	@Before
	public void setUp() throws Exception
	{
		referenceView = new MockReferenceView();
	}

	@Test
	public void testSearchingForKeywordSetsViewsTitleToMeaningfulText() throws Exception
	{
		referenceView.setLookupText("test");
		referenceView.setKeywordOrReference("keyword");
		referenceView.setReferenceSourceId("asdf");

		ReferencePresenter presenter = new ReferencePresenter(referenceView);
		presenter.handleEvent(new ReferenceViewEvent(ReferenceViewEvent.REFERENCE_VIEW_SEARCH));

		assertEquals("Keyword: test", referenceView.getTitle());
	}

	@Test
	public void testSearchingForReferenceSetsViewsTitleToMeaningfulText() throws Exception
	{
		referenceView.setLookupText("john 3:16");
		referenceView.setKeywordOrReference("reference");
		referenceView.setReferenceSourceId("asdf");

		ReferencePresenter presenter = new ReferencePresenter(referenceView);
		presenter.handleEvent(new ReferenceViewEvent(ReferenceViewEvent.REFERENCE_VIEW_SEARCH));

		assertEquals("Reference: john 3:16", referenceView.getTitle());
	}

	@Test
	public void testDoingShowEntireChapterSetsViewsTitleToMeaningfulText() throws Exception
	{
		BibleVerse bibleVerse = new BibleVerse();
		bibleVerse.setBook("John");
		bibleVerse.setChapter(12);
		referenceView.setSelectedVerse(bibleVerse);

		ReferencePresenter presenter = new ReferencePresenter(referenceView);
		presenter.handleEvent(new ReferenceViewEvent(
				ReferenceViewEvent.REFERENCE_VIEW_SHOW_ENTIRE_CHAPTER));

		assertEquals("Reference: John 12", referenceView.getTitle());
	}

	private class MockReferenceView implements IReferenceView
	{
		public void addReferenceViewListener(IReferenceViewListener listener)
		{
		}

		private BibleVerse selectedBibleVerse;

		public void setSelectedVerse(BibleVerse bibleVerse)
		{
			selectedBibleVerse = bibleVerse;
		}

		public BibleVerse getSelectedVerse()
		{
			return selectedBibleVerse;
		}

		private String title;

		public void setViewTitle(String title)
		{
			this.title = title;
		}

		public String getTitle()
		{
			return title;
		}

		public void displayErrorMessage(String message)
		{
		}

		public void displayLimitResultsMessage(int totalSize)
		{
		}

		public void fireEvent(ReferenceViewEvent event)
		{
		}

		private String keywordOrReference;

		public void setKeywordOrReference(String keywordOrReference)
		{
			this.keywordOrReference = keywordOrReference;
		}

		public String getKeywordOrReference()
		{
			return keywordOrReference;
		}

		private String lookupText;

		public void setLookupText(String lookupText)
		{
			this.lookupText = lookupText;
		}

		public String getLookupText()
		{
			return lookupText;
		}

		private String referenceSourceId;

		public void setReferenceSourceId(String referenceSourceId)
		{
			this.referenceSourceId = referenceSourceId;
		}

		public String getReferenceSourceId()
		{
			return referenceSourceId;
		}

		public void hideLimitResultsMessage()
		{
		}

		public void removeReferenceViewListener(IReferenceViewListener listener)
		{
		}

		public void setDataSourcesInDropDown(List<String> ids)
		{
		}

		public void setResults(BibleVerse[] results)
		{
		}

		public void showRightClickMenu()
		{
		}
	}
}
