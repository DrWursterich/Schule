package Paketstation;

import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Pair;

public class UIHandler extends Handler {
	private static final Package NO_PACKAGE = new Package("-", PackageStation.getPackageNumber());
	private ObservableList<Package> packageItems = FXCollections.observableArrayList();
	private ListView<Package> packageList = new ListView<>();
	private VBox buttonBox = new VBox();
	private HBox contentBox = new HBox();
	private VBox root = new VBox();
	private Scene scene;
	private Stage parentStage;

	public UIHandler(Stage stage) {
		this.parentStage = stage;
	}

	@Override
	public void promptUser(UserOption... options) {
	} 

	@Override
	public void handleOutput(String output) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Warnung!");
		alert.setContentText(output);
		alert.setHeaderText(null);
		alert.showAndWait();
	}

	@Override
	public Package createPackage(int packageNumber) {
		Package newPackage = null;
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Packet einlagern");
		dialog.setHeaderText(null);
		dialog.setContentText("Geben Sie den Empfänder an:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			newPackage = new Package(result.get(), packageNumber);
		}
		return newPackage;
	}

	@Override
	public void removePackages(Package[] packages) {
		Dialog<Pair<Boolean, String>> dialog = new Dialog<>();
		GridPane gridPane = new GridPane();
		ToggleGroup radioGroup = new ToggleGroup();
		RadioButton radioReceiver = new RadioButton("Empfänger");
		RadioButton radioSlot = new RadioButton("Fach");
		TextField inputField = new TextField();

		inputField.textProperty().addListener((v, o, n) -> {
			if (radioSlot.equals(radioGroup.getSelectedToggle())) {
				if (!n.matches("\\d*")) {
					inputField.setText(n.replaceAll("[^\\d]", ""));
				}
				try {
					int input = Integer.parseInt(n);
					int clammedInput = Math.max(1, Math.min(this.packageItems.size(), input));
					if (input != clammedInput) {
						inputField.setText(Integer.toString(clammedInput));
					}
				} catch (NumberFormatException e) {
					inputField.setText("1");
				}
			}
		});
		radioGroup.selectedToggleProperty().addListener((v, o, n) -> {
			inputField.setText(inputField.getText());
		});
		radioReceiver.setToggleGroup(radioGroup);
		radioReceiver.setSelected(true);
		radioReceiver.requestFocus();
		radioSlot.setToggleGroup(radioGroup);

		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(20, 150, 10, 10));
		gridPane.add(new Label("Anhand welches Kriteriums möchten Sie selectieren?"), 0, 0);
		gridPane.add(radioReceiver, 0, 1);
		gridPane.add(radioSlot, 1, 1);
		gridPane.add(inputField, 0, 2);

		dialog.setTitle("Packet(e) entnehmen");
		dialog.setHeaderText(null);
		dialog.getDialogPane().setContent(gridPane);
		dialog.getDialogPane().getButtonTypes().addAll(
				ButtonType.APPLY, ButtonType.CANCEL);
		((Button)dialog.getDialogPane().lookupButton(
					ButtonType.APPLY )
				).setDefaultButton(true);
		
		dialog.setResultConverter(button -> {
			if (ButtonType.APPLY.equals(button)) {
				return new Pair<>(
						radioSlot.equals(radioGroup.getSelectedToggle()),
						inputField.getText());
			}
			return null;
		});
		Optional<Pair<Boolean, String>> result = dialog.showAndWait();
		ArrayList<Pair<Integer, String>> removed = new ArrayList<>();
		if (result.isPresent()) {
			if (result.get().getKey()) {
				try {
					int toRemove = Integer.parseInt(result.get().getValue()) - 1;
					removed.add(new Pair<>(toRemove, packages[toRemove].getReceiver()));
					packages[toRemove] = null;
				} catch (NumberFormatException | IndexOutOfBoundsException e) {
					this.handleOutput("Das Paket aus Fach " +
							result.get().getValue() +
							" konnte nicht entfernt werden.");
				}
			} else {
				for (int i=0;i<packages.length;i++) {
					if (packages[i] != null
							&& packages[i].getReceiver().equals(result.get().getValue())) {
						removed.add(new Pair<>(i, packages[i].getReceiver()));
						packages[i] = null;
					}
				}
			}
		}
		if (removed.size() == 0) {
			this.handleOutput("Es wurden keine Pakete entnommen.");
		} else {
			String outputMessage = removed.size() + " Entnommene Packete:";
			int slotDigits = 1 + (int)Math.log10(this.packageList.getItems().size());
			for (Pair<Integer, String> p : removed) {
				outputMessage += String.format(
						"\nFach: %" + slotDigits + "d, Empfänger: %s",
						p.getKey() + 1, p.getValue());
			}
			this.handleOutput(outputMessage);
		}
	}

	@Override
	public void listPackages(Package... packages) {
		this.packageItems.clear();
		for (int i=0;i<packages.length;i++) {
			this.packageItems.add(
					packages[i] != null
						? packages[i]
						: UIHandler.NO_PACKAGE);
		}
	}

	@Override
	public void run() {
		this.packageList.setItems(this.packageItems);
		this.packageList.setCellFactory(param -> {
			return new ListCell<Package>() {
				@Override
				protected void updateItem(Package item, boolean empty) {
					super.updateItem(item, empty);
					setText(!empty && item != null
							? UIHandler.NO_PACKAGE.equals(item)
									? "-"
									: this.formatItem(item)
							: null);
				}
				
				private String formatItem(Package item) {
					return String.format(
							"%-" + Package.MAX_RECEIVER_LENGTH 
							+ "s\t%" 
							+ ((int)Math.log10(
									PackageStation.MAX_PACKAGE_NUMBER) + 1) 
							+ "d", 
						item.getReceiver(), 
						item.getNumber());
				}
			};
		});
		Insets buttonInsets = new Insets(10);
		for (UserOption option : this.menuOptions) {
			Button button = new Button(option.getTitle());
			VBox.setMargin(button, buttonInsets);
			button.prefWidthProperty().bind(this.parentStage.widthProperty().multiply(0.3));
			button.prefHeightProperty().bind(this.parentStage.heightProperty().divide(this.menuOptions.length));
			button.setOnAction(e -> {
				option.getAction().run();
				this.onUpdate.run();
			});
			this.buttonBox.getChildren().add(button);
		}
		HBox.setHgrow(this.packageList, Priority.ALWAYS);
		this.contentBox.getChildren().addAll(this.packageList, this.buttonBox);
		this.root.getChildren().addAll(this.menuBar(), this.contentBox);
		this.scene = new Scene(this.root,
				0.75*Screen.getPrimary().getBounds().getWidth(),
				0.60*Screen.getPrimary().getBounds().getHeight());

		this.parentStage.setTitle(this.getClass().getPackage().getName());
		this.parentStage.setScene(this.scene);
		this.parentStage.show();
		this.onStart.run();
	}

	private MenuBar menuBar() {
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("Datei");
		for (UserOption option : this.menuOptions) {
			MenuItem optionItem = new MenuItem(option.getTitle());
			optionItem.setOnAction(e -> {
					option.getAction().run();
					this.onUpdate.run();
				});
			optionItem.setAccelerator(option.getAccelerator());
			fileMenu.getItems().add(optionItem);
		}

		menuBar.getMenus().addAll(fileMenu);
		return menuBar;
	}
}
