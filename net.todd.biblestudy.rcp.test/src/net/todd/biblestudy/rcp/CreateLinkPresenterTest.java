package net.todd.biblestudy.rcp;

@Deprecated
public class CreateLinkPresenterTest {
	// private MockCreateLinkDialog mockCreateLinkDialog;
	// private MockNoteView mockNoteView;
	// private MockNoteModel mockNoteModel;
	//
	// @Before
	// public void setUp() throws Exception {
	// mockCreateLinkDialog = new MockCreateLinkDialog();
	// mockNoteView = new MockNoteView();
	// mockNoteModel = new MockNoteModel();
	// }
	//
	// @Test
	// public void testCreateThePresenter() throws Exception {
	// new CreateLinkToDialogPresenter(mockCreateLinkDialog, mockNoteView,
	// mockNoteModel, false);
	// assertTrue(mockCreateLinkDialog.wasListenerAdded());
	// assertTrue(mockCreateLinkDialog.wasDialogOpened());
	// }
	//
	// @Test
	// public void testHandleDialogOpened() throws Exception {
	// ICreateLinkListener presenter = new
	// CreateLinkToDialogPresenter(mockCreateLinkDialog, mockNoteView,
	// mockNoteModel, false);
	//
	// mockNoteView.setSelectedText("woot");
	// presenter.handleCreateLinkEvent(new
	// ViewEvent(ViewEvent.CREATE_LINK_DIALOG_OPENED));
	// assertEquals("woot", mockCreateLinkDialog.getSelectedText());
	//
	// mockNoteView.setSelectedText("test");
	// presenter.handleCreateLinkEvent(new
	// ViewEvent(ViewEvent.CREATE_LINK_DIALOG_OPENED));
	// assertEquals("test", mockCreateLinkDialog.getSelectedText());
	// }
	//
	// @Test
	// public void testHandleDialogClosed() throws Exception {
	// ICreateLinkListener presenter = new
	// CreateLinkToDialogPresenter(mockCreateLinkDialog, mockNoteView,
	// mockNoteModel, false);
	// assertTrue(mockCreateLinkDialog.wasListenerAdded());
	// presenter.handleCreateLinkEvent(new
	// ViewEvent(ViewEvent.CREATE_LINK_DIALOG_CLOSED));
	// assertFalse(mockCreateLinkDialog.wasListenerAdded());
	// }
	//
	// @Test
	// public void testHandleDoCreateLinkToNote() throws Exception {
	// ICreateLinkListener presenter = new
	// CreateLinkToDialogPresenter(mockCreateLinkDialog, mockNoteView,
	// mockNoteModel, false);
	//
	// assertTrue(mockCreateLinkDialog.wasDialogOpened());
	//
	// mockCreateLinkDialog.setLinkText("test");
	// mockNoteView.setSelectionPoint(new Point(1, 2));
	//
	// presenter.handleCreateLinkEvent(new
	// ViewEvent(ViewEvent.CREATE_LINK_DO_CREATE_LINK_TO_NOTE));
	//
	// assertFalse(mockCreateLinkDialog.wasDialogOpened());
	//
	// assertEquals("test", mockNoteModel.getLinkToNoteName());
	// assertEquals(1, mockNoteModel.getLinkToNoteStart());
	// assertEquals(2, mockNoteModel.getLinkToNoteStop());
	//
	// mockCreateLinkDialog.setLinkText("woot");
	// mockNoteView.setSelectionPoint(new Point(3, 4));
	//
	// presenter.handleCreateLinkEvent(new
	// ViewEvent(ViewEvent.CREATE_LINK_DO_CREATE_LINK_TO_NOTE));
	//
	// assertEquals("woot", mockNoteModel.getLinkToNoteName());
	// assertEquals(3, mockNoteModel.getLinkToNoteStart());
	// assertEquals(4, mockNoteModel.getLinkToNoteStop());
	// }
	//
	// @Test
	// public void testHandleDoCreateLinkToReference() throws Exception {
	// ICreateLinkListener presenter = new
	// CreateLinkToDialogPresenter(mockCreateLinkDialog, mockNoteView,
	// mockNoteModel, false);
	//
	// assertTrue(mockCreateLinkDialog.wasDialogOpened());
	//
	// mockCreateLinkDialog.setLinkText("test");
	// mockNoteView.setSelectionPoint(new Point(1, 2));
	//
	// presenter.handleCreateLinkEvent(new
	// ViewEvent(ViewEvent.CREATE_LINK_DO_CREATE_LINK_TO_REFERENCE));
	//
	// assertFalse(mockCreateLinkDialog.wasDialogOpened());
	//
	// assertNull(mockNoteModel.getLinkToNoteName());
	// assertEquals("test", mockNoteModel.getLinkToReference());
	// assertEquals(1, mockNoteModel.getLinkToNoteStart());
	// assertEquals(2, mockNoteModel.getLinkToNoteStop());
	//
	// mockCreateLinkDialog.setLinkText("woot");
	// mockNoteView.setSelectionPoint(new Point(3, 4));
	//
	// presenter.handleCreateLinkEvent(new
	// ViewEvent(ViewEvent.CREATE_LINK_DO_CREATE_LINK_TO_REFERENCE));
	//
	// assertNull(mockNoteModel.getLinkToNoteName());
	// assertEquals("woot", mockNoteModel.getLinkToReference());
	// assertEquals(3, mockNoteModel.getLinkToNoteStart());
	// assertEquals(4, mockNoteModel.getLinkToNoteStop());
	// }
	//
	// @Test
	// public void testValidateReference() throws Exception {
	// ICreateLinkListener presenter = new
	// CreateLinkToDialogPresenter(mockCreateLinkDialog, mockNoteView,
	// mockNoteModel, false);
	//
	// mockCreateLinkDialog.setLinkText(null);
	// presenter.handleCreateLinkEvent(new
	// ViewEvent(ViewEvent.CREATE_LINK_VALIDATE_REFERENCE));
	// assertTrue(mockCreateLinkDialog.errorMessageShown());
	//
	// mockCreateLinkDialog.setLinkText("");
	// presenter.handleCreateLinkEvent(new
	// ViewEvent(ViewEvent.CREATE_LINK_VALIDATE_REFERENCE));
	// assertTrue(mockCreateLinkDialog.errorMessageShown());
	//
	// mockCreateLinkDialog.setLinkText("test");
	// presenter.handleCreateLinkEvent(new
	// ViewEvent(ViewEvent.CREATE_LINK_VALIDATE_REFERENCE));
	// assertFalse(mockCreateLinkDialog.errorMessageShown());
	//
	// mockCreateLinkDialog.setLinkText("1 test");
	// presenter.handleCreateLinkEvent(new
	// ViewEvent(ViewEvent.CREATE_LINK_VALIDATE_REFERENCE));
	// assertFalse(mockCreateLinkDialog.errorMessageShown());
	// }
	//
	// private class MockNoteModel implements INoteModel {
	// private String linkToNoteName;
	// private int linkToNoteStart;
	// private int linkToNoteStop;
	//
	// public String getLinkToNoteName() {
	// return linkToNoteName;
	// }
	//
	// public int getLinkToNoteStart() {
	// return linkToNoteStart;
	// }
	//
	// public int getLinkToNoteStop() {
	// return linkToNoteStop;
	// }
	//
	// public void addLinkToNote(String noteName, int start, int stop) {
	// this.linkToNoteName = noteName;
	// this.linkToNoteStart = start;
	// this.linkToNoteStop = stop;
	// }
	//
	// private String linkToReference;
	//
	// public String getLinkToReference() {
	// return linkToReference;
	// }
	//
	// public void addLinkToReference(Reference reference, int start, int stop)
	// {
	// this.linkToReference = reference.toString();
	// this.linkToNoteStart = start;
	// this.linkToNoteStop = stop;
	// }
	//
	// public void createNewNoteInfo(String noteName) {
	// }
	//
	// public void deleteNoteAndLinks() {
	// }
	//
	// public Link getLinkAtOffset(int offset) {
	// return null;
	// }
	//
	// public Note getNote() {
	// return null;
	// }
	//
	// public List<NoteStyle> getNoteStylesForRange(int lineOffset, int length)
	// {
	// return null;
	// }
	//
	// @Override
	// public boolean isDocumentDirty() {
	// return false;
	// }
	//
	// public void populateNoteInfo(String noteName) {
	// }
	//
	// public void registerModelListener(INoteModelListener listener) {
	// }
	//
	// @Override
	// public void save() {
	// }
	//
	// public void unRegisterModelListener(INoteModelListener listener) {
	// }
	//
	// public void updateContent(String newContentText) {
	// }
	// }
	//
	// private class MockNoteView implements INoteView {
	// public void addNoteViewListener(INoteViewListener noteListener) {
	// }
	//
	// public void changeCursorToPointer() {
	// }
	//
	// public void changeCursorToText() {
	// }
	//
	// public void closeView(String noteName) {
	// }
	//
	// public void deleteNote() {
	// }
	//
	// @Override
	// public String getContent() {
	// return null;
	// }
	//
	// @Override
	// public int getCurrentCarretPosition() {
	// return 0;
	// }
	//
	// public Point getDropCoordinates() {
	// return null;
	// }
	//
	// public List<BibleVerse> getDroppedVerses() {
	// return null;
	// }
	//
	// @Override
	// public Point getLastClickedCoordinates() {
	// return null;
	// }
	//
	// private String selectedText;
	//
	// public void setSelectedText(String string) {
	// selectedText = string;
	// }
	//
	// @Override
	// public String getSelectedContent() {
	// return selectedText;
	// }
	//
	// private Point selectionPoint;
	//
	// public void setSelectionPoint(Point selectionPoint) {
	// this.selectionPoint = selectionPoint;
	// }
	//
	// public Point getSelectionPoint() {
	// return selectionPoint;
	// }
	//
	// public int openDeleteConfirmationWindow() {
	// return 0;
	// }
	//
	// public void showDropReferenceMenu(int x, int y) {
	// }
	//
	// public void removeNoteStyles() {
	// }
	//
	// public void removeNoteViewListener(INoteViewListener noteListener) {
	// }
	//
	// public void replaceNoteStyles(List<NoteStyle> list) {
	// }
	//
	// public void saveNote() {
	// }
	//
	// @Override
	// public void setContent(String text) {
	// }
	//
	// public void setViewTitle(String title) {
	// }
	//
	// @Override
	// public void showRightClickPopup(int x, int y) {
	// }
	//
	// public void changesToNoteTextFiresEvent(boolean makeChangesToNoteText) {
	// }
	// }
	//
	// private class MockCreateLinkDialog implements ICreateLinkDialogView {
	// private boolean listenerAdded = false;
	//
	// public boolean wasListenerAdded() {
	// return listenerAdded;
	// }
	//
	// public void addCreateLinkListener(ICreateLinkListener createLinklistener)
	// {
	// listenerAdded = true;
	// }
	//
	// public void removeCreateLinkListener(ICreateLinkListener
	// createLinkListener) {
	// listenerAdded = false;
	// }
	//
	// private String linkText;
	//
	// @Override
	// public void setLinkText(String linkText) {
	// this.linkText = linkText;
	// }
	//
	// @Override
	// public String getLinkText() {
	// return linkText;
	// }
	//
	// private boolean dialogWasOpened = false;
	//
	// public boolean wasDialogOpened() {
	// return dialogWasOpened;
	// }
	//
	// public void openDialog(boolean isLinkToReference) {
	// dialogWasOpened = true;
	// }
	//
	// public void closeDialog() {
	// dialogWasOpened = false;
	// }
	//
	// private String selectedText;
	//
	// public String getSelectedText() {
	// return selectedText;
	// }
	//
	// public void setLinkText(String selectionText) {
	// this.selectedText = selectionText;
	// }
	//
	// private boolean errorMessageShown = false;
	//
	// @Override
	// public void showErrorMessage() {
	// errorMessageShown = true;
	// }
	//
	// public boolean errorMessageShown() {
	// return errorMessageShown;
	// }
	//
	// @Override
	// public void hideErrorMessage() {
	// errorMessageShown = false;
	// }
	// }
}
