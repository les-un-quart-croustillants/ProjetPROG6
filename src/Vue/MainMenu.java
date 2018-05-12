package Vue;

import javafx.scene.layout.VBox;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.text.*;

public class MainMenu extends VBox {
	private static MainMenu instance = null;

	public static MainMenu getInstance() {
		if (instance == null)
			instance = new MainMenu();
		return instance;
	}

	private MainMenu() {
		Font.loadFont(getClass().getResourceAsStream("LuckiestCuy.ttf"), 14);
		this.getStyleClass().add("menu");
		this.getStylesheets().add("banquise.css");
		create_buttons();
	}

	private void create_buttons() {
		// Allocations
		Button new_game = new Button("Nouvelle partie");
		Button highscores = new Button("Scores");
		Button quit = new Button("Quitter");

		this.getChildren().add(new_game);
		this.getChildren().add(highscores);
		this.getChildren().add(quit);

		quit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
			}
		});
	}
}