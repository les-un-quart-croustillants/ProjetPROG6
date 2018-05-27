package Vue.Pane;

import java.util.ArrayList;

import Modele.Joueurs.Joueur;
import Modele.Joueurs.Joueur.Difficulte;
import Modele.Joueurs.JoueurIA;
import Modele.Joueurs.JoueurPhysique;
import Modele.Moteur.Moteur;
import Modele.Plateau.Plateau;
import Vue.Cadre.PlateauCadre;
import Vue.Donnees.Niveau;
import javafx.scene.layout.StackPane;

public class GamePane extends StackPane{
	private static GamePane instance = null;
	private PlateauCadre plateauCadre;
	private Moteur moteur;
	
	/**
	 * getInstance : renvoie l'instance du singleton (ou la cr�e avec un plateau par d�faut si elle n'existe pas)
	 */
	public static GamePane getInstance(){
		if(GamePane.instance==null){
			GamePane.instance = new GamePane();
			GamePane.getPlateauCadre().start();
			return GamePane.instance;
		}
		return GamePane.instance;
	}
	
	/**
	 * init : initialisation (appel�e par les constructeurs)
	 */
	private void init(Niveau niveau){
		this.plateauCadre = new PlateauCadre(moteur,niveau);
		this.plateauCadre.prefWidthProperty().bind(this.widthProperty());
		this.plateauCadre.prefHeightProperty().bind(this.heightProperty());
		this.getChildren().add(plateauCadre);
	}
	
	private GamePane(){
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
		joueurs.add(new JoueurPhysique(0,2,"Joueur 1"));
		joueurs.add(new JoueurIA(1,2,"IA facile",Difficulte.FACILE));
		joueurs.add(new JoueurIA(2,2,"IA facile",Difficulte.FACILE));
		joueurs.add(new JoueurIA(3,2,"IA facile",Difficulte.FACILE));
		this.moteur = new Moteur(new Plateau(8,8),joueurs);
		init(Niveau.BANQUISE);
	}
	private GamePane(Moteur m,Niveau niveau){
		this.moteur = m;
		init(niveau);
	}
	
	/**
	 * newInstance : remplace l'instance du singleton par une nouvelle (permet de changer le plateau)
	 * @param p : le plateau
	 */
	public static void newInstance(Moteur m,Niveau n){
		if(GamePane.instance!=null)
			GamePane.getPlateauCadre().animationTimer.stop();
		GamePane.instance = new GamePane(m, n);
		GamePane.instance.plateauCadre.start();
	}
	
	public static PlateauCadre getPlateauCadre(){
		return GamePane.getInstance().plateauCadre;
	}
	public static Moteur moteur(){
		return GamePane.getInstance().moteur;
	}
	
}
