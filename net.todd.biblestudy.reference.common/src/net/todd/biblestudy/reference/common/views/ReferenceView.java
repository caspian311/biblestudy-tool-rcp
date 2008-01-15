package net.todd.biblestudy.reference.common.views;


import java.util.List;

import javax.swing.event.EventListenerList;

import net.todd.biblestudy.reference.common.ReferenceSearchResults;
import net.todd.biblestudy.reference.common.presenters.IReferenceViewListener;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class ReferenceView extends ViewPart implements IReferenceView
{
	private static final String INITIAL_SEARCH_TEXT = "Search...";

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
		createControls(parent);
		createResultsArea(parent);
	}

	private void createResultsArea(Composite parent)
	{
		resultsTableViewer = new TableViewer(parent, SWT.BORDER);
		resultsTableViewer.setLabelProvider(new ResultsTableLabelProvider());
		resultsTableViewer.setContentProvider(new ArrayContentProvider());
	}
	
	public void setResults(ReferenceSearchResults[] results)
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
		referenceCombo.setText("Select a Reference Source...");
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
	
	class ResultsTableLabelProvider implements ITableLabelProvider
	{
		public Image getColumnImage(Object element, int columnIndex)
		{
			return null;
		}

		public String getColumnText(Object element, int columnIndex)
		{
			return null;
		}

		public void addListener(ILabelProviderListener listener)
		{
		}

		public void dispose()
		{
		}

		public boolean isLabelProperty(Object element, String property)
		{
			return false;
		}

		public void removeListener(ILabelProviderListener listener)
		{
		}
	}
}