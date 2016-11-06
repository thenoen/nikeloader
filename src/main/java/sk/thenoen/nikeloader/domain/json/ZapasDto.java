package sk.thenoen.nikeloader.domain.json;

import java.util.List;

public class ZapasDto {


	private List<VysledokDto> vysledky;

	public List<VysledokDto> getVysledky() {
		return vysledky;
	}

	public void setVysledky(List<VysledokDto> vysledky) {
		this.vysledky = vysledky;
	}
}
