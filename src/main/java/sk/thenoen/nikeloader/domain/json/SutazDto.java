package sk.thenoen.nikeloader.domain.json;

import java.util.List;

public class SutazDto {

	private String nazov;
	private List<ZapasDto> zapasy;

	public String getNazov() {
		return nazov;
	}

	public void setNazov(String nazov) {
		this.nazov = nazov;
	}

	public List<ZapasDto> getZapasy() {
		return zapasy;
	}

	public void setZapasy(List<ZapasDto> zapasy) {
		this.zapasy = zapasy;
	}
}
