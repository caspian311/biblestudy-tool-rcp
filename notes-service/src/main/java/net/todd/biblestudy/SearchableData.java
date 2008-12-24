package net.todd.biblestudy;

import java.util.HashMap;
import java.util.Map;

public class SearchableData {
	private final Map<Type, String> data;

	public SearchableData() {
		data = new HashMap<Type, String>();
	}
	
	public void addDatum(Type dataType, String value) {
		data.put(dataType, value);
	}

	public String getValue(Type dataType) {
		return data.get(dataType);
	}
	
	enum Type {
		ID, TITLE, CONTENT;

		@Override
		public String toString() {
			String s = null;

			if (this == ID) {
				s = "ID";
			} else if (this == TITLE) {
				s = "TITLE";
			} else if (this == CONTENT) {
				s = "CONTENT";
			}
			return s;
		}
	}
}