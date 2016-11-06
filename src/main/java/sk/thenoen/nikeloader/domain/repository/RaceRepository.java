package sk.thenoen.nikeloader.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.thenoen.nikeloader.domain.model.Race;

public interface RaceRepository extends JpaRepository<Race, Long> {

}
