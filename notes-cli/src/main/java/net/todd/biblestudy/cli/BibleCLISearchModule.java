package net.todd.biblestudy.cli;

import net.todd.biblestudy.BibleStudyService;
import net.todd.biblestudy.client.BibleStudyClient;

import com.google.inject.Binder;
import com.google.inject.Module;

public class BibleCLISearchModule implements Module {
	public void configure(Binder binder) {
		binder.bind(BibleStudyService.class).to(BibleStudyClient.class);
	}
}
