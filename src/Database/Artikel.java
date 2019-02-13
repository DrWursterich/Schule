package Database;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Artikel extends Application {
	private StackPane root;
	private GridPane myWindow;
	private Button ausgebenArtikel;
	private Button ausgebenEingaenge;
	private Button ausgebenNachArtNr;
	private Button ausgebenNachArtNrTeil;
	private Label label1;
	private Label label2;
	private TextField textFieldArtNr;
	private TextField textFieldAnfang;

	@Override
	public void start(final Stage primaryStage) {
		this.root = new StackPane();
		primaryStage.setScene(new Scene(this.root, 500, 120));
		primaryStage.setTitle("Datenbankzugriff");
		this.myWindow = new GridPane();
		this.myWindow.setVgap(5);
		this.myWindow.setHgap(5);
		this.myWindow.setPadding(new Insets(10));
		this.ausgebenArtikel = new Button("Artikel ausgeben");
		this.ausgebenEingaenge = new Button("Eingänge ausgeben");
		this.label1 = new Label("Artikelnr. eingeben:");
		this.textFieldArtNr = new TextField();
		this.ausgebenNachArtNr = new Button("Ausgänge anzeigen");
		this.label2 = new Label("Anfang Artikelnr. eingeben:");
		this.textFieldAnfang = new TextField();
		this.ausgebenNachArtNrTeil = new Button("Ausgänge anzeigen (Teil)");
		this.myWindow.add(this.getAusgebenArtikel(), 0, 0);
		this.myWindow.add(this.getAusgebenEingaenge(), 2, 0);
		this.myWindow.add(this.label1, 0, 1);
		this.myWindow.add(this.getTextFieldArtNr(), 1, 1);
		this.myWindow.add(this.getAusgebenNachArtNr(), 2, 1);
		this.myWindow.add(this.label2, 0, 2);
		this.myWindow.add(this.getTextFieldAnfang(), 1, 2);
		this.myWindow.add(this.getAusgebenNachArtNrTeil(), 2, 2);
		this.root.getChildren().add(this.myWindow);
		primaryStage.show();
//		Presenter.start(this);
	}

	public static void main(final String[] args) {
		Application.launch(args);
	}

	public Button getAusgebenArtikel() {
		return this.ausgebenArtikel;
	}

	public Button getAusgebenEingaenge() {
		return this.ausgebenEingaenge;
	}

	public Button getAusgebenNachArtNr() {
		return this.ausgebenNachArtNr;
	}

	public Button getAusgebenNachArtNrTeil() {
		return this.ausgebenNachArtNrTeil;
	}

	public TextField getTextFieldArtNr() {
		return this.textFieldArtNr;
	}

	public TextField getTextFieldAnfang() {
		return this.textFieldAnfang;
	}
}
