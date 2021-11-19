package service.core;

public class CakeSpec implements java.io.Serializable{
	private String cakeType;
	private String topping;
	private String flavor;
	private String icing;
	private int serving;
	private String decor;
	private String occasion;
	private String county;

	public CakeSpec(String cakeType, String topping, String flavor, String icing, int serving, String decor, String occasion, String county) {
		this.cakeType = cakeType;
		this.topping = topping;
		this.flavor = flavor;
		this.icing = icing;
		this.serving = serving;
		this.decor = decor;
		this.occasion = occasion;
		this.county = county;
	}

	public CakeSpec(){}

	public String getCakeType() {
		return cakeType;
	}

	public void setCakeType(String cakeType) {
		this.cakeType = cakeType;
	}

	public String getTopping() {
		return topping;
	}

	public void setTopping(String topping) {
		this.topping = topping;
	}

	public String getFlavor() {
		return flavor;
	}

	public void setFlavor(String flavor) {
		this.flavor = flavor;
	}

	public String getIcing() {
		return icing;
	}

	public void setIcing(String icing) {
		this.icing = icing;
	}

	public int getServing() {
		return serving;
	}

	public void setServing(int serving) {
		this.serving = serving;
	}

	public String getDecor() {
		return decor;
	}

	public void setDecor(String decor) {
		this.decor = decor;
	}

	public String getOccasion() {
		return occasion;
	}

	public void setOccasion(String occasion) {
		this.occasion = occasion;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}
}
