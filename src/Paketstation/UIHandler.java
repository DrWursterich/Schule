package Paketstation;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;

public class UIHandler extends Handler {
	private ObservableList<Package> packageItems = FXCollections.observableArrayList();
	private ListView<Package> packageList = new ListView<>();
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
		this.handleOutput("remove Package");
	}

	@Override
	public void listPackages(Package... packages) {
		this.packageItems.clear();
		this.packageItems.addAll(0, FXCollections.observableArrayList(packages));
	}

	@Override
	public void run() {
		this.packageList.setItems(this.packageItems);
		this.packageList.setCellFactory(new Callback<ListView<Package>, ListCell<Package>>() {
			@Override
			public ListCell<Package> call(ListView<Package> param) {
				System.out.println("Called");
				return new ListCell<Package>() {
					@Override
					protected void updateItem(Package item, boolean empty) {
						super.updateItem(item, empty);
						System.out.println("updated");
						setText(empty || item == null ? "-" : item.getReceiver());
					}
				};
			}
		});
		this.root.getChildren().addAll(this.menuBar(), this.packageList);
		this.scene = new Scene(this.root,
				0.75*Screen.getPrimary().getBounds().getWidth(),
				0.60*Screen.getPrimary().getBounds().getHeight());

		this.parentStage.setTitle(this.getClass().getPackage().getName());
		this.parentStage.setScene(this.scene);
		this.parentStage.show();
	}

	private MenuBar menuBar() {
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("Datei");
		for (UserOption option : this.menuOptions) {
			MenuItem optionItem = new MenuItem(option.getTitle());
			optionItem.setOnAction(e -> option.getAction().run());
			fileMenu.getItems().add(optionItem);
		}

		menuBar.getMenus().addAll(fileMenu);
		return menuBar;
	}
}
