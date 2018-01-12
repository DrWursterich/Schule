package Marmelade;

public class Marmelade {
	private String fruchtsorte;
	private String einmachdatum;
	private int menge;
	
	public Marmelade(String fruchtsorte, String einmachdatum, int menge) {
		this.fruchtsorte = fruchtsorte;
		this.einmachdatum = einmachdatum;
		this.menge = menge;
	}
	
	public boolean istLeer() {
		return this.menge == 0;
	}
	
	public void entnehmen(int menge) {
		this.menge = Math.max(0, this.menge-menge);
		System.out.println("neue Menge: " + this.menge);
	}
	
	public String ausgeben() {
		return String.format("%-14s %-12s %2d Unzen", 
				this.fruchtsorte, this.einmachdatum, this.menge);
	}

	public String getFruchtsorte() {
		return fruchtsorte;
	}

	public void setFruchtsorte(String fruchtsorte) {
		this.fruchtsorte = fruchtsorte;
	}

	public String getEinmachdatum() {
		return einmachdatum;
	}

	public void setEinmachdatum(String einmachdatum) {
		this.einmachdatum = einmachdatum;
	}

	public int getMenge() {
		return menge;
	}

	public void setMenge(int menge) {
		this.menge = menge;
	}
}