package Kontoverwaltung;

import java.util.GregorianCalendar;
import java.util.ArrayList;
import javax.naming.LimitExceededException;

public class Kunde {
	private static String kundenNummerierung = "00001";
	private String kundenNummer;
	private GregorianCalendar kundeSeit = new GregorianCalendar();
	private ArrayList<Konto> myKonten = new ArrayList<Konto>();
	private Bank myBank;
	private String name;
	private String adresse;

	public Kunde(Bank myBank, String name, String adresse, Konto konto) throws LimitExceededException {
		this.setzeParameter(myBank, name, adresse);
		this.kontoHinzufuegen(konto);
		this.erfasseInstanz();
	}

	protected Kunde(Bank myBank, String name, String adresse) throws LimitExceededException {
		this.setzeParameter(myBank, name, adresse);
		this.erfasseInstanz();
	}

	private void setzeParameter(Bank myBank, String name, String adresse) throws IllegalArgumentException, LimitExceededException {
		if (myBank == null) {
			throw new IllegalArgumentException("Bank kann nicht null sein");
		}
		if (name == null || name.trim().equals("")) {
			throw new IllegalArgumentException("Name ist ungültig");
		}
		if (adresse == null || adresse.trim().equals("")) {
			throw new IllegalArgumentException("Adresse ist ungültig");
		}
		if (Kunde.kundenNummerierung.chars().allMatch(e -> e == '9')) {
			throw new LimitExceededException("Maximale Anzahl an Kunden erreicht");
		}
		this.name = name;
		this.adresse = adresse;
		this.myBank = myBank;
	}

	private void erfasseInstanz() {
		this.myBank.addKunde(this);
		this.kundenNummer = Kunde.kundenNummerierung;
		Kunde.kundenNummerierung = Helper.increaseString(Kunde.kundenNummerierung);
	}

	protected void kontoHinzufuegen(Konto konto) {
		if (konto == null) {
			throw new IllegalArgumentException("Konto kann nicht null sein");
		}
		this.myKonten.add(konto);
	}

	public GregorianCalendar getKundeSeit() {
		return this.kundeSeit;
	}

	public String getKundenNummer() {
		return this.kundenNummer;
	}

	public String getName() {
		return name;
	}

	public String getAdresse() {
		return this.adresse;
	}

	public void printInfo(GregorianCalendar datum) {
		System.out.println(this.myKonten.size()==0 ? ("Für den Kunden " + this.name
				+ " sind keine Konten eingetragen") : ("Kontoinhaber: " + this.name));
		for(int i=0;i<this.myKonten.size();i++) {
			if (datum != null) {
				this.myKonten.get(i).berechneZinsen(datum);
			}
			this.myKonten.get(i).printInfo();
			System.out.println();
		}
	}

	public void printInfo() {
		this.printInfo(null);
	}
}
