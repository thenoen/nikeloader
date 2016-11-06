package sk.thenoen.nikeloader.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.thenoen.nikeloader.domain.model.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

	public Event findByNikeId(String nikeId);

	public List<Event> findAllByKnownResult(Boolean knownResult);

}
