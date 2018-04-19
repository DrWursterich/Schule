package Kontoverwaltung;

import java.util.GregorianCalendar;
import javax.naming.LimitExceededException;

public class Girokonto extends Konto {
	private static double sollzins = 7.5;
	protected static int zinsabrechnungMonatsInterval = 3;
	private double dispo;

	public Girokonto(Kunde myKunde, Bank myBank, double dispo) throws LimitExceededException {
		super(myKunde, myBank);
		this.setzeDispo(dispo);
	}

	public Girokonto(Bank myBank, double dispo, String kundenName, String kundenAdresse) throws LimitExceededException {
		super(myBank, kundenName, kundenAdresse);
		this.setzeDispo(dispo);
	}

	public static Girokonto eroeffnen(Kunde myKunde, Bank myBank, double dispo) throws LimitExceededException {
		return new Girokonto(myKunde, myBank, dispo);
	}

	private void setzeDispo(double dispo) {
		if (dispo <= 0 || Double.isInfinite(dispo) || Double.isNaN(dispo)) {
			throw new IllegalArgumentException("Ungültiger Dispo");
		}
		this.dispo = dispo;
	}

	@Override
	protected void abhebenInner(double betrag, GregorianCalendar datum) {
		if (this.kontostand-betrag < -this.dispo) {
			System.out.println(String.format("Kreditlimit von Konto %s überzogen."
					+ " Buchung über -%.2f Euro vom %s wurde nicht ausgeführt.",
					this.ktoNummer, betrag, Helper.getDate(datum)));
		} else {
			super.abhebenInner(betrag, datum);
		}
	}

	@Override
	public void berechneZinsen(GregorianCalendar datum) {
		this.berechneZinsen(datum, zinsabrechnungMonatsInterval);
	}

	@Override
	protected double getZinsSumme(double kontoStand, GregorianCalendar datumVon, GregorianCalendar datumBis) {
		return kontoStand/100*(kontoStand<0 ? sollzins : habenzins)*this.getJahresanteil(datumVon, datumBis);
	}
}
