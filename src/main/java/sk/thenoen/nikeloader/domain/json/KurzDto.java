package sk.thenoen.nikeloader.domain.json;

public class KurzDto {

	private String nazov;
	private Double hodnota;
	private String tipID;

	public String getNazov() {
		return nazov;
	}

	public void setNazov(String nazov) {
		this.nazov = nazov;
	}

	public Double getHodnota() {
		return hodnota;
	}

	public void setHodnota(Double hodnota) {
		this.hodnota = hodnota;
	}

	public String getTipID() {
		return tipID;
	}

	public void setTipID(String tipID) {
		this.tipID = tipID;
	}
}
