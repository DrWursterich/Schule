package Kontoverwaltung;

import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import javax.naming.LimitExceededException;

public class Girokonto extends Konto {
	private static double sollzins = 7.5;
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
	public void abheben(double betrag) {
		this.abheben(betrag, new GregorianCalendar());
	}

	@Override
	public void abheben(double betrag, GregorianCalendar datum) {
		if (betrag <= 0 || Double.isInfinite(betrag) || Double.isNaN(betrag)) {
			throw new IllegalArgumentException("Betrag ist ungültig");
		}
		if (this.kontostand-betrag < -this.dispo) {
			System.out.println(String.format("Kreditlimit von Konto %s überzogen."
					+ " Buchung über -%.2f Euro vom %s wurde nicht ausgeführt.", 
					this.ktoNummer, betrag, Helper.getDate(datum)));
		} else {
			super.abheben(betrag);
		}
	}

	@Override
	public void berechneZinsen(GregorianCalendar datum) {
		GregorianCalendar datumVon = datum;
		for (int i=this.myBew.size()-1;i>=0;i--) {
			if ("Zinsen".equals(this.myBew.get(i).getBewegungsart()) || i==0) {
				datumVon = this.myBew.get(i).getDatum();
				break;
			}
		}
		GregorianCalendar temp = (GregorianCalendar)datumVon.clone();
		temp.set(GregorianCalendar.DAY_OF_MONTH, 1);
		temp.add(GregorianCalendar.MONTH, 3-(temp.get(GregorianCalendar.MONTH)
				+ datumVon.get(GregorianCalendar.DAY_OF_MONTH)==1 ? 1 : 0)%3);
		boolean ersteRepitition = false;
		while(!temp.after(datum)) {
			double zinsSumme = this.letzterZinsenKontostand/100
					*(this.letzterZinsenKontostand<0 ? sollzins : habenzins)*
					(TimeUnit.DAYS.convert(datum.getTimeInMillis()
					- datumVon.getTimeInMillis(), TimeUnit.MILLISECONDS)/
					(datum.isLeapYear(datum.get(GregorianCalendar.YEAR)) ? 366 : 365));
			this.createKontoBewegung(zinsSumme, "Zinsen", temp);
			this.letzterZinsenKontostand += zinsSumme;
			temp.add(GregorianCalendar.MONTH, 3);
			if (temp.after(datum) && !ersteRepitition) {
				temp = (GregorianCalendar)datum.clone();
				this.kontostand = this.letzterZinsenKontostand;
				ersteRepitition = true;
			}
		}
	}
}
