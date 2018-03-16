package Kontoverwaltung;

import java.util.ArrayList;

public class Konto {

	private static String kontenNummerierung = "00000000";
	protected String ktoNummer;
	protected double kontostand;
	protected double habenzins;
	protected Kunde myKunde;
	protected Bank myBank;
	protected ArrayList<Kontobewegung> myBew = new ArrayList<Kontobewegung>();

	public Konto(Kunde myKunde, Bank myBank) {
		this.myKunde = myKunde;
		this.myBank = myBank;
		this.ktoNummer = Konto.kontenNummerierung;
		Konto.kontenNummerierung = Helper.increaseString(Konto.kontenNummerierung);
		myKunde.addKonto(this);
	}

	protected static String getKontenNummerierung() {
		return kontenNummerierung;
	}

	public void eroeffnen() {

	}

	public void einzahlen(double betrag) {
		Kontobewegung kontbew = new Kontobewegung(Math.abs(betrag), this, "Einzahlung");
		myBew.add(kontbew);
	}

	public void abheben(double betrag) {
		Kontobewegung kontbew = new Kontobewegung(-Math.abs(betrag), this, "Auszahlung");
		myBew.add(kontbew);
	}

	public void printInfo() {
		System.out.println(String.format("     Kto-Nr.: %s,\n     BLZ: %s, %s,\n     Kontostand: %,2f Euro",
				this.ktoNummer, this.myBank.getBLZ(), this.myBank.getName(), this.kontostand));
	}
	
	public void printKontoauszug() {
		System.out.println("Kontoauszug");
		this.printInfo();
		System.out.println("     Kontoinhaber: " + this.myKunde.getName()+ "\n");
		for (int i=1;i<this.myBew.size();i++) {
			System.out.println(String.format("%d    %s     % 7.2f Euro          %s",
					i, Helper.getDate(this.myBew.get(i-1).getDatum()), this.myBew.get(i-1).getBetrag(), this.myBew.get(i-1).getBewegungsart() ));
		}
		
	}
}
