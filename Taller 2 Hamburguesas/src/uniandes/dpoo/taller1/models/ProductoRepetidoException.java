package uniandes.dpoo.taller1.models;

public class ProductoRepetidoException extends HamburguesaException{

	public boolean equalProduct(Product product_1, Product product_2) {
		boolean answer = false;
		boolean equalname = equalName(product_1.getName(), product_2.getName());
		boolean equalprice = equalPrice(product_1.getPrice(), product_2.getPrice());
		if(equalname == true && equalprice == true) {
			answer = true;
		}
		return answer;
	}

}
