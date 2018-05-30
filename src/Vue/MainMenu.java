package Vue;

import javafx.scene.layout.VBox;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.text.*;

public class MainMenu extends VBox {
	Button new_game, regles, quit, load_game;
	
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
		load_game = new Button("Charger une partie");
		regles = new Button("Regles");
		quit = new Button("Quitter");
		
		new_game.getStyleClass().add("textbutton");
		load_game.getStyleClass().add("textbutton");
		regles.getStyleClass().add("textbutton");
		quit.getStyleClass().add("textbutton");
		
		this.getChildren().addAll(	new_game,
									load_game,
									//regles,
									quit);
		
		quit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
			}
		});		
		
	}
}