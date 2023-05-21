package uniandes.dpoo.taller1.models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;

import uniandes.dpoo.taller1.models.Product;
public class Order {
	//parameters
	String name_customer;
	String customer_address;
	static int instancecounter = 0;
	static int instancecounter2 = 0;
	int IdOrder;
	int number_of_orders;
	int totalprice = 0;
	int ivaprice = 0;
	int netprice = 0;
	int price = 0;
	private List<Product> Elements = new ArrayList<>();
	//constructor
	public Order(String name_customer, String customer_address) {
		this.name_customer = name_customer;
		this.customer_address = customer_address;
		instancecounter++;
		instancecounter2++;
		IdOrder = instancecounter;
		number_of_orders = instancecounter2;
	}
	
	//Methods
	public int GetIdOrder() {
		return IdOrder;
	}
	
	public String getNameofCustomer() {
		return name_customer;
	}
	
	public String getCustomerAdress() {
		return customer_address;
	}
	
	public int getnumOrders() {
		return number_of_orders;
	}
	
	public void AddProduct(Product newProduct) {
		Elements.add(newProduct);
		price = price + newProduct.getPrice();
	}
	
	public int GetNetPrice() {
		for (Product element : Elements) {
			int subvariable = element.getFinalPrice();
			netprice = netprice + subvariable;
		}
		return netprice;
	}
	
	public int GetIvaPrice() {
		double subnet = netprice;
		double iva = subnet * 0.19;
		ivaprice = (int)iva;
		return ivaprice;
	}
	
	public int GetTotalPrice() {
		totalprice = ivaprice + netprice;
		return totalprice;
	}
	
	public int getprice() {return price;}
	
	public List<Product> getElements(){
		return Elements;
	}
	
}
