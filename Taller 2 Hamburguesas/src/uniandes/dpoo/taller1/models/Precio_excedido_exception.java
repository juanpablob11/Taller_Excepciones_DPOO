package uniandes.dpoo.taller1.models;

public class Precio_excedido_exception {
	public boolean precioExcedido(Order order, Product product) {
		boolean answer = false;
		if(order.GetTotalPrice() + product.getFinalPrice() > 150000) {
			answer = true;
		}
		return answer;
	}
}
