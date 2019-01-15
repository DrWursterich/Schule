package Sportgames.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import Sportgames.Verein;
import Sportgames.application.Paarung;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
	@FXML
	private VBox pairingsPane;
	@FXML
	private final ObservableList<Verein> associations =
			FXCollections.observableArrayList();
	private File lastSaved;
	private final ArrayList<Paarung> pairings = new ArrayList<>();

	public void initialize() {
		this.table.setItems(this.associations);
		this.generatePairings();
	}

	public void open() {
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open File");
		fileChooser.setInitialDirectory(
				this.lastSaved != null
					? this.lastSaved.getParentFile()
					: new File(System.getProperty("user.dir")));
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
		if (this.lastSaved != null) {
			fileChooser.setInitialDirectory(this.lastSaved.getParentFile());
			fileChooser.setInitialFileName(this.lastSaved.getName());
		} else {
			fileChooser.setInitialDirectory(
					new File(System.getProperty("user.dir")));
		}
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
			if (result != null && result.isPresent()) {
				this.associations.setAll(result.get());
				this.generatePairings();
			}
		} catch (final IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	protected void store(final File file) {
		if (file == null) {
			return;
		}
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

	private void generatePairings() {
		this.pairings.clear();
		for (Verein firstTeam : this.associations) {
			for (Verein secondTeam : this.associations) {
				if (!firstTeam.equals(secondTeam)) {
					this.pairings.add(new Paarung(
							"location",
							new GregorianCalendar(),
							firstTeam,
							secondTeam));
				}
			}
		}
		this.updatePairingsPane();
	}

	private void updatePairingsPane() {
		this.pairingsPane.getChildren().setAll(this.pairings);
	}
}
