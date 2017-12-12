import TicTacToe.*;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		TicTacToe t = new TicTacToe(new Player("Spieler 1", 'X'), new Player("Spieler 2", 'O'));
		String input = "Y";
		while(input.equals("Y") || input.equals("y")) {
			t.play();
			System.out.print("Schreibe 'Y' um noch mal zu spielen ");
			input = sc.next().trim();
		}
		sc.close();
	}
}
