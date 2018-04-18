package Kontoverwaltung;

import java.util.ArrayList;
import javax.naming.LimitExceededException;

public class Bank {
	private static String blzNummerierung = "00000001";
	private String blz;
	private String institutsname;
	private ArrayList<Kunde> myKunden = new ArrayList<Kunde>();
	private ArrayList<Konto> myKonten = new ArrayList<Konto>();

	public Bank(String institutsname) throws LimitExceededException {
		if (institutsname == null || institutsname.trim().equals("")) {
			throw new IllegalArgumentException("Institutsname kann nicht null sein");
		}
		if (Bank.blzNummerierung.chars().allMatch(e -> e == '9')) {
			throw new LimitExceededException("Maximale Anzahl an Kunden erreicht");
		}
		this.institutsname = institutsname;
		this.blz = Bank.blzNummerierung;
		Bank.blzNummerierung = Helper.increaseString(Bank.blzNummerierung);
	}

	public String getBLZ() {
		return this.blz;
	}

	public String getName() {
		return this.institutsname;
	}

	protected void addKunde(Kunde kunde) {
		if (kunde == null) {
			throw new IllegalArgumentException("Kunde kann nicht null sein");
		}
		this.myKunden.add(kunde);
	}

	protected void addKonto(Konto konto) {
		if (konto == null) {
			throw new IllegalArgumentException("Konto kann nicht null sein");
		}
		this.myKonten.add(konto);
	}
}
