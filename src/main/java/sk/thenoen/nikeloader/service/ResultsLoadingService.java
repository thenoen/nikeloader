package sk.thenoen.nikeloader.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.thenoen.nikeloader.domain.json.JsonConstants;
import sk.thenoen.nikeloader.domain.json.ResultsDto;
import sk.thenoen.nikeloader.domain.json.SutazDto;
import sk.thenoen.nikeloader.domain.json.VysledokDto;
import sk.thenoen.nikeloader.domain.model.Event;
import sk.thenoen.nikeloader.domain.repository.EventRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Service
public class ResultsLoadingService {

	Logger logger = LoggerFactory.getLogger(ResultsLoadingService.class);

	@Value("${race.results.url}")
	private String raceResultsUrl;

	@Autowired
	private EventRepository eventRepository;

	public List<VysledokDto> loadResults(List<Event> events) {

		List<VysledokDto> allResults = new ArrayList<>();

		List<Long> timestamtps = events.stream().filter(distinctByKey(o -> o.getFinishedTime())).map(it -> it.getFinishedTime()).collect(Collectors.toList());

		List<String> keys = events.stream().map(it -> getKeyFromTimestamp(it.getFinishedTime())).collect(Collectors.toList()).stream().filter(distinctByKey(o -> o)).collect(Collectors.toList());

		for (Long timestamp : timestamtps) {
			logger.info(getKeyFromTimestamp(timestamp));

			ResultsDto resultsDto = loadResults(timestamp + 3480000); // + 58 min.

			SutazDto sutazDto = null;
			for (SutazDto s : resultsDto.getSutaze()) {
				if (s.getNazov().equals(JsonConstants.NAZOV_SUTAZE)) {
					sutazDto = s;
				}
			}

			if (sutazDto == null) {
				return new ArrayList<>();
			} else {
				allResults.addAll(sutazDto.getZapasy().stream().flatMap(it -> it.getVysledky().stream()).collect(Collectors.toList()));
			}
		}
		return allResults;
	}

	private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	private String getKeyFromTimestamp(Long timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(timestamp));
		return String.format("%Td.%Tm. %TH:%TM", calendar, calendar, calendar, calendar);
		//		return new SimpleDateFormat("dd.MM. HH:MM").format(new Date(calendar.getTimeInMillis()));
	}

	private ResultsDto loadResults(Long timestamp) {
		RestTemplate restTemplate = new RestTemplateBuilder().build();
		ResponseEntity<String> response = restTemplate.getForEntity(raceResultsUrl, String.class, timestamp, timestamp);
		logger.info("loading results for timestamp: {}", timestamp);

		//		logger.info(response.getBody());

		ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
		objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			ResultsDto resultsDto = objectMapper.readValue(response.getBody(), ResultsDto.class);
			return resultsDto;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}
}
