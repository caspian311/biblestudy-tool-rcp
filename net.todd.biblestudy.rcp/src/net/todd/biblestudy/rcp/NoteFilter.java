package net.todd.biblestudy.rcp;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class NoteFilter extends ViewerFilter {
	private final String filter;

	public NoteFilter(String filter) {
		this.filter = filter;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		Note note = (Note) element;
		String name = note.getName().toLowerCase();
		boolean foundText = false;
		if (filter.startsWith("*") && filter.endsWith("*")) {
			foundText = name.contains(filter);
		} else if (filter.startsWith("*")) {
			foundText = name.endsWith(filter);
		} else {
			foundText = name.startsWith(filter);
		}
		return foundText;
	}
}
