package Vue;

import javafx.scene.layout.*;
import Modele.Moteur.*;
import Modele.Joueurs.*;
import Modele.Plateau.*;
import Utils.GameConfig;
import Vue.Pane.GamePane;

import java.util.*;
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
	
	private Plateau create_plateau() {
		Plateau p = new Plateau(TerrainMenu.getInstance().dim, ConfigMenu.getInstance().create_config().nb_pingouins());
		return p;
	}
	
	private ArrayList<Joueur> create_joueurs() {
		GameConfig gc = ConfigMenu.getInstance().create_config();
		ArrayList<Joueur> j = new ArrayList<Joueur>();
		int ids = 0;
		for(GameConfig.ConfigJoueur cj : gc.joueurs) {
			if(cj.type == GameConfig.TypeJoueur.HUMAIN) {
				j.add(new JoueurPhysique(ids, cj.nb_pingouins, cj.name));
			} else {
				Joueur.Difficulte d;
				switch(cj.difficulte_ia) {
				case FACILE:	d = Joueur.Difficulte.FACILE;		break;
				case MOYEN:		d = Joueur.Difficulte.MOYEN;		break;
				case DIFFICILE:	d = Joueur.Difficulte.DIFFICILE;	break;
				default:		d = Joueur.Difficulte.PHYSIQUE;		break; // inaccessible
				}
				j.add(new JoueurIA(ids, cj.nb_pingouins, cj.name, d));
			}
			ids++;
		}
		return j;
	}
	
	private void button_behaviour() {
		mainMenuBehaviour();
		newGameBehaviour();
		configMenuBehaviour();
		terrainMenuBehaviour();
	}
	
	private void terrainMenuBehaviour() {
		TerrainMenu.getInstance().retour.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				instance.getChildren().remove(TerrainMenu.getInstance());
			}
		});
		
		TerrainMenu.getInstance().jouer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				GamePane.newInstance(new Moteur(create_plateau(), create_joueurs()));
				mApp.transition(Action.NOUVELLE_PARTIE);
				ig.graphic_state();
			}
		});
	}
	
	private void configMenuBehaviour() {
		ConfigMenu.getInstance().retour.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				instance.getChildren().remove(ConfigMenu.getInstance());
			}
		});
		
		ConfigMenu.getInstance().jouer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				GamePane.newInstance(new Moteur(create_plateau(), create_joueurs()));
				mApp.transition(Action.NOUVELLE_PARTIE);
				ig.graphic_state();
			}
		});
		
		ConfigMenu.getInstance().map_customization.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				instance.getChildren().add(TerrainMenu.getInstance());
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
				GamePane.newInstance(new Moteur(create_plateau(), create_joueurs()));
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