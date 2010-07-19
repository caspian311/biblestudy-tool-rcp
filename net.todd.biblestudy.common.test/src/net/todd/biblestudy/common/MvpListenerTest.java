package net.todd.biblestudy.common;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class MvpListenerTest {
	private IMvpListener testObject;

	@Before
	public void setUp() {
		testObject = new MvpListener() {
		};
	}

	@Test
	public void notifyingWithoutAnyListenersDoesntBlowUp() {
		testObject.notifyListeners();
	}

	@Test
	public void singleListenerListeningToDefaultNotification() {
		CallCountListener listener = new CallCountListener();
		testObject.addListener(listener);

		assertEquals(0, listener.getCallCount());

		testObject.notifyListeners();

		assertEquals(1, listener.getCallCount());
	}

	@Test
	public void multipleListenersListeningToDefaultNotification() {
		CallCountListener listener1 = new CallCountListener();
		CallCountListener listener2 = new CallCountListener();
		testObject.addListener(listener1);
		testObject.addListener(listener2);

		assertEquals(0, listener1.getCallCount());
		assertEquals(0, listener2.getCallCount());

		testObject.notifyListeners();

		assertEquals(1, listener1.getCallCount());
		assertEquals(1, listener2.getCallCount());
	}

	@Test
	public void sameListenerAddedTwiceIsNotifiedOnce() {
		CallCountListener listener1 = new CallCountListener();
		testObject.addListener(listener1);
		testObject.addListener(listener1);

		assertEquals(0, listener1.getCallCount());

		testObject.notifyListeners();

		assertEquals(1, listener1.getCallCount());
	}

	@Test
	public void defaultListenersNotifiedMultipleTimes() {
		CallCountListener listener1 = new CallCountListener();
		CallCountListener listener2 = new CallCountListener();
		testObject.addListener(listener1);
		testObject.addListener(listener2);

		assertEquals(0, listener1.getCallCount());
		assertEquals(0, listener2.getCallCount());

		testObject.notifyListeners();
		testObject.notifyListeners();
		testObject.notifyListeners();

		assertEquals(3, listener1.getCallCount());
		assertEquals(3, listener2.getCallCount());
	}

	@Test
	public void listenersOnlyGetNotifiedIfTheirTypeIsNotified() {
		CallCountListener listener1 = new CallCountListener();
		CallCountListener listener2 = new CallCountListener();
		CallCountListener listener3 = new CallCountListener();
		testObject.addListener(listener1, Types.TYPE1);
		testObject.addListener(listener2, Types.TYPE2);
		testObject.addListener(listener3);

		assertEquals(0, listener1.getCallCount());
		assertEquals(0, listener2.getCallCount());

		testObject.notifyListeners(Types.TYPE1);
		testObject.notifyListeners(Types.TYPE2);
		testObject.notifyListeners(Types.TYPE1);
		testObject.notifyListeners();

		assertEquals(2, listener1.getCallCount());
		assertEquals(1, listener2.getCallCount());
		assertEquals(1, listener3.getCallCount());
	}

	private static class CallCountListener implements IListener {
		private int callCount;

		@Override
		public void handleEvent() {
			callCount++;
		}

		public int getCallCount() {
			return callCount;
		}
	}

	public enum Types {
		TYPE1, TYPE2
	}
}
