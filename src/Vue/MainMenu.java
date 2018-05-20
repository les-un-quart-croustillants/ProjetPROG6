package Vue;

import javafx.scene.layout.VBox;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.text.*;

public class MainMenu extends VBox {
	Button new_game, highscores, quit;
	
	private static MainMenu instance = null;
	
	public static MainMenu getInstance() {
		if(instance == null)
			instance = new MainMenu();
		return instance;
	}
	
	private MainMenu() {
		Font.loadFont(getClass().getResourceAsStream("LuckiestCuy.ttf"), 14);
		this.getStyleClass().add("menu");
		create_buttons();
	}
	
	private void create_buttons() {
		// Allocations
		new_game = new Button("Nouvelle partie");
		highscores = new Button("Scores");
		quit = new Button("Quitter");
		
		new_game.getStyleClass().add("textbutton");
		highscores.getStyleClass().add("textbutton");
		quit.getStyleClass().add("textbutton");
		
		this.getChildren().add(new_game);
		this.getChildren().add(highscores);
		this.getChildren().add(quit);
		
		quit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
			}
		});		
	}
}