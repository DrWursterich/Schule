package VierGewinnt;

public class VierGewinnt {
	private char[][] field;
	private Player[] players;

	public VierGewinnt(Player player1, Player player2) throws IllegalArgumentException {
		if (player1 == null || player2 == null) {
			throw new IllegalArgumentException("players can not be null");
		}
		this.players = new Player[]{player1, player2};
	}

	public void play() {
		this.resetField();
		boolean won = false;
		int turn = (int)Math.round(Math.random());
		for (int i=this.field.length*this.field[0].length;i>0 && !won;i--) {
			turn = 1-turn;
			this.printField();
			int input;
			do {
				System.out.println(this.players[turn].getName() + "(" + this.players[turn].getSymbol() + ") ist am Zug");
				input = this.players[turn].makeMove();
			} while(this.field[input][0] != ' ');
			int height = 0;
			while(height+1 < this.field[0].length ? this.field[input][height+1] == ' ' : false) {
				height++;
			}
			this.field[input][height] = this.players[turn].getSymbol();
			won = this.getWon(input, height);
		}
		this.printField();
		System.out.println(won ? (this.players[turn].getName() + " gewinnt!") : "Unendschieden!");
	}

	private void resetField() {
		if (this.field == null) {
			this.field = new char[7][6];
		}
		for (int i=this.field.length-1;i>=0;i--) {
			for (int j=this.field[0].length-1;j>=0;j--) {
				this.field[i][j] = ' ';
			}
		}
	}

	private boolean getWon(int lastMoveRow, int lastMoveColumn) {
		int lr = lastMoveRow;
		int lc = lastMoveColumn;
		int horizontalLength = 1;
		int verticalLength = 1;
		int diagonal1Length = 1;
		int diagonal2Length = 1;

		for (int i=lr-1;i>=0 ? this.field[i][lc] == this.field[lr][lc] : false;i--) {
			verticalLength++;
		}
		for (int i=lr+1;i<this.field.length ? this.field[i][lc] == this.field[lr][lc] : false;i++) {
			System.out.println("CHECKING: " + i + ", " + lc);
			verticalLength++;
		}

		for (int i=lc-1;i>=0 ? this.field[lr][i] == this.field[lr][lc] : false;i--) {
			horizontalLength++;
		}
		for (int i=lc+1;i<this.field[0].length ? this.field[lr][i] == this.field[lr][lc] : false;i++) {
			horizontalLength++;
		}

		for (int i=-1;(lr+i>=0 && lc+i>=0) ? this.field[lr+i][lc+i] == this.field[lr][lc] : false;i--) {
			diagonal1Length++;
		}
		for (int i=1;(lr+i<this.field.length && lc+i<this.field[0].length) ? this.field[lr+i][lc+i] == this.field[lr][lc] : false;i++) {
			diagonal1Length++;
		}

		for (int i=1;(lr-i>=0 && lc+i<this.field[0].length) ? this.field[lr-i][lc+i] == this.field[lr][lc] : false;i++) {
			diagonal2Length++;
		}
		for (int i=1;(lr+i<this.field.length && lc-i>=0) ? this.field[lr+i][lc-i] == this.field[lr][lc] : false;i++) {
			diagonal2Length++;
		}

		return horizontalLength>=4 || verticalLength>=4 || diagonal1Length>=4 || diagonal2Length>=4;
	}

	private void printField() {
		for (int i=0;i<this.field[0].length+1;i++) {
			System.out.println("+---+---+---+---+---+---+---+");
			for (int j=0;i!=this.field[0].length && j<this.field.length;j++) {
				System.out.print("| " + this.field[j][i] + (j==this.field.length-1 ? " |\n" : " "));
			}
		}
	}
}