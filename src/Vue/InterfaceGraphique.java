package Vue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Vue.Pane.GamePane;

public class InterfaceGraphique extends Application {

	public static void creer(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Scene jeu = new Scene(GamePane.getInstance(), 1000, 800);
		primaryStage.setScene(jeu);
		primaryStage.show();
		primaryStage.setMinHeight(400);
		primaryStage.setMinWidth(600);
	}

}
