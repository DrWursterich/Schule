package Fussball;

public class Tester {
	public static void main(String...args) {
		Verein v = new Verein();
		Spieler s1 = new Spieler("Mats", "Hummmels", "Abwehr", 25);
		Spieler s2 = new Spieler("Mario", "Götze", "Mittelfeld", 14);
		Trainer t = new Trainer("Jürgen", "Klopp", s2);
		Arzt a = new Arzt("Markus", "Braun", "Orthopädie");

		s1.setGehalt(12300);
		s2.setGehalt(12300);
		t.setGehalt(12300);
		a.setGehalt(12300);
		
		v.hinzufuegenMitglied(s1);
		v.hinzufuegenMitglied(s2);
		v.hinzufuegenMitglied(t);
		v.hinzufuegenMitglied(a);
		
		v.ausgebenGehaltsliste();
	}
}
