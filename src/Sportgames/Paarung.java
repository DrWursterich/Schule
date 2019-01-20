package Sportgames;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Pair;
import resource.ResourceManager;

public class Paarung extends VBox implements Serializable {

	class TeamStatistic {
		private final Verein team;
		private IntegerProperty goalsProperty;

		TeamStatistic(final Verein team) {
			this(team, 0);
		}

		TeamStatistic(final Verein team, final int goals) {
			this.team = team;
			this.goalsProperty = new SimpleIntegerProperty(goals);
		}

		public Verein getTeam() {
			return this.team;
		}

		public int getGoals() {
			return this.goalsProperty.get();
		}

		public void setGoals(final int goals) {
			this.goalsProperty.set(goals);
		}

		public IntegerProperty goalsProperty() {
			return this.goalsProperty;
		}
	}

	private static final long serialVersionUID = 7856712790469321920L;
	private static final Paint WINNING_TEAM_COLOR = Color.DARKGREEN;
	private static final Paint LOSING_TEAM_COLOR = Color.DARKRED;
	private static final Paint DRAWING_TEAM_COLOR = Color.BLACK;
	private TeamStatistic firstTeam;
	private TeamStatistic secondTeam;
	private StringProperty location;
	private ObjectProperty<GregorianCalendar> date;
	private ObjectProperty<PairingState> state;
	@FXML
	private Label firstTeamLabel;
	@FXML
	private Label secondTeamLabel;
	@FXML
	private Label firstTeamGoals;
	@FXML
	private Label secondTeamGoals;
	@FXML
	private Label locationLabel;
	@FXML
	private Label dateLabel;

	public Paarung(
			final String location,
			final GregorianCalendar date,
			final Verein firstTeam,
			final Verein secondTeam) {
		this.initialize(location, date, firstTeam, secondTeam);
	}

	private void initialize(
			final String location,
			final GregorianCalendar date,
			final Verein firstTeam,
			final Verein secondTeam) {
		ResourceManager.initResource(this, "Sportgames.Paarung", this);
		this.location = new SimpleStringProperty(location);
		this.date = new SimpleObjectProperty<>(date);
		this.firstTeam = new TeamStatistic(firstTeam);
		this.secondTeam = new TeamStatistic(secondTeam);
		this.state = new SimpleObjectProperty<>(PairingState.NOT_STARTED);
		this.firstTeamLabel.setText(this.getFirstTeam().getName());
		this.secondTeamLabel.setText(this.getSecondTeam().getName());
		this.firstTeamGoals.textProperty().bind(
				StringExpression.stringExpression(
					this.firstTeam.goalsProperty()));
		this.secondTeamGoals.textProperty().bind(
				StringExpression.stringExpression(
					this.secondTeam.goalsProperty()));
		this.locationLabel.textProperty().bind(this.location);
		this.dateLabel.textProperty().bind(
				Bindings.createStringBinding(
					() -> String.format(
						"%s %02d.%02d.%04d at %02d:%02d",
						this.state.get().getLabel(),
						this.date.get().get(Calendar.DATE),
						this.date.get().get(Calendar.MONTH) + 1,
						this.date.get().get(Calendar.YEAR),
						this.date.get().get(Calendar.HOUR_OF_DAY),
						this.date.get().get(Calendar.MINUTE)),
					this.state,
					this.date));
		this.firstTeam.goalsProperty.addListener(e -> this.updateColors());
		this.secondTeam.goalsProperty.addListener(e -> this.updateColors());
		this.setFocusTraversable(true);
		this.setOnMouseClicked(e -> {
			e.consume();
			this.requestFocus();
			if (e.getClickCount() == 2) {
				this.edit();
			}
		});
		this.setOnKeyPressed(e -> {
			switch (e.getCode()) {
				case ENTER:
					this.edit();
					break;
				case DOWN:
					e.consume();
					this.fireEvent(
							new KeyEvent(
								this,
								this,
								KeyEvent.KEY_PRESSED,
								"",
								"",
								KeyCode.TAB,
								false,
								false,
								false,
								false));
					break;
				case UP:
					e.consume();
					this.fireEvent(
							new KeyEvent(
								this,
								this,
								KeyEvent.KEY_PRESSED,
								"",
								"",
								KeyCode.TAB,
								true,
								false,
								false,
								false));
					break;
				default:
					break;
			}
		});
	}

	public String getLocation() {
		return this.location.get();
	}

	public void setLocation(final String location) {
		this.location.set(location);
	}

	public GregorianCalendar getDate() {
		return this.date.get();
	}

	public void setDate(final GregorianCalendar date) {
		this.date.set(date);
	}

	public Verein[] getTeams() {
		return new Verein[] {
				this.firstTeam.getTeam(),
				this.secondTeam.getTeam()};
	}

	public Verein getFirstTeam() {
		return this.firstTeam.getTeam();
	}

	public Verein getSecondTeam() {
		return this.secondTeam.getTeam();
	}

	public Pair<Integer, Integer> getScore() {
		return new Pair<>(
				this.firstTeam.getGoals(),
				this.secondTeam.getGoals());
	}

	public int getFirstTeamGoals() {
		return this.firstTeam.getGoals();
	}

	public int getSecondTeamGoals() {
		return this.secondTeam.getGoals();
	}

	public void setFirstTeamGoals(final int goals) {
		this.firstTeam.setGoals(goals);
	}

	public void setSecondTeamGoals(final int goals) {
		this.secondTeam.setGoals(goals);
	}

	public PairingState getState() {
		return this.state.get();
	}

	public void setState(PairingState state) {
		this.state.set(state);
	}

	public void edit() {
		new PaarungDialog(this).showAndWait();
	}

	@Override
	public String toString() {
		return this.getFirstTeam().getName() + " "
				+ this.getScore().getKey() + ":"
				+ this.getScore().getValue() + " "
				+ this.getSecondTeam().getName();
	}

	private void writeObject(ObjectOutputStream stream)
			throws IOException {
		stream.writeObject(this.getFirstTeam());
		stream.writeObject(this.getSecondTeam());
		stream.writeInt(this.getFirstTeamGoals());
		stream.writeInt(this.getSecondTeamGoals());
		stream.writeObject(this.getLocation());
		stream.writeObject(this.getDate());
		stream.writeObject(this.getState());
	}

	private void readObject(ObjectInputStream stream)
			throws IOException, ClassNotFoundException {
		final Verein firstTeam = (Verein)stream.readObject();
		final Verein secondTeam = (Verein)stream.readObject();
		final int firstTeamGoals = stream.readInt();
		final int secondTeamGoals = stream.readInt();
		final String location = (String)stream.readObject();
		final GregorianCalendar date = (GregorianCalendar)stream.readObject();
		final PairingState state = (PairingState)stream.readObject();
		this.initialize(location, date, firstTeam, secondTeam);
		this.setFirstTeamGoals(firstTeamGoals);
		this.setSecondTeamGoals(secondTeamGoals);
		this.setState(state);
	}

	private void updateColors() {
		if (this.getFirstTeamGoals() > this.getSecondTeamGoals()) {
			this.firstTeamLabel.setTextFill(Paarung.WINNING_TEAM_COLOR);
			this.firstTeamGoals.setTextFill(Paarung.WINNING_TEAM_COLOR);
			this.secondTeamLabel.setTextFill(Paarung.LOSING_TEAM_COLOR);
			this.secondTeamGoals.setTextFill(Paarung.LOSING_TEAM_COLOR);
		} else if (this.getFirstTeamGoals() < this.getSecondTeamGoals()) {
			this.firstTeamLabel.setTextFill(Paarung.LOSING_TEAM_COLOR);
			this.firstTeamGoals.setTextFill(Paarung.LOSING_TEAM_COLOR);
			this.secondTeamLabel.setTextFill(Paarung.WINNING_TEAM_COLOR);
			this.secondTeamGoals.setTextFill(Paarung.WINNING_TEAM_COLOR);
		} else {
			this.firstTeamLabel.setTextFill(Paarung.DRAWING_TEAM_COLOR);
			this.firstTeamGoals.setTextFill(Paarung.DRAWING_TEAM_COLOR);
			this.secondTeamLabel.setTextFill(Paarung.DRAWING_TEAM_COLOR);
			this.secondTeamGoals.setTextFill(Paarung.DRAWING_TEAM_COLOR);
		}
	}
}
