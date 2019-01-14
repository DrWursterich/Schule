package Sportgames.application;

import Sportgames.Verein;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class VereinCellFactory implements
		Callback<TableColumn<Object, Object>, TableCell<Object, Object>> {
	private String defaultString = " - ";

	@Override
	public TableCell<Object, Object> call(
			final TableColumn<Object, Object> column) {
		return new TableCell<Object, Object>() {
			@Override
			protected void updateItem(final Object item, final boolean empty) {
				if (item != null) {
					System.out.println("updating (Object=" + item + ")");
				}
				this.setText(empty || item == null
						? VereinCellFactory.this.defaultString
						: item instanceof Verein
							? ((Verein)item).getName()
							: item.getClass().getName());
			}
		};
	}

	public void setDefaultString(final String defaultString) {
		this.defaultString = defaultString;
	}

	public String getDefaultString() {
		return this.defaultString;
	}
}
