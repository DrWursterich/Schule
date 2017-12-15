package Matches;

public class Stats {
	private final int FROM = 0;
	private final int TO;
	private double results[];
	private int players;

	public Stats(int players, int max_matches) {
		this.players = players;
		TO = max_matches;
		results = new double[TO-FROM+1];
		for (int i = FROM;i <= TO;i++) {
			results[i-FROM] = getChance(players, i);
			//tree(i);
			//System.out.println(dec(i, 2) + " => " + dec(results[i-FROM]*100, 3) + "%");
		}
	}

	private double getChance(int players, int matches) {
		int[] options = getOptions(matches);
		if (players <= 1) {
			if (matches <= 3) {
				return 1.0;
			}
			return Math.max(Math.max(results[options[0]-FROM], results[options[1]-FROM]), results[options[2]-FROM]);
		}
		if (matches <= 3) {
			return 0.0;
		}
		return 1.0/3.0*(double)getChance(players-1, options[0])
				+ 1.0/3.0*(double)getChance(players-1, options[1])
				+ 1.0/3.0*(double)getChance(players-1, options[2]);
	}

	private int[] getOptions(int matches) {
		int[] options = new int[3];
		for (int i=3;i > 0;i--) {
			options[i - 1] = Math.max(matches - i, 0);
		}
		return options;
	}

	public int getBestChoice(int matches) {
		if (matches <= 3) {
			return matches;
		}
		double chance = 0.0;
		int toReturn = 0;
		for (int i=1;i<=3;i++) {
			if (results[matches-FROM-i] > chance) {
				chance = results[matches-FROM-i];
				toReturn = i;
			}
		}
		if (chance == 0) {
			toReturn = (int)Math.round(Math.random()*3);
		}
		return toReturn;
	}

	private String dec(double src, int dec) {
		String str = "" + (int)Math.abs(src);
		if (src == 0) {
			while(dec-- > 0) {
				str = " " + str;
			}
			return str;
		}
		double src2 = src;
		dec = (int)Math.pow(10, dec);
		while (Math.abs(src2) < dec) {
			str = " " + str;
			src2 *= 10;
		}
		return str;
	}

	private void tree(int src) {
		//funktioniert nur bei 3 Spielern
		System.out.println("                " + dec(src, 2));
		System.out.println("     +-----------+-----------+");
		System.out.println("    " + dec(src-1, 2) + "         " + dec(src-2, 2) + "         " + dec(src-3, 2));
		System.out.println(" +---+---+   +---+---+   +---+---+");
		System.out.println(dec(src-2, 2) + " " + dec(src-3, 2) + " " + dec(src-4, 2) + " " + dec(src-3, 2) + " "
				+ dec(src-4, 2) + " " + dec(src-5, 2) + " " + dec(src-4, 2) + " " + dec(src-5, 2) + " " + dec(src-6, 2));
	}
}