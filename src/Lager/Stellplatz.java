package Lager;
import java.text.DecimalFormat;

public class Stellplatz {
	private String nummer;
	private Artikel myArtikel;
	private double maxGewicht;
	
	public Stellplatz(String nr, Artikel art, double gew) {
		this.nummer = nr;
		this.myArtikel = art;
		this.maxGewicht = gew;
	}
	
	public String ausgeben() {
		DecimalFormat df = new DecimalFormat("#,##0.000");
		return "Auf Platz " + this.nummer + " stehen " + df.format(this.myArtikel.kgProStck * this.myArtikel.bestand) + " kg " + this.myArtikel.artikelbezeichnung;
	}
	
	public void pruefenGewicht() {
		System.out.println("Die Palette mit " + this.myArtikel.artikelbezeichnung + " ist " 				
				+ (this.myArtikel.kgProStck * this.myArtikel.bestand <= this.maxGewicht ? (
						this.myArtikel.kgProStck * this.myArtikel.bestand == this.maxGewicht ? 
								"passend" 
								: "noch nicht" 
					) + " voll" 
					: "überladen"
				) + ".");
	}
	
	public void umlagern(Artikel art) {
		this.myArtikel = art;
		}

	public Artikel getMyArtikel() {
		return myArtikel;
	}
}