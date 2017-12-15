package Matches;
import java.util.Scanner;

public class Spieler {
	private final int PULL_MIN = 1;
	private final int PULL_MAX = 3;
	private int input;// 0 => menschlicher input, 1+ => verschiedene KI's
	private String name;
	private Scanner scanner;
	private Stats stats;

	public Spieler(int input) {
		switch (input) {
		default:
			System.out.println(input + " ist ungültig! Es wird 'Mensch' verwendet");
			input = 0;
		case 0:
			this.name = "Mensch";
			break;
		case 1:
			this.name = "CPU Random";
			break;
		case 2:
			this.name = "CPU 2 Player";
			break;
		case 3:
			this.name = "Smart CPU";
			break;
		}
		this.input = input;
	}

	public Spieler() {
		this(0);
	}

	public int getInput(int players, int matches) {
		int toPull = 0;
		while (toPull < PULL_MIN || toPull > Math.min(matches, PULL_MAX)) {
			switch (this.input) {
			case 0:
				if (scanner == null) {
					scanner = new Scanner(System.in);
				}
				toPull = scanner.nextInt();
				break;
			case 1:
				toPull = (int)Math.round(1+Math.random()*(Math.min(matches, PULL_MAX)-1));
				break;
			case 2:
				toPull = (matches % (PULL_MAX+PULL_MIN) == 0) ?
						(int)Math.round(PULL_MIN+Math.random()*(Math.min(matches, PULL_MAX)-PULL_MIN))
						: matches % (PULL_MAX+PULL_MIN);
				break;
			case 3:
				if (stats == null) {
					stats = new Stats(players, matches);
				}
				toPull = stats.getBestChoice(matches);
				break;
			default:
				break;
			}
			if (toPull < PULL_MIN || toPull > Math.min(matches, PULL_MAX)) {
				System.out.println("Es wird ein Input zwischen " + PULL_MIN + Math.min(matches, PULL_MAX) + " erwartet!");
			}
		}
		return toPull;
	}

	public String getName() {
		return this.name;
	}
}