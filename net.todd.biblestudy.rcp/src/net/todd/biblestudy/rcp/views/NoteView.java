package net.todd.biblestudy.rcp.views;

import java.util.List;

import javax.swing.event.EventListenerList;

import net.todd.biblestudy.db.NoteStyle;
import net.todd.biblestudy.rcp.presenters.INoteListener;
import net.todd.biblestudy.rcp.presenters.ViewEvent;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class NoteView extends ViewPart implements INoteView
{
	public static final String ID = "net.todd.biblestudy.rcp.NoteView"; 
	
	EventListenerList eventListeners = new EventListenerList();

	private StyledText content;

	private Menu rightClickTextMenu;

	private Point lastClickedCoordinates;
	
	private Composite parent;

	private ITextViewer textViewer;

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
	}

	private void createRightClickMenu(Composite parent)
	{
		rightClickTextMenu = new Menu(parent);
		rightClickTextMenu.setVisible(false);
		MenuItem createLink = new MenuItem(rightClickTextMenu, SWT.POP_UP);
		createLink.setText("Create link");
		createLink.setEnabled(true);
		createLink.addSelectionListener(new SelectionAdapter() 
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				fireEvent(new ViewEvent(ViewEvent.NOTE_CREATE_LINK_EVENT));
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
		
//		textViewer = new TextViewer(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
//		textViewer.setDocument(new Document());
//		
//		textViewer.getTextWidget();
		
		content = new StyledText(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		content.setLayoutData(gridData);
		content.addModifyListener(new ModifyListener() 
		{
			@Override
			public void modifyText(ModifyEvent e)
			{
				fireEvent(new ViewEvent(ViewEvent.NOTE_CONTENT_CHANGED));
			}
		});
		content.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseUp(MouseEvent e)
			{
				if (e.button == 3) 
				{	// right-click
					lastClickedCoordinates = new Point(e.x, e.y);
					fireEvent(new ViewEvent(ViewEvent.NOTE_SHOW_RIGHT_CLICK_MENU));
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.views.INoteView#getLastClickedCoordinates()
	 */
	public Point getLastClickedCoordinates()
	{
		return lastClickedCoordinates;
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.views.INoteView#showRightClickPopup(int, int)
	 */
	public void showRightClickPopup(int x, int y)
	{
		String selectionText = content.getSelectionText();
		
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

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.views.INoteView#addNoteViewListener(net.todd.biblestudy.rcp.presenters.INoteListener)
	 */
	public void addNoteViewListener(INoteListener noteListener)
	{
		eventListeners.add(INoteListener.class, noteListener);
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.views.INoteView#setContentText(java.lang.String)
	 */
	public void setContentText(String text)
	{
		if (text != null)
		{
			content.setText(text);
		}
	}
	
	public String getContentText()
	{
		return content.getText();
	}

	/*
	 * (non-Javadoc)
	 * @see net.todd.biblestudy.rcp.views.INoteView#setViewTitle(java.lang.String)
	 */
	public void setViewTitle(String title)
	{
		setPartName(title);
	}
	
	@Override
	public void dispose()
	{
		fireEvent(new ViewEvent(ViewEvent.NOTE_CLOSE));
		
		super.dispose();
	}
	
	@Override
	public void removeNoteViewListener(INoteListener noteListener)
	{
		eventListeners.remove(INoteListener.class, noteListener);
	}

	@Override
	public String getSelectedText()
	{
		return content.getSelectionText();
	}

	@Override
	public Point getSelectionPoint()
	{
		return content.getSelection();
	}

	@Override
	public void saveNote()
	{
		fireEvent(new ViewEvent(ViewEvent.NOTE_SAVE));
	}

	@Override
	public void deleteNote()
	{
		fireEvent(new ViewEvent(ViewEvent.NOTE_DELETE));
	}

	@Override
	public void closeView(String secondaryId)
	{
		IViewReference viewReference = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findViewReference(ID, secondaryId);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView(viewReference);
	}

	@Override
	public void replaceNoteStyles(List<NoteStyle> styleList)
	{
		for (NoteStyle style : styleList)
		{
			StyleRange styleRange = convertToStyleRange(style);
			
			content.setStyleRange(styleRange);
		}
	}
	
	private StyleRange convertToStyleRange(NoteStyle style)
	{
		StyleRange styleRange = new StyleRange();
		styleRange.start = style.getStart();
		styleRange.length = style.getLength();
		styleRange.underline = style.isUnderlined();
		
		return styleRange;
	}
	
//	public int promptForSave()
//	{
//		Shell shell = Display.getCurrent().getActiveShell();
//		MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK | SWT.CANCEL);
//		messageBox.setMessage("Changes have been made to this note without saving.\nIf you close this note now, you will lose all your data.\nContinue?");
//		messageBox.setText("Are you sure you want to close this note?");
//		
//		return messageBox.open();
//	}
}
