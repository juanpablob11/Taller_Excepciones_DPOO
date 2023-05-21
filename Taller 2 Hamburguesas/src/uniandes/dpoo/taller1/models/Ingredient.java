package uniandes.dpoo.taller1.models;

public class Ingredient 
{
	//Parameters of the class
	String name;
	int AdditionalCost;
	int calories;
	
	//Constructor
	public Ingredient(String name, int AdditionalCost, int calories) {
		this.name = name;
		this.AdditionalCost = AdditionalCost;
		this.calories = calories;
	}
	
	//Methods
	public String getName() {
		return name;
	}
	
	public int getcalories() {
		return calories;
	}
	
	public int getAdditionalCost() {
		return AdditionalCost;
	}
	
	public String ToString() {
		return "The product " + name + " has an additional price of " + AdditionalCost;
	}

}
