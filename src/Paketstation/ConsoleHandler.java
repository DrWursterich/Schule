package Paketstation;

import java.util.Collections;
import java.util.Scanner;

public class ConsoleHandler extends Handler {
	private final Scanner scanner;

	public ConsoleHandler() {
		this.scanner = new Scanner(System.in);
	}

	@Override
	public void handleOutput(String output) {
		System.out.println(output);
	}

	private void promptUser() {
		this.handleOutput("Paketstation Menü");
		for (int i=0;i<this.menuOptions.length;i++) {
			this.handleOutput("(" + (i + 1) + ") " + this.menuOptions[i].getTitle());
		}
		int input = 0;
		while (input <= 0 || input > this.menuOptions.length) {
			this.handleOutput("Geben Sie die Zahl einer Aktion an, um diese auszuführen.");
			try {
				input = Integer.parseInt(this.scanner.next());
			} catch (NumberFormatException e) {}
		}
		this.menuOptions[input - 1].run();
		this.handleOutput("");
	}

	@Override
	public Package createPackage(int packageNumber) {
		this.handleOutput("Geben Sie den Empfänder an: ");
		String receiver = this.scanner.next();
		return new Package(receiver, packageNumber);
	}

	@Override
	public void removePackages(Package[] packages) {
		this.handleOutput("Geben Sie den Empfänger oder die Fachnummer an: ");
		String toRemove = this.scanner.next();
		int packageSlot = 0;
		int amountRemoved = 0;
		try {
			packageSlot = Integer.parseInt(toRemove);
		} catch (NumberFormatException e) {}
		if (packageSlot > 0 && packageSlot <= packages.length) {
			if (packages[packageSlot - 1] != null) {
				packages[packageSlot -1] = null;
				amountRemoved++;
			} else {
				this.handleOutput("Das Fach " + packageSlot + " ist bereits leer");
			}
		} else {
			for (int i = packages.length-1;i>=0;i--) {
				if (packages[i] != null && toRemove.equals(packages[i].getReceiver())) {
					packages[i] = null;
					amountRemoved++;
				}
			}
		}
		this.handleOutput("Es wurden " + amountRemoved + " Packete entfernt");
	}

	@Override
	public void listPackages(Package... packages) {
		if (packages.length == 0) {
			return;
		}

		String numberHeader = "Nr.";
		String receiverHeader = "Empfänger";
		String packageNumberHeader = "Id";

		int indexDigits = Math.max((int)Math.log10(packages.length), numberHeader.length());
		int receiverLength = Math.max(Package.MAX_RECEIVER_LENGTH, receiverHeader.length());
		int packageNumberDigits = Math.max((int)Math.log10(PackageStation.getPackageNumber()), packageNumberHeader.length());

		String formatString = "%" + indexDigits + "d│%" + receiverLength + "s│%" + packageNumberDigits + "d";
		String textFormatString = formatString.replace('d', 's');

		this.handleOutput(String.format(textFormatString, numberHeader, receiverHeader, packageNumberHeader));
		this.handleOutput(String.join(
				"┼",
				String.join("", Collections.nCopies(indexDigits, "─")),
				String.join("", Collections.nCopies(receiverLength, "─")),
				String.join("", Collections.nCopies(packageNumberDigits, "─"))));

		for (int i=0;i<packages.length;i++) {
			this.handleOutput(String.format(
					packages[i] != null ? formatString : textFormatString,
					i + 1,
					packages[i] != null ? packages[i].getReceiver() : "-",
					packages[i] != null ? packages[i].getNumber() : "-"));
		}
	}
	
	@Override
	public void initialize() {
		this.setOnUpdate(this::promptUser);
	}

	@Override
	public void run() {
		this.promptUser();
	}

	@Override
	protected void finalize() {
		this.scanner.close();
	}
}
