package uniandes.dpoo.taller1.procession;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import uniandes.dpoo.taller1.models.Combo;
import uniandes.dpoo.taller1.models.Drink;
import uniandes.dpoo.taller1.models.Ingredient;
import uniandes.dpoo.taller1.models.IngredienteRepetidoException;
import uniandes.dpoo.taller1.models.MenuProduct;
import uniandes.dpoo.taller1.models.Product;
import uniandes.dpoo.taller1.models.ProductoRepetidoException;

public class restaurant_loader {
	static File ingredientsfile = new File("data/ingredientes.txt");
	static File menufile = new File("data/menu.txt");
	static File combos = new File("data/combos.txt");
	static File bebidas = new File("data/bebidas.txt");
	
	// methods of restaurant_loader to access the files from another scope
	public static File ingredientsFile() {
		return ingredientsfile;
	}
	public static File menuFile() {
		return menufile;
	}
	public static File combosFile() {
		return combos;
	}
	public static File drinksFile() {
		return bebidas;
	}
	public static restaurant cargarArchivo() throws FileNotFoundException, IOException
	{
		ProductoRepetidoException repeated_product_exception = new ProductoRepetidoException();
		IngredienteRepetidoException repetead_ingredient_exception = new IngredienteRepetidoException();
		
		//Creating Data structures to store the info
		List<Ingredient> Ingredients = new ArrayList<>();
		List<Combo> Combos = new ArrayList<>();
		List<Product> Products = new ArrayList<>();
		List<Drink> drinks = new ArrayList<>();
		List<String> exceptions = new ArrayList<>();
		//Accessing from another scope the static files of the app 
		File restaurantFile = restaurant_loader.ingredientsFile();
		File menuFile = restaurant_loader.menuFile();
		File combosFile = restaurant_loader.combosFile();
		File drinksFile = restaurant_loader.drinksFile();
		//Reading and processing the files
		BufferedReader br1 = new BufferedReader(new FileReader(restaurantFile));
		String line = br1.readLine();
		while (line != null) // When it comes to the end of the file the variable line will have null value
		{
			//System.out.println(line);
			String[] parts = line.split(";");
			String name = parts[0];
			int additionalCost = Integer.parseInt(parts[1]);
			int calories = Integer.parseInt(parts[2]);
			Ingredient ingredient = new Ingredient(name, additionalCost, calories);
			
			if(Ingredients.size()>0) {
				for(Ingredient element : Ingredients) {
					if(repetead_ingredient_exception.equalIngredient(ingredient, element)) {
						exceptions.add(name);
					}
				}
			}
			if(!exceptions.contains(name)) {
				Ingredients.add(ingredient);
			}
			line = br1.readLine(); // To read next line of the text file
		}

		br1.close();
		
		BufferedReader br2 = new BufferedReader(new FileReader(menuFile));
		String line2 = br2.readLine();
		while (line2 != null) // When it comes to the end of the file the variable line will have null value
		{
			String[] parts2 = line2.split(";");
			String name2 = parts2[0];
			int price = Integer.parseInt(parts2[1]);
			int calories = Integer.parseInt(parts2[2]);
			MenuProduct Product_menu = new MenuProduct(name2, price, calories);
			
			if(Products.size()>0) {
				for(Product element : Products) {
					if(repeated_product_exception.equalProduct(Product_menu, element)) {
						exceptions.add(name2);
					}
				}
			}
			if(!exceptions.contains(name2)) {
				Products.add(Product_menu);
			}
			line2 = br2.readLine(); // To read next line of the text file
		}
		br2.close();
		
		BufferedReader br10 = new BufferedReader(new FileReader(drinksFile));
		String line10 = br10.readLine();
		while (line10 != null) // When it comes to the end of the file the variable line will have null value
		{
			String[] parts3 = line10.split(";");
			String name3 = parts3[0];
			int price3 = Integer.parseInt(parts3[1]);
			int calories3 = Integer.parseInt(parts3[2]);
			Drink drink = new Drink(name3, price3, calories3);
			drinks.add(drink);
			line10 = br10.readLine(); // To read next line of the text file
		}
		br10.close();
		
		BufferedReader br3 = new BufferedReader(new FileReader(combosFile));
		String line3 = br3.readLine();
		while (line3 != null) // When it comes to the end of the file the variable line will have null value
		{
			String[] parts3 = line3.split(";");
			String name3 = parts3[0];
			String[] subparts = parts3[1].split("%");
			//Getting and formating the discount
			float discount = 0;
			if(subparts[0].length()>1) {
				discount = Float.parseFloat("0." + subparts[0]);
			}
			else if (subparts[0].length()<2) {
				discount = Float.parseFloat("0.0" + subparts[0]);
			}
			Combo combo = new Combo(discount, name3);
			String product1 = parts3[2];
			String product2 = parts3[3];
			String product3 = parts3[4];
			for (Product element : Products) {
				if (element.getName().equals(product1) || element.getName().equals(product2) ||
						element.getName().equals(product3)) {
					combo.AddItemToCombo(element);
				}
			}
			Combos.add(combo);
			line3 = br3.readLine(); // To read next line of the text file
		}

		br3.close();
		
		//Creating the bigger class restaurant and passing the loaded elements
		restaurant restaurant = new restaurant(Ingredients, Combos, Products, drinks, exceptions);
		return restaurant;
		
	}
}
