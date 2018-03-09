package Kontoverwaltung;

public class Sparkonto extends Konto {
	
	private char art;
	
	public Sparkonto(Kunde myKunde, Bank myBank, char art) 
	{
		super(myKunde, myBank);
		this.art = art;
	}
}
