package Sportgames.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Sportgames.Verein;
import Sportgames.controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import resource.ResourceManager;

public class Main extends Application {
	private static Main instance;
	private Stage stage;

	public static Main getInstance() {
		return Main.instance;
	}

	public static void main(final String...args) {
		ResourceManager.setResourcePath("resources");
		Application.launch(args);
	}

	@Override
	public void start(final Stage stage) throws Exception {
		Main.instance = this;
		this.stage = stage;
		stage.setScene(
				new Scene(ResourceManager.getResource("Sportgames.Main")));
		stage.setTitle("Sportgames");
		stage.show();
	}

	public Stage getStage() {
		return this.stage;
	}

	public List<Verein> getAssociations() {
		try {
			return ResourceManager.getController(
					"Sportgames.Main", MainController.class).getAssociations();
		} catch (final IOException e) {
			return new ArrayList<>();
		}
	}

	public List<Paarung> getPairings() {
		try {
			return ResourceManager.getController(
					"Sportgames.Main", MainController.class).getPairings();
		} catch (final IOException e) {
			return new ArrayList<>();
		}
	}

	public void refreshTable() {
		try {
			ResourceManager.getController(
					"Sportgames.Main", MainController.class).refreshTable();
		} catch (final IOException e) {}
	}
}
