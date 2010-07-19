package net.todd.biblestudy.rcp.views;

import java.util.List;

import net.todd.biblestudy.db.NoteStyle;
import net.todd.biblestudy.reference.BibleVerse;
import net.todd.biblestudy.reference.ReferenceTransfer;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class NoteView implements INoteView {
	private final ListenerManager noteContentModifiedListenerManager = new ListenerManager();
	private final ListenerManager createLinkListenerManager = new ListenerManager();
	private final ListenerManager createReferenceListenerManager = new ListenerManager();
	private final ListenerManager rightClickListenerManager = new ListenerManager();
	private final ListenerManager leftClickListenerManager = new ListenerManager();
	private final ListenerManager mouseHoveringListenerManager = new ListenerManager();
	private final ListenerManager contentDroppedInListenerManager = new ListenerManager();
	private final ListenerManager insertLinkToReferenceListenerManager = new ListenerManager();
	private final ListenerManager dropReferenceOptionListenerManager = new ListenerManager();
	private final ListenerManager dropReferenceWithTextListenerManager = new ListenerManager();

	private final Menu rightClickTextMenu;
	private final Menu dropReferenceOptionsMenu;

	private Point lastClickedLocation;
	private Point currentMouseLocation;

	private Point dropCoordinates;

	private final Composite parent;

	private final StyledText noteContentText;

	private final Color blueColor;
	private final Color greenColor;
	private final Color blackColor;

	private List<BibleVerse> droppedVerses;
	private final NoteViewPart parentViewPart;

	public NoteView(Composite parent, NoteViewPart parentViewPart) {
		this.parent = parent;
		this.parentViewPart = parentViewPart;

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().margins(2, 2).applyTo(composite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);

		noteContentText = new StyledText(parent, SWT.BORDER | SWT.MULTI
				| SWT.V_SCROLL | SWT.H_SCROLL);
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(noteContentText);
		noteContentText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				noteContentModifiedListenerManager.notifyListeners();
			}
		});
		noteContentText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				lastClickedLocation = new Point(e.x, e.y);
				if (isRightClick(e) || isMacRightClick(e)) {
					rightClickListenerManager.notifyListeners();
				} else if (e.stateMask == SWT.BUTTON1) {
					leftClickListenerManager.notifyListeners();
				}
			}
		});
		noteContentText.addMouseMoveListener(new MouseMoveListener() {
			@Override
			public void mouseMove(MouseEvent e) {
				currentMouseLocation = new Point(e.x, e.y);
				mouseHoveringListenerManager.notifyListeners();
			}
		});
		DropTarget dropTarget = new DropTarget(noteContentText, DND.DROP_MOVE);
		dropTarget
				.setTransfer(new Transfer[] { ReferenceTransfer.getInstance() });
		dropTarget.addDropListener(new DropTargetAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void drop(DropTargetEvent event) {
				dropCoordinates = new Point(event.x, event.y);
				droppedVerses = (List<BibleVerse>) event.data;

				contentDroppedInListenerManager.notifyListeners();
			}
		});

		rightClickTextMenu = new Menu(parent);
		rightClickTextMenu.setVisible(false);

		MenuItem createLinkToNote = new MenuItem(rightClickTextMenu, SWT.POP_UP);
		createLinkToNote.setText("Create Link to Note");
		createLinkToNote.setEnabled(true);
		createLinkToNote.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				createLinkListenerManager.notifyListeners();
			}
		});

		MenuItem createLinkToReference = new MenuItem(rightClickTextMenu,
				SWT.POP_UP);
		createLinkToReference.setText("Create Link to Reference");
		createLinkToReference.setEnabled(true);
		createLinkToReference.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				createReferenceListenerManager.notifyListeners();
			}
		});

		dropReferenceOptionsMenu = new Menu(parent);
		dropReferenceOptionsMenu.setVisible(false);

		MenuItem dropReferenceLink = new MenuItem(dropReferenceOptionsMenu,
				SWT.POP_UP);
		dropReferenceLink.setText("Insert Link to Reference");
		dropReferenceLink.setEnabled(true);
		dropReferenceLink.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				insertLinkToReferenceListenerManager.notifyListeners();
			}
		});

		MenuItem dropReferenceText = new MenuItem(dropReferenceOptionsMenu,
				SWT.POP_UP);
		dropReferenceText.setText("Insert Text");
		dropReferenceText.setEnabled(true);
		dropReferenceText.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dropReferenceOptionListenerManager.notifyListeners();
			}
		});

		MenuItem dropReferenceAndText = new MenuItem(dropReferenceOptionsMenu,
				SWT.POP_UP);
		dropReferenceAndText.setText("Insert Reference with Text");
		dropReferenceAndText.setEnabled(true);
		dropReferenceAndText.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dropReferenceWithTextListenerManager.notifyListeners();
			}
		});

		blueColor = new Color(Display.getDefault(), 0, 0, 255);
		greenColor = new Color(Display.getDefault(), 0, 150, 0);
		blackColor = new Color(Display.getDefault(), 0, 0, 0);

		parent.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				blackColor.dispose();
				blueColor.dispose();
				greenColor.dispose();
			}
		});
	}

	@Override
	public void setTitle(String title) {
		parentViewPart.setPartName(title);
	}

	@Override
	public void addNoteContentListener(IListener listener) {
		noteContentModifiedListenerManager.addListener(listener);
	}

	@Override
	public void addCreateLinkListener(IListener listener) {
		createLinkListenerManager.addListener(listener);
	}

	@Override
	public void addCreateReferenceListener(IListener listener) {
		createReferenceListenerManager.addListener(listener);
	}

	@Override
	public void addRightClickListener(IListener listener) {
		rightClickListenerManager.addListener(listener);
	}

	@Override
	public void addLeftClickListener(IListener listener) {
		leftClickListenerManager.addListener(listener);
	}

	@Override
	public void addMouseHoveringListener(IListener listener) {
		mouseHoveringListenerManager.addListener(listener);
	}

	@Override
	public void addContentDroppedInListener(IListener listener) {
		contentDroppedInListenerManager.addListener(listener);
	}

	@Override
	public void addInsertLinkToReferenceListener(IListener listener) {
		insertLinkToReferenceListenerManager.addListener(listener);
	}

	@Override
	public void addDropReferenceOptionListener(IListener listener) {
		dropReferenceOptionListenerManager.addListener(listener);
	}

	@Override
	public void addDropReferenceWithTextListener(IListener listener) {
		dropReferenceWithTextListenerManager.addListener(listener);
	}

	private boolean isMacRightClick(MouseEvent e) {
		return e.stateMask == (SWT.BUTTON1 | SWT.CTRL);
	}

	private boolean isRightClick(MouseEvent e) {
		return e.stateMask == SWT.BUTTON3;
	}

	@Override
	public int getCurrentCarretPosition() {
		return noteContentText.getCaretOffset();
	}

	@Override
	public Point getLastClickedCoordinates() {
		return lastClickedLocation;
	}

	@Override
	public void showRightClickPopup(int x, int y) {
		String selectionText = noteContentText.getSelectionText();

		if (selectionText == null || selectionText.length() != 0) {
			Point point = parent.toDisplay(x, y);
			rightClickTextMenu.setLocation(point);
			rightClickTextMenu.setVisible(true);
		}
	}

	@Override
	public void setContent(String content) {
		noteContentText.setText(content);
	}

	@Override
	public String getContent() {
		return noteContentText.getText();
	}

	@Override
	public String getSelectedContent() {
		return noteContentText.getSelectionText();
	}

	@Override
	public Point getCurrentMouseLocation() {
		return currentMouseLocation;
	}

	// @Override
	// public Point getSelectionPoint() {
	// return noteContentText.getSelection();
	// }

	@Override
	public void replaceNoteStyles(List<NoteStyle> styleList) {
		removeNoteStyles();

		for (NoteStyle style : styleList) {
			StyleRange styleRange = convertToStyleRange(style);

			noteContentText.setStyleRange(styleRange);
		}
	}

	@Override
	public void removeNoteStyles() {
		StyleRange styleRange = new StyleRange();
		styleRange.start = 0;
		styleRange.length = noteContentText.getText().length();
		styleRange.underline = false;
		styleRange.foreground = blackColor;

		noteContentText.setStyleRange(styleRange);
	}

	private StyleRange convertToStyleRange(NoteStyle style) {
		StyleRange styleRange = new StyleRange();
		styleRange.start = style.getStart();
		styleRange.length = style.getLength();
		styleRange.underline = style.isUnderlined();
		if (NoteStyle.Colors.BLUE.equals(style.getForeground())) {
			styleRange.foreground = blueColor;
		} else if (NoteStyle.Colors.GREEN.equals(style.getForeground())) {
			styleRange.foreground = greenColor;
		}

		return styleRange;
	}

	@Override
	public void changeCursorToPointer() {
		Cursor cursor = new Cursor(Display.getDefault(), SWT.CURSOR_HAND);
		noteContentText.setCursor(cursor);
	}

	@Override
	public void changeCursorToText() {
		Cursor cursor = new Cursor(Display.getDefault(), SWT.CURSOR_IBEAM);
		noteContentText.setCursor(cursor);
	}

	@Override
	public List<BibleVerse> getDroppedVerses() {
		return droppedVerses;
	}

	@Override
	public void showDropReferenceMenu(int x, int y) {
		dropReferenceOptionsMenu.setLocation(new Point(x, y));
		dropReferenceOptionsMenu.setVisible(true);
	}

	@Override
	public Point getDropCoordinates() {
		return dropCoordinates;
	}
}
