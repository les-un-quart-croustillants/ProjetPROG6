package Vue.Cadre;

import Controleur.MiseEnEvidenceCase;
import Controleur.PoserPingouin;
import Modele.Moteur.Moteur;
import Modele.Plateau.Plateau;
import Vue.Donnees;
import Vue.GameObject.BackgroundGraphique;
import Vue.GameObject.Brume;
import Vue.GameObject.InfoGraphique;
import Vue.GameObject.JoueurCourantGraphique;
import Vue.GameObject.MoteurGraphique;
import Vue.GameObject.ParticleSystem;
import Vue.GameObject.PlateauGraphique;
import Vue.GameObject.ScoresGraphique;
import Vue.Pane.GamePane;
import Vue.Pane.ParametrePane;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class PlateauCadre extends Cadre {
	public PlateauGraphique plateauGraphique;
	public Plateau plateau;
	public JoueurCourantGraphique joueurCourantGraphique;
	public InfoGraphique infoGraphique;

	public MoteurGraphique moteurGraphique;
	private Moteur moteur;

	/**
	 * init : initialisation (appel�e par les constructeurs)
	 * 
	 * @param p
	 *            : un plateau
	 */
	private void init(Moteur m) {
		this.moteur = m;
		this.plateau = m.plateau();
		this.plateauGraphique = new PlateauGraphique(plateau, this);
		this.gameObjects.get(0).add(plateauGraphique);
		this.gameObjects.get(3).add(new Brume());
		this.gameObjects.get(4).add(new BackgroundGraphique());
		this.joueurCourantGraphique = new JoueurCourantGraphique("Joueur");
		//this.gameObjects.get(5).add(joueurCourantGraphique);
		this.moteurGraphique = new MoteurGraphique();
		this.gameObjects.get(0).add(moteurGraphique);
		this.gameObjects.get(5).add(new ScoresGraphique());
		this.infoGraphique = new InfoGraphique("");
		this.gameObjects.get(5).add(infoGraphique);
		this.gameObjects.get(3).add(new ParticleSystem(Donnees.IMG_PARTICLE, 0, 0));
		this.setOnMouseMoved(new MiseEnEvidenceCase(this));
		this.setOnMousePressed(new PoserPingouin(this));
		this.setStyle("-fx-background-color: rgb(70,190,255);");

		this.getChildren().add(construire_entete());
	}

	public PlateauCadre(Moteur m) {
		super();
		init(m);
	}

	public PlateauCadre(Moteur m, int w, int h) {
		super(w, h);
		init(m);
	}

	public void start() {
		this.joueurCourantGraphique.setText(
				"Joueur " + (1 + moteur.indexJoueurCourant()) + "(" + moteur.joueurCourant().scoreFish() + ")");
		this.joueurCourantGraphique.setCouleur(Donnees.getCouleur(moteur.indexJoueurCourant()));
		new AnimationTimer() {
			@Override
			public void handle(long currentNanoTime) {
				update();
				draw();
			}
		}.start();
	}

	private HBox construire_entete() {
		HBox hv = new HBox();
		hv.prefWidthProperty().bind(this.widthProperty());
		hv.prefHeightProperty().bind(this.heightProperty().multiply(0.1));
		StackPane p = new StackPane();
		p.prefWidthProperty().bind(hv.widthProperty());
		p.getChildren().add(creer_bouton_quitter());
		p.setAlignment(Pos.CENTER_RIGHT);
		hv.getChildren().add(creer_bouton_undo());
		hv.getChildren().add(creer_bouton_redo());
		hv.getChildren().add(p);
		hv.setAlignment(Pos.CENTER_LEFT);
		hv.setPadding(new Insets(0,20,0,20));
		hv.setSpacing(20);
		return hv;
	}
	
	private Button creer_bouton_quitter() {
		Button b = new Button();
		b.setStyle("-fx-graphic: url('bouton_parametre.png'); -fx-background-color: transparent; -fx-padding: 0 0 0 0;");

		b.setOnMouseEntered(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				b.setStyle("-fx-graphic: url('bouton_parametre_hover.png'); -fx-background-color: transparent; -fx-padding: 0;");
			}
		});
		b.setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				b.setStyle("-fx-graphic: url('bouton_parametre.png'); -fx-background-color: transparent; -fx-padding: 0;");
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
	
	private Button creer_bouton_undo() {
		Button b = new Button();
		b.setStyle("-fx-graphic: url('undo.png'); -fx-background-color: transparent; -fx-padding: 0; ");
		b.setOnMousePressed(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				b.setStyle("-fx-graphic: url('undo.png'); -fx-background-color: transparent; -fx-padding: 5 0 0 5;");
			}
		});
		
		b.setOnMouseReleased(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				b.setStyle("-fx-graphic: url('undo.png'); -fx-background-color: transparent; -fx-padding: 0; ");
			}
		});
		return b;
	}
	
	private Button creer_bouton_redo() {
		Button b = new Button();
		b.setStyle("-fx-graphic: url('redo.png'); -fx-background-color: transparent; -fx-padding: 0; ");
		b.setOnMousePressed(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				b.setStyle("-fx-graphic: url('redo.png'); -fx-background-color: transparent; -fx-padding: 5 0 0 5;");
			}
		});
		
		b.setOnMouseReleased(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				b.setStyle("-fx-graphic: url('redo.png'); -fx-background-color: transparent; -fx-padding: 0; ");
			}
		});
		return b;
	}

}
