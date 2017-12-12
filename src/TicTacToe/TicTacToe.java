package TicTacToe;


public class TicTacToe {
	private char[][] field;
	private Player[] players;

	public TicTacToe(Player player1, Player player2) throws IllegalArgumentException {
		if (player1 == null || player2 == null) {
			throw new IllegalArgumentException("players can not be null");
		}
		this.players = new Player[]{player1, player2};
	}

	public void play() {
		this.resetField();
		boolean won = false;
		int turn = (int)Math.round(Math.random());
		for(int i=9;i>0 && !won;i--) {
			turn = 1 - turn;
			this.printField();
			int[] input;
			do {
				System.out.println(this.players[turn].getName() + "(" + this.players[turn].getSymbol() + ") ist am Zug");
				input = this.players[turn].makeMove();
			} while(input == null ? true : (input != null && this.field[input[0]][input[1]] != ' '));
			this.field[input[0]][input[1]] = this.players[turn].getSymbol();
			won = this.getWon(input[0], input[1]);
		}
		this.printField();
		System.out.println(won ? (this.players[turn].getName() + " gewinnt!") : ("Unentschieden!"));
	}

	private void resetField() {
		this.field = new char[3][3];
		for (int i=2;i>=0;i--) {
			for (int j=2;j>=0;j--) {
				this.field[i][j] = ' ';
			}
		}
	}

	private boolean getWon(int lastMoveRow, int lastMoveColumn) {
		int lr = lastMoveRow;
		int lc = lastMoveColumn;
		if (this.field[lr][lc] == this.field[(lr+1)%3][lc]
				&& this.field[lr][lc] == this.field[(lr+2)%3][lc]) {
			return true;
		}
		if (this.field[lr][lc] == this.field[lr][(lc+1)%3]
				&& this.field[lr][lc] == this.field[lr][(lc+2)%3]) {
			return true;
		}
		if ((lr == lc ? this.field[lr][lc] == field[(lr+1)%3][(lc+1)%3]
						&& this.field[lr][lc] == field[(lr+2)%3][(lc+2)%3] : false)
				|| (lr+lc == 2 ? this.field[lr][lc] == field[(lr+1)%3][(lc+2)%3]
						&& this.field[lr][lc] == field[(lr+2)%3][(lc+1)%3] : false)) {
			return true;
		}
		return false;
	}

	private void printField() {
		for (int i=0;i<4;i++) {
			System.out.println("+---+---+---+");
			for (int j=0;i!=3 && j<3;j++) {
				System.out.print("| " + this.field[j][i] + (j==2 ? " |\n" : " "));
			}
		}
	}
}
