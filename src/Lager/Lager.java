package Lager;
import java.util.Scanner;

public class Lager {
	public static void main(String[] args) {
		Artikel art1 = new Artikel("Ungarische Salami", 250, 9.9, 1);
		Artikel art2 = new Artikel("Prager Schinken", 100, 8.5, 2);
		Artikel art3 = new Artikel("Argentinisches Steak", 600, 15, .25);
		Stellplatz platz1 = new Stellplatz("P101", art1, 200);
		Stellplatz platz2 = new Stellplatz("P102", art2, 240);
		Stellplatz platz3 = new Stellplatz("P103", art3, 250);
		Scanner sc = new Scanner(System.in);

		int input = sc.nextInt();
		platz1.getMyArtikel().veraendernBestand(input);
		System.out.println("Liefermenge Salami: " + input);
		System.out.println(art1.ausgebenInformationen(input));
		input = sc.nextInt();
		platz2.getMyArtikel().veraendernBestand(input);
		System.out.println("\nLiefermenge Schinken: " + input);
		System.out.println(art2.ausgebenInformationen(input));
		input = sc.nextInt();
		platz3.getMyArtikel().veraendernBestand(input);
		System.out.println("\nLiefermenge Steak: " + input);
		System.out.println(art3.ausgebenInformationen(input) + "\n");
		sc.close();

		platz1.pruefenGewicht();
		platz2.pruefenGewicht();
		platz3.pruefenGewicht();
		
		System.out.println("\nVor der Umlagerung:");
		System.out.println(platz1.ausgeben());
		System.out.println(platz2.ausgeben());
		System.out.println(platz3.ausgeben());

		platz1.umlagern(art3);
		platz2.umlagern(art1);
		platz3.umlagern(art2);
		
		System.out.println("\nNach der Umlagerung:");
		System.out.println(platz1.ausgeben());
		System.out.println(platz2.ausgeben());
		System.out.println(platz3.ausgeben());
	}
}