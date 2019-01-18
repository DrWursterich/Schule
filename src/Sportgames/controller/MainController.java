package Sportgames.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import Sportgames.Verein;
import Sportgames.application.AssociationsDialog;
import Sportgames.application.Paarung;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController {
	private static final String FILE_NAME_TEAMS = "teams";
	private static final String FILE_NAME_PAIRINGS = "pairings";
	private static final String[] LOCATIONS = new String[] {
					"Wembley-Stadion",
					"Nationalstadion Peking",
					"Rose Bowl Stadium",
					"FNB-Stadion",
					"Azadi-Stadion",
					"Camp Nou",
					"Melbourne Cricket Ground",
					"Aztekenstadion",
					"Yuba Bharati Krirangan",
					"Stadion Erster Mai"};
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
		fileChooser.getExtensionFilters().add(
				new ExtensionFilter("Zip-Archive (*.ZIP)", "*.zip", "*.ZIP"));
		fileChooser.setInitialDirectory(
				this.lastSaved != null
					? this.lastSaved.getParentFile()
					: new File(System.getProperty("user.dir")));
		fileChooser.setInitialFileName(
				this.lastSaved != null
					? this.lastSaved.getName()
					: "");
		this.load(fileChooser.showOpenDialog(null));
		this.refreshTable();
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
		fileChooser.getExtensionFilters().add(
				new ExtensionFilter("Zip-Archive (*.ZIP)", "*.zip", "*.ZIP"));
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
		AssociationsDialog dialog = new AssociationsDialog();
		final Optional<List<Verein>> result = dialog.showAndWait();
		if (result != null && result.isPresent()) {
			this.associations.setAll(result.get());
			this.generatePairings();
		}
	}

	protected void store(final File file) {
		if (file == null) {
			return;
		}
		if (file.exists()) {
			if (file.isDirectory()) {
				System.out.println("cannot be saved as a directoy");
				return;
			}
		} else {
			try {
				file.createNewFile();
			} catch (final IOException e) {
				System.out.println(
						"Unable to create File \""
						+ file.getAbsolutePath() + "\"");
				return;
			}
		}
		ZipOutputStream out = null;
		ObjectOutputStream objOut = null;
		ObjectOutputStream objOut2 = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(file));
			objOut = new ObjectOutputStream(new BufferedOutputStream(out));
			out.putNextEntry(new ZipEntry(MainController.FILE_NAME_TEAMS));
			objOut.writeObject(this.associations.toArray(
					new Verein[this.associations.size()]));
			objOut.flush();
			out.closeEntry();
			out.putNextEntry(new ZipEntry(MainController.FILE_NAME_PAIRINGS));
			objOut2 = new ObjectOutputStream(new BufferedOutputStream(out));
			objOut2.writeObject(this.pairings.toArray(
					new Paarung[this.pairings.size()]));
			objOut2.flush();
			out.closeEntry();
		} catch (IOException e) {
			System.out.println("An Error occoured, the File might be unsaved");
			e.printStackTrace();
		} finally {
			try {
				objOut.close();
				objOut2.close();
				out.close();
			} catch (final IOException e) {
				System.out.println("An Error occoured, the File might be unsaved");
			} catch (final NullPointerException e) {}
		}
	}

	private void load(final File file) {
		if (file == null) {
			return;
		}
		if (!file.exists() || file.isDirectory()) {
			System.out.println("Unable to load File");
			return;
		}
		ObjectInputStream objIn = null;
		ZipFile zip = null;
		boolean foundTeams = false;
		boolean foundPairings = false;
		try {
			zip = new ZipFile(file);
			final Enumeration<? extends ZipEntry> entries = zip.entries();
			while(entries.hasMoreElements()) {
				final ZipEntry entry = entries.nextElement();
				if (MainController.FILE_NAME_TEAMS.equals(entry.getName())) {
					foundTeams = true;
					objIn = new ObjectInputStream(new BufferedInputStream(zip.getInputStream(entry)));
					this.associations.setAll((Verein[])objIn.readObject());
					objIn.close();
				} else if (MainController.FILE_NAME_PAIRINGS.equals(entry.getName())) {
					foundPairings = true;
					objIn = new ObjectInputStream(new BufferedInputStream(zip.getInputStream(entry)));
					this.pairings.clear();
					this.pairings.addAll(Arrays.asList((Paarung[])objIn.readObject()));
					this.updatePairingsPane();
					objIn.close();
				} else {
					System.out.println(
							"Found expendable File in Archive \""
							+ entry.getName() + "\"");
				}
			}
		} catch (final IOException | ClassNotFoundException e) {
			System.out.println("An Error occoured, the File may have not been loaded");
			e.printStackTrace();
		} finally {
			try {
				objIn.close();
				zip.close();
			} catch (final IOException e) {
				System.out.println("An Error occoured, the File may have not been loaded");
			} catch (final NullPointerException e) {}
		}
		if (!foundTeams) {
			System.out.println("No Teams found");
		}
		if (!foundPairings) {
			System.out.println("No Pairings found");
		}
	}

	private void generatePairings() {
		this.pairings.clear();
		GregorianCalendar date = new GregorianCalendar();
		for (Verein firstTeam : this.associations) {
			for (Verein secondTeam : this.associations) {
				if (!firstTeam.equals(secondTeam)) {
					this.pairings.add(new Paarung(
							MainController.LOCATIONS[(int)(
									Math.random()
									* (MainController.LOCATIONS.length - 1))],
							(GregorianCalendar)date.clone(),
							firstTeam,
							secondTeam));
					date.add(
							GregorianCalendar.DATE,
							(int)(1 + Math.random() * 20));
					date.add(
							GregorianCalendar.HOUR_OF_DAY,
							(int)(Math.random() * 8 - 4));
				}
			}
		}
		this.updatePairingsPane();
	}

	private void updatePairingsPane() {
		this.pairingsPane.getChildren().setAll(this.pairings);
	}

	public List<Verein> getAssociations() {
		return this.associations;
	}

	public List<Paarung> getPairings() {
		return this.pairings;
	}

	public void refreshTable() {
		this.table.refresh();
	}
}
