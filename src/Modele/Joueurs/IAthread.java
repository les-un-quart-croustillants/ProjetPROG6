package Modele.Joueurs;

import java.util.ArrayList;

import Modele.Joueurs.Joueur.Difficulte;
import Modele.Plateau.Plateau;
import Utils.Couple;
import Utils.Position;

public class IAthread implements Runnable{

	private Difficulte difficulte;
	private boolean actionType;
	private Plateau plateau;
	private ArrayList<ArrayList<Integer>> scores;
	private IAshared shared;
	private int id;
	
	/**
	 * Constructeur pour jouerCoup
	 * @param d
	 * @param plateau
	 * @param scores
	 */
	public IAthread(Difficulte d, Plateau plateau, int id, ArrayList<ArrayList<Integer>> scores, IAshared shared) {
		this.difficulte = d;
		this.actionType = true;
		this.scores = scores;
		this.plateau = plateau;
		this.id = id;
		this.shared = shared;
		
		(new Thread(this)).start();
	}
	
	public IAthread(Difficulte d,Plateau plateau, int id, IAshared shared) {
		this.difficulte = d;
		this.actionType = false;
		this.scores = null;
		this.plateau = plateau;
		this.id = id;
		this.shared = shared;
		
		(new Thread(this)).start();
	}
	
	@Override
	public void run() {
		if(this.actionType) {
			switch(this.difficulte) {
				case FACILE:
					this.shared.setCoupCalcule(UtilsIA.jouerCoupFacile(this.plateau, this.id));
					break;
				case MOYEN:
					this.shared.setCoupCalcule(UtilsIA.jouerCoupFacile(this.plateau, this.id));
					break;
				case DIFFICILE:
					this.shared.setCoupCalcule(UtilsIA.jouerCoupDifficile(this.plateau, this.id, this.scores));
					break;
				default:
					this.shared.setCoupCalcule(new Couple<Position,Position>(new Position(-1,-1),new Position(-1,-1)));
					break;
			}
		} else {
			this.shared.setPoseCalcule(UtilsIA.bestplace(this.plateau, this.id));
		}
		
	}

}
