package net.todd.biblestudy.rcp;

@Deprecated
public class UpdatingLinksInNoteModelTest {
	// private NoteModel noteModel;
	//
	// @Before
	// public void setup() throws Exception {
	// noteModel = new NoteModelHarness() {
	// @Override
	// protected Note getSampleNote() {
	// Note note = new Note();
	// note.setLastModified(new Date());
	//
	// return note;
	// }
	// };
	//
	// noteModel.populateNoteInfo(null);
	// }
	//
	// @Test
	// public void testLocationAndLengthOfNewText() throws Exception {
	// noteModel.getNote().setText("abcdefg");
	//
	// String newContent = "abczdefg";
	//
	// int location = noteModel.findLocationOfNewText(newContent);
	// int differenceLength = noteModel.findLengthOfDifferingText(newContent);
	//
	// assertEquals(newContent.indexOf("z"), location);
	// assertEquals("z".length(), differenceLength);
	//
	// noteModel.getNote().setText("abcdefg");
	//
	// newContent = "abc z defg";
	//
	// location = noteModel.findLocationOfNewText(newContent);
	// differenceLength = noteModel.findLengthOfDifferingText(newContent);
	//
	// assertEquals(newContent.indexOf(" z "), location);
	// assertEquals(" z ".length(), differenceLength);
	//
	// noteModel.getNote().setText("a b c d e f g");
	//
	// newContent = "a b c z d e f g";
	//
	// location = noteModel.findLocationOfNewText(newContent);
	// differenceLength = noteModel.findLengthOfDifferingText(newContent);
	//
	// assertEquals(newContent.indexOf("z "), location);
	// assertEquals("z ".length(), differenceLength);
	// }
	//
	// @Test
	// public void testLocationAndLengthOfNewTextWhenJustAddingOneWhiteSpace()
	// throws Exception {
	// noteModel.getNote().setText("test");
	//
	// String newContent = " test";
	//
	// int location = noteModel.findLocationOfNewText(newContent);
	// int differenceLength = noteModel.findLengthOfDifferingText(newContent);
	//
	// assertEquals(0, location);
	// assertEquals(1, differenceLength);
	// }
	//
	// @Test
	// public void
	// testLocationAndLengthOfNewTextWhenRemovingTwoCharactersAtOnce() throws
	// Exception {
	// noteModel.getNote().setText("test");
	//
	// String newContent = " test";
	//
	// int location = noteModel.findLocationOfNewText(newContent);
	// int differenceLength = noteModel.findLengthOfDifferingText(newContent);
	//
	// assertEquals(0, location);
	// assertEquals(1, differenceLength);
	// }
	//
	// @Test
	// public void testLocationAndLengthOfNewTextWhenAddingTwoWhiteSpaces()
	// throws Exception {
	// noteModel.getNote().setText("test test");
	//
	// String newContent = "te test";
	//
	// int location = noteModel.findLocationOfNewText(newContent);
	// int differenceLength = noteModel.findLengthOfDifferingText(newContent);
	//
	// assertEquals(2, location);
	// assertEquals(2, differenceLength);
	// }
	//
	// @Test
	// @Ignore
	// public void testLocationAndLengthOfNewTextWhenAddingSameLetterToFront()
	// throws Exception {
	// // FIXME: this needs a fixin'
	// noteModel.getNote().setText("abcd");
	//
	// String newContent = "aabcd";
	//
	// int location = noteModel.findLengthOfDifferingText(newContent);
	// int differenceLength = noteModel.findLengthOfDifferingText(newContent);
	//
	// assertEquals(1, differenceLength);
	// assertEquals(0, location);
	// }
}
