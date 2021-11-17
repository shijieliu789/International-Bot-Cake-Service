package service.core;

import java.nio.file.LinkOption;
import java.util.Date;
import java.util.List;

public class CakeSpec implements java.io.Serializable{
	private CakeTypePrice cakeTypePrice;
	private List<ToppingsPrice> topping;
	private FlavorPrice flavorPrice;
	private IcingPrice icingPrice;
	private int servingSize;
	private DecorationIntricacy decorationIntricacy;
	private Date dateExpected;
	private String occasion;
	private String county;

	public CakeSpec(CakeTypePrice cakeType, List<ToppingsPrice> topping, FlavorPrice flavor, IcingPrice icing, int servingSize, DecorationIntricacy decor, Date dateExpected, String occasion, String county) {
		this.cakeTypePrice = cakeType;
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

	public CakeTypePrice getCakeTypePrice() {
		return cakeTypePrice;
	}

	public void setCakeTypePrice(CakeTypePrice cakeTypePrice) {
		this.cakeTypePrice = cakeTypePrice;
	}

	public List<ToppingsPrice> getTopping() {
		return topping;
	}

	public void setTopping(List<ToppingsPrice> topping) {
		this.topping = topping;
	}

	public FlavorPrice getFlavorPrice() {
		return flavorPrice;
	}

	public void setFlavorPrice(FlavorPrice flavorPrice) {
		this.flavorPrice = flavorPrice;
	}

	public IcingPrice getIcingPrice() {
		return icingPrice;
	}

	public void setIcingPrice(IcingPrice icingPrice) {
		this.icingPrice = icingPrice;
	}

	public int getServingSize() {
		return servingSize;
	}

	public void setServingSize(int servingSize) {
		this.servingSize = servingSize;
	}

	public DecorationIntricacy getDecorationIntricacy() {
		return decorationIntricacy;
	}

	public void setDecorationIntricacy(DecorationIntricacy decorationIntricacy) {
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
