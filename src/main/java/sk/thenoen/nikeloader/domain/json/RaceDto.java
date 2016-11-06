package sk.thenoen.nikeloader.domain.json;

import java.util.List;

public class RaceDto {

	private List<UdalostDto> udalosti;

	private String nazovSutaze;

	private Long nextTime;

	private Long timeoutDoZaciatku;

	private Long timeoutDoZobrazeniaNovej;

	public String getNazovSutaze() {
		return nazovSutaze;
	}

	public void setNazovSutaze(String nazovSutaze) {
		this.nazovSutaze = nazovSutaze;
	}

	public Long getNextTime() {
		return nextTime;
	}

	public void setNextTime(Long nextTime) {
		this.nextTime = nextTime;
	}

	public List<UdalostDto> getUdalosti() {
		return udalosti;
	}

	public void setUdalosti(List<UdalostDto> udalosti) {
		this.udalosti = udalosti;
	}

	public Long getTimeoutDoZaciatku() {
		return timeoutDoZaciatku;
	}

	public void setTimeoutDoZaciatku(Long timeoutDoZaciatku) {
		this.timeoutDoZaciatku = timeoutDoZaciatku;
	}

	public Long getTimeoutDoZobrazeniaNovej() {
		return timeoutDoZobrazeniaNovej;
	}

	public void setTimeoutDoZobrazeniaNovej(Long timeoutDoZobrazeniaNovej) {
		this.timeoutDoZobrazeniaNovej = timeoutDoZobrazeniaNovej;
	}
}
