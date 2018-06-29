package Rekursion;

import java.util.Arrays;

public class Bauteil {
	private long TeileNummer;
	private Bauteil[] TeileListe;
	private double Preis;

	public Bauteil(long TeileNummer, Bauteil[] TeileListe, double Preis) {
		this.TeileNummer = TeileNummer;
		this.TeileListe = TeileListe;
		this.Preis = Preis;
	}
	
	public double bestimmePreis() {
		return this.Preis + (this.TeileListe != null
				? Arrays.stream(this.TeileListe).mapToDouble(Bauteil::bestimmePreis).sum()
				: 0);
	}

	public double getElementPreis() {
		return this.Preis;
	}
}
