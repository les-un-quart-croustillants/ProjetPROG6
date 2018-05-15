package Vue;

import javafx.scene.layout.*;
import java.util.LinkedList;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.text.*;

public class ConfigMenu extends VBox {
	public class JoueurConfig extends HBox {
		JoueurConfig objet = this;
		public Label name, nbPenguin;
		public Button typeJoueur, difficulte, minusPenguin, plusPenguin, delete;
		public int nb_Penguin;
		
		public JoueurConfig(int nb_joueur, VBox parent) {
			this.getStyleClass().add("joueurconfig");
			VBox joueurLyt = new VBox();
			name = new Label("Joueur "+nb_joueur);
			typeJoueur = new Button("Humain");
			difficulte = new Button("Facile");
			nbPenguin = new Label("x"+nb_Penguin);
			minusPenguin = new Button();
			delete = new Button("x");
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
			
			joueurLyt.getChildren().add(typeJoueur);
			this.getChildren().addAll(delete, name, joueurLyt, minusPenguin, nbPenguin, plusPenguin);
		}
	}
	
	private static ConfigMenu instance = null;
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
		Label dimensions = new Label("DIMENSIONS DU PLATEAU");
		Label widthLbl = new Label("Largeur");
		Label heightLbl = new Label("Hauteur");
		Label widthLblNb = new Label("6");
		Label heightLblNb = new Label("7");
		HBox dimensionsBox = new HBox();
		Label configLbl = new Label("CONFIG.");
		ScrollPane joueursPane = new ScrollPane();
		VBox listJoueurs = new VBox();
		Button newJoueur = new Button("Nouveau joueur");
		
		retour = new Button("Retour");
		
		// Configuration
		configLbl.getStyleClass().add("title");
		retour.getStyleClass().add("textbutton"); 
		joueursPane.setContent(listJoueurs);
		
		newJoueur.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				listJoueurs.getChildren().remove(newJoueur);
				listJoueurs.getChildren().add(new JoueurConfig(1, listJoueurs));
				listJoueurs.getChildren().add(newJoueur);
			}
		});
		
		// Ajout
		listJoueurs.getChildren().addAll(new JoueurConfig(1, listJoueurs), 
				new JoueurConfig(2, listJoueurs), 
				new JoueurConfig(3, listJoueurs), 
				new JoueurConfig(4, listJoueurs), 
				newJoueur);
		
		dimensionsBox.getChildren().addAll(widthLbl, widthLblNb, heightLbl, heightLblNb);
		this.getChildren().addAll(configLbl, joueursPane, dimensions, dimensionsBox, retour);
	}
}