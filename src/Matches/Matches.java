package Matches;
 
public class Matches {
	public static void main (String args[]) {
		int matches = (int)(Math.random()*10)+10;
		Spieler spieler[] = {new Spieler(0), new Spieler(0)};
		if (args.length != 0) {
			matches = Integer.parseInt(args[0]);
			if (args.length != 1) {
				spieler = new Spieler[args.length-1];
				for (int i = 0;i < spieler.length;i++) {
					spieler[i] = new Spieler(Integer.parseInt(args[i+1]));
				}
			}
		}
		

		while(matches > 0) {
			for (int i = 0;i < spieler.length;i++) {
				System.out.println("Spieler " + i + " (" + spieler[i].getName() + ")" + " ist an der Reihe");
				System.out.println((matches == 1 ? "Es liegt 1 Streichholz" : "Es liegen " + matches + " Streichhoelzer") + " auf dem Tisch");
				int toPull = spieler[i].getInput(spieler.length, matches);
				System.out.println(toPull + " Streichhoelzer gezogen\n");
				matches -= toPull;
				if (matches == 0) {
					System.out.println("Spieler " + i + " (" + spieler[i].getName() + ")" + " gewinnt!\n");
					break;
				}
			}
		}
	}
}