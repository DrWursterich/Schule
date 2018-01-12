package Marmelade;

import java.util.Scanner;

public class VorratskammerTester {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Marmelade staches = new Marmelade("Stachelbeer", "04.07.09", 12);
		Marmelade erdbeer = new Marmelade("Erdbeer", "30.09.09", 8);
		Marmelade aprikose = new Marmelade("Aprikosen", "31.10.09", 3);
		
		Vorratskammer vorrat = new Vorratskammer();
		vorrat.einlagern(staches);
		vorrat.einlagern(erdbeer);
		vorrat.einlagern(aprikose);
		
		while (true) {
			System.out.println("Willkommen zu Mutter Hubbards Vorratskammer!");
			vorrat.ausgeben();
			System.out.print("Treffen sie ihre Auswahl: (Exit)");
			String fruchtsorte = sc.nextLine();
			if ("Exit".equals(fruchtsorte)) {
				System.exit(0);
			}
			System.out.println("Geben Sie die Menge ein, die entnommen werden soll: ");
			vorrat.auswaehlen(fruchtsorte, sc.nextInt());
			System.out.println();
		}
	}
}