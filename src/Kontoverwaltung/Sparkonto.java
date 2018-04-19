package Kontoverwaltung;

import java.util.GregorianCalendar;
import javax.naming.LimitExceededException;

public class Sparkonto extends Konto {
	private char art;

	public Sparkonto(Kunde myKunde, Bank myBank, char art) throws LimitExceededException {
		super(myKunde, myBank);
		this.art = art;
	}

	public Sparkonto(Bank myBank, char art, String kundenName, String kundenAdresse) throws LimitExceededException {
		super(myBank, kundenName, kundenAdresse);
		this.art = art;
	}

	public static Sparkonto eroeffnen(Kunde myKunde, Bank myBank, char art) throws LimitExceededException {
		return new Sparkonto(myKunde, myBank, art);
	}

	public char getArt() {
		return this.art;
	}

	@Override
	public void abhebenInner(double betrag, GregorianCalendar datum) {
		if (this.kontostand-betrag < 0) {
			System.out.println(String.format("Konto %s kann nicht überzogen werden."
					+ " Buchung über -%.2f Euro vom %s wurde nicht ausgeführt.",
					this.ktoNummer, betrag, Helper.getDate(datum)));
		} else {
			super.abhebenInner(betrag, datum);
		}
	}
}
