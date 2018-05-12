package Vue.Pane;

import Modele.Moteur.Moteur;
import Modele.Moteur.Moteur.State;
import Modele.Plateau.Plateau;
import Vue.Cadre.PlateauCadre;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

public class GamePane extends Pane {
	private static GamePane instance = null;
	private PlateauCadre plateauCadre;
	private Moteur moteur;

	/**
	 * getInstance : renvoie l'instance du singleton (ou la cr�e avec un plateau par
	 * d�faut si elle n'existe pas)
	 */
	public static GamePane getInstance() {
		if (GamePane.instance == null) {
			GamePane.instance = new GamePane();
			return GamePane.instance;
		}
		return GamePane.instance;
	}

	/**
	 * init : initialisation (appel�e par les constructeurs)
	 */
	private void init() {
		this.getChildren().add(plateauCadre);
		this.moteur = new Moteur(plateauCadre.plateau, 2);
		this.moteur.setCurrentState(State.POSER_PINGOUIN);
		new AnimationTimer() {
			@Override
			public void handle(long currentNanoTime) {
				plateauCadre.update();
				plateauCadre.draw();
			}
		}.start();
	}

	private GamePane() {
		this.plateauCadre = new PlateauCadre(new Plateau(8));
		plateauCadre.prefWidthProperty().bind(this.widthProperty());
		plateauCadre.prefHeightProperty().bind(this.heightProperty());
		init();
	}

	private GamePane(Plateau p) {
		GamePane.getInstance().plateauCadre = new PlateauCadre(p);
		plateauCadre.prefWidthProperty().bind(this.widthProperty());
		plateauCadre.prefHeightProperty().bind(this.heightProperty());
		init();
	}

	/**
	 * newInstance : remplace l'instance du singleton par une nouvelle (permet de
	 * changer le plateau)
	 * 
	 * @param p
	 *            : le plateau
	 */
	public static void newInstance(Plateau p) {
		GamePane.instance = new GamePane(p);
	}

	public static PlateauCadre getPlateauCadre() {
		return GamePane.getInstance().plateauCadre;
	}

	public static Moteur moteur() {
		return GamePane.getInstance().moteur;
	}

}