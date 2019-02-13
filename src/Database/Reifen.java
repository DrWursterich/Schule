package Database;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Reifen extends Application {
	private StackPane root;
	private GridPane grid1;
	private GridPane grid2;
	private GridPane grid3;
	private GridPane grid4;
	private GridPane grid5;
	private GridPane grid6;
	private Button start1;
	private Button start2;
	private Label label1;
	private Label label2;
	private TextArea area1;
	private TextArea area2;
	private TextField textField;
	private RadioButton radioSommer;
	private RadioButton radioWinter;
	private ComboBox<String> combo;
	private ToggleGroup group;

	@Override
	public void start(final Stage primaryStage) {
		this.root = new StackPane();
		primaryStage.setScene(new Scene(this.root, 660, 500));
		primaryStage.setTitle("Datenbankzugriff II");
		this.grid1 = new GridPane();
		this.grid1.setVgap(5);
		this.grid1.setHgap(5);
		this.grid1.setPadding(new Insets(10));
		this.grid2 = new GridPane();
		this.grid2.setVgap(5);
		this.grid2.setHgap(5);
		this.grid2.setPadding(new Insets(10));
		this.grid3 = new GridPane();
		this.grid3.setVgap(5);
		this.grid3.setHgap(5);
		this.grid3.setPadding(new Insets(10));
		this.grid4 = new GridPane();
		this.grid4.setVgap(5);
		this.grid4.setHgap(5);
		this.grid4.setPadding(new Insets(10));
		this.grid5 = new GridPane();
		this.grid5.setVgap(5);
		this.grid5.setHgap(5);
		this.grid5.setPadding(new Insets(10));
		this.grid6 = new GridPane();
		this.grid6.setVgap(5);
		this.grid6.setHgap(5);
		this.grid6.setPadding(new Insets(10));
		this.start1 = new Button("Start");
		this.start2 = new Button("Start");
		this.label1 = new Label("Teil 1:");
		this.label2 = new Label("Teil 2:");
		this.area1 = new TextArea();
		this.area2 = new TextArea();
		this.textField = new TextField();
		this.group = new ToggleGroup();
		this.radioSommer = new RadioButton("Sommerreifen");
		this.radioWinter = new RadioButton("Winterreifen");
		this.radioSommer.setToggleGroup(this.getGroup());
		this.radioWinter.setToggleGroup(this.getGroup());
		this.radioSommer.setSelected(true);
		this.combo = new ComboBox<>();
		this.combo.getItems()
				.addAll("Brückstein", "Continent", "Kingroyal", "Michigan");
		this.combo.setValue("Brückstein");
		this.grid1.add(this.grid2, 0, 0);
		this.grid1.add(this.grid5, 0, 1);
		this.grid2.add(this.grid3, 0, 0);
		this.grid2.add(this.grid4, 1, 0);
		this.grid3.add(this.label1, 0, 0);
		this.grid3.add(this.getRadioSommer(), 0, 1);
		this.grid3.add(this.getRadioWinter(), 0, 2);
		this.grid3.add(this.getStart1(), 0, 4);
		this.grid4.add(this.getArea1(), 0, 0);
		this.grid4.add(this.getTextField(), 0, 1);
		this.grid5.add(this.grid6, 0, 0);
		this.grid5.add(this.getArea2(), 1, 0);
		this.grid6.add(this.label2, 0, 0);
		this.grid6.add(this.getCombo(), 0, 1);
		this.grid6.add(this.getStart2(), 0, 2);
		this.root.getChildren().add(this.grid1);
		primaryStage.show();
//		Presenter.start(this);
	}

	public void setAusgabeArea1(final String ausgabe) {
		this.getArea1().setText(ausgabe);
	}

	public void setAusgabeArea2(final String ausgabe) {
		this.getArea2().setText(ausgabe);
	}

	public void setAusgabeTextField(final String ausgabe) {
		this.getTextField().setText(ausgabe);
	}

	public static void main(final String[] args) {
		Application.launch(args);
	}

	public Button getStart1() {
		return this.start1;
	}

	public Button getStart2() {
		return this.start2;
	}

	public RadioButton getRadioSommer() {
		return this.radioSommer;
	}

	public RadioButton getRadioWinter() {
		return this.radioWinter;
	}

	public ComboBox<String> getCombo() {
		return this.combo;
	}

	public TextArea getArea1() {
		return this.area1;
	}

	public TextArea getArea2() {
		return this.area2;
	}

	public TextField getTextField() {
		return this.textField;
	}

	public ToggleGroup getGroup() {
		return this.group;
	}
}
