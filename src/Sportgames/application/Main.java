package Sportgames.application;

import Sportgames.Verein;
import Sportgames.controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import resource.ResourceManager;

public class Main extends Application {
	public static void main(final String...args) {
		ResourceManager.setResourcePath("resources");
		Application.launch(args);
	}

	@Override
	public void start(final Stage stage)
			throws Exception {
		stage.setScene(
				new Scene(ResourceManager.getResource("Sportgames.Main")));
		final MainController mainController = ResourceManager.getController(
				"Sportgames.Main", MainController.class);
		mainController.addAssociations(new Verein("Verein 1"));
		stage.setTitle("Sportgames");
		stage.show();
	}
}
