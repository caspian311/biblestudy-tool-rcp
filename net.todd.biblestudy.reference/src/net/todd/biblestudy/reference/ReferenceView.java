package net.todd.biblestudy.reference;

import java.util.ArrayList;
import java.util.List;

import net.todd.biblestudy.common.AbstractMvpEventer;
import net.todd.biblestudy.common.ViewerUtils;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
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
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class ReferenceView extends AbstractMvpEventer implements IReferenceView {
	protected static final String ID = "net.todd.biblestudy.reference.common.ReferenceView";

	// private static final int TEXT_COLUMN_WIDTH = 200;
	private static final String TEXT_COLUMN_HEADER = "Text";
	private static final String REFERENCE_COLUMN_HEADER = "Reference";
	private static final int REFERENCE_COLUMN_WIDTH = 100;

	private Text lookupText;

	private TableViewer resultsTableViewer;
	private Table resultsTable;
	private Label resultsMessage;
	private Button lookupButton;

	private TableColumn textColumn;

	private static final int TEXT_MARGIN = 3;

	private Menu rightClickMenu;

	private Point lastRightClickPosition;

	private TableItem[] currentSelection;

	private final ReferenceViewPart referenceViewPart;

	public ReferenceView(Composite composite, ReferenceViewPart referenceViewPart) {
		this.referenceViewPart = referenceViewPart;

		GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
		GridLayoutFactory.fillDefaults().numColumns(2).margins(2, 2).applyTo(composite);

		createControls(composite);
		createResultsArea(composite);
		createRightClickMenu(composite);
	}

	private void createRightClickMenu(Composite parent) {
		rightClickMenu = new Menu(parent);
		rightClickMenu.setVisible(false);

		MenuItem showEntireChapterMenuItem = new MenuItem(rightClickMenu, SWT.POP_UP);
		showEntireChapterMenuItem.setText("Show Entire Chapter");
		showEntireChapterMenuItem.setEnabled(true);
		showEntireChapterMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				notifyListeners(SHOW_ENTIRE_CHAPTER);
			}
		});
	}

	private void createResultsArea(Composite composite) {
		resultsMessage = new Label(composite, SWT.NORMAL);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(resultsMessage);

		resultsTableViewer = new TableViewer(composite, SWT.BORDER | SWT.V_SCROLL | SWT.SHADOW_ETCHED_IN
				| SWT.FULL_SELECTION | SWT.MULTI);
		resultsTableViewer.setLabelProvider(new ResultsTableLabelProvider());
		resultsTableViewer.setContentProvider(new ArrayContentProvider());

		resultsTable = resultsTableViewer.getTable();
		GridDataFactory.fillDefaults().grab(true, true).span(2, 1).applyTo(resultsTable);
		resultsTable.setHeaderVisible(true);
		resultsTable.setLinesVisible(true);

		resultsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (isRightClick(e) || isMacRightClick(e)) {
					lastRightClickPosition = new Point(e.x, e.y);

					notifyListeners(RIGHT_CLICK);
				}
			}

			private boolean isMacRightClick(MouseEvent e) {
				return e.stateMask == (SWT.BUTTON1 | SWT.CTRL);
			}

			private boolean isRightClick(MouseEvent e) {
				return e.stateMask == SWT.BUTTON3;
			}
		});

		resultsTable.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem[] selection = resultsTable.getSelection();

				currentSelection = selection;
			}
		});

		resultsTable.addListener(SWT.MeasureItem, new Listener() {
			@Override
			public void handleEvent(Event event) {
				TableItem item = (TableItem) event.item;
				String text = item.getText(event.index);
				Point size = event.gc.textExtent(text);
				event.width = size.x + 2 * TEXT_MARGIN;
				event.height = Math.max(event.height, size.y + TEXT_MARGIN);
			}
		});
		resultsTable.addListener(SWT.EraseItem, new Listener() {
			@Override
			public void handleEvent(Event event) {
				event.detail &= ~SWT.FOREGROUND;
			}
		});
		resultsTable.addListener(SWT.PaintItem, new Listener() {
			@Override
			public void handleEvent(Event event) {
				TableItem item = (TableItem) event.item;
				String text = item.getText(event.index);
				/* center column 1 vertically */
				int yOffset = 0;
				if (event.index == 1) {
					Point size = event.gc.textExtent(text);
					yOffset = Math.max(0, (event.height - size.y) / 2);
				}
				event.gc.drawText(text, event.x + TEXT_MARGIN, event.y + yOffset, true);
			}
		});
		resultsTable.addControlListener(new ControlListener() {
			@Override
			public void controlMoved(ControlEvent e) {
			}

			@Override
			public void controlResized(ControlEvent e) {
				Rectangle clientArea = resultsTable.getClientArea();

				int tableWidth = clientArea.width - 100;

				int textColumnWidth = tableWidth - REFERENCE_COLUMN_WIDTH;
				textColumn.setWidth(textColumnWidth);
				redoTheText();
				// textColumn.pack();
			}
		});

		TableColumn referenceColumn = new TableColumn(resultsTable, SWT.LEFT);
		referenceColumn.setText(REFERENCE_COLUMN_HEADER);
		referenceColumn.setWidth(REFERENCE_COLUMN_WIDTH);
		referenceColumn.setResizable(true);

		textColumn = new TableColumn(resultsTable, SWT.LEFT);
		textColumn.setText(TEXT_COLUMN_HEADER);
		textColumn.setWidth(200);
		textColumn.setResizable(true);

		makeDragable();
	}

	@Override
	public Verse getSelectedVerse() {
		Verse selectedVerse = null;

		if (currentSelection != null && currentSelection.length > 0) {
			selectedVerse = (Verse) currentSelection[0].getData();
		}

		return selectedVerse;
	}

	private void redoTheText() {
		// GC gc = new GC(resultsTable);
		// FontMetrics fontMetrics = gc.getFontMetrics();
		// int averageCharWidth = fontMetrics.getAverageCharWidth();
		// gc.dispose();
		//
		// for (TableItem item : resultsTable.getItems()) {
		// String columnText = item.getText(1);
		//
		// int maxCharactersPerLine = TEXT_COLUMN_WIDTH / averageCharWidth;
		//
		// String newColumnText = ScriptureTextUtil.addNewLines(columnText,
		// maxCharactersPerLine);
		//
		// item.setText(1, newColumnText);
		// }
		//
		// resultsTable.layout();
	}

	private void makeDragable() {
		DragSource dragSource = new DragSource(resultsTableViewer.getTable(), DND.DROP_MOVE);
		dragSource.setTransfer(new Transfer[] { ReferenceTransfer.getInstance() });
		dragSource.addDragListener(new DragSourceAdapter() {
			@Override
			public void dragSetData(DragSourceEvent event) {
				if (ReferenceTransfer.getInstance().isSupportedType(event.dataType)) {
					List<Verse> verses = new ArrayList<Verse>();

					TableItem[] selectionList = getCurrentSelection();

					if (selectionList != null) {
						for (TableItem selectedItem : selectionList) {
							Verse verse = (Verse) selectedItem.getData();

							verses.add(verse);
						}
					}

					event.data = verses;
				}
			}
		});
	}

	private TableItem[] getCurrentSelection() {
		return currentSelection;
	}

	@Override
	public void setLookupButtonEnabled(boolean enabled) {
		lookupButton.setEnabled(enabled);
	}

	@Override
	public void setSearchResults(List<Verse> results) {
		resultsTableViewer.setInput(results);

		redoTheText();
	}

	private void createControls(Composite composite) {
		lookupText = new Text(composite, SWT.BORDER);
		lookupText.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event event) {
				notifyListeners(SEARCH_TEXT);
			}
		});
		GridDataFactory.fillDefaults().grab(true, false).applyTo(lookupText);

		lookupButton = new Button(composite, SWT.PUSH);
		lookupButton.setText("Search");
		GridDataFactory.swtDefaults().grab(false, false).hint(ViewerUtils.getButtonWidth(lookupButton), SWT.DEFAULT)
				.applyTo(lookupButton);
		lookupButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				notifyListeners(LOOKUP_BUTTON);
			}
		});

		composite.getShell().setDefaultButton(lookupButton);
	}

	@Override
	public void setSearchText(String lookupString) {
		lookupText.setText(lookupString);
	}

	@Override
	public String getLookupText() {
		return lookupText.getText();
	}

	@Override
	public void displayErrorMessage(String message) {
		resultsMessage.setText(message);
		resultsMessage.setVisible(true);
	}

	@Override
	public void hideErrorMessage() {
		resultsMessage.setVisible(false);
	}

	@Override
	public void showRightClickMenu() {
		int x = lastRightClickPosition.x;
		int y = lastRightClickPosition.y;

		Point point = resultsTableViewer.getControl().toDisplay(x, y);

		rightClickMenu.setLocation(point);
		rightClickMenu.setVisible(true);
	}

	@Override
	public void setViewTitle(String title) {
		referenceViewPart.setPartName(title);
	}
}