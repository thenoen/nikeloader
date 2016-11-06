package sk.thenoen.nikeloader.domain.json;

import java.util.List;
import java.util.Map;

public class UdalostDto {

	private String id;
	private String cislo;
	private String super1;
	private String super2;
	private List<KurzGroupDto> kurzGroups;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCislo() {
		return cislo;
	}

	public void setCislo(String cislo) {
		this.cislo = cislo;
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

	public List<KurzGroupDto> getKurzGroups() {
		return kurzGroups;
	}

	public void setKurzGroups(List<KurzGroupDto> kurzGroups) {
		this.kurzGroups = kurzGroups;
	}
}
