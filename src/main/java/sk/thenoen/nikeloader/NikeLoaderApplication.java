package sk.thenoen.nikeloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import sk.thenoen.nikeloader.domain.json.RaceDto;
import sk.thenoen.nikeloader.domain.json.VysledokDto;
import sk.thenoen.nikeloader.domain.model.Event;
import sk.thenoen.nikeloader.domain.repository.EventRepository;
import sk.thenoen.nikeloader.service.BettingService;
import sk.thenoen.nikeloader.service.RaceLoadingService;
import sk.thenoen.nikeloader.service.ResultsLoadingService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class NikeLoaderApplication {

	Logger logger = LoggerFactory.getLogger(NikeLoaderApplication.class);

	@Autowired
	private RaceLoadingService raceLoadingService;

	@Autowired
	private BettingService bettingService;

	@Autowired
	private ResultsLoadingService resultsLoadingService;

	@Autowired
	private EventRepository eventRepository;

	private double bank = 100;
	private boolean lastBetWon = false;
	private double lastBet = 0.5;

	public static void main(String[] args) {
		SpringApplication.run(NikeLoaderApplication.class, args);
	}

	@Scheduled(cron = "0/30 * * * * *")
	public void betting() {
		logger.info("betting ...");
		RaceDto raceDto = raceLoadingService.loadNextRace();
		boolean betWasPlaced = bettingService.bet(raceDto);

		if (betWasPlaced) {
			lastBet *= 2;
			bank -= lastBet;
			logger.info("Bet placed: {}", lastBet);
		}
	}

	@Scheduled(cron = "0/60 * * * * *")
	public void checkingResults() {
		logger.info("checkingResults ...");

		List<Event> events = eventRepository.findAllByKnownResult(false);

		List<VysledokDto> allResults = resultsLoadingService.loadResults(events);

		boolean resultsAvailable = false;
		boolean won = false;
		double odds = 0.0;

		for (Event event : events) {
			String key = getKeyFromTimestamp(event.getFinishedTime());
			for (VysledokDto v : allResults) {
				if (v.getDatumZaciatku().equals(key)) {
					event.setKnownResult(true);
					resultsAvailable = true;
					if (v.getSuper1().equals(event.getName()) && event.getBetPlaced()) {
						int poradie = Integer.parseInt(v.getVysledok());
						if (poradie < 4) {
							event.setWinning(true);
							won = true;
							odds = event.getBetRatio();
						}
					}
				}
			}

		}

		if (resultsAvailable) {
			if (won) {
				lastBetWon = true;
				bank += lastBet * odds;

				logger.info("WIN: {}", lastBet * odds);
				logger.info("bank state: {}", bank);
				lastBet = 0.5;
			} else {
				lastBetWon = false;
				logger.info("LOST - bank state: {}", bank);
			}
		}

		eventRepository.save(events);
	}

	private String getKeyFromTimestamp(Long timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(timestamp));
		return String.format("%Td.%Tm. %TH:%TM", calendar, calendar, calendar, calendar);
		//		return new SimpleDateFormat("dd.MM. HH:MM").format(new Date(calendar.getTimeInMillis()));
	}
}
