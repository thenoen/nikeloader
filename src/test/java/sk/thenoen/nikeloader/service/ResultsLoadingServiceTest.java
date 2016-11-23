package sk.thenoen.nikeloader.service;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.w3c.dom.traversal.TreeWalker;
import sk.thenoen.nikeloader.domain.model.Event;
import sk.thenoen.nikeloader.domain.repository.EventRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResultsLoadingServiceTest {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private ResultsLoadingService resultsLoadingService;

	@After
	public void cleanUp() {
		eventRepository.deleteAll();
	}

	@Test
	public void test() {
		eventRepository.save(crateEvent(true, 123L));
		eventRepository.save(crateEvent(false, 456L));
		eventRepository.save(crateEvent(false, 456L));
		eventRepository.save(crateEvent(false, 789L));

		resultsLoadingService.loadResults(eventRepository.findAll());
	}

	private Event crateEvent(Boolean knownResult, Long finishedTime) {
		Event event = new Event();
		event.setKnownResult(knownResult);
		event.setFinishedTime(finishedTime);
		return event;
	}

}
