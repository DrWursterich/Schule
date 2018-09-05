package Paketstation;

import java.util.Scanner;
import java.util.function.Function;

public class ConsoleHandler extends Handler {
	private final Scanner scanner;

	public ConsoleHandler() {
		this.scanner = new Scanner(System.in);
	}

	@Override
	public void handleOutput(String output) {
		System.out.println(output);
	}

	@Override
	public void promptUser(UserOption<?>... options) {
		System.out.println("Paketstation Menü");
		for (int i=0;i<options.length;i++) {
			System.out.println("(" + (i + 1) + ") " + options[i].getTitle());
		}
		int input = this.scanner.nextInt();
		for (int i=0;i<options.length;i++) {
			if (input == i) {
				options[i].run();
			}
		}
	}
}
