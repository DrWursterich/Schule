package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Speisekarte extends Application {
	private StackPane root;
	private GridPane grid;
	private GridPane grid1;
	private GridPane grid2;
	private GridPane grid3;
	private GridPane grid4;
	private GridPane grid5;
	private GridPane grid6;
	private GridPane grid7;
	private Label label1;
	private Label label2;
	private Label label3;
	private Label label4;
	private Label label5;
	private Label label6;
	private Label label7;
	private Label label8;
	private Label label9;
	private Label label10;
	private Label label11;
	private Label label12;
	private Label label13;
	private TextField spnr1;
	private TextField bezeich;
	private TextField kategorie;
	private TextField preis;
	private TextField spnr2;
	private TextField spnr3;
	private TextField neuerPreis;
	private Button hinzufuegen;
	private Button loeschen;
	private Button aendern;
	private Button anzeigen;
	private Button start1;
	private Button start2;
	private Button start3;
	private TextArea ausgabe;
	private ComboBox<String> combo;
	private RadioButton fuenf;
	private RadioButton zehn;
	private RadioButton fuenfzehn;
	private ToggleGroup group;
	private CheckBox ohnemwst;
	private Connection connection;

	@Override
	public void start(final Stage primaryStage) throws Exception {
		this.root = new StackPane();
		primaryStage.setScene(new Scene(this.root, 400, 900));
		primaryStage.setTitle("Speisekarte");
		this.grid = new GridPane();
		this.grid.setVgap(5);
		this.grid.setHgap(5);
		this.grid.setPadding(new Insets(12));
		this.grid1 = new GridPane();
		this.grid1.setVgap(5);
		this.grid1.setHgap(5);
		this.grid1.setPadding(new Insets(12));
		this.label1 = new Label("Datensatz hinzufügen");
		this.label2 = new Label("Speisen-Nr:");
		this.label3 = new Label("Bezeichnung:");
		this.label4 = new Label("Kategorie:");
		this.label5 = new Label("Preis:");
		this.spnr1 = new TextField();
		this.bezeich = new TextField();
		this.kategorie = new TextField();
		this.preis = new TextField();
		this.hinzufuegen = new Button("hinzufügen");
		this.hinzufuegen.setOnAction(e -> {
			try {
				final PreparedStatement statement
						= this.connection.prepareStatement(
							"INSERT INTO Speisen(Bezeichnung, Kategorie, Preis)"
							+ " VALUES (?, ?, ?);");
				statement.setString(1, this.getBezeich().getText());
				statement.setString(2, this.getKategorie().getText());
				statement.setInt(3,
						Integer.parseInt(this.getPreis().getText()));
				statement.execute();
			} catch (final Exception ex) {
				ex.printStackTrace();
			}
		});
		this.grid2 = new GridPane();
		this.grid2.setVgap(5);
		this.grid2.setHgap(5);
		this.grid2.setPadding(new Insets(12));
		this.label6 = new Label("Datensatz löschen");
		this.label7 = new Label("Speisen-Nr:");
		this.spnr2 = new TextField();
		this.loeschen = new Button("löschen");
		this.loeschen.setOnAction(e -> {
			try {
				final PreparedStatement statement
						= this.connection.prepareStatement(
							"DELETE FROM Speisen WHERE SpNr LIKE ?;");
				statement.setString(0, this.getSpnr1().getText());
				statement.execute();
			} catch (final Exception ex) {
				ex.printStackTrace();
			}
		});
		this.grid3 = new GridPane();
		this.grid3.setVgap(5);
		this.grid3.setHgap(5);
		this.grid3.setPadding(new Insets(12));
		this.label8 = new Label("Preisänderung");
		this.label9 = new Label("Speisen-Nr:");
		this.label10 = new Label("neuer Preis");
		this.spnr3 = new TextField();
		this.neuerPreis = new TextField();
		this.aendern = new Button("ändern");
		this.aendern.setOnAction(e -> {
			try {
				final PreparedStatement statement
						= this.connection.prepareStatement(
							"UPDATE Speisen SET Preis = ? WHERE SpNr LIKE ?;");
				statement.setInt(1,
						Integer.parseInt(this.getNeuerPreis().getText()));
				statement.setString(2, this.getSpnr2().getText());
				statement.execute();
			} catch (final Exception ex) {
				ex.printStackTrace();
			}
		});
		this.grid4 = new GridPane();
		this.grid4.setVgap(5);
		this.grid4.setHgap(5);
		this.grid4.setPadding(new Insets(12));
		this.anzeigen = new Button("Datensätze anzeigen");
		this.ausgabe = new TextArea();
		this.grid5 = new GridPane();
		this.grid5.setVgap(5);
		this.grid5.setHgap(5);
		this.grid5.setPadding(new Insets(12));
		this.label11 = new Label("Kategorie löschen");
		this.combo = new ComboBox<>();
		this.getCombo().getItems()
				.addAll("Fleisch",
						"Vegetarisch",
						"Appetizer",
						"Eis",
						"Kuchen",
						"Salat",
						"Suppe");
		this.getCombo().setValue("Fleisch");
		this.start1 = new Button("Start");
		this.start1.setOnAction(e -> {
			try {
				final PreparedStatement statement
						= this.connection.prepareStatement(
							"DELETE FROM Speisen WHERE Kategorie LIKE ?;");
				statement.setString(1, this.getKategorie().getText());
				statement.execute();
			} catch (final Exception ex) {
				ex.printStackTrace();
			}
		});
		this.grid6 = new GridPane();
		this.grid6.setVgap(5);
		this.grid6.setHgap(5);
		this.grid6.setPadding(new Insets(12));
		this.label12 = new Label("Preiserhöhung");
		this.fuenf = new RadioButton("5 %");
		this.zehn = new RadioButton("10 %");
		this.fuenfzehn = new RadioButton("15 %");
		this.start2 = new Button("Start");
		this.start2.setOnAction(e -> {
			try {
				final PreparedStatement statement
						= this.connection.prepareStatement(
							"UPDATE Speisen SET Preis = Preis * ? WHERE 1 = 1;");
				statement.setDouble(1,
						this.getFuenf().isSelected()
							? 1.05
							: this.getZehn().isSelected()
								? 1.1
								: this.getFuenfzehn().isSelected()
									? 1.15
									: 1);
				statement.execute();
			} catch (final Exception ex) {
				ex.printStackTrace();
			}
		});
		this.group = new ToggleGroup();
		this.getFuenf().setToggleGroup(this.group);
		this.getZehn().setToggleGroup(this.group);
		this.getFuenfzehn().setToggleGroup(this.group);
		this.grid7 = new GridPane();
		this.grid7.setVgap(5);
		this.grid7.setHgap(5);
		this.grid7.setPadding(new Insets(12));
		this.label13 = new Label("Preisliste ausgeben");
		this.ohnemwst = new CheckBox("ohne Mehrwertsteuer");
		this.start3 = new Button("Start");
		this.start3.setOnAction(e -> {
			try {
				final Statement statement = this.connection.createStatement();
				final ResultSet result = statement.executeQuery(
						"SELECT Bezeichnung, Kategorie, Preis FROM Speisen");
				while(result.next()) {
					System.out.println(String.format(
							"%-12s%-12s%2.2f",
							result.getString("Bezeichnung"),
							result.getString("Kategorie"),
							result.getInt("Preis")
								* (this.getOhnemwst().isSelected()
									? 0.85
									: 1)));
				}
			} catch (final Exception ex) {
				ex.printStackTrace();
			}
		});
		this.grid.add(this.grid1, 0, 1);
		this.grid.add(this.grid2, 0, 2);
		this.grid.add(this.grid3, 0, 3);
		this.grid.add(this.grid4, 0, 4);
		this.grid.add(this.grid5, 0, 5);
		this.grid.add(this.grid6, 0, 6);
		this.grid.add(this.grid7, 0, 7);
		this.grid1.add(this.label1, 0, 0);
		this.grid1.add(this.label2, 0, 1);
		this.grid1.add(this.label3, 0, 2);
		this.grid1.add(this.label4, 0, 3);
		this.grid1.add(this.label5, 0, 4);
		this.grid1.add(this.getSpnr1(), 1, 1);
		this.grid1.add(this.getBezeich(), 1, 2);
		this.grid1.add(this.getKategorie(), 1, 3);
		this.grid1.add(this.getPreis(), 1, 4);
		this.grid1.add(this.getHinzufuegen(), 2, 4);
		this.grid2.add(this.label6, 0, 0);
		this.grid2.add(this.label7, 0, 1);
		this.grid2.add(this.getSpnr2(), 1, 1);
		this.grid2.add(this.getLoeschen(), 2, 1);
		this.grid3.add(this.label8, 0, 0);
		this.grid3.add(this.label9, 0, 1);
		this.grid3.add(this.label10, 0, 2);
		this.grid3.add(this.getSpnr3(), 1, 1);
		this.grid3.add(this.getNeuerPreis(), 1, 2);
		this.grid3.add(this.getAendern(), 2, 2);
		this.grid4.add(this.getAnzeigen(), 0, 0);
		this.grid4.add(this.ausgabe, 0, 1);
		this.grid5.add(this.label11, 0, 0);
		this.grid5.add(this.getCombo(), 0, 1);
		this.grid5.add(this.getStart1(), 2, 1);
		this.grid6.add(this.label12, 0, 0);
		this.grid6.add(this.getFuenf(), 0, 1);
		this.grid6.add(this.getZehn(), 0, 2);
		this.grid6.add(this.getFuenfzehn(), 0, 3);
		this.grid6.add(this.getStart2(), 2, 3);
		this.grid7.add(this.label13, 0, 0);
		this.grid7.add(this.getOhnemwst(), 0, 1);
		this.grid7.add(this.getStart3(), 2, 1);
		this.root.getChildren().add(this.grid);
		this.connection = DriverManager.getConnection(
				"jdbc:sqlite:resources/db/Database/Speisekarte.db");
		primaryStage.show();
	}

	public static void main(final String[] args) {
		Application.launch(args);
	}

	public Button getHinzufuegen() {
		return this.hinzufuegen;
	}

	public Button getLoeschen() {
		return this.loeschen;
	}

	public Button getAendern() {
		return this.aendern;
	}

	public Button getAnzeigen() {
		return this.anzeigen;
	}

	public Button getStart1() {
		return this.start1;
	}

	public Button getStart2() {
		return this.start2;
	}

	public Button getStart3() {
		return this.start3;
	}

	public void setAusgabe(final String ausgabe) {
		this.ausgabe.setText(ausgabe);
	}

	public ComboBox<String> getCombo() {
		return this.combo;
	}

	public RadioButton getFuenf() {
		return this.fuenf;
	}

	public RadioButton getZehn() {
		return this.zehn;
	}

	public RadioButton getFuenfzehn() {
		return this.fuenfzehn;
	}

	public CheckBox getOhnemwst() {
		return this.ohnemwst;
	}

	public TextField getSpnr1() {
		return this.spnr1;
	}

	public TextField getBezeich() {
		return this.bezeich;
	}

	public TextField getKategorie() {
		return this.kategorie;
	}

	public TextField getPreis() {
		return this.preis;
	}

	public TextField getSpnr2() {
		return this.spnr2;
	}

	public TextField getSpnr3() {
		return this.spnr3;
	}

	public TextField getNeuerPreis() {
		return this.neuerPreis;
	}
}
