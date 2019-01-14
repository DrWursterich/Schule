package Sportgames.controller;

import java.util.List;
import Sportgames.Verein;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

public class AssociationsController extends Dialog<List<Verein>> {
	@FXML
	private ListView<Verein> list;

	@FXML
	public void initialize() {
		this.list.getItems().add(new Verein("test verein"));
		this.list.setCellFactory(e -> new ListCell<Verein>() {
			@Override
			public void updateItem(final Verein item, final boolean empty) {
				this.setText(item == null || empty ? "" : item.getName());
			}
		});
		this.list.setEditable(true);
		this.getDialogPane().setContent(new StackPane(this.list));
	}

	public AssociationsController() {
		this.getDialogPane().getButtonTypes().setAll(
				ButtonType.APPLY, ButtonType.CANCEL);
		this.setResultConverter(e ->
				ButtonType.APPLY.equals(e) ? this.list.getItems() : null);
		this.setTitle("Edit Associations");
	}
}
