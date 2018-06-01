package Haustiere;

public class Hund extends Haustiere {
	private Kategorie kategorie;

	public Hund(String name, double futtervorrat, double gewicht, Kategorie kategorie) {
		super(name, futtervorrat, gewicht);
		super.setFutterverbrauch(kategorie.getFutterverbrauch());
		this.kategorie = kategorie;
	}

	@Override
	public void sprich() {
		System.out.println("Wuff!");
	}

	@Override
	public void anzeigen() {
		System.out.println("[Gewicht: " + super.getGewicht() + "] (Kategorie: " + this.kategorie.getName() + ") Der Vorrat für " + super.getName()
				+ " reicht " + super.getAnzahlTage() + " Tage.");
	}
}
