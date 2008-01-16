package net.todd.biblestudy.reference.common.views;


import java.util.List;

import javax.swing.event.EventListenerList;

import net.todd.biblestudy.common.ViewHelper;
import net.todd.biblestudy.reference.common.ReferenceSearchResult;
import net.todd.biblestudy.reference.common.presenters.IReferenceViewListener;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class ReferenceView extends ViewPart implements IReferenceView
{
	public static final String INITIAL_COMBO_BOX_TEXT = "Select a Reference Source...";
	public static final String INITIAL_SEARCH_TEXT = "Search...";

	private EventListenerList eventListeners = new EventListenerList();

	private Combo referenceCombo;

	private Text lookupText;

	private TableViewer resultsTableViewer;
	
	protected static final String ID = "net.todd.biblestudy.reference.common.ReferenceView";

	public ReferenceView() 
	{
	}

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
		
		Table table = resultsTableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.setHeaderVisible(true);
		
		TableColumn column1 = new TableColumn(table, SWT.LEFT);
		column1.setText("Reference");
		column1.setWidth(100);
		
		TableColumn column2 = new TableColumn(table, SWT.LEFT);
		column2.setText("Text");
		column2.setWidth(200);
		
		table.pack();
	}
	
	public void setResults(ReferenceSearchResult[] results)
	{
		resultsTableViewer.setInput(results);
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
		lookupText.setText(INITIAL_SEARCH_TEXT);
		lookupText.addFocusListener(new FocusListener() 
		{
			public void focusGained(FocusEvent e)
			{
				if (INITIAL_SEARCH_TEXT.equals(lookupText.getText())) 
				{
					lookupText.setText("");
				}
			}

			public void focusLost(FocusEvent e)
			{
				if ("".equals(lookupText.getText())) 
				{
					lookupText.setText(INITIAL_SEARCH_TEXT);
				}
			}
		});
		
		Button lookupButton = new Button(composite, SWT.PUSH);
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
		referenceCombo.setText(INITIAL_COMBO_BOX_TEXT);
	}

	@Override
	public void setFocus()
	{
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
	}

	public String getReferenceSourceId()
	{
		return referenceCombo.getText();
	}

	public String getLookupText()
	{
		return lookupText.getText();
	}
	
	public void popupErrorMessage(String error)
	{
		ViewHelper.showError(new Exception(error));
	}
}