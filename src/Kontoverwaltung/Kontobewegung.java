package Kontoverwaltung;

import java.util.GregorianCalendar;

public class Kontobewegung {
	
	private String bewegungsart;
	private double betrag;
	private GregorianCalendar datum; 
	private Konto myKonto;
	
	public Kontobewegung(double betrag, Konto myKonto, String bewegungsart)
	{
		this.bewegungsart = bewegungsart;
		this.betrag = betrag;
		this.myKonto = myKonto;
		this.datum = new GregorianCalendar();
	}
	
	public String getBewegungsart(){
		return this.bewegungsart;
	}
	
	public GregorianCalendar getDatum(){
		return this.datum;
	}
	
	public double getBetrag() {
		return this.betrag;
	}
	
}
