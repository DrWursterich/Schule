package Kontoverwaltung;

import java.util.GregorianCalendar;

public class Kontobewegung {
	
	private double betrag;
	private GregorianCalendar datum; 
	private Konto myKonto;
	
	public Kontobewegung(double betrag, Konto myKonto)
	{
		this.betrag = betrag;
		this.myKonto = myKonto;
	}
}
