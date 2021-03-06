package net.todd.biblestudy.rcp;

@Deprecated
public class NoteModelManageLinksTest {
	// private static final String originalNoteContent = "blah blah blah";
	// private NoteModel noteModel;
	//
	// @Before
	// public void setup() throws Exception {
	// noteModel = new NoteModelHarness() {
	// @Override
	// protected Note getSampleNote() {
	// Note note = new Note();
	// note.setName("Test");
	// note.setNoteId(new Integer(1));
	// note.setText(originalNoteContent);
	// note.setLastModified(new Date());
	// return note;
	// }
	// };
	//
	// noteModel.populateNoteInfo(null);
	// }
	//
	// @Test
	// public void testAddLinkToNote() {
	// List<NoteStyle> noteStyles = noteModel.getNoteStylesForRange(0, 100);
	// assertEquals(0, noteStyles.size());
	//
	// noteModel.addLinkToNote("blah", 5, 9);
	//
	// // before link
	// noteStyles = noteModel.getNoteStylesForRange(0, 4);
	// assertEquals(0, noteStyles.size());
	//
	// // after link
	// noteStyles = noteModel.getNoteStylesForRange(10, 100);
	// assertEquals(0, noteStyles.size());
	//
	// // exactly on link
	// noteStyles = noteModel.getNoteStylesForRange(5, 9);
	// assertEquals(1, noteStyles.size());
	// assertEquals(new Integer(5), noteStyles.get(0).getStart());
	// assertEquals(new Integer(4), noteStyles.get(0).getLength());
	// assertTrue(noteStyles.get(0).isUnderlined());
	//
	// // starting before link but ending in middle of link
	// noteStyles = noteModel.getNoteStylesForRange(6, 8);
	// assertEquals(1, noteStyles.size());
	// assertEquals(new Integer(6), noteStyles.get(0).getStart());
	// assertEquals(new Integer(2), noteStyles.get(0).getLength());
	// assertTrue(noteStyles.get(0).isUnderlined());
	//
	// // inside link in both start and end
	// noteStyles = noteModel.getNoteStylesForRange(3, 7);
	// assertEquals(1, noteStyles.size());
	// assertEquals(new Integer(5), noteStyles.get(0).getStart());
	// assertEquals(new Integer(2), noteStyles.get(0).getLength());
	// assertTrue(noteStyles.get(0).isUnderlined());
	//
	// // starting in middle of linke and ending after link
	// noteStyles = noteModel.getNoteStylesForRange(7, 11);
	// assertEquals(1, noteStyles.size());
	// assertEquals(new Integer(7), noteStyles.get(0).getStart());
	// assertEquals(new Integer(2), noteStyles.get(0).getLength());
	// assertTrue(noteStyles.get(0).isUnderlined());
	// }
	//
	// @Test
	// public void testLinksStaySameWhenUpdateContentWithSameContent() throws
	// Exception {
	// noteModel.addLinkToNote("blah", 5, 9);
	//
	// List<NoteStyle> noteStyles = noteModel.getNoteStylesForRange(0, 100);
	// assertEquals(1, noteStyles.size());
	//
	// noteModel.updateContent(originalNoteContent);
	//
	// noteStyles = noteModel.getNoteStylesForRange(0, 4);
	// assertEquals(0, noteStyles.size());
	//
	// noteStyles = noteModel.getNoteStylesForRange(10, 100);
	// assertEquals(0, noteStyles.size());
	//
	// noteStyles = noteModel.getNoteStylesForRange(5, 9);
	// assertEquals(1, noteStyles.size());
	// assertEquals(new Integer(5), noteStyles.get(0).getStart());
	// assertEquals(new Integer(4), noteStyles.get(0).getLength());
	// assertTrue(noteStyles.get(0).isUnderlined());
	//
	// noteStyles = noteModel.getNoteStylesForRange(6, 8);
	// assertEquals(1, noteStyles.size());
	// assertEquals(new Integer(6), noteStyles.get(0).getStart());
	// assertEquals(new Integer(2), noteStyles.get(0).getLength());
	// assertTrue(noteStyles.get(0).isUnderlined());
	//
	// noteStyles = noteModel.getNoteStylesForRange(3, 7);
	// assertEquals(1, noteStyles.size());
	// assertEquals(new Integer(5), noteStyles.get(0).getStart());
	// assertEquals(new Integer(2), noteStyles.get(0).getLength());
	// assertTrue(noteStyles.get(0).isUnderlined());
	//
	// noteStyles = noteModel.getNoteStylesForRange(7, 11);
	// assertEquals(1, noteStyles.size());
	// assertEquals(new Integer(7), noteStyles.get(0).getStart());
	// assertEquals(new Integer(2), noteStyles.get(0).getLength());
	// assertTrue(noteStyles.get(0).isUnderlined());
	// }
	//
	// @Test
	// public void
	// testLinksAreRemovedWhenUpdateContentWithCompletelyDifferentContent()
	// throws Exception {
	// List<NoteStyle> noteStyles = noteModel.getNoteStylesForRange(0, 100);
	// assertEquals(0, noteStyles.size());
	//
	// noteModel.addLinkToNote("blah", 5, 9);
	//
	// noteModel.updateContent("woot woot woot");
	//
	// noteStyles = noteModel.getNoteStylesForRange(0, 4);
	// assertEquals(0, noteStyles.size());
	//
	// noteStyles = noteModel.getNoteStylesForRange(10, 100);
	// assertEquals(0, noteStyles.size());
	//
	// noteStyles = noteModel.getNoteStylesForRange(5, 9);
	// assertEquals(0, noteStyles.size());
	//
	// noteStyles = noteModel.getNoteStylesForRange(6, 8);
	// assertEquals(0, noteStyles.size());
	//
	// noteStyles = noteModel.getNoteStylesForRange(3, 7);
	// assertEquals(0, noteStyles.size());
	//
	// noteStyles = noteModel.getNoteStylesForRange(7, 11);
	// assertEquals(0, noteStyles.size());
	// }
	//
	// @Test
	// public void testLinksShiftRightWhenContentAddedBeforeLink() throws
	// Exception {
	// List<NoteStyle> noteStyles = noteModel.getNoteStylesForRange(0, 100);
	// assertEquals(0, noteStyles.size());
	//
	// noteModel.addLinkToNote("blah", 5, 9);
	//
	// noteModel.updateContent("woot " + originalNoteContent);
	//
	// // new link should be at 10, 14
	//
	// // before link
	// noteStyles = noteModel.getNoteStylesForRange(0, 9);
	// assertEquals(0, noteStyles.size());
	//
	// // after link
	// noteStyles = noteModel.getNoteStylesForRange(15, 100);
	// assertEquals(0, noteStyles.size());
	//
	// // exactly on link
	// noteStyles = noteModel.getNoteStylesForRange(10, 14);
	// assertEquals(1, noteStyles.size());
	// assertEquals(new Integer(10), noteStyles.get(0).getStart());
	// assertEquals(new Integer(4), noteStyles.get(0).getLength());
	// assertTrue(noteStyles.get(0).isUnderlined());
	//
	// // inside link in both start and end
	// noteStyles = noteModel.getNoteStylesForRange(11, 13);
	// assertEquals(1, noteStyles.size());
	// assertEquals(new Integer(11), noteStyles.get(0).getStart());
	// assertEquals(new Integer(2), noteStyles.get(0).getLength());
	// assertTrue(noteStyles.get(0).isUnderlined());
	//
	// // starting before link but ending in middle of link
	// noteStyles = noteModel.getNoteStylesForRange(8, 12);
	// assertEquals(1, noteStyles.size());
	// assertEquals(new Integer(10), noteStyles.get(0).getStart());
	// assertEquals(new Integer(2), noteStyles.get(0).getLength());
	// assertTrue(noteStyles.get(0).isUnderlined());
	//
	// // starting in middle of link and ending after link
	// noteStyles = noteModel.getNoteStylesForRange(12, 16);
	// assertEquals(1, noteStyles.size());
	// assertEquals(new Integer(12), noteStyles.get(0).getStart());
	// assertEquals(new Integer(2), noteStyles.get(0).getLength());
	// assertTrue(noteStyles.get(0).isUnderlined());
	// }
	//
	// @Test
	// public void testAddOneSpaceBeforeLinkAndLinkShouldMoveOneSpace() throws
	// Exception {
	// noteModel.updateContent("blah");
	//
	// List<NoteStyle> noteStyles = noteModel.getNoteStylesForRange(0, 100);
	// assertEquals(0, noteStyles.size());
	//
	// noteModel.addLinkToNote("blah", 0, 4);
	//
	// noteStyles = noteModel.getNoteStylesForRange(0, 100);
	// assertEquals(1, noteStyles.size());
	//
	// NoteStyle noteStyle = noteStyles.get(0);
	// assertEquals(0, noteStyle.getStart().intValue());
	// assertEquals(4, noteStyle.getLength().intValue());
	//
	// noteModel.updateContent(" blah");
	//
	// noteStyles = noteModel.getNoteStylesForRange(0, 100);
	// assertEquals(1, noteStyles.size());
	//
	// noteStyle = noteStyles.get(0);
	// assertEquals(1, noteStyle.getStart().intValue());
	// assertEquals(4, noteStyle.getLength().intValue());
	// }
	//
	// @Test
	// public void testLinksShiftsLeftWhenContentRemovedBeforeLink() throws
	// Exception {
	// noteModel.updateContent("blah blah blah");
	//
	// List<NoteStyle> noteStyles = noteModel.getNoteStylesForRange(0, 100);
	// assertEquals(0, noteStyles.size());
	//
	// noteModel.addLinkToNote("blah", 5, 9);
	//
	// noteStyles = noteModel.getNoteStylesForRange(0, 100);
	// assertEquals(1, noteStyles.size());
	// NoteStyle noteStyle = noteStyles.get(0);
	//
	// assertEquals(5, noteStyle.getStart().intValue());
	// assertEquals(4, noteStyle.getLength().intValue());
	//
	// noteModel.updateContent("bla blah blah");
	//
	// noteStyles = noteModel.getNoteStylesForRange(0, 100);
	// assertEquals(1, noteStyles.size());
	// noteStyle = noteStyles.get(0);
	//
	// assertEquals(4, noteStyle.getStart().intValue());
	// assertEquals(4, noteStyle.getLength().intValue());
	// }
	//
	// @Test
	// public void
	// testLinksShiftsLeftWhenMultipleCharactersAreRemovedBeforeLink() throws
	// Exception {
	// noteModel.updateContent("blah blah blah");
	//
	// List<NoteStyle> noteStyles = noteModel.getNoteStylesForRange(0, 100);
	// assertEquals(0, noteStyles.size());
	//
	// noteModel.addLinkToNote("blah", 5, 9);
	//
	// noteStyles = noteModel.getNoteStylesForRange(0, 100);
	// assertEquals(1, noteStyles.size());
	// NoteStyle noteStyle = noteStyles.get(0);
	//
	// assertEquals(5, noteStyle.getStart().intValue());
	// assertEquals(4, noteStyle.getLength().intValue());
	//
	// noteModel.updateContent("bl blah blah");
	//
	// noteStyles = noteModel.getNoteStylesForRange(0, 100);
	// assertEquals(1, noteStyles.size());
	// noteStyle = noteStyles.get(0);
	//
	// assertEquals(3, noteStyle.getStart().intValue());
	// assertEquals(4, noteStyle.getLength().intValue());
	// }
}
