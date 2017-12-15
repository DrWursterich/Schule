package Boxen;
import java.util.Vector;
import java.*;

public class Box{
	
	private double breite;
	private double hoehe;
	private double laenge;
	
	public Box(double b, double h, double l) {
		this.breite = b;
		this.hoehe = h;
		this.laenge = l;;
	}
	
	public Box(Box alteBox){
		this.breite = alteBox.getBreite();
		this.hoehe = alteBox.getHoehe();
		this.laenge = alteBox.getLaenge();
	}
	
	public Box groessereBox()
	{
		return new Box(this.breite * 1.25, this.hoehe * 1.25, this.laenge * 1.25);
	}
	
	public Box kleinereBox()
	{
		return new Box(this.breite * .75, this.hoehe * .75, this.laenge * .75);
	}
	
	public double getBreite()
	{
		return this.breite;
	}
	
	public double getHoehe()
	{
		return this.hoehe;
	}
	
	public double getLaenge()
	{
		return this.laenge;
	}
	
	public double berechneVolumen() {
		return this.breite * this.hoehe * this.laenge;
	}
	
	private double berechneFlaecheVorn(){
		return this.breite * this.hoehe;
	}
	
	private double berechneFlaecheOben(){
		return this.breite * this.laenge;
	}
	
	private double berechneFlaecheSeitlich(){
		return this.laenge * this.hoehe;
	}
	
	public double berechneFlaeche() {
		return 2 * berechneFlaecheVorn() + 2 * berechneFlaecheOben() + 2 * berechneFlaecheSeitlich();
	}
	
	private Box dreheBox(char achse) {
		switch (achse) {
		case 'b':
			return new Box(this.breite, this.hoehe, this.laenge);
		case 'l':
			return new Box(this.hoehe, this.laenge, this.breite);
		case 'h':
			return new Box(this.laenge, this.breite, this.hoehe);
		default:
			System.out.println("Falsche Parameter!");
			return null;
		}
	}
	
	public boolean passtIn(Box aeussereBox){
		return this.breite < aeussereBox.getBreite() && this.laenge < aeussereBox.getLaenge() && this.hoehe < aeussereBox.getHoehe();
	}
	
	public boolean passtInGedreht(Box aeussereBox) {
		Box temp = this;
		for (int i=0;i<2;i++) {
			temp = temp.dreheBox('b');
			for (int j=0;j<2;j++) {
				temp = temp.dreheBox('l');
				for (int k=0;k<2;k++) {
					temp = temp.dreheBox('h');
					if (temp.passtIn(aeussereBox)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}