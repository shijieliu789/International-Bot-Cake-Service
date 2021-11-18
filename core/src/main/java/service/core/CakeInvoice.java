package service.core;

public class CakeInvoice implements java.io.Serializable{

	private String cakery;
	private String reference;
	private double price;

	public CakeInvoice(String cakery, String reference, double price) {
		this.cakery = cakery;
		this.reference = reference;
		this.price = price;
	}

	public CakeInvoice() {
	}

	public String getCakery() {
		return cakery;
	}

	public void setCakery(String cakery) {
		this.cakery = cakery;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
