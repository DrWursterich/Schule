package Fussball;

public class Arzt extends Person {
	protected String fach;
	protected double monatsgehalt; 

	public Arzt(String vorname, String name, String fach) {
		super(vorname, name);
		this.fach = fach;
	}

	@Override
	public void setGehalt(double gehalt) {
		this.monatsgehalt = gehalt;
	}

	@Override
	public String getTyp() {
		return "A";
	}

	@Override
	public double ermittelnGehalt() {
		return this.monatsgehalt*12;
	}

	@Override
	public String erzeugenDetail() {
		return "Fachrichtung: " + this.fach;
	}
}
