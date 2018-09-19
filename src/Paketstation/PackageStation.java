package Paketstation;

import java.util.List;
import java.util.function.Consumer;
import javafx.application.Application;
import javafx.stage.Stage;

public class PackageStation extends Application {
	public static int MAX_PACKAGE_NUMBER = 0;
	private Stage stage;
	private Package[] packages;
	private Handler handler;
	private final Command[] commands = {
			this.new Command("handler", e -> {
				if ("ui".equals(e)) {
					this.handler = new UIHandler(this.stage);
				} else if (!"console".equals(e)) {
					this.handler.handleOutput("Invalid handler \"" + e + "\"");
					System.exit(0);
				}
			}),
			this.new Command("size", e -> {
				int size = 0;
				try {
					size = Integer.parseInt(e);
				} catch (NumberFormatException ex) {
					this.handler.handleOutput("Invalid size \"" + e + "\"");
					System.exit(0);
				}
				this.packages = new Package[size];
			})
	};
	
	private class Command {
		private final String name;
		private final Consumer<String> action;
		
		Command(String name, Consumer<String> action) {
			this.name = name;
			this.action = action;
		}
		
		String getName() {
			return this.name;
		}
		
		Consumer<String> getAction() {
			return this.action;
		}
	}


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
		this.stage = primaryStage;
		this.handler = new ConsoleHandler();
		List<String> args = this.getParameters().getRaw();
		for (int i = 0;i < args.size(); i++) {
			String arg = args.get(i);
			if (arg.startsWith("-")) {
				arg = arg.substring(1);
				boolean exists = false;
				for (Command command : this.commands) {
					if (command.getName().equals(arg)) {
						exists = true;
						if (args.size() > ++i) {
							String param = args.get(i);
							command.getAction().accept(param);
							break;
						} else {
							this.abort(
									"Missing parameter for " + arg);
						}
					}
				}
				if (!exists) {
					this.abort("Invalid command \"" + arg + "\" (not found)");
				}
			} else {
				this.abort("Invalid command \"" + arg + "\"");
			}
		}
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
	
	private void abort(String message) {
		this.handler.handleOutput(message);
		System.exit(0);
	}
}
