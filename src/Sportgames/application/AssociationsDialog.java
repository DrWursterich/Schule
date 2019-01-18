package Sportgames.application;

import java.io.IOException;
import java.util.List;

import Sportgames.Verein;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import resource.ResourceManager;

public class AssociationsDialog extends Dialog<List<Verein>> {

	public class VereinListCell extends ListCell<Verein> {
		private final TextField textField = new TextField();

		public VereinListCell() {
			this.textField.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
				switch (e.getCode()) {
					case ESCAPE:
						this.cancelEdit();
						break;
					case ENTER:
						this.commitEdit(new Verein(this.textField.getText()));
						break;
					default:
						break;
				}
			});
			this.setGraphic(this.textField);
		}

		@Override
		protected void updateItem(Verein item, boolean empty) {
			super.updateItem(item, empty);
			if (this.isEditing()) {
				this.textField.setText(item == null || empty ? "" : item.getName());
				this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			} else {
				this.setText(item == null || empty ? "" : item.getName());
				this.setContentDisplay(ContentDisplay.TEXT_ONLY);
			}
		}

		@Override
		public void startEdit() {
			super.startEdit();
			this.textField.setText(this.getItem().getName());
			this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			this.textField.requestFocus();
			this.textField.selectAll();
		}

		@Override
		public void cancelEdit() {
			super.cancelEdit();
			this.setText(this.getItem() != null ? this.getItem().getName() : "");
			this.setContentDisplay(ContentDisplay.TEXT_ONLY);
		}

		@Override
		public void commitEdit(Verein newValue) {
			super.commitEdit(newValue);
		}
	}

	@FXML
	private ListView<Verein> list;

	@FXML
	public void initialize() {
		this.list.getItems().setAll(Main.getInstance().getAssociations());
		this.list.setCellFactory(e -> new VereinListCell());
		this.list.setEditable(true);
		this.list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.list.setFixedCellSize(30);
		this.list.setOnKeyPressed(e -> {
			switch (e.getCode()) {
				case N:
					if (e.isControlDown() && !e.isAltDown() && !e.isShiftDown()) {
						this.add();
						e.consume();
					}
					break;
				default:
					break;
			}
		});
	}

	public AssociationsDialog() {
		this.getDialogPane().getButtonTypes().setAll(
				ButtonType.APPLY, ButtonType.CANCEL);
		this.setResultConverter(e ->
				ButtonType.APPLY.equals(e) ? this.list.getItems() : null);
		this.setTitle("Edit Associations");
		this.setHeaderText("If Changes are saved, Team-Pairings will reset!");
		this.initModality(Modality.WINDOW_MODAL);
		try {
			ResourceManager.initDialog(this, "Sportgames.AssociationsDialog");
		} catch (final IOException e) {
			System.out.println("Unable to initialize AssociationsDialog");
		}
	}

	public void delete() {
		this.list.getItems().removeAll(
				this.list.getSelectionModel().getSelectedItems());
	}

	public void add() {
		final Verein team = new Verein("new Association");
		this.list.getItems().add(team);
		this.list.getSelectionModel().clearSelection();
		this.list.getSelectionModel().select(team);
		this.list.edit(this.list.getSelectionModel().getSelectedIndex());
	}
}
