package net.todd.biblestudy.rcp;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Button;

public class ViewerUtils {
	public static <T> void setSelection(StructuredViewer structuredViewer, T element) {
		if (element != null) {
			structuredViewer.setSelection(new StructuredSelection(element));
		}
	}

	public static int getButtonWidth(Button button) {
		GC gc = new GC(button);
		gc.setFont(button.getFont());
		FontMetrics fontMetrics = gc.getFontMetrics();

		int minimumSize = button.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x;
		int widthHint = (fontMetrics.getAverageCharWidth() * IDialogConstants.BUTTON_WIDTH + 2) / 4;

		return Math.max(minimumSize, widthHint);
	}
}
