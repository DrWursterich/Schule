package Sportgames.application;

import java.io.IOException;
import java.time.LocalDate;
import java.util.GregorianCalendar;

import Sportgames.PairingState;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import resource.ResourceManager;

public class PaarungDialog extends Stage {
	@FXML
	private Label firstTeamLabel;
	@FXML
	private Label secondTeamLabel;
	@FXML
	private Label separatorLabel;
	@FXML
	private NumberField firstTeamGoals;
	@FXML
	private NumberField secondTeamGoals;
	@FXML
	private ComboBox<PairingState> stateBox;
	@FXML
	private TextField locationField;
	@FXML
	private DatePicker datePicker;
	@FXML
	private NumberField hoursField;
	@FXML
	private NumberField minutesField;
	private Paarung pairing;

	public PaarungDialog(final Paarung pairing) {
		this.pairing = pairing;
		this.initModality(Modality.WINDOW_MODAL);
		try {
			this.setScene(new Scene(ResourceManager.loadNewResource(
					"Sportgames.PaarungDialog", this)));
		} catch (IOException e) {
			System.out.println("Unable to initialize PaarungDialog");
		}
	}

	@FXML
	public void initialize() {
		this.firstTeamLabel.setText(this.pairing.getFirstTeam().getName());
		this.secondTeamLabel.setText(this.pairing.getSecondTeam().getName());
		this.firstTeamGoals.setValue(this.pairing.getFirstTeamGoals());
		this.secondTeamGoals.setValue(this.pairing.getSecondTeamGoals());
		this.locationField.setText(this.pairing.getLocation());
		final GregorianCalendar date = this.pairing.getDate();
		this.datePicker.setValue(LocalDate.of(
				date.get(GregorianCalendar.YEAR),
				date.get(GregorianCalendar.MONTH) + 1,
				date.get(GregorianCalendar.DATE)));
		this.hoursField.setValue(date.get(GregorianCalendar.HOUR_OF_DAY));
		this.minutesField.setValue(date.get(GregorianCalendar.MINUTE));
		this.stateBox.valueProperty().addListener((v, o , n) -> {
			switch (n) {
				case NOT_STARTED:
					PaarungDialog.this.firstTeamGoals.setText("0");
					PaarungDialog.this.secondTeamGoals.setText("0");
					PaarungDialog.this.firstTeamGoals.setDisable(true);
					PaarungDialog.this.firstTeamGoals.setDisable(true);
					break;
				default:
					PaarungDialog.this.firstTeamGoals.setDisable(false);
					PaarungDialog.this.firstTeamGoals.setDisable(false);
					break;
			}
		});
		this.stateBox.getSelectionModel().select(this.pairing.getState());
	}

	public void apply() {
		this.pairing.setFirstTeamGoals(this.firstTeamGoals.getValue());
		this.pairing.setSecondTeamGoals(this.secondTeamGoals.getValue());
		this.pairing.setState(this.stateBox.getSelectionModel().getSelectedItem());
		this.pairing.setLocation(this.locationField.getText());
		final LocalDate date = this.datePicker.getValue();
		this.pairing.setDate(new GregorianCalendar(
				date.getYear(),
				date.getMonthValue() - 1,
				date.getDayOfMonth(),
				this.hoursField.getValue(),
				this.minutesField.getValue()));
		Main.getInstance().refreshTable();
		this.close();
	}
}
