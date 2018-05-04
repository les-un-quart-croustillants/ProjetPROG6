package Joueurs;

import Modele.Plateau.Plateau;
import Utils.Position;

import java.util.ArrayList;

public abstract class Joueur {
	private int id;
	private ArrayList<Pingouin> squad;
	private int score;

	Joueur(){
		this.id = 0;
		this.squad = new ArrayList<Pingouin>();
		this.score = 0;
	}
	
	Joueur(int id){
		this.id = id;
		this.squad = new ArrayList<Pingouin>();
		this.score = 0;
	}

	Joueur(int id, ArrayList<Pingouin> s){
		this.id = id;
		this.squad = s;
		this.score = 0;
	}

	public int id() {
		return this.id;
	}
	
	public ArrayList<Pingouin> squad(){
		return this.squad;
	}

	public int score() {
		return this.score;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setScore(int s) {
		this.score = s;
	}
	
	public void addScore(int a) {
		this.score += a;
	}
	
	public void removeScore(int l) {
		this.score -= l;
	}

	public void setSquad(ArrayList<Pingouin> s) {
		this.squad = s;
	}

	public int squadSize() {
		return this.squad.size();
	}

	public void addSquad(Pingouin p) throws Exception {
		if(p.employeur() == this.id()) {
			this.squad.add(p);
		} else {
			throw new Exception("insertion impossible. Le pingouin en "+p.position()+" n'appartient pas au joueur "+this.id()+".");
		}
		
	}

	public void removeSquad(Pingouin p) {
		this.squad.remove(p);
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
	 * @param pingouin le pingouin concerne
	 * @param goal la destination souhaitee
	 * @return le nombre de poissons mangee par le pingouin lors du coup ou -1 si une erreur c'est produite
	 * @throws Exception 
	 */
	public int jouerCoup(Plateau plateau, Pingouin pingouin, Position goal) throws Exception {
		return -1;
	}
}
