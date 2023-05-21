package uniandes.dpoo.taller1.models;

public class IngredienteRepetidoException extends HamburguesaException{

	public boolean equalIngredient(Ingredient ingredient_1, Ingredient ingredient_2) {
		boolean answer = false;
		boolean equalname = equalName(ingredient_1.getName(), ingredient_2.getName());
		boolean equalprice = equalPrice(ingredient_1.getAdditionalCost(), ingredient_2.getAdditionalCost());
		if(equalname == true && equalprice == true) {
			answer = true;
		}
		return answer;
	}

}
