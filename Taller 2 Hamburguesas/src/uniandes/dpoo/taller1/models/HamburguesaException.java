package uniandes.dpoo.taller1.models;

public abstract class HamburguesaException {
	public boolean equalName(String name1, String name2) {
		boolean answer = false;
		if(name1.equals(name2)) {
			answer = true;
		}
		return answer;
	}
	public boolean equalPrice(int price1, int price2) {
		boolean answer = false;
		if(price1 == price2) {
			answer = true;
		}
		return answer;
	}
	
}
