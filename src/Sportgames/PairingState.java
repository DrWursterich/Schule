package Sportgames;

import java.io.Serializable;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

public enum PairingState implements Serializable {
	NOT_STARTED("Not Started", "starts"),
	BEEING_PLAYED("Beeing Played", "started"),
	FINISHED("Finished", "was played");

	private final String name;
	private final ReadOnlyStringWrapper label;

	PairingState(final String name, final String label) {
		this.name = name;
		this.label = new ReadOnlyStringWrapper(label);
	}

	public String getLabel() {
		return this.label.get();
	}

	public ReadOnlyStringProperty labelProperty() {
		return this.label.getReadOnlyProperty();
	}

	@Override
	public String toString() {
		return this.name;
	}
}
