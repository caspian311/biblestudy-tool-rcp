package net.todd.biblestudy.cli;

import java.io.PrintStream;

import net.todd.biblestudy.BibleVerse;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class BibleSearch {
	private final IBibleLookup bibleLookup;
	private final INoteLookup noteLookup;

	@Inject
	public BibleSearch(IBibleLookup service, INoteLookup noteLookup) {
		this.bibleLookup = service;
		this.noteLookup = noteLookup;
	}

	private void printUsage(PrintStream out) {
		out.println("Usage: biblesearch [options] <text>\n  options:\n"
				+ "    -ref = search for references\n"
				+ "    -note = search for notes");
	}

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new BibleCLISearchModule());
		BibleSearch instance = injector.getInstance(BibleSearch.class);
		instance.execute(args, System.out);
	}

	public void execute(String[] args, PrintStream out) {
		if (args == null || args.length < 2) {
			printUsage(out);
		} else if ("-ref".equals(args[0])) {
			if (bibleLookup != null) {
				BibleVerse[] references = bibleLookup
						.searchForReference(mergeToQueryStr(args));
				printVerses(references, out);
			}
		} else if ("-note".equals(args[0])) {
			if (noteLookup != null) {
				noteLookup.searchForNote(mergeToQueryStr(args));
			}
		} else {
			printUsage(out);
		}
	}

	private void printVerses(BibleVerse[] verses, PrintStream out) {
		for (BibleVerse verse : verses) {
			out.println(verse.getVerse() + " : " + verse.getText());
		}
	}

	private String mergeToQueryStr(String[] args) {
		StringBuffer sb = new StringBuffer();

		for (int i = 1; i < args.length; i++) {
			if (i != 1) {
				sb.append(" ");
			}
			sb.append(args[i]);
		}

		return sb.toString();
	}
}
