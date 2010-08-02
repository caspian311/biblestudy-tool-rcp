package net.todd.biblestudy.common;

public interface IMvpEventer {
	void addListener(IListener listener);

	void notifyListeners();

	void addListener(IListener listener, Enum<?> type);

	void removeListener(IListener listener);

	void notifyListeners(Enum<?> type);
}
