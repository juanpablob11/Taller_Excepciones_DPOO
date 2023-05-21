package uniandes.dpoo.taller1.console;

import org.junit.Test;

import uniandes.dpoo.taller1.models.Order;

import static org.junit.Assert.assertEquals;

public class AppTest {
	@Test
	   public void testOrder1(Order order) {
	       assertEquals(15000, order.getprice());
	       System.out.println("Test for adding products and getting price completed succesfully");
	    }
	 	public void testOrder2(Order order) {
	       assertEquals(57900, order.getprice());
	       System.out.println("Test for adding 3 products and getting price completed succesfully");
	    }
	 	public void testOrder3(Order order) {
		       assertEquals(17000, order.getprice());
		       System.out.println("Test for adding 3 products and adjusted product and getting price completed succesfully");
		    }
}
