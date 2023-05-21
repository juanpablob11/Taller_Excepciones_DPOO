package uniandes.dpoo.taller1.procession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uniandes.dpoo.taller1.models.Combo;
import uniandes.dpoo.taller1.models.Drink;
import uniandes.dpoo.taller1.models.Ingredient;
import uniandes.dpoo.taller1.models.Order;
import uniandes.dpoo.taller1.models.Product;
import uniandes.dpoo.taller1.procession.restaurant_loader;

public class restaurant {
	//Attributes
	private List<Ingredient> Ingredients;
	private List<Combo> Combos;
	private List<Product> Products;
	private List<Drink> Drinks;
	private List<Order> Orders = new ArrayList<>();
	private List<String> exceptions;
	//Constructor
	public restaurant(List<Ingredient> Ingredients, List<Combo> Combos,
			List<Product> Products, List<Drink> Drinks, List<String> exceptions) {
		this.Ingredients = Ingredients;
		this.Combos = Combos;
		this.Products = Products;
		this.Drinks = Drinks;
		this.exceptions = exceptions;
	}
	//Methods
	// Getters 
	public List<String> getExceptions(){
		return exceptions;
	}
	public List<Ingredient> getIngredients(){
		return Ingredients;
	}
	public List<Product> getbaseMenu(){
		return Products;
		}
	public List<Drink> getDrinks(){
		return Drinks;
		}
	public List<Combo> getCombos(){
		return Combos;
	}
	public List<Order> getOrders(){
		return Orders;
	}
	//Main methods
	public void StartOrder(String customer_name, String customer_address) {
		Order new_order = new Order(customer_name, customer_address);
		Orders.add(new_order);
	}
	public void closeNsaveOrder() {
		
	}
	public Order getOrderInCourse() {
		return Orders.get(Orders.size()-1);
	}
	public void addElementToOrder(int idorder, Product product) {
		Order actualOrder = Orders.get(idorder-1);
		actualOrder.AddProduct(product);
	}
	public Order getOrderById(int idorder) {
		Order actualOrder = Orders.get(idorder-1);
		return actualOrder;
	}
	
}
