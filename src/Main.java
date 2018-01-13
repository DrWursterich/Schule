import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		TicTacToe.TicTacToe game = new TicTacToe.TicTacToe(new TicTacToe.Player("Spieler 1", 'X'), new TicTacToe.Player("Spieler 2", 'O'));
		//VierGewinnt.VierGewinnt game = new VierGewinnt.VierGewinnt(new VierGewinnt.Player("Spieler 1", 'X'), new VierGewinnt.Player("Spieler 2", 'O'));
		String input = "Y";
		while(input.equals("Y") || input.equals("y")) {
			game.play();
			System.out.print("Schreibe 'Y' um noch mal zu spielen ");
			input = sc.next().trim();
		}
		sc.close();
	}
}
