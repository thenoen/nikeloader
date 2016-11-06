package sk.thenoen.nikeloader.domain.json;

public class VysledokDto {

	private String datumZaciatku;

	private String super1;

	private String super2;

	private String vysledok;

	private String skore;

	public String getDatumZaciatku() {
		return datumZaciatku;
	}

	public void setDatumZaciatku(String datumZaciatku) {
		this.datumZaciatku = datumZaciatku;
	}

	public String getSuper1() {
		return super1;
	}

	public void setSuper1(String super1) {
		this.super1 = super1;
	}

	public String getSuper2() {
		return super2;
	}

	public void setSuper2(String super2) {
		this.super2 = super2;
	}

	public String getVysledok() {
		return vysledok;
	}

	public void setVysledok(String vysledok) {
		this.vysledok = vysledok;
	}

	public String getSkore() {
		return skore;
	}

	public void setSkore(String skore) {
		this.skore = skore;
	}
}
