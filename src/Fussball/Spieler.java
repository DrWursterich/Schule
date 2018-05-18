package Fussball;

public class Spieler extends Person {

	protected String position;
	protected int einsaetze;
	protected double monatsgehalt;
	protected double spielpraemie;
	
	public Spieler(String vorname, String name, String position, int einsaetze) {
		super(vorname, name);
		this.einsaetze = einsaetze;
		this.position = position;
	}
	
	@Override
	public void setGehalt(double gehalt) {
		this.monatsgehalt = gehalt;
	}
	
	public void setSpielpraemie(double spielpraemie) {
		this.spielpraemie = spielpraemie;
	}

	@Override
	public String getTyp() {
		return  "S";
	}

	@Override
	public double ermittelnGehalt() {
		return this.monatsgehalt*12+this.einsaetze * this.spielpraemie;
	}

	@Override
	public String erzeugenDetail() {
		return "Position: " + this.position + " / Spieleinsätze: " + this.einsaetze;
	}

}
