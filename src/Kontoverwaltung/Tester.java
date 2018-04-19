package Kontoverwaltung;

import java.util.GregorianCalendar;
import javax.naming.LimitExceededException;

public class Tester {
	public static void main(String...args) {
		try {
			Bank bank = new Bank("Sparkasse Münster");

			Girokonto konto1 = new Girokonto(bank, 5000, "Paul Müller", "Woauchimmer");
			konto1.abheben(8_500, new GregorianCalendar(2017, 1, 11));
			System.out.println();
			konto1.einzahlen(1, new GregorianCalendar(2018, 1, 10));
			konto1.getKunde().printInfo(new GregorianCalendar(2018, 3, 2));
			System.out.println();

			Sparkonto konto2 = new Sparkonto(bank, 'p', "Lieschen Fleisig", "Gegenüber");
			Kunde kunde2 = konto2.getKunde();
			konto2.einzahlen(1, new GregorianCalendar(2018, 0, 2));
			konto2.einzahlen(2_850, new GregorianCalendar(2018, 1, 11));
			Girokonto konto3 = new Girokonto(kunde2, bank, 5000);
			konto3.einzahlen(1, new GregorianCalendar(2018, 0, 2));
			konto3.abheben(100.5, new GregorianCalendar(2018, 1, 11));
			konto3.abheben(50, new GregorianCalendar(2018, 1, 11));
			kunde2.printInfo(new GregorianCalendar(2019, 0, 2));
			System.out.println();
			konto2.printKontoauszug(new GregorianCalendar(2019, 0, 2));
			System.out.println("\n");

			konto1.printKontoauszug(new GregorianCalendar(2018, 3, 3));
			System.out.println("\n");

			konto3.printKontoauszug(new GregorianCalendar(2018, 3, 2));
		} catch (LimitExceededException e) {
			e.printStackTrace();
		}
	}
}
