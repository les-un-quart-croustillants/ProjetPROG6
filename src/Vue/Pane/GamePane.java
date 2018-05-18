package Vue.Pane;

import java.util.ArrayList;

import Modele.Joueurs.Joueur;
import Modele.Joueurs.Joueur.Difficulte;
import Modele.Joueurs.JoueurIA;
import Modele.Joueurs.JoueurPhysique;
import Modele.Moteur.Moteur;
import Modele.Moteur.Moteur.State;
import Modele.Plateau.Plateau;
import Vue.Cadre.PlateauCadre;
import javafx.scene.layout.Pane;

public class GamePane extends Pane{
	private static GamePane instance = null;
	private PlateauCadre plateauCadre;
	private Moteur moteur;
	
	/**
	 * getInstance : renvoie l'instance du singleton (ou la cr�e avec un plateau par d�faut si elle n'existe pas)
	 */
	public static GamePane getInstance(){
		if(GamePane.instance==null){
			GamePane.instance = new GamePane();
			return GamePane.instance;
		}
		return GamePane.instance;
	}
	
	/**
	 * init : initialisation (appel�e par les constructeurs)
	 */
	private void init(){
		this.plateauCadre = new PlateauCadre(moteur);
		this.plateauCadre.prefWidthProperty().bind(this.widthProperty());
		this.plateauCadre.prefHeightProperty().bind(this.heightProperty());
		this.getChildren().add(plateauCadre);
		this.moteur.setCurrentState(State.POSER_PINGOUIN);
		plateauCadre.start();
	}
	
	private GamePane(){
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
		joueurs.add(new JoueurPhysique(0,3,"Joueur 1"));
		//joueurs.add(new JoueurIA(0,"Loukavocat",Difficulte.FACILE));
		joueurs.add(new JoueurIA(1,3,"Loukavocat",Difficulte.FACILE));
		joueurs.add(new JoueurIA(2,3,"Loukanape",Difficulte.FACILE));
		joueurs.add(new JoueurIA(3,3,"Loukasscouilles",Difficulte.FACILE));
		this.moteur = new Moteur(new Plateau(8),joueurs);
		init();
	}
	private GamePane(Moteur m){
		this.moteur = m;
		init();
	}
	
	/**
	 * newInstance : remplace l'instance du singleton par une nouvelle (permet de changer le plateau)
	 * @param p : le plateau
	 */
	public static void newInstance(Moteur m){
		GamePane.instance = new GamePane(m);
	}
	
	public static PlateauCadre getPlateauCadre(){
		return GamePane.getInstance().plateauCadre;
	}
	public static Moteur moteur(){
		return GamePane.getInstance().moteur;
	}
	
}