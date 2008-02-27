package net.todd.biblestudy.rcp.views;

import java.util.List;

import javax.swing.event.EventListenerList;

import net.todd.biblestudy.db.NoteStyle;
import net.todd.biblestudy.rcp.presenters.INoteListener;
import net.todd.biblestudy.rcp.presenters.ViewEvent;
import net.todd.biblestudy.reference.common.BibleVerse;
import net.todd.biblestudy.reference.common.ReferenceTransfer;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.part.ViewPart;

public class NoteView extends ViewPart implements INoteView
{
	public static final String ID = "net.todd.biblestudy.rcp.NoteView";

	private EventListenerList eventListeners = new EventListenerList();

	private Menu rightClickTextMenu;
	private Menu dropReferenceOptionsMenu;

	private Point lastClickedCoordinates;
	private Point dropCoordinates;

	private Composite parent;

	private StyledText noteContentText;

	private Color blueColor;
	private Color greenColor;
	private Color blackColor;

	private List<BibleVerse> droppedVerses;

	@Override
	public void createPartControl(Composite parent)
	{
		this.parent = parent;

		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginTop = 2;
		gridLayout.marginBottom = 2;
		gridLayout.marginLeft = 2;
		gridLayout.marginRight = 2;

		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);

		Composite composite = new Composite(parent, SWT.NONE);

		composite.setLayout(gridLayout);
		composite.setLayoutData(gridData);

		createTextBox(composite);
		createRightClickMenu(parent);
		createDropReferenceOptions(parent);

		createColors();
	}

	private void createColors()
	{
		blueColor = new Color(Display.getDefault(), 0, 0, 255);
		greenColor = new Color(Display.getDefault(), 0, 150, 0);
		blackColor = new Color(Display.getDefault(), 0, 0, 0);
	}

	private void createRightClickMenu(Composite parent)
	{
		rightClickTextMenu = new Menu(parent);
		rightClickTextMenu.setVisible(false);

		MenuItem createLinkToNote = new MenuItem(rightClickTextMenu, SWT.POP_UP);
		createLinkToNote.setText("Create Link to Note");
		createLinkToNote.setEnabled(true);
		createLinkToNote.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				fireEvent(new ViewEvent(ViewEvent.NOTE_CREATE_LINK_TO_NOTE_EVENT));
			}
		});

		MenuItem createLinkToReference = new MenuItem(rightClickTextMenu, SWT.POP_UP);
		createLinkToReference.setText("Create Link to Reference");
		createLinkToReference.setEnabled(true);
		createLinkToReference.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				fireEvent(new ViewEvent(ViewEvent.NOTE_CREATE_LINK_TO_REFERENCE_EVENT));
			}
		});
	}

	private void fireEvent(ViewEvent event)
	{
		INoteListener[] listeners = eventListeners.getListeners(INoteListener.class);

		for (INoteListener listener : listeners)
		{
			listener.handleEvent(event);
		}
	}

	private void createTextBox(Composite parent)
	{
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);

		noteContentText = new StyledText(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL
				| SWT.H_SCROLL);
		noteContentText.setLayoutData(gridData);

		noteContentText.setLayoutData(gridData);
		noteContentText.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				fireEvent(new ViewEvent(ViewEvent.NOTE_CONTENT_CHANGED));
			}
		});

		setupMouseListeners();
		makeDropable();
	}

	private void setupMouseListeners()
	{
		noteContentText.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseUp(MouseEvent e)
			{
				if (e.stateMask == SWT.BUTTON3 || e.stateMask == (SWT.BUTTON1 | SWT.CTRL))
				{ // right-click and ctrl+mouse1 for macs
					lastClickedCoordinates = new Point(e.x, e.y);
					fireEvent(new ViewEvent(ViewEvent.NOTE_SHOW_RIGHT_CLICK_MENU));
				}
				if (e.stateMask == SWT.BUTTON1)
				{
					Point point = new Point(e.x, e.y);

					try
					{
						int offset = noteContentText.getOffsetAtLocation(point);

						ViewEvent viewEvent = new ViewEvent(ViewEvent.NOTE_CLICKED);
						viewEvent.setData(offset);
						fireEvent(viewEvent);
					}
					catch (IllegalArgumentException e1)
					{
					}
				}
			}
		});
		noteContentText.addMouseMoveListener(new MouseMoveListener()
		{
			public void mouseMove(MouseEvent e)
			{
				ViewEvent viewEvent = new ViewEvent(ViewEvent.NOTE_HOVERING);

				Point point = new Point(e.x, e.y);

				try
				{
					Integer offset = new Integer(noteContentText.getOffsetAtLocation(point));
					viewEvent.setData(offset);
				}
				catch (Exception ex)
				{
				}

				fireEvent(viewEvent);
			}
		});
	}

	private void makeDropable()
	{
		DropTarget dropTarget = new DropTarget(noteContentText, DND.DROP_MOVE);
		dropTarget.setTransfer(new Transfer[] { ReferenceTransfer.getInstance() });
		dropTarget.addDropListener(new DropTargetAdapter()
		{
			@SuppressWarnings("unchecked")
			@Override
			public void drop(DropTargetEvent event)
			{
				dropCoordinates = new Point(event.x, event.y);
				droppedVerses = (List<BibleVerse>) event.data;

				setFocus();

				fireEvent(new ViewEvent(ViewEvent.NOTE_DROPPED_REFERENCE));
			}
		});
	}

	public int getCurrentCarretPosition()
	{
		return noteContentText.getCaretOffset();
	}

	public Point getLastClickedCoordinates()
	{
		return lastClickedCoordinates;
	}

	public void showRightClickPopup(int x, int y)
	{
		String selectionText = noteContentText.getSelectionText();

		if (selectionText == null || selectionText.length() != 0)
		{
			Point point = parent.toDisplay(x, y);
			rightClickTextMenu.setLocation(point);
			rightClickTextMenu.setVisible(true);
		}
	}

	@Override
	public void setFocus()
	{
	}

	public void addNoteViewListener(INoteListener noteListener)
	{
		eventListeners.add(INoteListener.class, noteListener);
	}

	public void setContentText(String text)
	{
		if (text != null)
		{
			noteContentText.setText(text);
		}
	}

	public String getContentText()
	{
		return noteContentText.getText();
	}

	public void setViewTitle(String title)
	{
		setPartName(title);
	}

	@Override
	public void dispose()
	{
		fireEvent(new ViewEvent(ViewEvent.NOTE_CLOSE));

		blueColor.dispose();
		greenColor.dispose();
		blackColor.dispose();

		super.dispose();
	}

	public void removeNoteViewListener(INoteListener noteListener)
	{
		eventListeners.remove(INoteListener.class, noteListener);
	}

	public String getSelectedText()
	{
		return noteContentText.getSelectionText();
	}

	public Point getSelectionPoint()
	{
		return noteContentText.getSelection();
	}

	public void saveNote()
	{
		fireEvent(new ViewEvent(ViewEvent.NOTE_SAVE));
	}

	public void deleteNote()
	{
		fireEvent(new ViewEvent(ViewEvent.NOTE_DELETE));
	}

	public void closeView(String noteName)
	{
		ViewerFactory.getViewer().closeNoteView(noteName);
	}

	public void replaceNoteStyles(List<NoteStyle> styleList)
	{
		removeNoteStyles();

		for (NoteStyle style : styleList)
		{
			StyleRange styleRange = convertToStyleRange(style);

			noteContentText.setStyleRange(styleRange);
		}
	}

	public void removeNoteStyles()
	{
		StyleRange styleRange = new StyleRange();
		styleRange.start = 0;
		styleRange.length = noteContentText.getText().length();
		styleRange.underline = false;
		styleRange.foreground = blackColor;

		noteContentText.setStyleRange(styleRange);
	}

	private StyleRange convertToStyleRange(NoteStyle style)
	{
		StyleRange styleRange = new StyleRange();
		styleRange.start = style.getStart();
		styleRange.length = style.getLength();
		styleRange.underline = style.isUnderlined();
		if (NoteStyle.Colors.BLUE.equals(style.getForeground()))
		{
			styleRange.foreground = blueColor;
		}
		else if (NoteStyle.Colors.GREEN.equals(style.getForeground()))
		{
			styleRange.foreground = greenColor;
		}

		return styleRange;
	}

	public void changeCursorToPointer()
	{
		Cursor cursor = new Cursor(Display.getDefault(), SWT.CURSOR_HAND);
		noteContentText.setCursor(cursor);
	}

	public void changeCursorToText()
	{
		Cursor cursor = new Cursor(Display.getDefault(), SWT.CURSOR_IBEAM);
		noteContentText.setCursor(cursor);
	}

	public List<BibleVerse> getDroppedVerse()
	{
		return droppedVerses;
	}

	public void openDropReferenceOptions(int x, int y)
	{
		dropReferenceOptionsMenu.setLocation(new Point(x, y));
		dropReferenceOptionsMenu.setVisible(true);
	}

	private void createDropReferenceOptions(Composite parent)
	{
		dropReferenceOptionsMenu = new Menu(parent);
		dropReferenceOptionsMenu.setVisible(false);

		MenuItem dropReferenceLink = new MenuItem(dropReferenceOptionsMenu, SWT.POP_UP);
		dropReferenceLink.setText("Insert Link to Reference");
		dropReferenceLink.setEnabled(true);
		dropReferenceLink.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				fireEvent(new ViewEvent(ViewEvent.NOTE_DROP_LINK_TO_REFERENCE));
			}
		});

		MenuItem dropReferenceText = new MenuItem(dropReferenceOptionsMenu, SWT.POP_UP);
		dropReferenceText.setText("Insert Text");
		dropReferenceText.setEnabled(true);
		dropReferenceText.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				fireEvent(new ViewEvent(ViewEvent.NOTE_DROP_REFERENCE_TEXT));
			}
		});

		MenuItem dropReferenceAndText = new MenuItem(dropReferenceOptionsMenu, SWT.POP_UP);
		dropReferenceAndText.setText("Insert Reference with Text");
		dropReferenceAndText.setEnabled(true);
		dropReferenceAndText.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				fireEvent(new ViewEvent(ViewEvent.NOTE_DROP_REFERENCE_AND_TEXT));
			}
		});
	}

	public Point getDropCoordinates()
	{
		return dropCoordinates;
	}

	public int openDeleteConfirmationWindow()
	{
		MessageDialog dialog = new MessageDialog(Display.getDefault().getActiveShell(),
				"Are you sure?", null, "Are you sure you want to delete this note?",
				MessageDialog.QUESTION, new String[] { "No", "Yes" }, 0);
		return dialog.open();
	}
}
