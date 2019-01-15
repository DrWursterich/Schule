package Sportgames.controller;

import java.util.List;

import Sportgames.Verein;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;

public class AssociationsController extends Dialog<List<Verein>> {
	@FXML
	private ListView<Verein> list;
	@FXML
	private HBox root;

	@FXML
	public void initialize() {
		for (int i=1; i<=5; i++) {
			this.list.getItems().add(new Verein("test verein " + i));
		}
		this.list.setCellFactory(e -> new ListCell<Verein>() {
			@Override
			public void updateItem(final Verein item, final boolean empty) {
				this.setText(item == null || empty ? "-" : item.getName());
			}
		});
		this.list.setEditable(true);
		this.list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.setHeaderText("If Changes are saved, Team-Pairings will be reset!");
		this.initModality(Modality.WINDOW_MODAL);
		this.getDialogPane().setContent(root);
	}

	public AssociationsController() {
		this.getDialogPane().getButtonTypes().setAll(
				ButtonType.APPLY, ButtonType.CANCEL);
		this.setResultConverter(e ->
				ButtonType.APPLY.equals(e) ? this.list.getItems() : null);
		this.setTitle("Edit Associations");
		this.setHeaderText("Whatever");
	}
}
