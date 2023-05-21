package uniandes.dpoo.taller1.models;

import java.util.ArrayList;
import java.util.List;

public class AdjustedProduct implements Product{
	//Attributes
	String name;
	int price;
	String added_products;
	int calories;
	private List<Ingredient> AddedElements = new ArrayList<>();
	private List<Ingredient> EliminatedElements = new ArrayList<>();
	//Constructor
	public AdjustedProduct(MenuProduct subproduct) {
		this.copy(subproduct);
	}
	public void copy (MenuProduct subproduct) {
		this.setName(subproduct.getName());
		this.setPrice(subproduct.getPrice());
		this.setcalories(subproduct.getcalories());
	}
	//methods
	public void setName(String name) {
		this.name = name;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public void setcalories(int calories) {
			this.calories = calories;
	}
	public void AddIngredient(Ingredient ingredient) {
		AddedElements.add(ingredient);
	}
	public void EliminateIngredient(Ingredient ingredient) {
		EliminatedElements.add(ingredient);
	}
	@Override
	public int getPrice() {
		for (Ingredient element : AddedElements) {
			price = price + element.getAdditionalCost();
		}
		return price;
	}
	
	public int getFinalPrice() {
		return price;
	}
	
	public int getcalories() {
		for (Ingredient element : AddedElements) {
			calories = calories + element.getcalories();
		}
		return calories;
	}
	
	@Override
	public String getName() {
		added_products = name;
		if (AddedElements.size()>0 && AddedElements.size()<2) {
		added_products = added_products + " with an addition of ";
		for (Ingredient element : AddedElements) {
			added_products = "\n" + added_products + element.getName() + ",";
			}
		added_products = added_products.substring(0, added_products.length()-1);
		}
		else if (AddedElements.size()>1){
			added_products = added_products + " with additions of ";
			for (Ingredient element : AddedElements) {
				added_products = "\n" + added_products + element.getName() + ",";
				}
			added_products = added_products.substring(0, added_products.length()-1);
		}
		if (EliminatedElements.size()>0 && EliminatedElements.size()<2) {
			added_products = added_products + " without the ingredient ";
			for (Ingredient element : EliminatedElements) {
				added_products = "\n" + added_products + element.getName() + ",";
				}
			added_products = added_products.substring(0, added_products.length()-1);
			}
		else if (EliminatedElements.size()>1){
			added_products = added_products + " and without these ingredients ";
			for (Ingredient element : EliminatedElements) {
				added_products = "\n" + added_products + element.getName() + ",";
				}
			added_products = added_products.substring(0, added_products.length()-1);
		}
		return added_products;
	}

	@Override
	public String generateBillText() {
		return added_products + " " + price;
	}

}
