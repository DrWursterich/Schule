package Kontoverwaltung;

import java.util.GregorianCalendar;

public class Girokonto extends Konto {
	
	private static double sollzins;
	private double dispo;
	
	public Girokonto(Kunde myKunde, Bank myBank, double dispo) {
		super(myKunde, myBank);
		this.dispo = dispo;
	}
	
	@Override
	public void abheben(double betrag) {
		if (this.kontostand-Math.abs(betrag) < -this.dispo) {
			System.out.println(String.format("Kreditlimit von Konto %s überzogen."
					+ " Buchung über %.2f Euro vom %s wurde nicht ausgeführt.", 
					this.ktoNummer, Math.abs(betrag), Helper.getDate(new GregorianCalendar())));
		}
		else {
			super.abheben(betrag);
		}
	}
}
