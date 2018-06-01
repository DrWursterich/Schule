package Haustiere;

import java.util.Arrays;

public class HaustierFuettern {
	public static void main(String...args) {
		Haustiere[] haustiere = new Haustiere[] {
			new Katze("Dummes Vieh", 7, 2, Haltung.Wohnung),
			new Hund("Günther", 10, 4.8, Kategorie.Grosse_Hunde),
			new Hund("Günther 2", 2, 0.4,  Kategorie.Kleinhunde),
			new Katze("Minka", 2, 16, Haltung.Artgerecht),
			new Hund("Strolchie", 3, 2.1, Kategorie.Kleinhunde)
		};

		Arrays.sort(haustiere);

		for (Haustiere h : haustiere) {
			h.fuettern();
			System.out.println();
		}
	}
}
