package Kontoverwaltung;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;
import javax.naming.LimitExceededException;

public abstract class Konto {
	private static String kontenNummerierung = "00000001";
	protected static int zinsabrechnungMonatsInterval = 12;
	protected static double habenzins = 5;
	protected String ktoNummer;
	public double kontostand;
	protected Kunde myKunde;
	protected Bank myBank;
	protected ArrayList<Kontobewegung> myBew = new ArrayList<Kontobewegung>();
	public double letzterZinsenKontostand = 0;

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
		this.abhebenInner(betrag, datum);
	}

	protected void abhebenInner(double betrag, GregorianCalendar datum) {
		this.berechneZinsen(datum);
		this.createKontoBewegung(betrag, "Auszahlung", datum);
		this.kontostand -= betrag;
	}

	public void abheben(double betrag) {
		this.abheben(betrag, new GregorianCalendar());
	}

	protected void createKontoBewegung(double betrag, String art, GregorianCalendar datum)
			throws IllegalArgumentException {
		if (Double.isInfinite(betrag) || Double.isNaN(betrag)) {
			throw new IllegalArgumentException("Betrag ist ungültig");
		}
		if (this.myBew.size() > 0 && this.myBew.get(this.myBew.size()-1).getDatum().after(datum)) {
			throw new IllegalArgumentException("Es können keine Kontobewegungen in der Vergangenheit getätigt werden");
		}
		myBew.add(new Kontobewegung(betrag, this, art, datum));
	}

	public void printInfo(GregorianCalendar datum) {
		if (datum == null) {
			throw new IllegalArgumentException("Datum kann nicht null sein");
		}
		this.berechneZinsen(datum);
		System.out.println(String.format("\tKto-Nr.: %s,\n\tBLZ: %s, %s,\n\tKontostand: %.2f Euro",
				this.ktoNummer, this.myBank.getBLZ(), this.myBank.getName(), this.kontostand));
	}

	public void printInfo() {
		this.printInfo(new GregorianCalendar());
	}

	public void printKontoauszug(GregorianCalendar datum) {
		System.out.println("Kontoauszug");
		this.printInfo(datum);
		System.out.println("\tKontoinhaber: " + this.myKunde.getName() + "\n");
		for (int i=1;i<=this.myBew.size();i++) {
			System.out.println(String.format("%d\t%s\t%s%7.2f Euro\t\t%s",
					i, Helper.getDate(this.myBew.get(i-1).getDatum()),
					("Auszahlung".equals(this.myBew.get(i-1).getBewegungsart()) ? "-" : " "),
					this.myBew.get(i-1).getBetrag(), this.myBew.get(i-1).getBewegungsart()));
		}
	}

	public void printKontoauszug() {
		this.printKontoauszug(new GregorianCalendar());
	}

	public void berechneZinsen(GregorianCalendar datum) {
		this.berechneZinsen(datum, zinsabrechnungMonatsInterval);
	}

	protected void berechneZinsen(GregorianCalendar datum, int interval) {
		GregorianCalendar datumVon = this.myBew.size() == 0 ? datum :
			(GregorianCalendar)this.myBew.get(this.myBew.size()-1).getDatum();
		GregorianCalendar temp = (GregorianCalendar)datumVon.clone();
		temp.set(GregorianCalendar.DAY_OF_MONTH, 1);
		temp.add(GregorianCalendar.MONTH, interval-(temp.get(GregorianCalendar.MONTH)
				+ (datumVon.get(GregorianCalendar.DAY_OF_MONTH)==1 ? 1 : 0))%interval);
		while(!temp.after(datum)) {
			GregorianCalendar t = (GregorianCalendar)temp.clone();
			t.add(GregorianCalendar.DAY_OF_MONTH, 1);
			double zinsSumme = this.getZinsSumme(this.kontostand, datumVon, temp);
			this.createKontoBewegung(zinsSumme, "Zinsen", temp);
			this.kontostand += zinsSumme;
			this.letzterZinsenKontostand = this.kontostand;
			datumVon = (GregorianCalendar)temp.clone();
			temp.add(GregorianCalendar.MONTH, interval);
		}
		this.letzterZinsenKontostand = this.kontostand + this.getZinsSumme(this.kontostand, datumVon, datum);
	}

	protected double getZinsSumme(double kontoStand, GregorianCalendar datumVon, GregorianCalendar datumBis) {
		return kontoStand/100*habenzins*this.getJahresanteil(datumVon, datumBis);
	}

	protected double getJahresanteil(GregorianCalendar datumVon, GregorianCalendar datumBis) {
		double TageUnterschied = TimeUnit.DAYS.convert(datumBis.getTimeInMillis()
				- datumVon.getTimeInMillis(), TimeUnit.MILLISECONDS);
		return Math.floor(TageUnterschied)/
				(datumBis.isLeapYear(datumBis.get(GregorianCalendar.YEAR)) ? 366.0 : 365.0);
	}

	public Kunde getKunde() {
		return this.myKunde;
	}
}
