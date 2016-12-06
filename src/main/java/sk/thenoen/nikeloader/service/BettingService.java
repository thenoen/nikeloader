package sk.thenoen.nikeloader.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sk.thenoen.nikeloader.domain.json.JsonConstants;
import sk.thenoen.nikeloader.domain.json.KurzDto;
import sk.thenoen.nikeloader.domain.json.RaceDto;
import sk.thenoen.nikeloader.domain.json.UdalostDto;
import sk.thenoen.nikeloader.domain.model.Event;
import sk.thenoen.nikeloader.domain.repository.EventRepository;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Double.compare;
import static java.lang.Math.abs;
import static sk.thenoen.nikeloader.domain.json.JsonConstants.KURZ_NAZOV;

@Service
public class BettingService {

	Logger logger = LoggerFactory.getLogger(BettingService.class);

	@Autowired
	EventRepository eventRepository;

	@Value("${targetOdds}")
	private Double targetOdds;

	public boolean bet(RaceDto raceDto) {

		if (!raceDto.getNazovSutaze().equals(JsonConstants.NAZOV_SUTAZE)) {
			logger.info("We are not betting on {}", raceDto.getNazovSutaze());
			return false;
		}

		Map<KurzDto, UdalostDto> map = new HashMap<>();
		for (UdalostDto udalostDto : raceDto.getUdalosti()) {

			if (eventRepository.findByNikeId(udalostDto.getId()) != null) {
				continue;
			}

			for (KurzDto kurzDto : udalostDto.getKurzGroups().get(0).getKurzy()) {
				if (kurzDto.getNazov().equals(KURZ_NAZOV)) {
					map.put(kurzDto, udalostDto);
				}
			}
		}

		if (map.isEmpty()) {
			logger.info("Bet already placed");
			return false;
		}

		KurzDto bestKurzDto = map.keySet().stream().sorted((f1, f2) -> compare(abs(f1.getHodnota() - targetOdds), abs(f2.getHodnota() - targetOdds))).findFirst().get();

		// todo: bug - if there is some delay in code it may happen that that finished time will be few miliseconds late
		// System.currentTimeMillis() should be taken as soon as raceDto is received;
		// RaceLoadingService:46 - put it inside raceDta as transient property
		// or just for result fetching purposes decrease this time by a few minutes in request parameter (method loadResults)
		Long finishedTime = System.currentTimeMillis() + (raceDto.getTimeoutDoZaciatku() * 1000);

		for (Map.Entry<KurzDto, UdalostDto> entry : map.entrySet()) {
			Event event;
			if (entry.getKey().equals(bestKurzDto)) {
				event = createEvent(raceDto, entry, true, finishedTime);
				event = eventRepository.save(event);
			} else {
				event = createEvent(raceDto, entry, false, finishedTime);
				event = eventRepository.save(event);
			}
			logger.info("Event: {} - {} - {} - {}", event.getNikeNumber(), event.getName(), event.getBetPlaced(), event.getFinishedTime());
		}

		return true;
	}

	private Event createEvent(RaceDto raceDto, Map.Entry<KurzDto, UdalostDto> entry, Boolean betPlaced, Long finishedTime) {
		Event event = new Event();
		event.setName(entry.getValue().getSuper1());
		event.setBetPlaced(betPlaced);
		event.setBetRatio(entry.getKey().getHodnota());
		event.setNikeId(entry.getValue().getId());
		event.setFinishedTime(finishedTime);
		event.setNikeNumber(entry.getValue().getCislo());
		return event;
	}
}
