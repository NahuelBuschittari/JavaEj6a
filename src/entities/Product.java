package entities;

public class Product {
	private int idproduct;
	private String name;
	private String description;
	private double price;
	private int stock;
	private boolean shippingIncluded;
	
	public int getIdproduct() {
		return idproduct;
	}
	public void setIdproduct(int idproduct) {
		this.idproduct = idproduct;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public boolean isShippingIncluded() {
		return shippingIncluded;
	}
	public void setShippingIncluded(boolean shippingIncluded) {
		this.shippingIncluded = shippingIncluded;
	}
	public Product() {};
	
	public String mostrarDatos() {
		return this.getIdproduct()+"		"+this.getName()+"		"+
	this.getDescription()+"		$"+this.getPrice()+"		"+this.getStock()+"		"+this.isShippingIncluded();
		
	}

}
