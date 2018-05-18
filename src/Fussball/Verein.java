package Fussball;

import java.util.ArrayList;

public class Verein {
	private ArrayList<Person> mitglieder = new ArrayList<>();
	
	public void hinzufuegenMitglied(Person p) {
		this.mitglieder.add(p);  
	}
	
	public void ausgebenGehaltsliste() {
		System.out.println(String.format("%1s\t%20s%20s\t%10s\t%s", "Typ", "Name",
				"Vorname","Jahresgehalt", "Sonstiges"));
		System.out.println("--------------------------------------------------------------"
				+ "--------------------------------------------------------------");
		for(Person p : this.mitglieder) {
			System.out.println(p.erzeugenZeile());
		}
	}
}
