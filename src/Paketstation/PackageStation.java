package Paketstation;

import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;

public class PackageStation extends Application {
	public static int MAX_PACKAGE_NUMBER = 0;
	private Package[] packages;
	private Handler handler;

	public void receivePackage() {
		for (int i=0;i<this.packages.length;i++) {
			if (this.packages[i] == null) {
				Package newPackage = this.handler.createPackage(++PackageStation.MAX_PACKAGE_NUMBER);
				if (newPackage == null) {
					PackageStation.MAX_PACKAGE_NUMBER--;
				}
				this.packages[i] = newPackage;
				return;
			}
		}
		this.handler.handleOutput("Packstation ist voll!");
	}

	public void removePackages() {
		this.handler.removePackages(this.packages);
	}

	public void listPackages() {
		System.out.println("list packages");
		this.handler.listPackages(this.packages);
	}

	public void exit() {
		System.exit(0);
	}

	public static void main(String... args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		List<String> args = this.getParameters().getRaw();
		Handler handler = new ConsoleHandler();
		int size = 10;
		int argsPos = 0;
		if (args.size() > 0) {
			switch (args.get(0)) {
				case "ui":
					handler = new UIHandler(primaryStage);
				case "console":
					argsPos++;
					break;
			}
			if (args.size() - argsPos > 0) {
				try {
					size = Integer.parseInt(args.get(argsPos));
					if (size <= 0) {
						throw new NumberFormatException();
					}
				} catch (NumberFormatException e) {
					handler.handleOutput(
							"wrong size parameter \"" 
							+ args.get(argsPos) + "\"");
					System.exit(0);
				}
			}
			if (args.size() - argsPos == 0) {
				handler.handleOutput("wrong parameter usage");
				System.exit(0);
			}
		}
		this.packages = new Package[size];
		this.handler = handler;
		this.handler.setMenuOptions(
				handler.new UserOption(
						"Paket einlagern", this::receivePackage, "CTRL+N"),
				handler.new UserOption(
						"Packet(e) entnehmen", this::removePackages, "CTRL+D"),
				handler.new UserOption(
						"Packete anzeigen", this::listPackages, "CTRL+R"),
				handler.new UserOption(
						"Beenden", this::exit, "CTRL+Q"));
		this.handler.setOnUpdate(this::listPackages);
		this.handler.setOnStart(this::listPackages);
		this.handler.initialize();
		this.handler.getOnStart().run();
		this.handler.run();
	}

	public static int getPackageNumber() {
		return PackageStation.MAX_PACKAGE_NUMBER;
	}
}
