package Kontoverwaltung;

import java.util.GregorianCalendar;
import java.util.ArrayList;

public class Kunde {
	private static String kundenNummerierung = "00001";
	private String kundenNummer;
	private String name;
	private String adresse;
	private GregorianCalendar kundeSeit;
	private Bank myBank;
	private ArrayList<Konto> myKonten = new ArrayList<Konto>();
	
	public Kunde(Bank myBank, String name, String adresse) {
		this.name = name;
		this.adresse = adresse;
		this.myBank = myBank;
		this.kundenNummer = Kunde.kundenNummerierung;
		Kunde.kundenNummerierung = Helper.increaseString(Kunde.kundenNummerierung);
	}
	
	protected void addKonto(Konto k) {
		this.myKonten.add(k);
	}

	public String getName() {
		return name;
	}
	
	public void printInfo() {
		System.out.println("Kontoinhaber: " + this.name);
		for(int i = 0; i < this.myKonten.size(); i++)
		{
			this.myKonten.get(i).printInfo();
			System.out.println();
		}
	}
}
