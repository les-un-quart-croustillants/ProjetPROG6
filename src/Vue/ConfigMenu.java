package Vue;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.*;
import Utils.GameConfig;
import javafx.scene.image.ImageView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ConfigMenu extends VBox {
	public boolean editFlag = false;	
	private static ConfigMenu instance = null;
	VBox listJoueurs;
	Button retour, jouer, newJoueur, charger;
	TriSlider proportions_pingouins;
	Integer nbP1, nbP2, nbP3;
	Label lblS1, lblS2, lblS3;
	Integer dim = 8;
	HBox terrainLayout;
	RadioButton rb_config, rb_load;
	FileChooser loadChooser;
	ToggleGroup tg;
	File terrainCharge;
	VBox configTerrain;
	VBox loadTerrain;
	
	public class JoueurConfig extends HBox {
		JoueurConfig objet = this;
		public boolean type_editted = false;
		public Label nbPenguin;
		public Button difficulte, minusPenguin, plusPenguin, delete;
		public TextField typeJoueur;
		private GameConfig.TypeJoueur type_joueur = GameConfig.TypeJoueur.HUMAIN;
		private GameConfig.difficulte diff_IA = GameConfig.difficulte.FACILE;
		private int nb_Penguin = 2;
		
		GameConfig.ConfigJoueur getConfig() {
			String name = typeJoueur.getText();
			if(type_joueur == GameConfig.TypeJoueur.IA && name.equals("IA")) {
				name += " " + diff_IA.toString();
			}
			return new GameConfig.ConfigJoueur(type_joueur, diff_IA, nb_Penguin, name);
		}
		
		public void editNbPenguins(int newnb) {
			nb_Penguin = newnb >= 4 ? 4 : newnb <= 0 ? 1 : newnb;
			nbPenguin.setText("x"+nb_Penguin);
			if(nb_Penguin == 4)
				plusPenguin.setVisible(false);
			else
				plusPenguin.setVisible(true);
			if(nb_Penguin == 1)
				minusPenguin.setVisible(false);
			else
				minusPenguin.setVisible(true);
		}
		
		public void editPlayerType(GameConfig.TypeJoueur type) {
			type_joueur = type;
			if(type_joueur == GameConfig.TypeJoueur.HUMAIN) {
				typeJoueur.setText(type_joueur.toString());
				if(difficulte.isVisible())
					difficulte.setVisible(false);
			} else {
					typeJoueur.setText(type_joueur.toString());
					if(!difficulte.isVisible())
						difficulte.setVisible(true);		
			}
		}
		
		public void editIA(GameConfig.difficulte diff) {
			diff_IA = diff;
			switch(diff_IA) {
			case FACILE:	difficulte.setText(diff_IA.toString());		break;
			case MOYEN:		difficulte.setText(diff_IA.toString());		break;
			case DIFFICILE:	difficulte.setText(diff_IA.toString());	break;
			default: break;
			}
		}
		
		public JoueurConfig(VBox parent) {
			type_joueur = GameConfig.TypeJoueur.HUMAIN;
			diff_IA = GameConfig.difficulte.FACILE;
			this.getStyleClass().add("joueurconfig");
			HBox joueurLyt = new HBox();
			typeJoueur = new TextField(type_joueur.toString());
			difficulte = new Button(diff_IA.toString());
			difficulte.setVisible(false);
			nbPenguin = new Label("x"+nb_Penguin);
			minusPenguin = new Button();
			delete = new Button();
			delete.getStyleClass().add("closebtn");
			plusPenguin = new Button();
			
			minusPenguin.getStyleClass().addAll("iconbutton", "leftbuttonsmall");
			plusPenguin.getStyleClass().addAll("iconbutton", "rightbuttonsmall");
			typeJoueur.getStyleClass().addAll("nameplayer");
			typeJoueur.setEditable(false);
			typeJoueur.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);
			
			minusPenguin.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					objet.type_editted = true;
					editNbPenguins(nb_Penguin-1);
				} 
			});
			
			delete.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					parent.getChildren().remove(objet); 
					if(!ConfigMenu.getInstance().editFlag)
						ConfigMenu.getInstance().normalize();
					instance.update_list_joueurs_elements();
				} 
			});
			
			plusPenguin.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					objet.type_editted = true;
					editNbPenguins(nb_Penguin+1);
				}
			});
			
			typeJoueur.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					if(e.getButton() == MouseButton.SECONDARY) {
						typeJoueur.setEditable(true);
						typeJoueur.requestFocus();
					} else {
						objet.type_editted = true;
						if(type_joueur == GameConfig.TypeJoueur.HUMAIN) {
							editPlayerType(GameConfig.TypeJoueur.IA);
						} else {
							editPlayerType(GameConfig.TypeJoueur.HUMAIN);
						}
					}
				}
			});
			
			typeJoueur.setOnKeyPressed(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent e) {
					if(e.getCode() == KeyCode.ENTER) {
						typeJoueur.setEditable(false);
					} else {
						objet.type_editted = true;
					}
				}
			});
			
			difficulte.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					objet.type_editted = true;
					switch(diff_IA) {
					case FACILE:	editIA(GameConfig.difficulte.MOYEN);	break;
					case MOYEN:		editIA(GameConfig.difficulte.DIFFICILE);break;
					case DIFFICILE:	editIA(GameConfig.difficulte.FACILE);	break;
					default: break;
					}
				}
			});
			
			joueurLyt.getChildren().addAll(typeJoueur, difficulte);
			this.getChildren().addAll(delete, joueurLyt, minusPenguin, nbPenguin, plusPenguin);
		}
	}
	
	public static ConfigMenu getInstance() {
		if(instance == null)
			instance = new ConfigMenu();
		return instance;
	}
	
	private ConfigMenu() {
		this.getStyleClass().add("menu");
		create_elements();
		create_elements_terrain();
		this.getChildren().addAll(jouer, retour);
		
	}
	
	private void normalize() {
		// Nb pingouins
		int size = listJoueurs.getChildren().size();
		int nbpenguins;
		if(size == 3)
			nbpenguins = 3;
		else if(size == 4)
			nbpenguins = 2;
		else
			nbpenguins = 4;
		// Type de joueur
		//if(Menu.getInstance().getStylesheets())
		for(Node jc : listJoueurs.getChildren()) {
			if(!((JoueurConfig)jc).type_editted) {
				((JoueurConfig)jc).editNbPenguins(nbpenguins);
			}
		}
		update_proportions();
	}
	
	private void update_list_joueurs_elements() {
		if(listJoueurs.getChildren().size() == 8) {
			newJoueur.setVisible(false);
		} else {
			newJoueur.setVisible(true);
			if(listJoueurs.getChildren().size() == 1)
				((JoueurConfig)listJoueurs.getChildren().get(0)).delete.setVisible(false);
			else
				((JoueurConfig)listJoueurs.getChildren().get(0)).delete.setVisible(true);
		}
	}
	
	private void create_elements() {
		// Allocations
		listJoueurs = new VBox();
		Label configLbl = new Label("CONFIG.");
		ScrollPane joueursPane = new ScrollPane();
		newJoueur = new Button("Nouveau joueur");
		Button minusDim = new Button();
		Button plusDim = new Button();
		VBox joueursBox = new VBox();
	
		retour = new Button("Retour");
		jouer = new Button("JOUER");
		
		// Configuration
		joueursBox.getStyleClass().add("center");
		jouer.getStyleClass().add("textbutton");
		listJoueurs.getStyleClass().add("center");
		newJoueur.getStyleClass().addAll("textbutton", "smallerbtn", "newplayerbutton");
		configLbl.getStyleClass().add("title");
		retour.getStyleClass().addAll("textbutton", "smallerbtn");
		joueursPane.setContent(listJoueurs);
		minusDim.getStyleClass().addAll("leftbutton", "iconbutton");
		plusDim.getStyleClass().addAll("rightbutton", "iconbutton");
		
		newJoueur.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				listJoueurs.getChildren().add(new JoueurConfig(listJoueurs));
				joueursPane.setVvalue(1.0);
				if(!editFlag)
					normalize();
				instance.update_list_joueurs_elements();
			}
		});
		
		// Ajout
		listJoueurs.getChildren().add(new JoueurConfig(listJoueurs));
		for(int i = 0; i < 3; ++i) {
			JoueurConfig jc = new JoueurConfig(listJoueurs);
			jc.editPlayerType(GameConfig.TypeJoueur.IA);
			listJoueurs.getChildren().add(jc);
			
		}
		
		joueursBox.getChildren().addAll(joueursPane, newJoueur);
		this.getChildren().addAll(configLbl, joueursBox);
	}
	
	private void create_elements_terrain() {
		tg = new ToggleGroup();
		Label terrainChargeLbl = new Label();
		terrainLayout = new HBox();
		VBox configTerrain = new VBox();
		VBox loadTerrain = new VBox();
		charger = new Button("Charger un terrain");
		proportions_pingouins = new TriSlider();
		Label mapDim = new Label("Dimensions");
		Label LblDim1 = new Label(dim.toString());
		Label repartitionPoissons = new Label("Repartition des poissons");
		Label x = new Label("x");
		Label LblDim2 = new Label(dim.toString());
		HBox dimBox = new HBox();
		Button plusDim = new Button();
		Button minusDim = new Button();
		rb_config = new RadioButton("Configuration manuelle");
		rb_load = new RadioButton("Terrain chargé");
		loadChooser = new FileChooser();
		loadChooser.setTitle("Choisissez un fichier de terrain");
		
		// Configuration
		terrainChargeLbl.getStyleClass().add("smaller");
		rb_load.setToggleGroup(tg);
		rb_load.setDisable(true);
		rb_config.setToggleGroup(tg);
		charger.getStyleClass().addAll("smallerbtn", "textbutton");
		retour.getStyleClass().addAll("textbutton", "smallerbtn");
		repartitionPoissons.getStyleClass().add("smaller");
		configTerrain.getStyleClass().addAll("center", "terrain");
		jouer.getStyleClass().add("textbutton");
		mapDim.getStyleClass().addAll("smaller");
		LblDim1.getStyleClass().addAll("smaller");
		terrainLayout.getStyleClass().addAll("terrainlayout");
		LblDim2.getStyleClass().addAll("smaller");
		x.getStyleClass().addAll("smaller");
		dimBox.getStyleClass().add("center");
		loadTerrain.getStyleClass().addAll("center", "terrain");
		plusDim.getStyleClass().addAll("iconbutton", "rightbuttonsmall");
		minusDim.getStyleClass().addAll("iconbutton", "leftbuttonsmall");
		
		plusDim.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				dim = dim+1 > 10 ? 10 : dim + 1;
				LblDim1.setText(dim.toString());
				LblDim2.setText(dim.toString());
				update_proportions();
			}
		});
		
		minusDim.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				dim = ((dim-1)*(dim-1)-Double.valueOf(Math.ceil((dim-1)/2)).intValue()) < ConfigMenu.getInstance().create_config().nb_pingouins() ? dim : dim-1;
				LblDim1.setText(dim.toString());
				LblDim2.setText(dim.toString());
				update_proportions();
			}
		});
		
		charger.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				terrainCharge = loadChooser.showOpenDialog(InterfaceGraphique.stage);
				if(terrainCharge != null) {
					terrainChargeLbl.setText(terrainCharge.getName());
					tg.selectToggle(rb_load);
					rb_load.setDisable(false);
				} else {
					tg.selectToggle(rb_config);
					rb_load.setDisable(true);
				}
			}
		});
		
		// TriSlider
		lblS1 = new Label();
		lblS2 = new Label();
		lblS3 = new Label();
		ImageView p1 = new ImageView();
		ImageView p2 = new ImageView();
		ImageView p3 = new ImageView();
		
		p1.getStyleClass().addAll("onepoisson");
		p2.getStyleClass().addAll("twopoisson");
		p3.getStyleClass().addAll("threepoisson");
		
		lblS1.getStyleClass().add("smaller");
		lblS2.getStyleClass().add("smaller");
		lblS3.getStyleClass().add("smaller");
		
		proportions_pingouins.getDividers().get(0).positionProperty().addListener((observable, oldvalue, newvalue) -> {
			update_proportions();
		});
		
		proportions_pingouins.getDividers().get(1).positionProperty().addListener((observable, oldvalue, newvalue) -> {
			update_proportions();
		});
		
		tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
			   if(tg.getSelectedToggle() != null && new_toggle.equals(rb_config)) {
				   configTerrain.getStyleClass().add("terrainchoix");
				   loadTerrain.getStyleClass().remove("terrainchoix");
			   } else {
				   configTerrain.getStyleClass().remove("terrainchoix");
				   loadTerrain.getStyleClass().add("terrainchoix");
			   }
			}
		});
		
		proportions_pingouins.getFirstSlice().getChildren().addAll(lblS1, p1);
		proportions_pingouins.getSecondSlice().getChildren().addAll(lblS2, p2);
		proportions_pingouins.getThirdSlice().getChildren().addAll(lblS3, p3);
		
		dimBox.getChildren().addAll(mapDim, minusDim, LblDim1, x, LblDim2, plusDim);
		configTerrain.getChildren().addAll(rb_config, dimBox, repartitionPoissons, proportions_pingouins);
		loadTerrain.getChildren().addAll(rb_load, charger, terrainChargeLbl);
		terrainLayout.getChildren().addAll(configTerrain, loadTerrain);
		this.getChildren().add(terrainLayout);
		
		tg.selectToggle(rb_config);
	}
	
	GameConfig create_config() {
		GameConfig gc = new GameConfig(dim);
		for(Node jc : listJoueurs.getChildren()) {
			gc.joueurs.add(((JoueurConfig)jc).getConfig());
		}
		return gc;
	}
	
	private void update_proportions() {
		Integer nbCases = (dim*dim-Double.valueOf(Math.ceil(dim/2)).intValue());
		Double reste = .0;
		Double minPosition = ConfigMenu.getInstance().create_config().nb_pingouins().doubleValue()/nbCases;
		if(proportions_pingouins.getDividers().get(0).getPosition() < minPosition)
			proportions_pingouins.getDividers().get(0).setPosition(minPosition);
		nbP1 = Double.valueOf(Math.round(proportions_pingouins.getDividers().get(0).getPosition()*nbCases)).intValue();
		reste = proportions_pingouins.getDividers().get(0).getPosition()*nbCases- nbP1.doubleValue();
		nbP2 = Double.valueOf(Math.round(reste+(proportions_pingouins.getDividers().get(1).getPosition()-proportions_pingouins.getDividers().get(0).getPosition())*nbCases)).intValue();
		nbP3 = nbCases-nbP2-nbP1;
		System.out.println(nbP1 + " | " + nbP2 + " | " + nbP3 + " ==> " + (nbP1+nbP2+nbP3));
		lblS1.setText(nbP1.toString());
		lblS2.setText(nbP2.toString());
		lblS3.setText(nbP3.toString());
		tg.selectToggle(rb_config);
	}
}