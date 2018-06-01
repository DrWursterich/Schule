package Haustiere;

public abstract class Haustiere implements Comparable<Haustiere> {
	private String name;
	private double futtervorrat;
	private double futterverbrauch;
	private int anzahlTage;
	private double gewicht;

	public Haustiere(String name, double futtervorrat, double gewicht) {
		this.name = name;
		this.futtervorrat = futtervorrat;
		this.gewicht = gewicht;
	}

	protected void setFutterverbrauch(double futterverbrauch) {
		this.futterverbrauch = futterverbrauch;
	}

	public void fuettern() {
		this.sprich();
		this.friss();
	}

	public void anzeigen() {
		System.out.println("[Gewicht: " + this.gewicht + "] Der Vorrat für " + this.name + " reicht " + this.anzahlTage + " Tage.");
	}

	public abstract void sprich();

	public void friss() {
		this.futtervorrat -= this.futterverbrauch;
		this.anzahlTage = (int)(this.futtervorrat / this.futterverbrauch);
		this.anzeigen();
	}

	protected String getName() {
		return this.name;
	}

	protected int getAnzahlTage() {
		return this.anzahlTage;
	}

	protected double getGewicht() {
		return this.gewicht;
	}
	
	@Override
	public int compareTo(Haustiere other) {
		return (int)(other == null ? 0 : this.getName().compareTo(other.getName())); 
//		return (int)(other == null ? 0 : this.gewicht - other.gewicht); 
	}
}
