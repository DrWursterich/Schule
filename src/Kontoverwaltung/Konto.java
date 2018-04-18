package Kontoverwaltung;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import javax.naming.LimitExceededException;

public abstract class Konto {
	private static String kontenNummerierung = "00000001";
	protected static double habenzins;
	protected String ktoNummer;
	protected double kontostand;
	protected Kunde myKunde;
	protected Bank myBank;
	protected ArrayList<Kontobewegung> myBew = new ArrayList<Kontobewegung>();
	protected double letzterZinsenKontostand = 0;

	public Konto(Kunde myKunde, Bank myBank) throws LimitExceededException {
		if (myKunde == null) {
			throw new IllegalArgumentException("Kunde kann nicht null sein");
		}
		if (myBank == null) {
			throw new IllegalArgumentException("Bank kann nicht null sein");
		}
		if (Konto.kontenNummerierung.chars().allMatch(e -> e == '9')) {
			throw new LimitExceededException("Maximale Anzahl an Konten erreicht");
		}
		this.myKunde = myKunde;
		this.myBank = myBank;
		this.ktoNummer = Konto.kontenNummerierung;
		this.myBank.addKonto(this);
		this.myKunde.kontoHinzufuegen(this);
		Konto.kontenNummerierung = Helper.increaseString(Konto.kontenNummerierung);
	}
	
	public Konto(Bank myBank, String kundenName, String kundenAdresse) throws LimitExceededException {
		this(new Kunde(myBank, kundenName, kundenAdresse), myBank);
	}

	public void einzahlen(double betrag, GregorianCalendar datum) {
		if (betrag <= 0) {
			throw new IllegalArgumentException("Betrag zu gering");
		}
		this.berechneZinsen(datum);
		this.createKontoBewegung(betrag, (this.myBew.size()==0 ? "Erste" : "E" ) + "inzahlung", datum);
		this.kontostand += betrag;
	}

	public void einzahlen(double betrag) {
		this.einzahlen(betrag, new GregorianCalendar());
	}

	public void abheben(double betrag, GregorianCalendar datum) {
		if (betrag <= 0) {
			throw new IllegalArgumentException("Betrag zu gering");
		}
		this.berechneZinsen(datum);
		this.createKontoBewegung(betrag, "Auszahlung", datum);
		this.kontostand += betrag;
	}

	public void abheben(double betrag) {
		this.abheben(betrag, new GregorianCalendar());
	}

	protected void createKontoBewegung(double betrag, String art, GregorianCalendar datum) throws IllegalArgumentException {
		if (Double.isInfinite(betrag) || Double.isNaN(betrag)) {
			throw new IllegalArgumentException("Betrag ist ungültig");
		}
		if (this.myBew.size() > 0 && this.myBew.get(this.myBew.size()-1).getDatum().after(datum)) {
			throw new IllegalArgumentException("Es können keine Kontobewegungen in der Vergangenheit getätigt werden");
		}
		myBew.add(new Kontobewegung(betrag, this, art, datum));
	}

	public void printInfo() {
		System.out.println(String.format("\tKto-Nr.: %s,\n\tBLZ: %s, %s,\n\tKontostand: %.2f Euro",
				this.ktoNummer, this.myBank.getBLZ(), this.myBank.getName(), this.kontostand));
	}

	public void printKontoauszug() {
		System.out.println("Kontoauszug");
		this.printInfo();
		System.out.println("\tKontoinhaber: " + this.myKunde.getName()+ "\n");
		for (int i=1;i<=this.myBew.size();i++) {
			System.out.println(String.format("%d\t%s\t%7.2f Euro\t\t%s",
					i, Helper.getDate(this.myBew.get(i-1).getDatum()),
					this.myBew.get(i-1).getBetrag(), this.myBew.get(i-1).getBewegungsart()));
		}
	}

	public void berechneZinsen(GregorianCalendar datum) {
		GregorianCalendar datumVon = datum;
		for (int i=this.myBew.size()-1;i>=0;i--) {
			if ("Zinsen".equals(this.myBew.get(i).getBewegungsart())) {
				datumVon = this.myBew.get(i).getDatum();
				break;
			}
		}
		GregorianCalendar temp = (GregorianCalendar)datumVon.clone();
		temp.set(GregorianCalendar.DAY_OF_MONTH, 1);
		temp.add(GregorianCalendar.MONTH, 12-(temp.get(GregorianCalendar.MONTH)
				+ datumVon.get(GregorianCalendar.DAY_OF_MONTH)==1 ? 1 : 0)%12);
		boolean ersteRepitition = false;
		while(!temp.after(datum)) {
			double zinsSumme = this.letzterZinsenKontostand/100*habenzins*
					(TimeUnit.DAYS.convert(datum.getTimeInMillis() - datumVon.getTimeInMillis(), TimeUnit.MILLISECONDS)/
					(datum.isLeapYear(datum.get(GregorianCalendar.YEAR)) ? 366 : 365));
			this.createKontoBewegung(zinsSumme, "Zinsen", temp);
			this.letzterZinsenKontostand += zinsSumme;
			temp.add(GregorianCalendar.MONTH, 12);
			if (temp.after(datum) && !ersteRepitition) {
				temp = (GregorianCalendar)datum.clone();
				this.kontostand = this.letzterZinsenKontostand;
				ersteRepitition = true;
			}
		}
	}

	public Kunde getKunde() {
		return this.myKunde;
	}
}
