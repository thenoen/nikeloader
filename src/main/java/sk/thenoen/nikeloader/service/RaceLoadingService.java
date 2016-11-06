package sk.thenoen.nikeloader.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import sk.thenoen.nikeloader.domain.json.RaceDto;
import sk.thenoen.nikeloader.domain.repository.RaceRepository;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Service
public class RaceLoadingService {

	private static final Logger logger = LoggerFactory.getLogger(RaceLoadingService.class);

	@Value("${race.next.url}")
	private String nextRaceUrl;

	@Autowired
	private RaceRepository raceRepository;

	public RaceDto loadNextRace() {
		RestTemplate restTemplate = new RestTemplateBuilder().build();
		ResponseEntity<String> response = restTemplate.getForEntity(nextRaceUrl, String.class);
//		logger.info(response.getBody());

		ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
		objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			RaceDto raceDto = objectMapper.readValue(response.getBody(), RaceDto.class);
			return raceDto;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void useApacheHttpClient() {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet(nextRaceUrl);

		try {
			HttpResponse httpResponse = client.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + httpResponse.getStatusLine());
			}
			String responseBody = StreamUtils.copyToString(httpResponse.getEntity().getContent(), Charset.defaultCharset());
			logger.info(new String(responseBody));
		} catch (IOException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} finally {
			httpGet.releaseConnection();
		}
	}
}
