package Sportgames.application;

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
		final MainController mainController = ResourceManager.getController(
				"Sportgames.Main", MainController.class);
		stage.setTitle("Sportgames");
		stage.show();
	}

	public Stage getStage() {
		return this.stage;
	}
}
