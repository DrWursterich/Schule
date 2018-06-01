package Haustiere;

public enum Kategorie {
	Kleinhunde("Kleinhunde", 1),
	Mittelgrosse_Hunde("Mittelgroße Hunde", 1.5),
	Grosse_Hunde("Große Hunde", 2);

	private final String name;
	private final double futterverbrauch;

	Kategorie(String name, double futterverbrauch) {
		this.name = name;
		this.futterverbrauch = futterverbrauch;
	}

	public String getName() {
		return this.name;
	}
	
	public double getFutterverbrauch() {
		return this.futterverbrauch;
	}
}
