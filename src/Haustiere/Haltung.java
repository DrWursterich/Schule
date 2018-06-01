package Haustiere;

public enum Haltung {
	Wohnung("Wohnung"),
	Artgerecht("Artgerecht");

	private final String name;

	Haltung(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
