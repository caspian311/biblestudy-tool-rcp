package net.todd.biblestudy.common;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;

public class AbstractMvpView extends AbstractMvpEventer implements IMvpView {
	private final Composite composite;

	public AbstractMvpView(Composite composite) {
		this.composite = composite;
	}

	@Override
	public void addDisposeListener(final IListener listener) {
		composite.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				listener.handleEvent();
			}
		});
	}
}
