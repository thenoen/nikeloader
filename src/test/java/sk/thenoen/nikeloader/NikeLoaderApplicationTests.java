package sk.thenoen.nikeloader;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sk.thenoen.nikeloader.domain.model.Race;
import sk.thenoen.nikeloader.domain.repository.RaceRepository;
import sk.thenoen.nikeloader.service.RaceLoadingService;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NikeLoaderApplicationTests {

	@Autowired
	private RaceRepository raceRepository;

	@Test
	public void contextLoads() {

		Race race = new Race();
		String name = "New_race";
		race.setName(name);

		raceRepository.save(race);

		List<Race> races = raceRepository.findAll();

		Assert.assertEquals(1, races.size());
		Assert.assertEquals(name, races.get(0).getName());

	}

}
