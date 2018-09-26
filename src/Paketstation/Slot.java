package Paketstation;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Slot {
	private ObjectProperty<Package> packageProperty
			= new SimpleObjectProperty<>();
	private IntegerProperty slotNrProperty
			= new SimpleIntegerProperty();

	public Slot(int slotNr) {
		this.slotNrProperty.setValue(slotNr);
	}

	public ObjectProperty<Package> packageProperty() {
		return this.packageProperty;
	}

	public Package getPackage() {
		return this.packageProperty.getValue();
	}

	public void setPackage(Package item) {
		this.packageProperty.setValue(item);
	}

	public boolean hasPackage() {
		return this.packageProperty.getValue() != null;
	}

	public IntegerProperty slotNrProperty() {
		return this.slotNrProperty;
	}
}
