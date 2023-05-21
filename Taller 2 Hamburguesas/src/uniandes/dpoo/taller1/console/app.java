package uniandes.dpoo.taller1.console;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


import uniandes.dpoo.taller1.models.AdjustedProduct;
import uniandes.dpoo.taller1.models.Combo;
import uniandes.dpoo.taller1.models.Drink;
import uniandes.dpoo.taller1.models.Ingredient;
import uniandes.dpoo.taller1.models.MenuProduct;
import uniandes.dpoo.taller1.models.Order;
import uniandes.dpoo.taller1.models.Precio_excedido_exception;
import uniandes.dpoo.taller1.models.Product;
//imports from classes
import uniandes.dpoo.taller1.procession.restaurant;
import uniandes.dpoo.taller1.procession.restaurant_loader;

public class app {
	
	private restaurant restaurant;
	public void run_app() 
	{
		System.out.println("***Welcome to the restaurant app!***");

		boolean continue_app = true;
		while (continue_app)
		{
			try
			{
				showMenu();
				int selected_option = Integer.parseInt(input("Please choose an option"));
				if (selected_option == 1)
					executeShowMenu();
				else if (selected_option == 2 && restaurant != null)
					executeInitializeOrder();
				else if (selected_option == 3 && restaurant != null)
					executeAddElement();
				else if (selected_option == 4 && restaurant != null)
					executeCloseandSave();
				else if (selected_option == 5 && restaurant != null)
					executeFilterbyId();
				else if(selected_option == 7 && restaurant != null)
					executeJunit_test();
				else if (selected_option == 6)
				{
					System.out.println("Gettin' out of the app...");
					continue_app = false;
				}
				else if (restaurant == null)
				{
					System.out.println("In order to load the options of the app, you must first show the menu"
							+ " to charge the data into the datastructures.");
				}
				else
				{
					System.out.println("Please chooose a correct option.");
				} 
			}
			catch (NumberFormatException e)
			{
				System.out.println("You must select one of the numbers from the options.");
			}
		}
	}
	
	public void executeJunit_test() {
		String junitoption = input("-> Please type 1 for 1st unit test adding to an order (unit tests)\n"
				+ "2 for 2nd test adding more elements for searching the correct price (unit tests)\n"
				+ "and 3 for test using adjusted product (Integration testing)");
		AppTest app_test = new AppTest();
		// unit tests
		if(junitoption.equals("1")) {
			//1st unit test adding to an order
			restaurant.StartOrder("test_name", "test_adress");
			Order order = restaurant.getOrderById(1);
			restaurant.addElementToOrder(1, restaurant.getbaseMenu().get(12));
			((AppTest) app_test).testOrder1(order);
			}
			else if(junitoption.equals("2")) {
			//2nd test adding more elements for searching the correct price
				restaurant.StartOrder("test_name2", "test_adress");
				Order order = restaurant.getOrderById(2);
				restaurant.addElementToOrder(2, restaurant.getbaseMenu().get(12));
				restaurant.addElementToOrder(2, restaurant.getbaseMenu().get(13));
				restaurant.addElementToOrder(2, restaurant.getbaseMenu().get(14));
				((AppTest) app_test).testOrder2(order);
				}
			else {
		// Integration testing
			//test using adjusted product
			restaurant.StartOrder("test_name3", "test_adress");
			Order order = restaurant.getOrderById(3);
			AdjustedProduct adjproduct = new AdjustedProduct((MenuProduct) restaurant.getbaseMenu().get(12));
			Ingredient ingredient = restaurant.getIngredients().get(0);
			adjproduct.AddIngredient(ingredient);
			adjproduct.getPrice();
			adjproduct.getName();	
			restaurant.addElementToOrder(3, adjproduct);
			((AppTest) app_test).testOrder3(order);
		}
	}

	public void showMenu()
	{
		System.out.println("\n-> Hamburguer App options <-");
		System.out.println("1. Show the menu.");
		System.out.println("2. Initialize a new order.");
		System.out.println("3. Add a new element to an order. (in order to add elements you must have created an order first!)"); 
		System.out.println("4. Close an order and save bill. (You have to use option first option 3)");
		System.out.println("5. Filter an order by id. (You have to first load option 4 to use this)");
		System.out.println("6. Get out of the App.\n");
		System.out.println("7. JUnit test 1.\n");
	}
	
	//1st option
	private void executeShowMenu() {
		try {
			System.out.println();
			restaurant = restaurant_loader.cargarArchivo();
			System.out.println();
			System.out.println("*** Menu loaded ***");
			if(restaurant.getExceptions().size()>0) {
				System.out.println("We´ve the following exceptions loading the menu: ");
				for ( String element : restaurant.getExceptions()) {
					System.out.println("   -> " + element + ".");
				}
			}
			System.out.println("We have the following products available today: ");
			for ( Product element : restaurant.getbaseMenu()) {
				System.out.println("   -> " + element.getName() + 
						" and it has a price of $" + element.getPrice() + ".");
			}
			System.out.println();
			System.out.println("We have the following drinks available today: ");
			for ( Product element : restaurant.getDrinks()) {
				System.out.println("   -> " + element.getName() + 
						" and it has a price of $" + element.getPrice() + ".");
			}
			System.out.println();
			System.out.println("We have the following combos available today: ");
			for ( Combo element : restaurant.getCombos()) {
				System.out.println("   -> " + element.getName() + 
						" has these products: ");
				System.out.print("          ");
				for (Product subelement : element.getComboElements()) {
					System.out.print(subelement.getName() + ", ");
						}
				System.out.println("and it has a total price of $" + element.getPrice() + ".");
			}
			System.out.println();
			System.out.println("Finally, we have the following ingredients available today: ");
			for ( Ingredient element : restaurant.getIngredients()) {
				System.out.println("   -> " + element.getName() + 
						" and it has an additional cost of $" + element.getAdditionalCost() + ".");
			}
		} catch (FileNotFoundException e) {
			System.out.println("->ERROR: The menu, ingredient and combos file were not found.");
		} catch (IOException e) {
			System.out.println("ERROR: There were some problems reading the files, please come later.");
			System.out.println(e.getMessage());
		}
	}
	
	//2nd option
	private void executeInitializeOrder() {
		System.out.println();
		System.out.println("*** Initializing your order ***");
		String name = input("-> Please type your name for the order");
		String address = input("-> Please type your address to send the order");
		System.out.println("-------------------------");
		restaurant.StartOrder(name, address);
		System.out.println("Your order has been initialized and it has an id of "
				+ restaurant.getOrderInCourse().GetIdOrder());
	}
	
	//3rd option
	private void executeAddElement() {
		boolean continueAdding = true;
		while (continueAdding) {
		System.out.println();
		System.out.println("*** Adding an element to your order ***");
		System.out.println("-> Do you want to "
				+ "add a single product or a combo ? "
				+ "(Type 1 for product");
		int priv_option = Integer.parseInt(input(" 2 for combo, 3 for a drink and "
				+ "4 to get out of this option when you´re finished)"));
		System.out.println();
			if (priv_option == 1) {
				Precio_excedido_exception Precio_excedido_exception3 = new Precio_excedido_exception();
				int  orderid = 0;
				//Getting a single product without changes
				boolean ordervalue = true;
				while (ordervalue) {
				orderid = Integer.parseInt(input("Which is the number of your order"));
				List<Integer> helplist = new ArrayList<>();		
				for (Order element : restaurant.getOrders()) {
					helplist.add(element.GetIdOrder());				
					}
				if (helplist.contains(orderid)) {
					System.out.println("Your order has been found!");
					ordervalue = false;
					}
				else {
					System.out.println("Your order hasnt been found, "
							+ "please type a correct id order.");
					}
				}
				System.out.println();
				System.out.println("We have the following products available today :");
				int counter = 1;
				for ( Product element : restaurant.getbaseMenu()) {
					System.out.println("   ->" + counter + ". " +element.getName() + 
						" and it has a price of $" + element.getPrice() + ".");
						counter ++;	
				}
				System.out.println();
				int suboption = Integer.parseInt(input("Please select number of product that you want"));
				MenuProduct subproduct = (MenuProduct) restaurant.getbaseMenu().get(suboption-1);
				//If it is wanted you can add or eliminate ingredients from 
				//your product
				System.out.println("->Type 1 if you want to add another"
						+ " ingredient to your product, ");
				int adjusted = Integer.parseInt(input("2 if you want to delete an ingredient from"
						+ " your product or 3 if you want the product as it is"));
				System.out.println();
				if(adjusted == 1) {
					System.out.println("*** Adding an ingredient to a product ***");
					System.out.println("We have the following ingredients available today :");
					AdjustedProduct adjproduct = new AdjustedProduct(subproduct);
					int counterforingredients = 1;
					for ( Ingredient element : restaurant.getIngredients()) {
						System.out.println("   ->" + counterforingredients + ". " +element.getName() + 
							" and it has a price of $" + element.getAdditionalCost() + ".");
						counterforingredients ++;	
					}
					System.out.println();
					int suboption2 = Integer.parseInt(input("Please select the number "
							+ "of ingredient that you want to add"));
					Ingredient ingredient = restaurant.getIngredients().get(suboption2-1);
					adjproduct.AddIngredient(ingredient);
					adjproduct.getPrice();
					adjproduct.getName();					
					
					if(Precio_excedido_exception3.precioExcedido(restaurant.getOrderById(orderid), adjproduct)) {
						System.out.println("The order final value passes the 150000 top value."
								+ "You cant order this because of its price as we mentioned before.");
					}
					else {
						restaurant.addElementToOrder(orderid, adjproduct);
						System.out.println("The product has been added to your order");
					}
				}
				else if(adjusted == 2) {
					System.out.println("*** Eliminating an ingredient to a product ***");
					System.out.println("We have the following ingredients available today :");
					AdjustedProduct adjproduct = new AdjustedProduct(subproduct);
					int counterforingredients2 = 1;
					for ( Ingredient element : restaurant.getIngredients()) {
						System.out.println("   ->" + counterforingredients2 + ". " + element.getName() + ".");
						counterforingredients2 ++;	
					}
					System.out.println();
					int suboption2 = Integer.parseInt(input("Please select the number "
							+ "of ingredient that you want to eliminate"));
					Ingredient ingredient = restaurant.getIngredients().get(suboption2-1);
					adjproduct.EliminateIngredient(ingredient);
					adjproduct.getName();
					
					if(Precio_excedido_exception3.precioExcedido(restaurant.getOrderById(orderid), adjproduct)) {
						System.out.println("The order final value passes the 150000 top value."
								+ "You cant order this because of its price as we mentioned before.");
					}
					else {
						restaurant.addElementToOrder(orderid, adjproduct);
						System.out.println("The product has been added to your order");
					}
				}
				
				if(adjusted == 3) {
					
					if(Precio_excedido_exception3.precioExcedido(restaurant.getOrderById(orderid), subproduct)) {
						System.out.println("The order final value passes the 150000 top value."
								+ "You cant order this because of its price as we mentioned before.");
					}
					else {
						restaurant.addElementToOrder(orderid, subproduct);
						System.out.println("The product has been added to your order");
					}
				}
			}
			//Getting a combo
			else if (priv_option == 2) {
				Precio_excedido_exception Precio_excedido_exception2 = new Precio_excedido_exception();
				int  orderid = 0;
				boolean ordervalue = true;
				while (ordervalue) {
				orderid = Integer.parseInt(input("->Which is the number of your order"));
				List<Integer> helplist = new ArrayList<>();		
				for (Order element : restaurant.getOrders()) {
					helplist.add(element.GetIdOrder());				
					}
				if (helplist.contains(orderid)) {
					System.out.println("Your order has been found!");
					ordervalue = false;
					}
				else {
					System.out.println("Your order hasnt been found, "
							+ "please type a correct id order.");
					}
				}
				System.out.println();
				System.out.println("We have the following combos available today: ");
				int counterforcombos = 1;
				for ( Combo element : restaurant.getCombos()) {
					System.out.println("   -> " + counterforcombos + ". " + 
							element.getName() + " has these products: ");
					System.out.print("          ");
					for (Product subelement : element.getComboElements()) {
						System.out.print(subelement.getName() + ", ");
							}
					System.out.println("and it has a total price of $" + element.getPrice() + ".");
				counterforcombos ++;
				}
				int suboption = Integer.parseInt(input("Please select number of combo"
						+ " that you want"));
				Product combo = restaurant.getCombos().get(suboption-1);
				
				restaurant.getOrderById(orderid).GetNetPrice();
				if(Precio_excedido_exception2.precioExcedido(restaurant.getOrderById(orderid), combo)) {
					System.out.println("The order final value passes the 150000 top value."
							+ "You cant order this because of its price as we mentioned before.");
				}
				else {
					restaurant.addElementToOrder(orderid, combo);
					System.out.println("The combo has been added to your order");
				}
			}
			else if (priv_option == 3) {
				Precio_excedido_exception Precio_excedido_exception = new Precio_excedido_exception();
				int  orderid = 0;
				boolean ordervalue = true;
				while (ordervalue) {
				orderid = Integer.parseInt(input("->Which is the number of your order"));
				List<Integer> helplist = new ArrayList<>();		
				for (Order element : restaurant.getOrders()) {
					helplist.add(element.GetIdOrder());				
					}
				if (helplist.contains(orderid)) {
					System.out.println("Your order has been found!");
					ordervalue = false;
					}
				else {
					System.out.println("Your order hasnt been found, "
							+ "please type a correct id order.");
					}
				}
				System.out.println();
				System.out.println("We have the following drinks available today: ");
				int counterfordrinks = 1;
				for ( Drink element : restaurant.getDrinks()) {
					System.out.println("   -> " + counterfordrinks + ". " + element.getName()
							+ " and it has a total price of $" + element.getPrice() + ".");
					counterfordrinks ++;
				}
				int suboption = Integer.parseInt(input("Please select number of drink"
						+ " that you want"));
				Product drink = restaurant.getDrinks().get(suboption-1);
				if(Precio_excedido_exception.precioExcedido(restaurant.getOrderById(orderid), drink)) {
					System.out.println("The order final value passes the 150000 top value."
							+ "You cant order this because of its price as we mentioned before.");
				}
				else {
				restaurant.addElementToOrder(orderid, drink);
				System.out.println("The drink has been added to your order");
				}
			}
			//Getting out of the option
			else if (priv_option == 4) {
				System.out.println("Finishing the proccess of adding "
						+ "elements  to your order");
				continueAdding = false;
			}
		}
	}
	
	//4th option
	private void executeCloseandSave() {
		int  orderid = 0;
		//Getting the order to present the bill in a txt file
		boolean ordervalue = true;
		while (ordervalue) {
		orderid = Integer.parseInt(input("->Which is the number of the order that"
				+ " you want to close?"));
		List<Integer> helplist = new ArrayList<>();		
		for (Order element : restaurant.getOrders()) {
			helplist.add(element.GetIdOrder());				
			}
		if (helplist.contains(orderid)) {
			System.out.println("Your order has been found!");
			ordervalue = false;
			}
		else {
			System.out.println("Your order hasnt been found, "
					+ "please type a correct id order.");
			}
		}
		Order orderToClose = restaurant.getOrderById(orderid);
		try {
			FileWriter writer = new FileWriter("bill.txt");
			writer.write("********* HAMBURGUER APP SERVICE *********");
			writer.write("\n		Hello " + orderToClose.getNameofCustomer().toUpperCase() + "!"
			+ "\n	We will send your order to the\n following address: "
			+ orderToClose.getCustomerAdress().toUpperCase());
			writer.write("\n" + "******************************************");
			int netpriceOrder = orderToClose.GetNetPrice();
			int ivapriceOrder = orderToClose.GetIvaPrice();
			int finalPrice = orderToClose.GetTotalPrice();
			writer.write("\n->Description                        ->Price");
			for (Product element : orderToClose.getElements()) {
				writer.write("\n" + element.generateBillText()
				+ " and has " + element.getcalories() + " calories");
			}
			writer.write("\n" + "******************************************");
			writer.write("\n" + "->Net price to pay :" + netpriceOrder);
			writer.write("\n" + "-> IVA price :" + ivapriceOrder);
			writer.write("\n" + "-> Total price to pay :" + finalPrice);
			writer.write("\n" + "******************************************");
			writer.write("\n************** THANK YOU !! **************");
			writer.close();
			System.out.println("-> Your receipt has been made, "
					+ "please check the bill.txt file.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//5th option
	private void executeFilterbyId() {
		int  orderid = 0;
		//Getting the order to filter
		boolean ordervalue = true;
		while (ordervalue) {
		orderid = Integer.parseInt(input("->Which is the number of the order that"
				+ " you want to close?"));
		List<Integer> helplist = new ArrayList<>();		
		for (Order element : restaurant.getOrders()) {
			helplist.add(element.GetIdOrder());				
			}
		if (helplist.contains(orderid)) {
			System.out.println("Your order has been found!");
			ordervalue = false;
			}
		else {
			System.out.println("Your order hasnt been found, "
					+ "please type a correct id order.");
			}
		}
		Order orderToFilter = restaurant.getOrderById(orderid);
		System.out.println("******************************");
		System.out.println();
		System.out.println("The order found has the following attributtes: ");
		System.out.println("-> Name of the customer : " + orderToFilter.getNameofCustomer());
		System.out.println("-> Address of the customer: " + orderToFilter.getCustomerAdress());
		System.out.println("The order " + orderToFilter.GetIdOrder() + 
				" has the following products: ");
		for (Product element : orderToFilter.getElements()) {
			System.out.println("-> " + element.getName() + 
			" with price of " + element.getFinalPrice()
			+ " and # calories of: " + element.getcalories());
		}
		System.out.println();
		int finalPrice = orderToFilter.GetTotalPrice();
		System.out.println("The order has a final price of: "
				+ finalPrice);
	}
	
	// input function to read what is written from console
	public String input(String message)
	{
		try
		{
			System.out.print(message + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error reading from console");
			e.printStackTrace();
		}
		return null;
	}
	
	//Main method to run the application
	public static void main(String[] args)
	{
		app new_app = new app();
		new_app.run_app();
	}
}
