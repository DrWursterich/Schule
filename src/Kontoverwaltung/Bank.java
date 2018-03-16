package Kontoverwaltung;

import java.util.ArrayList;

public class Bank {

	private static String blzNummerierung = "00000001";
	private String blz;
	private String institutsname;
	private ArrayList<Kunde> myKunden = new ArrayList<Kunde>();
	private ArrayList<Konto> myKonten = new ArrayList<Konto>();

	public Bank(String institutsname) {
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
}
