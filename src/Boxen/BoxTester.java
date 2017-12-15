package Boxen;
public class BoxTester{
	
	public static void main(String[] args){
		Box sexyBox = new Box(2.5, 5., 6.);
		Box andereSexyBox = new Box(3, 3, 3);
		System.out.println("Fläche: " + sexyBox.berechneFlaeche() + " Volumen: " + sexyBox.berechneVolumen());
		System.out.println(andereSexyBox.passtIn(sexyBox) ? "JA" : "NEIN");
	}
}