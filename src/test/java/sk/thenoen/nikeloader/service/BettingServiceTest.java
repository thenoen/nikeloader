package sk.thenoen.nikeloader.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import sk.thenoen.nikeloader.domain.json.RaceDto;
import sk.thenoen.nikeloader.domain.model.Event;
import sk.thenoen.nikeloader.domain.repository.EventRepository;

import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BettingServiceTest {

	@Autowired
	private BettingService bettingService;

	@Autowired
	private EventRepository eventRepository;

	@Test
	public void testBetting() throws IOException {

		ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
		objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

		RaceDto raceDto = objectMapper.readValue(new ClassPathResource("/nextRace.json").getInputStream(), RaceDto.class);

		List<Event> eventList = eventRepository.findAll();
		Assert.assertEquals(0, eventList.size());

		bettingService.bet(raceDto);

		eventList = eventRepository.findAll();
		Assert.assertEquals(6, eventList.size());

		Event bet = null;

		for (Event event : eventList) {
			if (event.getBetPlaced()) {
				bet = event;
				break;
			}
		}

		Assert.assertEquals("110247085", bet.getNikeId());
	}
}
