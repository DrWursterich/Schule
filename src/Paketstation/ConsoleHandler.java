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
			this.handleOutput("(" + (i + 1) + ") "
					+ this.menuOptions[i].getTitle());
		}
		int input = 0;
		while (input <= 0 || input > this.menuOptions.length) {
			this.handleOutput("Geben Sie die Zahl einer "
					+ "Aktion an, um diese auszuführen.");
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
	public void removePackages(Slot[] slots) {
		this.handleOutput("Geben Sie den Empfänger oder die Fachnummer an:");
		final String toRemove = this.scanner.next();
		int packageSlot = 0;
		int amountRemoved = 0;
		try {
			packageSlot = Integer.parseInt(toRemove);
		} catch (NumberFormatException e) {}
		if (packageSlot > 0 && packageSlot <= slots.length) {
			if (slots[packageSlot - 1].getPackage() != null) {
				slots[packageSlot -1].setPackage(null);
				amountRemoved++;
			} else {
				this.handleOutput("Das Fach "
						+ packageSlot + " ist bereits leer");
			}
		} else {
			for (int i = slots.length-1;i>=0;i--) {
				if (slots[i].hasPackage() && toRemove.equals(
							slots[i].getPackage().getReceiver())) {
					slots[i].packageProperty().setValue(null);
					amountRemoved++;
				}
			}
		}
		this.handleOutput("Es wurden " + amountRemoved + " Packete entfernt");
	}

	@Override
	public void listPackages(Slot... slots) {
		if (slots == null || slots.length == 0) {
			return;
		}

		final String numberHeader = "Nr.";
		final String receiverHeader = "Empfänger";
		final String packageNumberHeader = "Id";

		final int indexDigits = Math.max(
				(int)Math.log10(slots.length),
				numberHeader.length());
		final int receiverLength = Math.max(
				Package.MAX_RECEIVER_LENGTH,
				receiverHeader.length());
		final int packageNumberDigits = Math.max(
				(int)Math.log10(PackageStation.getPackageNumber()),
				packageNumberHeader.length());

		final String formatString = "%" + indexDigits + "d│%"
				+ receiverLength + "s│%" + packageNumberDigits + "d";
		final String textFormatString = formatString.replace('d', 's');

		this.handleOutput(String.format(
				textFormatString,
				numberHeader,
				receiverHeader,
				packageNumberHeader));
		this.handleOutput(String.join(
				"┼",
				String.join("",
						Collections.nCopies(indexDigits, "─")),
				String.join("",
						Collections.nCopies(receiverLength, "─")),
				String.join("",
						Collections.nCopies(packageNumberDigits, "─"))));

		for (int i=0;i<slots.length;i++) {
			Package value = slots[i].getPackage();
			this.handleOutput(String.format(
					slots[i].hasPackage() ? formatString : textFormatString,
					i + 1,
					slots[i].hasPackage() ? value.getReceiver() : "-",
					slots[i].hasPackage() ? value.getNumber() : "-"));
		}
	}

	@Override
	public void initialize() {
		this.enableOnUpdate = false;
		this.enableOnStart = false;
	}

	@Override
	public void run() {
		while (true) {
			this.promptUser();
		}
	}

	@Override
	protected void finalize() {
		this.scanner.close();
	}
}
