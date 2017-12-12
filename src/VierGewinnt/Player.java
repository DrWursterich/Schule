package VierGewinnt;

import java.util.Scanner;

public class Player {
	private String name;
	private char symbol;
	private Scanner sc;

	public Player(String name, char symbol) throws IllegalArgumentException{
		if (name == null || name.trim() == "") {
			throw new IllegalArgumentException("invalid name");
		}
		if (symbol == ' ') {
			throw new IllegalArgumentException("invalid symbol");
		}
		this.sc = new Scanner(System.in);
		this.name = name;
		this.symbol = symbol;
	}

	public String getName() {
		return this.name;
	}

	public char getSymbol() {
		return this.symbol;
	}

	public int makeMove() {
		int input = -1;
			do {
				System.out.print("Spalte: ");
				input = this.formatInput(this.sc.next())-1;
			} while(input < 0 || input > 7);
		return input;
	}

	private int formatInput(String str) {
		char[] chr = str.trim().toCharArray();
		int ret = 0;
		for (int i=0;i<chr.length;i++) {
			if (chr[i] >= '0' && chr[i] <= '9') {
				ret = ret * 10 + (chr[i]-'0');
			} else {
				return -1;
			}
		}
		return ret;
	}
}
