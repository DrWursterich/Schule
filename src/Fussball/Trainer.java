package Fussball;

public class Trainer extends Person {
	
	protected Spieler liebster;
	protected double monatsgehalt;

	public Trainer(String vorname, String name, Spieler liebster) {
		super(vorname, name);
		this.liebster = liebster;
	}

	@Override
	public void setGehalt(double gehalt) {
		this.monatsgehalt = gehalt;
	}

	@Override
	public String getTyp() {
		return "T";
	}

	@Override
	public double ermittelnGehalt() {
		return 12*this.monatsgehalt;
	}

	@Override
	public String erzeugenDetail() {
		return "Lieblingsspieler: " + this.liebster.getVorname() + " " + this.liebster.getName();
	}
	
}
