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
		Konto.kontenNummerierung = "" + (Integer.parseInt(Konto.kontenNummerierung) + 1);
	}

	protected static String getKontenNummerierung() {
		return kontenNummerierung;
	}

	protected static void setKontenNummerierung(String kontenNummerierung) {
		Konto.kontenNummerierung = kontenNummerierung;
	}

	public void eroeffnen() {

	}

	public void einzahlen(double betrag) {
		Kontobewegung kontbew = new Kontobewegung(Math.abs(betrag), this);
		myBew.add(kontbew);
	}

	public void abheben(double betrag) {
		Kontobewegung kontbew = new Kontobewegung(-Math.abs(betrag), this);
		myBew.add(kontbew);
	}

	public void printInfo() {
		System.out.println(String.format("     Kto-Nr.: %s,\n     BLZ: %s, %s,\n     Kontostand: %,2f Euro",
				this.ktoNummer, this.myBank.getBLZ(), this.myBank.getName(), this.kontostand));
	}
}
