package sk.thenoen.nikeloader.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nikeId;

	private Long finishedTime;

	private Boolean betPlaced = false;

	private Double betRatio;

	private String nikeNumber;

	private String name;

	private Boolean winning = false;

	private Boolean knownResult = false;

	private Integer place;

	public Long getId() {
		return id;
	}

	public String getNikeId() {
		return nikeId;
	}

	public void setNikeId(String nikeId) {
		this.nikeId = nikeId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(Long finishedTime) {
		this.finishedTime = finishedTime;
	}

	public Boolean getBetPlaced() {
		return betPlaced;
	}

	public void setBetPlaced(Boolean betPlaced) {
		this.betPlaced = betPlaced;
	}

	public Double getBetRatio() {
		return betRatio;
	}

	public void setBetRatio(Double betRatio) {
		this.betRatio = betRatio;
	}

	public String getNikeNumber() {
		return nikeNumber;
	}

	public void setNikeNumber(String nikeNumber) {
		this.nikeNumber = nikeNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getWinning() {
		return winning;
	}

	public void setWinning(Boolean winning) {
		this.winning = winning;
	}

	public Boolean getKnownResult() {
		return knownResult;
	}

	public void setKnownResult(Boolean knownResult) {
		this.knownResult = knownResult;
	}

	public Integer getPlace() {
		return place;
	}

	public void setPlace(Integer place) {
		this.place = place;
	}
}
