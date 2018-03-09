package Kontoverwaltung;

import java.util.ArrayList;

public class Bank {

	private static String blzNummerierung = "00000000";
	private String blz;
	private String institutsname;
	private ArrayList<Kunde> myKunden = new ArrayList<Kunde>();
	private ArrayList<Konto> myKonten = new ArrayList<Konto>();

	public Bank(String institutsname) {
		this.institutsname = institutsname;
	}

	public String getBLZ() {
		return this.blz;
	}

	public String getName() {
		return this.institutsname;
	}
}
