package Vue;

import javafx.scene.layout.*;
import javafx.event.*;
import Modele.Moteurs.*;
import Modele.Moteurs.MoteurApp.*;

public class Menu extends StackPane {
	private static Menu instance = null;
	private static MoteurApp mApp;
	private static InterfaceGraphique ig;
	
	public static Menu getInstance() {
		if(instance == null)
			instance = new Menu();
		return instance;
	}
	
	public static void setMoteurApp(MoteurApp mApp) {
		System.out.println("moteur ajouté");
		Menu.mApp = mApp;
	}
	
	public static void setInterfaceGraphique(InterfaceGraphique ig) {
		Menu.ig = ig;
	}
	
	private Menu() {		
		this.getStylesheets().add("menus.css");
		this.getStylesheets().add("banquise.css");
		this.getChildren().add(MainMenu.getInstance());
		button_behaviour();
	}
	
	private void button_behaviour() {
		mainMenuBehaviour();
		newGameBehaviour();
		configMenuBehaviour();
	}
	
	private void configMenuBehaviour() {
		ConfigMenu.getInstance().retour.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				instance.getChildren().remove(ConfigMenu.getInstance());
				System.out.println(ConfigMenu.getInstance().create_config());
			}
		});
	}
	
	private void mainMenuBehaviour() {
		MainMenu.getInstance().new_game.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				instance.getChildren().add(NewGameMenu.getInstance());
			}
		});
		
		MainMenu.getInstance().highscores.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				
			}
		});
		
		MainMenu.getInstance().quit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				mApp.transition(Action.QUITTER_APPLI);
				ig.graphic_state();
			}
		});
		
		MainMenu.getInstance().quickgame.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				mApp.transition(Action.NOUVELLE_PARTIE);
				ig.graphic_state();
			}
		});
	}
	
	private void newGameBehaviour() {
		NewGameMenu.getInstance().leftMap.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if(instance.getStylesheets().contains("enfer.css")) {
					instance.getStylesheets().remove("enfer.css");
					NewGameMenu.getInstance().mapName.setText("BANQUISE");
					instance.getStylesheets().add("banquise.css");
				} else if(instance.getStylesheets().contains("banquise.css")) {
					instance.getStylesheets().remove("banquise.css");
					NewGameMenu.getInstance().mapName.setText("ENFER");
					instance.getStylesheets().add("enfer.css");
				}
			}
		});
		
		NewGameMenu.getInstance().rightMap.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if(instance.getStylesheets().contains("enfer.css")) {
					instance.getStylesheets().remove("enfer.css");
					NewGameMenu.getInstance().mapName.setText("BANQUISE");
					instance.getStylesheets().add("banquise.css");
				} else if(instance.getStylesheets().contains("banquise.css")) {
					instance.getStylesheets().remove("banquise.css");
					NewGameMenu.getInstance().mapName.setText("ENFER");
					instance.getStylesheets().add("enfer.css");
				}
			}
		});
		
		NewGameMenu.getInstance().config.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				instance.getChildren().add(ConfigMenu.getInstance());
			}
		});
		
		NewGameMenu.getInstance().jouer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				mApp.transition(Action.NOUVELLE_PARTIE);
				ig.graphic_state();
			}
		});
		
		NewGameMenu.getInstance().retour.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				instance.getChildren().remove(NewGameMenu.getInstance());
			}
		});
	}
}