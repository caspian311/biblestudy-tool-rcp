package net.todd.biblestudy.common;

public interface IMvpListener {
	void addListener(IListener listener);

	void notifyListeners();

	void addListener(IListener listener, Enum<?> type);

	void notifyListeners(Enum<?> type);
}
