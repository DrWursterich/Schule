package Paketstation;

import java.util.ArrayList;
import java.util.Optional;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Pair;

public class UIHandler extends Handler {
	private ObservableList<Slot> packageItems =
			FXCollections.observableArrayList();
	private TableColumn<Slot, Integer> SlotColumn
			= new TableColumn<>("Fach");
	private TableColumn<Slot, String> ReceiverColumn
			= new TableColumn<>("Empfänger");
	private TableColumn<Slot, Integer> PackageNrColumn
			= new TableColumn<>("Packet ID");
	private TableView<Slot> packageList = new TableView<>();
	private VBox buttonBox = new VBox();
	private HBox contentBox = new HBox();
	private VBox root = new VBox();
	private Scene scene;
	private final Stage parentStage;

	@SuppressWarnings("unchecked")
	public UIHandler(Stage stage) {
		this.parentStage = stage;
		this.SlotColumn.setCellValueFactory(e -> {
			return e.getValue().slotNrProperty().asObject();
		});
		this.ReceiverColumn.setCellValueFactory(e -> {
			return new SimpleStringProperty(
					e.getValue().hasPackage()
						? e.getValue().getPackage().getReceiver()
						: "-");
		});
		this.PackageNrColumn.setCellValueFactory(e -> {
			return new SimpleIntegerProperty(
					e.getValue().hasPackage()
						? e.getValue().getPackage().getNumber()
						: 0
					).asObject();
		});
		this.packageList.getColumns().addAll(
				this.SlotColumn,
				this.ReceiverColumn,
				this.PackageNrColumn);
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
		if (result.isPresent() && !"".equals(result.get().trim())) {
			newPackage = new Package(result.get(), packageNumber);
		}
		return newPackage;
	}

	@Override
	public void removePackages(Slot[] slots) {
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
					int clammedInput = Math.max(1, Math.min(
							this.packageItems.size(), input));
					if (input != clammedInput) {
						inputField.setText(
								Integer.toString(clammedInput));
					}
				} catch (NumberFormatException e) {
					inputField.setText("1");
				}
			}
		});
		radioGroup.selectedToggleProperty().addListener((v, o, n) -> {
			inputField.setText("");
		});
		radioReceiver.setToggleGroup(radioGroup);
		radioReceiver.setSelected(true);
		radioReceiver.requestFocus();
		radioSlot.setToggleGroup(radioGroup);

		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(20, 150, 10, 10));
		gridPane.add(new Label(
					"Anhand welches Kriteriums "
					+ "möchten Sie selectieren?"),
				0, 0, 2, 1);
		gridPane.add(radioReceiver, 0, 1);
		gridPane.add(radioSlot, 1, 1);
		gridPane.add(inputField, 0, 2, 2, 1);

		dialog.setTitle("Packet(e) entnehmen");
		dialog.setHeaderText(null);
		dialog.getDialogPane().setContent(gridPane);
		dialog.getDialogPane().getButtonTypes().addAll(
				ButtonType.APPLY, ButtonType.CANCEL);
		((Button)dialog.getDialogPane().lookupButton(
					ButtonType.APPLY)
				).setDefaultButton(true);

		dialog.setResultConverter(button -> {
			if (ButtonType.APPLY.equals(button)) {
				return new Pair<>(
						radioSlot.equals(radioGroup.getSelectedToggle()),
						inputField.getText());
			}
			return null;
		});
		final Optional<Pair<Boolean, String>> result = dialog.showAndWait();
		final ArrayList<Pair<Integer, String>> removed = new ArrayList<>();
		if (result.isPresent()) {
			if (result.get().getKey()) {
				try {
					final int toRemove = Integer.parseInt(
							result.get().getValue()) - 1;
					if (slots[toRemove].hasPackage()) {
						removed.add(new Pair<>(
								toRemove,
								slots[toRemove].getPackage().getReceiver()));
						slots[toRemove].setPackage(null);
					} else {
						this.handleOutput("Das Fach "
								+ toRemove + " ist bereits leer.");
					}
				} catch (NumberFormatException | IndexOutOfBoundsException e) {
					this.handleOutput("Das Paket aus Fach "
							+ result.get().getValue()
							+ " konnte nicht entfernt werden.");
				}
			} else {
				for (int i=0;i<slots.length;i++) {
					if (slots[i].hasPackage()
							&& slots[i].getPackage().getReceiver().equals(
									result.get().getValue())) {
						removed.add(new Pair<>(
								i,
								slots[i].getPackage().getReceiver()));
						slots[i].setPackage(null);
					}
				}
			}
		}
		if (removed.size() == 0) {
			this.handleOutput("Es wurden keine Pakete entnommen.");
		} else {
			String outputMessage = removed.size() + " Entnommene Packete:";
			int slotDigits = 1 + (int)Math.log10(
					this.packageList.getItems().size());
			for (Pair<Integer, String> p : removed) {
				outputMessage += String.format(
						"\nFach: %" + slotDigits + "d, Empfänger: %s",
						p.getKey() + 1, p.getValue());
			}
			this.handleOutput(outputMessage);
		}
	}

	@Override
	public void listPackages(Slot... slots) {
		this.packageItems.setAll(slots);
	}

	@Override
	public void initialize() {
		this.packageList.setItems(this.packageItems);
		Insets buttonInsets = new Insets(10);
		for (UserOption option : this.menuOptions) {
			Button button = new Button(option.getTitle());
			VBox.setMargin(button, buttonInsets);
			button.prefWidthProperty().bind(
					this.parentStage.widthProperty().multiply(0.3));
			button.prefHeightProperty().bind(
					this.parentStage.heightProperty().divide(
							this.menuOptions.length));
			button.setOnAction(e -> {
				option.run();
			});
			this.buttonBox.getChildren().add(button);
		}
		HBox.setHgrow(this.packageList, Priority.ALWAYS);
		this.contentBox.getChildren().addAll(this.packageList, this.buttonBox);
		this.root.getChildren().addAll(this.menuBar(), this.contentBox);
		this.scene = new Scene(this.root,
				0.75 * Screen.getPrimary().getBounds().getWidth(),
				0.60 * Screen.getPrimary().getBounds().getHeight());

		this.parentStage.setTitle(this.getClass().getPackage().getName());
		this.parentStage.setScene(this.scene);
	}

	@Override
	public void run() {
		this.parentStage.show();
	}

	private MenuBar menuBar() {
		final MenuBar menuBar = new MenuBar();
		final Menu fileMenu = new Menu("Datei");
		for (UserOption option : this.menuOptions) {
			final MenuItem optionItem = new MenuItem(option.getTitle());
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
