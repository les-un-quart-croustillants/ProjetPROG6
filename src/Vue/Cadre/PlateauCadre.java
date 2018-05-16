package Vue.Cadre;

import Controleur.MiseEnEvidenceCase;
import Controleur.PoserPingouin;
import Modele.Moteur.Moteur;
import Modele.Plateau.Plateau;
import Vue.Donnees;
import Vue.GameObject.JoueurCourantGraphique;
import Vue.GameObject.MoteurGraphique;
import Vue.GameObject.PlateauGraphique;
import javafx.animation.AnimationTimer;

public class PlateauCadre extends Cadre{	
	public PlateauGraphique plateauGraphique;
	public Plateau plateau;
	public JoueurCourantGraphique joueurCourantGraphique;
	
	public MoteurGraphique moteurGraphique;
	private Moteur moteur;
	
	/**
	 * init : initialisation (appel�e par les constructeurs)
	 * @param p :  un plateau
	 */
	private void init(Moteur m){
		this.moteur = m;
		this.plateau = m.plateau();
		this.plateauGraphique = new PlateauGraphique(plateau,this);
		this.gameObjects.add(plateauGraphique);
		this.joueurCourantGraphique = new JoueurCourantGraphique("Joueur");
		this.gameObjects.add(joueurCourantGraphique);
		this.moteurGraphique = new MoteurGraphique();
		this.gameObjects.add(moteurGraphique);
		this.setOnMouseMoved(new MiseEnEvidenceCase(this));
		this.setOnMousePressed(new PoserPingouin(this));
		this.setStyle(
				"-fx-background-color: rgb(70,195,250);"+
	            "-fx-background-image: url(" +
	                "'bg_glacier.png'" +
	            "); " +
	            "-fx-background-size: 100%; -fx-background-repeat: no-repeat;"
	        );
	}

	public PlateauCadre(Moteur m){
		super();
		init(m);
	}
	public PlateauCadre(Moteur m,int w,int h){
		super(w,h);
		init(m);
	}
	
	public void start() {
		this.joueurCourantGraphique.setText("Joueur " + (1 + moteur.indexJoueurCourant()) + "("
				+ moteur.joueurCourant().scoreFish() + ")");
		this.joueurCourantGraphique.setCouleur(Donnees.COULEURS_JOUEURS[moteur.indexJoueurCourant()]);
		new AnimationTimer() {
			@Override
			public void handle(long currentNanoTime) {
				update();
				draw();
			}
		}.start();
	}
	
}
