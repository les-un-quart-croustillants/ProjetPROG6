package Vue;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.text.*;

public class NewGameMenu extends VBox {
	private static NewGameMenu instance = null;
	Button config, jouer, retour, leftMap, rightMap, mapButton;
	Label mapName;
	
	public static NewGameMenu getInstance() {
		if(instance == null)
			instance = new NewGameMenu();
		return instance;
	}
	
	private NewGameMenu() {
		Font.loadFont(getClass().getResourceAsStream("LuckiestCuy.ttf"), 14);
		this.getStyleClass().add("menu");
		create_elements();
	}
	
private void create_elements() {
		this.getStyleClass().add("menu");
		// Allocations
		config = new Button("Config.");
		jouer = new Button("Jouer");
		retour = new Button("Retour");
		leftMap = new Button();
		rightMap = new Button();
		mapButton = new Button();
		VBox playAndBackButtons = new VBox();
		mapName = new Label("BANQUISE");
		HBox mapSelection = new HBox();
		
		
		config.getStyleClass().addAll("textbutton", "smallerbtn");
		jouer.getStyleClass().add("textbutton");
		retour.getStyleClass().addAll("textbutton", "smallerbtn");
		
		mapButton.getStyleClass().addAll("mapbutton");
		
		leftMap.getStyleClass().addAll("iconbutton", "leftbutton");
		rightMap.getStyleClass().addAll("iconbutton", "rightbutton");
		
		mapSelection.setAlignment(Pos.CENTER);
		mapSelection.getStyleClass().add("mapselection");
		mapSelection.getChildren().add(leftMap);
		mapSelection.getChildren().add(mapButton);
		mapSelection.getChildren().add(rightMap);
		
		playAndBackButtons.getStyleClass().add("playandback");
		playAndBackButtons.getChildren().add(jouer);
		playAndBackButtons.getChildren().add(retour);
		
		this.getChildren().add(mapName);
		this.getChildren().add(config);
		this.getChildren().add(mapSelection);
		this.getChildren().add(playAndBackButtons);
		
		retour.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
			}
		});
	}
}