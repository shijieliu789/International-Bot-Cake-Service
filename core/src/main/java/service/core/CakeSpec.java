package service.core;

import java.util.Date;
import java.util.List;

public class CakeSpec implements java.io.Serializable{
	private int cakeTypePrice;
	private List<Integer> topping;
	private int flavorPrice;
	private int icingPrice;
	private int servingSize;
	private int decorationIntricacy;
	private Date dateExpected;
	private String occasion;
	private String county;

	public CakeSpec(int cakeTypePrice, List<Integer> topping, int flavor, int icing, int servingSize, int decor, Date dateExpected, String occasion, String county) {
		this.cakeTypePrice = cakeTypePrice;
		this.topping = topping;
		this.flavorPrice = flavor;
		this.icingPrice = icing;
		this.servingSize = servingSize;
		this.decorationIntricacy = decor;
		this.dateExpected = dateExpected;
		this.occasion = occasion;
		this.county = county;
	}

	public CakeSpec(){}

	public int getCakeTypePrice() {
		return cakeTypePrice;
	}

	public void setCakeTypePrice(int cakeTypePrice) {
		this.cakeTypePrice = cakeTypePrice;
	}

	public List<Integer> getTopping() {
		return topping;
	}

	public void setTopping(List<Integer> topping) {
		this.topping = topping;
	}

	public int getFlavorPrice() {
		return flavorPrice;
	}

	public void setFlavorPrice(int flavorPrice) {
		this.flavorPrice = flavorPrice;
	}

	public int getIcingPrice() {
		return icingPrice;
	}

	public void setIcingPrice(int icingPrice) {
		this.icingPrice = icingPrice;
	}

	public int getServingSize() {
		return servingSize;
	}

	public void setServingSize(int servingSize) {
		this.servingSize = servingSize;
	}

	public int getDecorationIntricacy() {
		return decorationIntricacy;
	}

	public void setDecorationIntricacy(int decorationIntricacy) {
		this.decorationIntricacy = decorationIntricacy;
	}

	public Date getDateExpected() {
		return dateExpected;
	}

	public void setDateExpected(Date dateExpected) {
		this.dateExpected = dateExpected;
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
