package Kontoverwaltung;

public class Tester {
	public static void main(String...args) {
		Bank Mario = new Bank("Arne");
		Girokonto g = new Girokonto(new Kunde(Mario, "Bernd", "Gegenüber"), Mario, 4000);
		g.abheben(5000);
	}
}
