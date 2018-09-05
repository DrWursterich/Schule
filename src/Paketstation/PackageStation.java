package Paketstation;

import java.util.function.Consumer;

public class PackageStation {
	private final Handler.UserOption<?>[] menuOptions;
	private final Package[] packages;
	private final Handler handler;
	
	public PackageStation(int size, Handler handler) {
		this.packages = new Package[size];
		this.handler = handler;
		Consumer<Package> a = this::receivePackage;
		this.menuOptions = new Handler.UserOption<?>[]{
				new Handler.UserOption<Package>("Paket einlagern", a, null)
			};
		this.run();
	}
	
	private void run() {
		this.handler.promptUser(this.menuOptions);
	}
	
	public void receivePackage(Package pack) {
		
	}
	
	public static void main(String...args) {
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
