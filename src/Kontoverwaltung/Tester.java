package Kontoverwaltung;

import java.util.GregorianCalendar;
import javax.naming.LimitExceededException;

public class Tester {
	public static void main(String...args) {
		try {
			Bank bank = new Bank("Sparkasse Münster");
			Girokonto konto1 = new Girokonto(bank, 5000, "Paul Müller", "Woauchimmer");
			konto1.abheben(8_500, new GregorianCalendar(2017, 1, 11));
			konto1.einzahlen(1, new GregorianCalendar(2017, 1, 12));
			konto1.getKunde().printInfo();
			
			
//			Sparkonto konto2 = new Sparkonto(bank, 'p', "Bernd", "Gegenüber");
//			Kunde kunde1 = konto2.getKunde();
//			Sparkonto.eroeffnen(kunde1, bank, 'p');
//			konto2.einzahlen(1, new GregorianCalendar(2018, 0, 2));
//			konto2.einzahlen(2850, new GregorianCalendar(2018, 1, 11));
//			kunde1.printInfo(new GregorianCalendar(2019, 0, 2));
//			konto2.printKontoauszug();
		} catch (LimitExceededException e) {
			e.printStackTrace();
		}
	}
}
