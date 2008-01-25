package net.todd.biblestudy.rcp.views;

import net.todd.biblestudy.db.Note;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class NoteFilter extends ViewerFilter
{
	private String filterString;

	public NoteFilter(String filterString)
	{
		this.filterString = filterString;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element)
	{
		Note note = (Note)element;
		
		return note.getName().toLowerCase().indexOf(filterString.toLowerCase()) > -1;
	}
}
