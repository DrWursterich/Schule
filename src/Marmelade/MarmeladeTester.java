package Marmelade;

public class MarmeladeTester {
	public static void main(String[] args) {
		Marmelade glas = new Marmelade ("Erdbeer", "30.09.09", 12);
		System.out.println(glas.ausgeben());
		
		if (glas.istLeer()) {
			System.out.println("Das Glas ist leer.");
		} else {
			glas.entnehmen(1);
			System.out.println(glas.ausgeben());
		}
	}
}