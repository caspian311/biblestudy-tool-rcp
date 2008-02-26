package net.todd.biblestudy.rcp.views;

import net.todd.biblestudy.db.Note;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

public class NoteViewerSorter extends ViewerSorter
{
	private static final int ASCENDING = 1;
	private static final int DESCENDING = -1;

	private int column = -1;
	private int direction = DESCENDING;

	public void doSort(int column)
	{
		if (this.column == column)
		{
			direction *= -1;
		}
		else
		{
			this.column = column;
			direction = ASCENDING;
		}
	}

	@Override
	public int compare(Viewer viewer, Object leftObject, Object rightObject)
	{
		int retVal = 0;

		if (viewer instanceof TableViewer && leftObject instanceof Note
				&& rightObject instanceof Note)
		{
			TableViewer tableViewer = (TableViewer) viewer;

			Note leftNote = (Note) leftObject;
			Note rightNote = (Note) rightObject;

			if (tableViewer.getTable().getSortColumn() != null)
			{
				String sortColumnName = tableViewer.getTable().getSortColumn().getText();

				if (OpenNoteDialog.NOTE_NAME_COLUMN_HEADER.equals(sortColumnName))
				{
					retVal = leftNote.getName().compareTo(rightNote.getName());
				}
				else if (OpenNoteDialog.LAST_MODIFIED_COLUMN_HEADER.equals(sortColumnName))
				{
					retVal = leftNote.getLastModified().compareTo(rightNote.getLastModified());
				}
				else if (OpenNoteDialog.CREATED_COLUMN_HEADER.equals(sortColumnName))
				{
					retVal = leftNote.getCreatedTimestamp().compareTo(
							rightNote.getCreatedTimestamp());
				}
			}
		}

		retVal *= direction;

		return retVal;
	}
}
