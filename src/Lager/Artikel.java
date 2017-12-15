package Lager;
import java.text.DecimalFormat;

public class Artikel {
	public String artikelbezeichnung;
	public int bestand;
	public double nettopreis;
	public double kgProStck;
	public final double STEUERSATZ = 0.07;
	
	public Artikel(String bezich, int best, double pr, double kg) {
		this.artikelbezeichnung = bezich;
		this.bestand = best;
		this.nettopreis = pr;
		this.kgProStck = kg;
	}
	
	public void veraendernBestand(int menge) {
		this.bestand += menge;
	}
	
	public String ausgebenInformationen(int menge) {
		DecimalFormat df = new DecimalFormat("#,##0.000");
		return "Die Lieferung des Artikels " + this.artikelbezeichnung + " hat eine Menge von " + menge + " Stück\n"
				+ "mit einem Stückpreis von " + df.format(this.holenBruttopreis()) 
				+ " Euro und einem Gesamtwert von " + df.format(this.berechnenPreisLieferung(menge));
	}
	
	public double holenBruttopreis() {
		return this.nettopreis * (1 + this.STEUERSATZ);
	}
	
	public double berechnenPreisLieferung(int menge) {
		return this.holenBruttopreis() * menge;
	}	
}
