package Kontoverwaltung;

import java.util.GregorianCalendar;

public final class Kontobewegung {
	private final double betrag;
	private final Konto myKonto;
	private final String bewegungsart;
	private final GregorianCalendar datum;

	protected Kontobewegung(double betrag, Konto myKonto, String bewegungsart, GregorianCalendar datum) {
		if (Double.isInfinite(betrag) || Double.isNaN(betrag)) {
			throw new IllegalArgumentException("Betrag ist ungültig");
		}
		if (myKonto == null) {
			throw new IllegalArgumentException("Konto kann nicht null sein");
		}
		if (bewegungsart == null || bewegungsart.trim().equals("")) {
			throw new IllegalArgumentException("Bewegungsart ist ungültig");
		}
		this.betrag = betrag;
		this.myKonto = myKonto;
		this.bewegungsart = bewegungsart;
		this.datum = datum == null ? new GregorianCalendar() : (GregorianCalendar)datum.clone();
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

	public Konto getKonto() {
		return this.myKonto;
	}
}
