package Sportgames.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Optional;
import Sportgames.Verein;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import resource.ResourceManager;

public class MainController {
	@FXML
	private Pane root;
	@FXML
	private MenuItem menuOpen;
	@FXML
	private MenuItem menuSave;
	@FXML
	private MenuItem menuSaveAs;
	@FXML
	private MenuItem menuClose;
	@FXML
	private MenuItem menuAssociations;
	@FXML
	private TableView<Verein> table;
	private final ObservableList<Verein> associations =
			FXCollections.observableArrayList();
	private File lastSaved;

	public void initialize() {
		this.table.setItems(this.associations);
	}

	public void addAssociations(final Verein...vereine) {
		this.associations.addAll(vereine);
	}

	public void open() {
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open File");
		fileChooser.setInitialDirectory(
				this.lastSaved != null
					? this.lastSaved.getParentFile()
					: new File(ResourceManager.projectPath()));
		fileChooser.setInitialFileName(
				this.lastSaved != null
					? this.lastSaved.getName()
					: "");
		final File file = fileChooser.showOpenDialog(null);
		if (file == null || !file.exists() || file.isDirectory()) {
			System.out.println("unable to load file");
			return;
		}
		ObjectInputStream input = null;
		try {
			input = new ObjectInputStream(
					new BufferedInputStream(
						new FileInputStream(file)));
			final Object read = input.readObject();
			if (!(read instanceof Verein[])) {
				System.out.println("file is corrupted");
				return;
			}
			this.associations.setAll((Verein[])read);
			this.lastSaved = file;
		} catch (final IOException | ClassNotFoundException e) {
			System.out.println("exception: " + e.getMessage());
		} finally {
			try {
				input.close();
			} catch (final NullPointerException | IOException e) {}
		}
	}

	public void save() {
		if (this.lastSaved != null) {
			this.store(this.lastSaved);
		} else {
			this.saveAs();
		}
	}

	public void saveAs() {
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save File As");
		fileChooser.setInitialDirectory(
				this.lastSaved != null
					? this.lastSaved.getParentFile()
					: new File(ResourceManager.projectPath()));
		fileChooser.setInitialFileName(
				this.lastSaved != null
					? this.lastSaved.getName()
					: "");
		this.store(fileChooser.showSaveDialog(null));
	}

	public void close() {
		System.exit(0);
	}

	public void associations() {
		try {
			final AssociationsController controller =
					ResourceManager.getController(
						"Sportgames.Associations",
						AssociationsController.class);
			final Optional<List<Verein>> result = controller.showAndWait();
			if (result.isPresent()) {
				this.associations.setAll(result.get());
			}
		} catch (final IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	protected void store(final File file) {
		ObjectOutputStream output = null;
		try {
			if (!file.exists()) {
				if (file.isDirectory()) {
					System.out.println("cannot be saved as a directoy");
					return;
				}
				file.createNewFile();
				System.out.println("created file: " + file.getAbsolutePath());
			}
			output = new ObjectOutputStream(
					new BufferedOutputStream(
						new FileOutputStream(file)));
			output.writeObject(this.associations.toArray(
					new Verein[this.associations.size()]));
			this.lastSaved = file;
		} catch (final IOException e) {
			System.out.println("exception: " + e.getMessage());
		} finally {
			try {
				output.close();
			} catch (final NullPointerException | IOException e) {}
		}
	}
}
