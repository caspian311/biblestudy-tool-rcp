package net.todd.biblestudy.reference;

import net.todd.biblestudy.common.IListener;

public class ReferencePresenter {
	public static void create(final IReferenceView view, final IReferenceModel model) {
		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.setSearchText(view.getLookupText());
			}
		}, IReferenceView.SEARCH_TEXT);

		view.addListener(new IListener() {
			@Override
			public void handleEvent() {
				model.performSearch();
			}
		}, IReferenceView.LOOK_UP_BUTTON);

		model.addListener(new IListener() {
			@Override
			public void handleEvent() {
				view.setSearchText(model.getLookupText());
			}
		}, IReferenceModel.SEARCH_TEXT);

		model.addListener(new IListener() {
			@Override
			public void handleEvent() {
				view.setSearchResults(model.getSearchResults());
			}
		}, IReferenceModel.RESULTS_CHANGED);
	}
}
