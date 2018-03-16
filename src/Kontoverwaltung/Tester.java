package Kontoverwaltung;

public class Tester {
	public static void main(String...args) {
		Bank bank = new Bank("Arnes Bank");
		Kunde kunde1 = new Kunde(bank, "Bernd", "Gegenüber");
		Girokonto g1 = new Girokonto(kunde1, bank, 4000);
		Girokonto g2 = new Girokonto(kunde1, bank, 4000);
		g1.abheben(5000);
		kunde1.printInfo();
		g1.printKontoauszug();
	}
}
