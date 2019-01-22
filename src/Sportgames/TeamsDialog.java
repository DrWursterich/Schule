package Sportgames;

import java.io.IOException;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import resource.ResourceManager;

public class TeamsDialog extends Dialog<List<Team>> {

	public class VereinListCell extends ListCell<Team> {
		private final TextField textField = new TextField();

		public VereinListCell() {
			this.textField.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
				switch (e.getCode()) {
					case ESCAPE:
						this.cancelEdit();
						break;
					case ENTER:
						if (TeamsDialog.this.listContainsTeamName(
								this.textField.getText())) {
							this.cancelEdit();
						} else {
							this.commitEdit(
									new Team(this.textField.getText()));
						}
						break;
					default:
						break;
				}
			});
			this.setGraphic(this.textField);
		}

		@Override
		protected void updateItem(Team item, boolean empty) {
			super.updateItem(item, empty);
			if (this.isEditing()) {
				this.textField.setText(
						item == null || empty ? "" : item.getName());
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
			this.setText(
					this.getItem() != null ? this.getItem().getName() : "");
			this.setContentDisplay(ContentDisplay.TEXT_ONLY);
		}

		@Override
		public void commitEdit(Team newValue) {
			super.commitEdit(newValue);
		}
	}

	@FXML
	private ListView<Team> list;

	@FXML
	public void initialize() {
		this.list.getItems().setAll(Main.getInstance().getTeams());
		this.list.setCellFactory(e -> new VereinListCell());
		this.list.setEditable(true);
		this.list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.list.setFixedCellSize(30);
		this.list.setOnKeyPressed(e -> {
			switch (e.getCode()) {
				case N:
					if (e.isControlDown()
							&& !e.isAltDown()
							&& !e.isShiftDown()) {
						this.add();
						e.consume();
					}
					break;
				default:
					break;
			}
		});
	}

	public TeamsDialog() {
		this.getDialogPane().getButtonTypes().setAll(
				ButtonType.APPLY, ButtonType.CANCEL);
		this.setResultConverter(e ->
				ButtonType.APPLY.equals(e) ? this.list.getItems() : null);
		this.setTitle("Edit Teams");
		this.setHeaderText("If Changes are saved, Team-Pairings will reset!");
		this.initModality(Modality.WINDOW_MODAL);
		try {
			ResourceManager.initDialog(this, "Sportgames.TeamsDialog");
		} catch (final IOException e) {
			System.out.println("Unable to initialize TeamsDialog");
		}
	}

	public void delete() {
		this.list.getItems().removeAll(
				this.list.getSelectionModel().getSelectedItems());
	}

	public void add() {
		final String baseName = "new Team";
		int counter = 1;
		String name = baseName;
		while (this.listContainsTeamName(name)) {
			name = baseName + counter++;
		}
		final Team team = new Team(name);
		this.list.getItems().add(team);
		this.list.getSelectionModel().clearSelection();
		this.list.getSelectionModel().select(team);
		this.list.edit(this.list.getSelectionModel().getSelectedIndex());
	}

	private boolean listContainsTeamName(final String name) {
		return this.list.getItems().stream()
				.filter(e -> name.equals(e.getName()))
				.count() > 0;
	}
}
