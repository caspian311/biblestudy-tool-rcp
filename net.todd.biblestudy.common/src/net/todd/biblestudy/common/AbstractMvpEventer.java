package net.todd.biblestudy.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMvpEventer implements IMvpEventer {
	private final Map<Enum<?>, List<IListener>> listenersByType = new HashMap<Enum<?>, List<IListener>>();

	private static enum InternalTypes {
		DEFAULT
	};

	private static final InternalTypes DEFAULT = InternalTypes.DEFAULT;

	@Override
	public void addListener(IListener listener) {
		addListener(listener, DEFAULT);
	}

	@Override
	public void addListener(IListener listener, Enum<?> type) {
		List<IListener> listeners = getListenersByType(type);
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	private List<IListener> getListenersByType(Enum<?> type) {
		List<IListener> listeners = listenersByType.get(type);
		if (listeners == null) {
			listenersByType.put(type, new ArrayList<IListener>());
			listeners = listenersByType.get(type);
		}
		return listeners;
	}

	@Override
	public void notifyListeners() {
		notifyListeners(DEFAULT);
	}

	@Override
	public void notifyListeners(Enum<?> type) {
		for (IListener listener : getListenersByType(type)) {
			listener.handleEvent();
		}
	}

	@Override
	public void removeListener(IListener listener) {
		Enum<?> type = getTypeForListener(listener);
		getListenersByType(type).remove(listener);
	}

	private Enum<?> getTypeForListener(IListener listenerToRemove) {
		Enum<?> targetType = null;
		for (Enum<?> type : listenersByType.keySet()) {
			List<IListener> listeners = getListenersByType(type);
			if (listeners.contains(listenerToRemove)) {
				targetType = type;
				break;
			}
		}
		return targetType;
	}
}
