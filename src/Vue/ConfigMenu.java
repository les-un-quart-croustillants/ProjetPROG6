package Vue;

import javafx.scene.layout.*;
import java.util.LinkedList;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.text.*;
import Utils.GameConfig;

public class ConfigMenu extends VBox {
	public class JoueurConfig extends HBox {
		JoueurConfig objet = this;
		public Label nbPenguin;
		public Button typeJoueur, difficulte, minusPenguin, plusPenguin, delete;
		public int nb_Penguin = 1;
		
		GameConfig.ConfigJoueur getConfig() {
			GameConfig.TypeJoueur t;
			GameConfig.difficulte d;
			
			if(typeJoueur.getText() == "Humain") {
				t = GameConfig.TypeJoueur.HUMAIN;
			} else {
				t = GameConfig.TypeJoueur.IA;
			}
			
			if(difficulte.getText() == "Facile") {
				d = GameConfig.difficulte.FACILE;
			} else if(difficulte.getText() == "Moyen") {
				d = GameConfig.difficulte.MOYEN;
			} else {
				d = GameConfig.difficulte.DIFFICILE;
			}
			
			return new GameConfig.ConfigJoueur(t, d, nb_Penguin);
		}
		
		public JoueurConfig(VBox parent) {
			this.getStyleClass().add("joueurconfig");
			VBox joueurLyt = new VBox();
			typeJoueur = new Button("Humain");
			difficulte = new Button("Facile");
			nbPenguin = new Label("x"+nb_Penguin);
			minusPenguin = new Button();
			delete = new Button();
			delete.getStyleClass().add("closebtn");
			plusPenguin = new Button();
			
			minusPenguin.getStyleClass().addAll("iconbutton", "leftbuttonsmall");
			plusPenguin.getStyleClass().addAll("iconbutton", "rightbuttonsmall");
			
			minusPenguin.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					nb_Penguin = nb_Penguin-1 <= 1 ? 1 : nb_Penguin-1;
					nbPenguin.setText("x"+nb_Penguin); 
				} 
			});
			
			delete.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					parent.getChildren().remove(objet); 
				} 
			});
			
			plusPenguin.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					nb_Penguin = nb_Penguin+1 >= 4 ? 4 : nb_Penguin+1;
					nbPenguin.setText("x"+nb_Penguin);
				}
			});
			
			typeJoueur.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					if(typeJoueur.getText() == "Humain") {
						typeJoueur.setText("IA");
						objet.getChildren().add(2, difficulte);
					} else {
						typeJoueur.setText("Humain");
						objet.getChildren().remove(difficulte);
					}
				}
			});
			
			difficulte.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					if(difficulte.getText() == "Facile") {
						difficulte.setText("Moyen");
					} else if(difficulte.getText() == "Moyen") {
						difficulte.setText("Difficile");
					} else {
						difficulte.setText("Facile");
					}
				}
			});
			
			joueurLyt.getChildren().add(typeJoueur);
			this.getChildren().addAll(delete, joueurLyt, minusPenguin, nbPenguin, plusPenguin);
		}
	}
	
	private static ConfigMenu instance = null;
	Integer dim = 8;
	VBox listJoueurs;
	Button retour;
	
	public static ConfigMenu getInstance() {
		if(instance == null)
			instance = new ConfigMenu();
		return instance;
	}
	
	private ConfigMenu() {
		Font.loadFont(getClass().getResourceAsStream("LuckiestCuy.ttf"), 14);
		this.getStyleClass().add("menu");
		create_elements();
	}
	
	private void create_elements() {
		// Allocations
		Label dimLbl = new Label("Dimension du plateau");
		Label dimLblNb = new Label(dim.toString());
		Label x = new Label("x");
		Label dimLblNb2 = new Label(dim.toString());
		HBox dimensionsBox = new HBox();
		listJoueurs = new VBox();
		Label configLbl = new Label("CONFIG.");
		ScrollPane joueursPane = new ScrollPane();
		Button newJoueur = new Button("Nouveau joueur");
		Button minusDim = new Button();
		Button plusDim = new Button();
		
		retour = new Button("Retour");
		
		// Configuration
		dimensionsBox.getStyleClass().add("hbox");
		listJoueurs.getStyleClass().add("center");
		newJoueur.getStyleClass().addAll("textbutton", "smallerbtn");
		configLbl.getStyleClass().add("title");
		retour.getStyleClass().add("textbutton"); 
		joueursPane.setContent(listJoueurs);
		minusDim.getStyleClass().addAll("leftbutton", "iconbutton");
		plusDim.getStyleClass().addAll("rightbutton", "iconbutton");
		
		plusDim.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				dim++;
				dimLblNb.setText(dim.toString());
				dimLblNb2.setText(dim.toString());
			}
		});
		
		minusDim.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				dim = dim-1 <= 0 ? 1 : dim-1;
				dimLblNb.setText(dim.toString());
				dimLblNb2.setText(dim.toString());
			}
		});
		
		newJoueur.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				listJoueurs.getChildren().add(new JoueurConfig(listJoueurs));
				joueursPane.setVvalue(1.0);
			}
		});
		
		// Ajout
		listJoueurs.getChildren().addAll(new JoueurConfig(listJoueurs), 
				new JoueurConfig(listJoueurs), 
				new JoueurConfig(listJoueurs), 
				new JoueurConfig(listJoueurs));
		
		dimensionsBox.getChildren().addAll(dimLbl, new Label(), minusDim, dimLblNb, x, dimLblNb2, plusDim);
		this.getChildren().addAll(configLbl, joueursPane, newJoueur, dimensionsBox, retour);
	}
	
	GameConfig create_config() {
		GameConfig gc = new GameConfig(dim);
		for(Node jc : listJoueurs.getChildren()) {
			gc.joueurs.add(((JoueurConfig)jc).getConfig());
		}
		return gc;
	}
}