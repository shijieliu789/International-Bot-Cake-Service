package service.core;

import java.util.ArrayList;
import java.util.List;

public class CakeSpec implements java.io.Serializable{
	private int cakeType;
	private ArrayList<Integer> topping;
	private int flavor;
	private int icing;
	private int serving;
	private int decor;
	private String occasion;
	private String county;

	public CakeSpec(int cakeType, ArrayList<Integer> topping, int flavor, int icing, int serving, int decor, String occasion, String county) {
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

	public int getCakeType() {
		return cakeType;
	}

	public void setCakeType(int cakeType) {
		this.cakeType = cakeType;
	}

	public ArrayList<Integer> getTopping() {
		return topping;
	}

	public void setTopping(ArrayList<Integer> topping) {
		this.topping = topping;
	}

	public int getFlavor() {
		return flavor;
	}

	public void setFlavor(int flavor) {
		this.flavor = flavor;
	}

	public int getIcing() {
		return icing;
	}

	public void setIcing(int icing) {
		this.icing = icing;
	}

	public int getServing() {
		return serving;
	}

	public void setServing(int serving) {
		this.serving = serving;
	}

	public int getDecor() {
		return decor;
	}

	public void setDecor(int decor) {
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
