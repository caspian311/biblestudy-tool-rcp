package net.todd.biblestudy;

import java.util.ArrayList;
import java.util.List;

public class BibleDao implements IBibleDao {
	private final List<Verse> allVerses = new ArrayList<Verse>();

	public BibleDao() {
		Verse verse1 = new Verse();
		verse1.setId(1);
		verse1.setContent("For God so loved the world...");

		Verse verse2 = new Verse();
		verse2.setId(2);
		verse2.setContent("I can do all things through Christ...");

		Verse verse3 = new Verse();
		verse3.setId(3);
		verse3.setContent("Greater is He that is within me...");

		allVerses.add(verse1);
		allVerses.add(verse2);
		allVerses.add(verse3);
	}
	
	public List<Verse> getAllVerses() {
		return allVerses;
	}
}
