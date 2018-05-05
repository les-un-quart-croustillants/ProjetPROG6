package Vue;

import Joueurs.Joueur;
import Joueurs.JoueurPhysique;

public class Moteur {
	private Joueur joueurs[];
	private int njoueurs;
	private int indexJoueurCourant=0;
	
	public Moteur(int njoueurs) {
		this.njoueurs = njoueurs;
		joueurs = new Joueur[njoueurs];
		//par defaut, on met que des joueurs physiques
		for(int i=0;i<njoueurs;i++) {
			joueurs[i] = new JoueurPhysique();
		}
	}
	
	public Joueur getJoueur(int index) {
		return joueurs[index];
	}
	public int njoueurs() {
		return njoueurs;
	}
	public Joueur getJoueurCourant() {
		return joueurs[indexJoueurCourant];
	}
}
