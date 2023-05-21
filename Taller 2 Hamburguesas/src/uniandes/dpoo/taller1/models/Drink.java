package uniandes.dpoo.taller1.models;

public class Drink implements Product{
	//Attributes
		private String name;
		private int baseprice;
		private int calories;
		//Constructor
		public Drink(String name, int baseprice, int calories) {
			this.name = name;
			this.baseprice = baseprice;
			this.calories = calories;
		}
		@Override
		public String getName() {
			return name;
		}
		
		public int getcalories() {
			return calories;
		}
		
		@Override
		public int getPrice() {
			return baseprice;
		}

		@Override
		public String generateBillText() {
			return name + "                        " + baseprice;
		}
		
		public int getFinalPrice() {
			return baseprice;
		}
}
