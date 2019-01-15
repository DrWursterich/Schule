package Sportgames.application;

import java.io.IOException;
import java.util.GregorianCalendar;
import Sportgames.Verein;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import resource.ResourceManager;
import resource.ResourceType;

public class Paarung extends HBox {

	class TeamStatistic {
		private final Verein team;
		private int goals;

		TeamStatistic(final Verein team) {
			this.team = team;
			this.goals = 0;
		}

		public Verein getTeam() {
			return this.team;
		}

		public int getGoals() {
			return this.goals;
		}

		public void setGoals(final int goals) {
			this.goals = goals;
		}
	}

	enum State {
		NOT_STARTED("starts "),
		BEEING_PLAYED("started "),
		FINISHED("was played ");

		private final ReadOnlyStringWrapper label;

		State(final String label) {
			this.label = new ReadOnlyStringWrapper(label);
		}

		public String getLabel() {
			return this.label.get();
		}

		public ReadOnlyStringProperty labelProperty() {
			return this.label.getReadOnlyProperty();
		}
	}

	private final TeamStatistic firstTeam;
	private final TeamStatistic secondTeam;
	private final String location;
	private final GregorianCalendar date;
	private State state;
	@FXML
	private Label firstTeamLabel;
	@FXML
	private Label secondTeamLabel;
	@FXML
	private Label TeamSeparationLabel;

	public Paarung(
			final String location,
			final GregorianCalendar date,
			final Verein firstTeam,
			final Verein secondTeam) {
		this.location = location;
		this.date = (GregorianCalendar)date.clone();
		this.firstTeam = new TeamStatistic(firstTeam);
		this.secondTeam = new TeamStatistic(secondTeam);
		this.state = State.NOT_STARTED;
		FXMLLoader loader = new FXMLLoader(
				ResourceManager.loadURL(
					ResourceType.FXML,
					"Sportgames.Paarung"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
			this.getStylesheets().add(
					ResourceManager.loadURL(
						ResourceType.CSS,
						"Sportgames.Paarung").toExternalForm());
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		this.firstTeamLabel.setText(this.getFirstTeam().getName());
		this.secondTeamLabel.setText(this.getSecondTeam().getName());
	}

	public String getLocation() {
		return this.location;
	}

	public GregorianCalendar getDate() {
		return this.date;
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

	public State getState() {
		return this.state;
	}

	@Override
	public String toString() {
		return this.getFirstTeam().getName() + " "
				+ this.getScore().getKey() + ":"
				+ this.getScore().getValue() + " "
				+ this.getSecondTeam().getName();
	}
}
