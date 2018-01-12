package Marmelade;

public class Vorratskammer {
	private static int groesse = 20;
	private Marmelade[] regal = new Marmelade[groesse];
	
	public Vorratskammer() {
	}
	
	public void einlagern(Marmelade marmelade) {
		boolean abort = false;
		for (int i=0;i<this.regal.length && !abort;i++) {
			if (regal[i] == null) {
				regal[i] = marmelade;
				abort = true;
			}
		}
		if (!abort) {
			System.out.println("Die Marmelade konnte nicht eingelagert werden. Das Regal ist voll.");
		}
	}
	
	public void ausgeben() {
		System.out.println("in dem Regal steht:");
		for (int i=0;i<this.regal.length;i++) {
			if (this.regal[i] != null) {
				System.out.println(String.format("%2d: %s", i+1, this.regal[i].ausgeben()));
			}
		}
	}
	
	public void auswaehlen(String fruchtsorte, int menge) {
		int vorhandeneMenge = 0;
		for (int i=0;i<this.regal.length;i++) {
			if (this.regal[i] != null) {
				if (this.regal[i].getFruchtsorte().equals(fruchtsorte)) {
					vorhandeneMenge += this.regal[i].getMenge();
				}
			}
		}
		int zuEntnehmen = menge;
		if (vorhandeneMenge >= menge) {
			System.out.println("Entnehmen von " + zuEntnehmen + " Unzen " + fruchtsorte + "marmelade");
			for (int i=0;i<this.regal.length && zuEntnehmen>0;i++) {
				if (this.regal[i] != null) {
					if (this.regal[i].getFruchtsorte().equals(fruchtsorte)) {
						int entfernt = this.regal[i].getMenge() > zuEntnehmen ? 
								this.regal[i].getMenge()-zuEntnehmen : this.regal[i].getMenge();
						zuEntnehmen -= this.regal[i].getMenge()-entfernt;
						this.regal[i].setMenge(entfernt);
					}
				}
			}
		} else {
			System.out.println("Es ist nicht genug Marmelade vorhanden");
		}
		
	}
}