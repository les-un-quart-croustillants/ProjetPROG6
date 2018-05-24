package Vue.Cadre;

import Controleur.MiseEnEvidenceCase;
import Controleur.PoserPingouin;
import Modele.Moteur.Moteur;
import Modele.Plateau.Cellule;
import Modele.Plateau.Plateau;
import Utils.Position;
import Vue.Donnees;
import Vue.InterfaceGraphique;
import Vue.GameObject.BackgroundGraphique;
import Vue.GameObject.Brume;
import Vue.GameObject.InfoGraphique;
import Vue.GameObject.JoueurCourantGraphique;
import Vue.GameObject.MoteurGraphique;
import Vue.GameObject.MoteurGraphique.StateGraph;
import Vue.GameObject.ParticleSystem;
import Vue.GameObject.PingouinGraphique;
import Vue.GameObject.PlateauGraphique;
import Vue.GameObject.ScoresGraphique;
import Vue.Pane.GamePane;
import Vue.Pane.ParametrePane;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class PlateauCadre extends Cadre {
	public PlateauGraphique plateauGraphique;
	public Plateau plateau;
	public JoueurCourantGraphique joueurCourantGraphique;
	public InfoGraphique infoGraphique;

	public MoteurGraphique moteurGraphique;
	private Moteur moteur;
	
	private Button undoBouton;
	private Button redoBouton;
	
	public AnimationTimer animationTimer;

	/**
	 * init : initialisation (appel�e par les constructeurs)
	 * 
	 * @param p
	 *            : un plateau
	 */
	private void init(Moteur m) {
		this.moteur = m;
		this.plateau = m.plateau();
		//this.plateauGraphique = new PlateauGraphique(plateau, this);
		construire_plateau();
		this.gameObjects.get(0).add(plateauGraphique);
		this.gameObjects.get(3).add(new Brume());
		this.gameObjects.get(4).add(new BackgroundGraphique());
		this.joueurCourantGraphique = new JoueurCourantGraphique("Joueur");
		// this.gameObjects.get(5).add(joueurCourantGraphique);
		this.moteurGraphique = new MoteurGraphique();
		this.gameObjects.get(0).add(moteurGraphique);
		this.gameObjects.get(5).add(new ScoresGraphique());
		this.infoGraphique = new InfoGraphique("");
		this.gameObjects.get(5).add(infoGraphique);
		this.gameObjects.get(3).add(new ParticleSystem(Donnees.IMG_PARTICLE, 0, 0));
		this.setOnMouseMoved(new MiseEnEvidenceCase());
		this.setOnMousePressed(new PoserPingouin(this));
		this.setStyle("-fx-background-color: rgb(70,190,255);");

		this.getChildren().add(construire_entete());
	}

	public void deconstruire_plateau() {
		// on nettoie l'ancien plateauGraphique et ses pingouins
		if (plateauGraphique != null)
			for (int i = 0; i < plateau.getSize(); i++) {
				for (int j = 0; j < plateau.getSize(); j++) {
					Cellule c = plateau.getCellule(new Position(i, j));
					if (c.aPingouin()) {
						plateauGraphique.cases[i][j].pingouinGraphique.detruire();
					}
				}
			}
		plateauGraphique.detruire();
	}

	public void construire_plateau() {
		// on recree un plateau graphique et ses pingouins
		this.plateauGraphique = new PlateauGraphique(plateau, this);
		this.gameObjects.get(0).add(plateauGraphique);
		for (int i = 0; i < plateau.getSize(); i++) {
			for (int j = 0; j < plateau.getSize(); j++) {
				Cellule c = plateau.getCellule(new Position(i, j));
				if (c.aPingouin()) {
					this.gameObjects.get(2).add(new PingouinGraphique(c.pingouin(), plateauGraphique,
							Donnees.getCouleur(c.pingouin().employeur())));
				}
			}
		}
		plateauGraphique.update();
	}

	public PlateauCadre(Moteur m) {
		super();
		init(m);
	}

	public PlateauCadre(Moteur m, int w, int h) {
		super(w, h);
		init(m);
	}

	private StackPane pane_transition;
	public void start() {
		this.joueurCourantGraphique.setText(
				"Joueur " + (1 + moteur.indexJoueurCourant()) + "(" + moteur.joueurCourant().scoreFish() + ")");
		this.joueurCourantGraphique.setCouleur(Donnees.getCouleur(moteur.indexJoueurCourant()));
		animationTimer = new AnimationTimer() {
			@Override
			public void handle(long currentNanoTime) {
				update();
				draw();
			}
		};
		
		//FONDU
		pane_transition = new StackPane();
		pane_transition.prefWidthProperty().bind(InterfaceGraphique.stage.getScene().widthProperty());
		pane_transition.prefHeightProperty().bind(InterfaceGraphique.stage.getScene().heightProperty());
		pane_transition.setStyle("-fx-background-color: rgb(0,0,0);");
		GamePane.getInstance().getChildren().add(pane_transition);
		FadeTransition ft = new FadeTransition(new Duration(500),pane_transition);
		ft.setFromValue(1);
		ft.setToValue(0);
		ft.play();
		ft.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GamePane.getInstance().getChildren().remove(pane_transition);
			}
		});
		plateauGraphique.start();
		animationTimer.start();
	}

	private HBox construire_entete() {
		HBox hv = new HBox();
		hv.prefWidthProperty().bind(this.widthProperty());
		hv.prefHeightProperty().bind(this.heightProperty().multiply(0.1));
		StackPane p = new StackPane();
		p.prefWidthProperty().bind(hv.widthProperty());
		p.getChildren().add(creer_bouton_quitter());
		p.setAlignment(Pos.CENTER_RIGHT);
		
		HBox cur = creer_cadre_undo_redo();
		cur.prefWidthProperty().bind(hv.widthProperty().multiply(0.5));
		hv.getChildren().add(cur);
		actualiser_undo_redo();
		//hv.getChildren().add(undoBouton);
		//hv.getChildren().add(redoBouton);
		hv.getChildren().add(p);
		hv.setAlignment(Pos.CENTER_LEFT);
		hv.setPadding(new Insets(0, 20, 0, 20));
		hv.setSpacing(20);
		return hv;
	}

	private Button creer_bouton_quitter() {
		Button b = new Button();
		b.setStyle(
				"-fx-graphic: url('bouton_parametre.png'); -fx-background-color: transparent; -fx-padding: 0 0 0 0;");

		b.setOnMouseEntered(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				b.setStyle(
						"-fx-graphic: url('bouton_parametre_hover.png'); -fx-background-color: transparent; -fx-padding: 0;");
			}
		});
		b.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				b.setStyle(
						"-fx-graphic: url('bouton_parametre.png'); -fx-background-color: transparent; -fx-padding: 0;");
			}
		});

		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				GamePane.getInstance().getChildren().add(new ParametrePane());
			}
		});

		return b;
	}

	//disable ou enable les boutons undo/redo
	public void actualiser_undo_redo(){
		if(moteur.redoPossible())
			redoBouton.setDisable(false);
		else
			redoBouton.setDisable(true);
		if(moteur.undoPossible())
			undoBouton.setDisable(false);
		else
			undoBouton.setDisable(true);
	}
	
	//execute undo/redo et actualise l'affichage en consequence
	private void executer_undo_redo(boolean undo) {
		try {
			deconstruire_plateau();
			if(undo)
				moteur.undo();
			else
				moteur.redo();
			construire_plateau();
			moteurGraphique.setCurrentState(StateGraph.ATTENDRE_MOTEUR);
			actualiser_undo_redo();
				
		} catch (Exception e) {
			System.out.println("Erreur: Undo/Redo: Historique invalide");
			e.printStackTrace();
			System.exit(1);
		}
	}

	private Button creer_bouton_undo() {
		Button b = new Button();
		b.setStyle("-fx-graphic: url('undo.png'); -fx-background-color: transparent; -fx-padding: 5 5 5 5; ");
		b.setOnMousePressed(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				b.setStyle("-fx-graphic: url('undo.png'); -fx-background-color: transparent; -fx-padding: 10 0 0 10;");
			}
		});

		b.setOnMouseReleased(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				b.setStyle("-fx-graphic: url('undo.png'); -fx-background-color: transparent; -fx-padding: 5 5 5 5; ");
			}
		});

		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				executer_undo_redo(true);
			}
		});
		return b;
	}

	private Button creer_bouton_redo() {
		Button b = new Button();
		b.setStyle("-fx-graphic: url('redo.png'); -fx-background-color: transparent; -fx-padding: 5 5 5 5; ");
		b.setOnMousePressed(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				b.setStyle("-fx-graphic: url('redo.png'); -fx-background-color: transparent; -fx-padding: 10 0 0 10;");
			}
		});

		b.setOnMouseReleased(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				b.setStyle("-fx-graphic: url('redo.png'); -fx-background-color: transparent; -fx-padding: 5 5 5 5; ");
			}
		});

		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				executer_undo_redo(false);
			}
		});
				return b;
	}
	
	private HBox creer_cadre_undo_redo(){
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.setSpacing(10);
		undoBouton = creer_bouton_undo();
		redoBouton = creer_bouton_redo();
		hbox.getChildren().add(undoBouton);
		hbox.getChildren().add(redoBouton);
		return hbox;
	}

}
