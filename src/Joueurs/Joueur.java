package Joueurs;

import Modele.Plateau.Plateau;
import Utils.Position;

import java.util.ArrayList;

public abstract class Joueur {
	private int id;
	private int scoreFish;
	private int scoreDestroyed;
	
	public Joueur(int id){
		this.id = id;
		this.scoreFish = 0;
		this.scoreDestroyed = 0;
	}

	public int id() {
		return this.id;
	}

	public int scoreFish() {
		return this.scoreFish;
	}
	
	public int scoreDestroyed() {
		return this.scoreDestroyed;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setScoreFish(int s) {
		this.scoreFish = s;
	}
	
	public void addScoreFish(int a) {
		this.scoreFish += a;
	}
	
	public void subScoreFish(int l) {
		this.scoreFish -= l;
	}
	
	public void setScoreDestroyed(int s) {
		this.scoreDestroyed = s;
	}
	
	public void addScoreDestroyed(int a) {
		this.scoreDestroyed += a;
	}
	
	public void subScoreDestroyed(int l) {
		this.scoreDestroyed -= l;
	}

	/**
	 * Fonction appelee dans l'IA pour calculer le prochain coup
	 * renvoie (-1,-1) si une erreur c'est produite
	 * @param plateau
	 * @return
	 */
	public Position prochainCoup(Plateau plateau) {
		return new Position(-1,-1);
	}

	/**
	 * Fonction appellee par un joueur pour jouer un coup
	 * renvois le nombre de poissons mangee par le pingouin lors du coup
	 * ou -1 si une erreur c'est produite
	 * 
	 * @param plateau le plateau de jeu
	 * @param pingouin position du pingouin a deplacer
	 * @param goal la destination souhaitee
	 * @return le nombre de poissons mangee par le pingouin lors du coup ou -1 si une erreur c'est produite
	 * @throws Exception 
	 */
	public int jouerCoup(Plateau plateau, Position start, Position goal) throws Exception {
		return -1;
	}
}
