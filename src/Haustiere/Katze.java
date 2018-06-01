package Haustiere;

public class Katze extends Haustiere {
	private Haltung haltung;

	public Katze(String name, double futtervorrat, double gewicht, Haltung haltung) {
		super(name, futtervorrat, gewicht);
		super.setFutterverbrauch(.5);
		this.haltung = haltung;
	}

	@Override
	public void sprich() {
		System.out.println("Miau!");
	}

	@Override
	public void anzeigen() {
		System.out.println("[Gewicht: " + this.getGewicht() + "] (Kategorie: " + this.haltung.getName() + ") Der Vorrat für " + super.getName()
				+ " reicht " + super.getAnzahlTage() + " Tage.");
	}
}
