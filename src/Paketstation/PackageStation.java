package Paketstation;

public class PackageStation {
	private final Package[] packages;
	private final Handler handler;
	private final Handler.UserOption[] menuOptions;

	public PackageStation(int size, Handler handler) {
		this.packages = new Package[size];
		this.handler = handler;
		this.menuOptions = new Handler.UserOption[]{
				new Handler.UserOption("Paket einlagern", this::receivePackage),
				new Handler.UserOption("Packet(e) entnehmen", this::removePackages),
				new Handler.UserOption("Packete anzeigen", this::listPackages),
				new Handler.UserOption("Beenden", this::exit)
			};
		this.run();
	}

	private void run() {
		while (true) {
			this.handler.promptUser(this.menuOptions);
		}
	}

	public void receivePackage() {
		System.out.println("receivePackage");
	}

	public void removePackages() {
		System.out.println("removePackages");
	}

	public void listPackages() {
		System.out.println("listPackages");
	}

	public void exit() {
		System.out.println("exiting");
		System.exit(0);
	}

	public static void main(String... args) {
		Handler handler = new ConsoleHandler();
		int size = 9;
		int argsPos = 0;
		if (args.length > 0) {
			switch (args[0]) {
				case "ui":
					handler = new UIHandler();
					break;
				case "console":
					argsPos++;
					break;
			}
			if (args.length - argsPos > 0) {
				try {
					size = Integer.parseInt(args[argsPos]);
				} catch (NumberFormatException e) {
					handler.handleOutput("wrong size parameter");
					System.exit(0);
				}
			}
			if (args.length - argsPos == 0) {
				handler.handleOutput("wrong parameter usage");
				System.exit(0);
			}
		}
		PackageStation packageStation = new PackageStation(size, handler);
	}
}
