package Sportgames;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import resource.ResourceManager;

public class PairingDialog extends Stage {
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
	private Pairing pairing;

	public PairingDialog(final Pairing pairing) {
		this.pairing = pairing;
		this.setTitle("Edit Pairing");
		this.initModality(Modality.WINDOW_MODAL);
		try {
			this.setScene(new Scene(ResourceManager.loadNewResource(
					"Sportgames.PairingDialog", this)));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Unable to initialize PairingDialog");
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
				date.get(Calendar.YEAR),
				date.get(Calendar.MONTH) + 1,
				date.get(Calendar.DATE)));
		this.hoursField.setValue(date.get(Calendar.HOUR_OF_DAY));
		this.minutesField.setValue(date.get(Calendar.MINUTE));
		this.stateBox.valueProperty().addListener((v, o , n) -> {
			switch (n) {
				case NOT_STARTED:
					PairingDialog.this.firstTeamGoals.setText("0");
					PairingDialog.this.secondTeamGoals.setText("0");
					PairingDialog.this.firstTeamGoals.setDisable(true);
					PairingDialog.this.secondTeamGoals.setDisable(true);
					break;
				default:
					PairingDialog.this.firstTeamGoals.setDisable(false);
					PairingDialog.this.secondTeamGoals.setDisable(false);
					break;
			}
		});
		this.stateBox.getSelectionModel().select(this.pairing.getState());
	}

	public void apply() {
		this.pairing.setFirstTeamGoals(this.firstTeamGoals.getValue());
		this.pairing.setSecondTeamGoals(this.secondTeamGoals.getValue());
		this.pairing.setState(
				this.stateBox.getSelectionModel().getSelectedItem());
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
