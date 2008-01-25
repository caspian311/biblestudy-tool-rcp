package net.todd.biblestudy.rcp.views;

import net.todd.biblestudy.db.Note;

import org.eclipse.jface.viewers.LabelProvider;

public class NoteLabelProvider extends LabelProvider
{
	@Override
	public String getText(Object element)
	{
		Note note = (Note)element;
		
		return note.getName();
	}
}
