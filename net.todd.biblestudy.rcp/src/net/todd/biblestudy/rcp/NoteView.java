package net.todd.biblestudy.rcp;

import net.todd.biblestudy.common.AbstractMvpView;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class NoteView extends AbstractMvpView implements INoteView {
	private Point lastClickedLocation;
	private Point currentMouseLocation;

	private final StyledText noteContentText;
	private final Color blueColor;
	private final Color greenColor;
	private final Color blackColor;

	private final NoteViewPart parentViewPart;

	public NoteView(Composite parent, NoteViewPart parentViewPart) {
		super(parent);
		this.parentViewPart = parentViewPart;

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().margins(2, 2).applyTo(composite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);

		noteContentText = new StyledText(composite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(noteContentText);
		noteContentText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				notifyListeners(CONTENT);
			}
		});
		noteContentText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				lastClickedLocation = noteContentText.toDisplay(new Point(e.x, e.y));
				if (isRightClick(e) || isMacRightClick(e)) {
					notifyListeners(RIGHT_CLICK);
				} else if (e.stateMask == SWT.BUTTON1) {
					notifyListeners(LEFT_CLICK);
				}
			}
		});
		noteContentText.addMouseMoveListener(new MouseMoveListener() {
			@Override
			public void mouseMove(MouseEvent e) {
				currentMouseLocation = new Point(e.x, e.y);
				notifyListeners(MOUSE_HOVER);
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
	public void setCurrentCarretPosition(int offset) {
		noteContentText.setCaretOffset(offset);
	}

	@Override
	public Point getLastClickedCoordinates() {
		return lastClickedLocation;
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

	@Override
	public void focusReceived() {
		notifyListeners(FOCUS_RECEIVED);
	}
}

// private final Menu dropReferenceOptionsMenu;
// private Point dropCoordinates;
// private List<Verse> droppedVerses;

// noteContentText.addKeyListener(new KeyAdapter() {
// @Override
// public void keyReleased(KeyEvent e) {
// notifyListeners(CONTENT);
// }
// });
// DropTarget dropTarget = new DropTarget(noteContentText,
// DND.DROP_MOVE);
// dropTarget.setTransfer(new Transfer[] {
// ReferenceTransfer.getInstance() });
// dropTarget.addDropListener(new DropTargetAdapter() {
// @SuppressWarnings("unchecked")
// @Override
// public void drop(DropTargetEvent event) {
// dropCoordinates = new Point(event.x, event.y);
// droppedVerses = (List<Verse>) event.data;
//
// notifyListeners(CONTENT_DROPPED);
// }
// });

// MenuItem createLinkToReference = new MenuItem(rightClickTextMenu,
// SWT.POP_UP);
// createLinkToReference.setText("Create Link to Reference");
// createLinkToReference.setEnabled(true);
// createLinkToReference.addSelectionListener(new SelectionAdapter() {
// @Override
// public void widgetSelected(SelectionEvent e) {
// notifyListeners(CREATE_REFERENCE);
// }
// });

// dropReferenceOptionsMenu = new Menu(parent);
// dropReferenceOptionsMenu.setVisible(false);

// MenuItem dropReferenceLink = new MenuItem(dropReferenceOptionsMenu,
// SWT.POP_UP);
// dropReferenceLink.setText("Insert Link to Reference");
// dropReferenceLink.setEnabled(true);
// dropReferenceLink.addSelectionListener(new SelectionAdapter() {
// @Override
// public void widgetSelected(SelectionEvent e) {
// notifyListeners(INSERT_LINK_TO_REFERENCE);
// }
// });

// MenuItem dropReferenceText = new MenuItem(dropReferenceOptionsMenu,
// SWT.POP_UP);
// dropReferenceText.setText("Insert Text");
// dropReferenceText.setEnabled(true);
// dropReferenceText.addSelectionListener(new SelectionAdapter() {
// @Override
// public void widgetSelected(SelectionEvent e) {
// notifyListeners(DROP_REFERENCE_OPTION);
// }
// });

// MenuItem dropReferenceAndText = new
// MenuItem(dropReferenceOptionsMenu, SWT.POP_UP);
// dropReferenceAndText.setText("Insert Reference with Text");
// dropReferenceAndText.setEnabled(true);
// dropReferenceAndText.addSelectionListener(new SelectionAdapter() {
// @Override
// public void widgetSelected(SelectionEvent e) {
// notifyListeners(DROP_REFERENCE_OPTION_WITH_TEXT);
// }
// });

// @Override
// public Point getSelectionPoint() {
// return noteContentText.getSelection();
// }

// @Override
// public void replaceNoteStyles(List<NoteStyle> styleList) {
// removeNoteStyles();
//
// for (NoteStyle style : styleList) {
// StyleRange styleRange = convertToStyleRange(style);
//
// noteContentText.setStyleRange(styleRange);
// }
// }
//
// @Override
// public void removeNoteStyles() {
// StyleRange styleRange = new StyleRange();
// styleRange.start = 0;
// styleRange.length = noteContentText.getText().length();
// styleRange.underline = false;
// styleRange.foreground = blackColor;
//
// noteContentText.setStyleRange(styleRange);
// }
//
// private StyleRange convertToStyleRange(NoteStyle style) {
// StyleRange styleRange = new StyleRange();
// styleRange.start = style.getStart();
// styleRange.length = style.getLength();
// styleRange.underline = style.isUnderlined();
// if (NoteStyle.Colors.BLUE.equals(style.getForeground())) {
// styleRange.foreground = blueColor;
// } else if (NoteStyle.Colors.GREEN.equals(style.getForeground())) {
// styleRange.foreground = greenColor;
// }
//
// return styleRange;
// }
//
// @Override
// public void changeCursorToPointer() {
// final Cursor cursor = new Cursor(Display.getDefault(), SWT.CURSOR_HAND);
// noteContentText.setCursor(cursor);
// noteContentText.addDisposeListener(new DisposeListener() {
// @Override
// public void widgetDisposed(DisposeEvent e) {
// cursor.dispose();
// }
// });
// }
//
// @Override
// public void changeCursorToText() {
// final Cursor cursor = new Cursor(Display.getDefault(), SWT.CURSOR_IBEAM);
// noteContentText.setCursor(cursor);
// noteContentText.addDisposeListener(new DisposeListener() {
// @Override
// public void widgetDisposed(DisposeEvent e) {
// cursor.dispose();
// }
// });
// }

// @Override
// public List<Verse> getDroppedVerses() {
// return droppedVerses;
// }

// @Override
// public void showDropReferenceMenu(int x, int y) {
// dropReferenceOptionsMenu.setLocation(new Point(x, y));
// dropReferenceOptionsMenu.setVisible(true);
// }

// @Override
// public Point getDropCoordinates() {
// return dropCoordinates;
// }
