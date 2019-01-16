package Sportgames.application;

import java.util.GregorianCalendar;
import Sportgames.Verein;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import resource.ResourceManager;

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
	private final State state;
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
		ResourceManager.initResource(this, "Sportgames.Paarung", true);
		this.location = location;
		this.date = (GregorianCalendar)date.clone();
		this.firstTeam = new TeamStatistic(firstTeam);
		this.secondTeam = new TeamStatistic(secondTeam);
		this.state = State.NOT_STARTED;
		this.firstTeamLabel.setText(this.getFirstTeam().getName());
		this.secondTeamLabel.setText(this.getSecondTeam().getName());
		this.setFocusTraversable(true);
		this.setOnMouseClicked(e -> {
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

	public void edit() {
		System.out.println("editing");
	}

	@Override
	public String toString() {
		return this.getFirstTeam().getName() + " "
				+ this.getScore().getKey() + ":"
				+ this.getScore().getValue() + " "
				+ this.getSecondTeam().getName();
	}
}
