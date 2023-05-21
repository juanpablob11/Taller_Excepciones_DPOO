package uniandes.dpoo.taller1.models;

import java.util.ArrayList;
import java.util.List;


public class Combo implements Product{
	//Attributes
	private float discount;
	private String name;
	private List<Product> Elements = new ArrayList<>();
	private int final_cost;
	private int calories;
	//Constructor
	public Combo(float discount, String name) {
		this.discount = discount;
		this.name = name;
	}
	//Methods
	@Override
	public int getPrice() {
		int total_cost = 0;
		for (Product element: Elements) {
			total_cost = total_cost + element.getPrice();
		}
		float new_cost = total_cost;
		float prefinal_value = new_cost*discount;
		final_cost = Math.round(new_cost-prefinal_value);
		return final_cost;
	}

	@Override
	public String getName() {
		return name;
	}
	public int getcalories() {
		for (Product element: Elements) {
			calories = calories + element.getcalories();
		}
		return calories;
	}
	public void AddItemToCombo (Product Product) {
		Elements.add(Product);
	}
	@Override
	public String generateBillText() {
		return name + "                        " + final_cost;
	}
	public List<Product> getComboElements(){
		return Elements;
	}
	public int getFinalPrice() {
		return final_cost;
	}

}
