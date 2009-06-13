package net.todd.biblestudy.client;

import java.net.MalformedURLException;

import net.todd.biblestudy.BibleStudyService;
import net.todd.biblestudy.Verse;
import net.todd.biblestudy.cli.Note;

import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;

public class BibleStudyClient implements BibleStudyService {
	private BibleStudyService service = null;

	public BibleStudyClient(String endpoint) {
		Service serviceModel = new ObjectServiceFactory()
				.create(BibleStudyService.class);

		try {
			service = (BibleStudyService) new XFireProxyFactory().create(
					serviceModel, endpoint);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public Verse[] searchForReference(String query) {
		return service.searchForReference(query);
	}

	public Note[] searchForNote(String query) {
		return service.searchForNote(query);
	}
}
