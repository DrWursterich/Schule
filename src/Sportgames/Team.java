package Sportgames;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Team implements Serializable {
	private static final long serialVersionUID = 1020500889918654466L;
	private StringProperty name;

	protected Team() {
		this.name = new SimpleStringProperty();
	}

	public Team(final String name) {
		this();
		this.name.set(name);
	}

	public String getName() {
		return this.name.get();
	}

	public int getPoints() {
		return Main.getInstance().getPairings().stream().mapToInt(
				e -> PairingState.FINISHED.equals(e.getState())
						&& (this.equals(e.getFirstTeam())
							|| this.equals(e.getSecondTeam()))
					? e.getFirstTeamGoals() != e.getSecondTeamGoals()
						? this.equals(e.getFirstTeam())
							? e.getFirstTeamGoals() > e.getSecondTeamGoals()
								? 3
								: 0
							: e.getFirstTeamGoals() > e.getSecondTeamGoals()
								? 0
								: 3
						: 1
					: 0).sum();
	}

	public int getGames() {
		return Main.getInstance().getPairings().stream().mapToInt(
				e -> PairingState.FINISHED.equals(e.getState())
						&& (this.equals(e.getFirstTeam())
							|| this.equals(e.getSecondTeam()))
					? 1
					: 0).sum();
	}

	public int getVictories() {
		return Main.getInstance().getPairings().stream().mapToInt(
				e -> PairingState.FINISHED.equals(e.getState())
						&& (this.equals(e.getFirstTeam())
							|| this.equals(e.getSecondTeam())
						&& e.getFirstTeamGoals() != e.getSecondTeamGoals())
					? this.equals(e.getFirstTeam())
						? e.getFirstTeamGoals() > e.getSecondTeamGoals()
							? 1
							: 0
						: e.getFirstTeamGoals() > e.getSecondTeamGoals()
							? 0
							: 1
					: 0).sum();
	}

	public int getLosses() {
		return Main.getInstance().getPairings().stream().mapToInt(
				e -> PairingState.FINISHED.equals(e.getState())
						&& (this.equals(e.getFirstTeam())
							|| this.equals(e.getSecondTeam())
						&& e.getFirstTeamGoals() != e.getSecondTeamGoals())
					? this.equals(e.getFirstTeam())
						? e.getFirstTeamGoals() > e.getSecondTeamGoals()
							? 0
							: 1
						: e.getFirstTeamGoals() > e.getSecondTeamGoals()
							? 1
							: 0
					: 0).sum();
	}

	public int getDraws() {
		return Main.getInstance().getPairings().stream().mapToInt(
				e -> PairingState.FINISHED.equals(e.getState())
						&& (this.equals(e.getFirstTeam())
							|| this.equals(e.getSecondTeam()))
						&& e.getFirstTeamGoals() == e.getSecondTeamGoals()
							? 1
							: 0).sum();
	}

	public int getGoals() {
		return Main.getInstance().getPairings().stream().mapToInt(
				e -> PairingState.FINISHED.equals(e.getState())
						&& (this.equals(e.getFirstTeam())
							|| this.equals(e.getSecondTeam()))
					? this.equals(e.getFirstTeam())
						? e.getFirstTeamGoals()
						: e.getSecondTeamGoals()
					: 0).sum();
	}

	public int getConsidingGoals() {
		return Main.getInstance().getPairings().stream().mapToInt(
				e -> PairingState.FINISHED.equals(e.getState())
				&& (this.equals(e.getFirstTeam())
						|| this.equals(e.getSecondTeam()))
				? this.equals(e.getFirstTeam())
					? e.getSecondTeamGoals()
					: e.getFirstTeamGoals()
				: 0).sum();
	}

	public int getGoalDifference() {
		return Main.getInstance().getPairings().stream().mapToInt(
				e -> PairingState.FINISHED.equals(e.getState())
				&& (this.equals(e.getFirstTeam())
						|| this.equals(e.getSecondTeam()))
				? this.equals(e.getFirstTeam())
					? e.getFirstTeamGoals() - e.getSecondTeamGoals()
					: e.getSecondTeamGoals() - e.getFirstTeamGoals()
				: 0).sum();
	}

	@Override
	public boolean equals(final Object other) {
		return other != null && other instanceof Team
				? this.name.get().equals(((Team)other).name.get())
				: false;
	}

	@Override
	public String toString() {
		return this.getName();
	}

	private void writeObject(final ObjectOutputStream out) throws IOException {
		out.writeObject(this.getName());
	}

	private void readObject(final ObjectInputStream in)
			throws IOException, ClassNotFoundException {
		final Object read = in.readObject();
		if (!(read instanceof String)) {
			throw new IOException("Unable to read Instance of Team");
		}
		if (this.name == null) {
			this.name = new SimpleStringProperty();
		}
		this.name.set((String)read);
	}
}
