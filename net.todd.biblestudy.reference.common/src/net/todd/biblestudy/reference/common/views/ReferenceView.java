package net.todd.biblestudy.reference.common.views;


import java.util.List;

import javax.swing.event.EventListenerList;

import net.todd.biblestudy.common.ViewHelper;
import net.todd.biblestudy.reference.common.BibleVerse;
import net.todd.biblestudy.reference.common.ReferenceTransfer;
import net.todd.biblestudy.reference.common.presenters.IReferenceViewListener;
import net.todd.biblestudy.reference.common.util.ScriptureTextUtil;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class ReferenceView extends ViewPart implements IReferenceView
{
	protected static final String ID = "net.todd.biblestudy.reference.common.ReferenceView";
	
	private static final int TEXT_COLUMN_WIDTH = 200;
	private static final String TEXT_COLUMN_HEADER = "Text";
	private static final String REFERENCE_COLUMN_HEADER = "Reference";
	private static final int REFERENCE_COLUMN_WIDTH = 100;

	private int textColumnWidth = TEXT_COLUMN_WIDTH;
	
	private EventListenerList eventListeners = new EventListenerList();

	private Combo referenceCombo;

	private Text lookupText;

	private TableViewer resultsTableViewer;
	private Table resultsTable;
	private Label resultsMessage;
	private Button lookupButton;

	private TableColumn textColumn;
	
	private static final int TEXT_MARGIN = 3;

	@Override
	public void createPartControl(Composite parent)
	{
		GridLayout layout = new GridLayout(1, false);
		layout.marginBottom = 2;
		layout.marginTop = 2;
		layout.marginLeft = 2;
		layout.marginRight = 2;
		
		Composite composite = new Composite(parent, SWT.NONE);
		
		composite.setLayout(layout);
		
		createControls(composite);
		createResultsArea(composite);
	}
	
	private void createResultsArea(Composite parent)
	{
		resultsTableViewer = new TableViewer(parent, SWT.BORDER | SWT.V_SCROLL | SWT.SHADOW_ETCHED_IN | SWT.FULL_SELECTION);
		resultsTableViewer.setLabelProvider(new ResultsTableLabelProvider());
		resultsTableViewer.setContentProvider(new ArrayContentProvider());
		
		resultsTable = resultsTableViewer.getTable();
		resultsTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		resultsTable.setHeaderVisible(true);
		resultsTable.setLinesVisible(true);
		
		resultsTable.addListener(SWT.MeasureItem, new Listener()
		{
			public void handleEvent(Event event)
			{
				TableItem item = (TableItem) event.item;
				String text = item.getText(event.index);
				Point size = event.gc.textExtent(text);
				event.width = size.x + 2 * TEXT_MARGIN;
				event.height = Math.max(event.height, size.y + TEXT_MARGIN);
			}
		});
		resultsTable.addListener(SWT.EraseItem, new Listener()
		{
			public void handleEvent(Event event)
			{
				event.detail &= ~SWT.FOREGROUND;
			}
		});
		resultsTable.addListener(SWT.PaintItem, new Listener()
		{
			public void handleEvent(Event event)
			{
				TableItem item = (TableItem) event.item;
				String text = item.getText(event.index);
				/* center column 1 vertically */
				int yOffset = 0;
				if (event.index == 1)
				{
					Point size = event.gc.textExtent(text);
					yOffset = Math.max(0, (event.height - size.y) / 2);
				}
				event.gc.drawText(text, event.x + TEXT_MARGIN, event.y + yOffset, true);
			}
		});
		resultsTable.addControlListener(new ControlListener()
		{
			public void controlMoved(ControlEvent e) 
			{
			}

			public void controlResized(ControlEvent e) 
			{
				Rectangle clientArea = resultsTable.getClientArea();
				
				int tableWidth = clientArea.width - 100;
				
				textColumnWidth = tableWidth - REFERENCE_COLUMN_WIDTH;
				textColumn.setWidth(textColumnWidth);
				redoTheText();
//				textColumn.pack();
			}
		});
		
		TableColumn referenceColumn = new TableColumn(resultsTable, SWT.LEFT);
		referenceColumn.setText(REFERENCE_COLUMN_HEADER);
		referenceColumn.setWidth(REFERENCE_COLUMN_WIDTH);
		referenceColumn.setResizable(false);
		
		textColumn = new TableColumn(resultsTable, SWT.LEFT);
		textColumn.setText(TEXT_COLUMN_HEADER);
		textColumn.setWidth(200);
		textColumn.setResizable(false);
		
		makeDragable();
	}

	private void redoTheText()
	{
		GC gc = new GC(resultsTable);
		FontMetrics fontMetrics = gc.getFontMetrics();
		int averageCharWidth = fontMetrics.getAverageCharWidth();
		gc.dispose();
		
		for (TableItem item : resultsTable.getItems())
		{
			String columnText = item.getText(1);
			
			int maxCharactersPerLine = textColumnWidth / averageCharWidth;
			
			String newColumnText = ScriptureTextUtil.addNewLines(columnText, maxCharactersPerLine);
			
			item.setText(1, newColumnText);
		}
	}
	
	private void makeDragable()
	{
		DragSource dragSource = new DragSource(resultsTableViewer.getTable(), DND.DROP_MOVE);
		dragSource.setTransfer(new Transfer[] {ReferenceTransfer.getInstance()});
		dragSource.addDragListener(new DragSourceAdapter() 
		{
			@Override
			public void dragSetData(DragSourceEvent event)
			{
				if (ReferenceTransfer.getInstance().isSupportedType(event.dataType))
				{
					event.data = resultsTableViewer.getTable().getSelection()[0].getData();
				}
			}
		});
	}
	
	public void setResults(final BibleVerse[] results)
	{
		ViewHelper.runWithBusyIndicator(new Runnable() 
		{
			public void run()
			{
				resultsTableViewer.setInput(results);
				
				redoTheText();
			}
		});
	}

	private void createControls(Composite parent)
	{
		Composite composite = new Composite(parent, SWT.NONE);
		
		GridLayout layout = new GridLayout(2, false);
		layout.marginBottom = 2;
		layout.marginTop = 2;
		layout.marginLeft = 2;
		layout.marginRight = 2;
		
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		lookupText = new Text(composite, SWT.BORDER);
		lookupText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		lookupButton = new Button(composite, SWT.PUSH);
		lookupButton.setText("Search");
		lookupButton.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				fireEvent(new ReferenceViewEvent(ReferenceViewEvent.REFERENCE_VIEW_SEARCH));
			}
		});
		
		referenceCombo = new Combo(composite, SWT.BORDER | SWT.DROP_DOWN);
		referenceCombo.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 2, 0));
		
		resultsMessage = new Label(composite, SWT.NORMAL);
		resultsMessage.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 0));
		
		getSite().getShell().setDefaultButton(lookupButton);
	}

	@Override
	public void setFocus()
	{
		lookupText.setFocus();
	}

	@Override
	public void dispose()
	{
		fireEvent(new ReferenceViewEvent(ReferenceViewEvent.REFERENCE_VIEW_DISPOSED));
		
		super.dispose();
	}
	
	public void addReferenceViewListener(IReferenceViewListener listener)
	{
		eventListeners.add(IReferenceViewListener.class, listener);
	}
	
	public void fireEvent(ReferenceViewEvent event)
	{
		IReferenceViewListener[] listeners = eventListeners.getListeners(IReferenceViewListener.class);
		
		for (IReferenceViewListener listener : listeners)
		{
			listener.handleEvent(event);
		}
	}

	public void removeReferenceViewListener(IReferenceViewListener listener)
	{
		eventListeners.remove(IReferenceViewListener.class, listener);
	}

	public void setDataSourcesInDropDown(List<String> sourceIds)
	{
		for (String sourceId : sourceIds)
		{
			referenceCombo.add(sourceId);
		}
		
		referenceCombo.select(0);
	}

	public String getReferenceSourceId()
	{
		return referenceCombo.getText();
	}

	public void setLookupText(String lookupString)
	{
		lookupText.setText(lookupString);
	}
	
	public String getLookupText()
	{
		return lookupText.getText();
	}
	
	public void popupErrorMessage(String error)
	{
		ViewHelper.showError(new Exception(error));
	}

	public void displayLimitResultsMessage(final int totalSize)
	{
		ViewHelper.runWithoutBusyIndicator(new Runnable() 
		{
			public void run()
			{
				resultsMessage.setText("Only displaying 100 of " + totalSize + " results.");
			}
		});
	}
	
	public void hideLimitResultsMessage()
	{
		ViewHelper.runWithoutBusyIndicator(new Runnable() 
		{
			public void run()
			{
				resultsMessage.setText("");
			}
		});
	}
	
//	class ResultsTableLabelProvider extends LabelProvider implements ITableLabelProvider
//	{
//		public Image getColumnImage(Object element, int columnIndex)
//		{
//			return null;
//		}
//
//		public String getColumnText(Object element, int columnIndex)
//		{
//			BibleVerse result = (BibleVerse)element;
//			
//			String text = null;
//			
//			if (columnIndex == 0)
//			{
//				text = result.getReference().toString();
//			}
//			else if (columnIndex == 1)
//			{
//				text = result.getText();
//			}
//			
//			return text;
//		}
//	}
}