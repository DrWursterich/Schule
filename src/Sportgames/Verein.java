package Sportgames;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Verein implements Serializable {
	private static final long serialVersionUID = 1020500889918654466L;
	private  StringProperty name;

	protected Verein() {
		this.name = new SimpleStringProperty();
	}

	public Verein(final String name) {
		this();
		this.name.set(name);
	}

	public String getName() {
		return this.name.get();
	}

	@Override
	public String toString() {
		return this.getName();
	}

	private void writeObject(final ObjectOutputStream out) throws IOException {
		out.writeObject(this.getName());
	}

	private void readObject(final ObjectInputStream in)
			throws IOException, ClassNotFoundException {
		final Object read = in.readObject();
		if (!(read instanceof String)) {
			throw new IOException("Unable to read Instance of Verein");
		}
		if (this.name == null) {
			this.name = new SimpleStringProperty();
		}
		this.name.set((String)read);
	}
}
