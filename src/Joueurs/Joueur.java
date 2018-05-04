package Joueurs;

import Modele.Plateau.Plateau;
import Utils.Position;

import java.util.LinkedList;

public abstract class Joueur {
	private LinkedList<Pingouin> squad;
	private int score;

	Joueur(){
		this.squad = new LinkedList<Pingouin>();
		this.score = 0;
	}

	Joueur(LinkedList<Pingouin> s){
		this.squad = s;
		this.score = 0;
	}

	public LinkedList<Pingouin> squad(){
		return this.squad;
	}

	public int score() {
		return this.score;
	}

	public void setScore(int s) {
		this.score = s;
	}

	public void setSquad(LinkedList<Pingouin> s) {
		this.squad = s;
	}

	public int squadSize() {
		return this.squad.size();
	}

	public void addSquad(Pingouin p) {
		this.squad.add(p);
	}

	public void removeSquad(Pingouin p) {
		this.squad.remove(p);
	}

	/**
	 * Fonction appelee dans l'IA pour calculer le prochain coup
	 * @param plateau
	 * @return
	 */
	public Position prochainCoup(Plateau plateau) {
		return new Position(0,0);
	}

	/**
	 * Fonction appellee par un joueur pour jouer un coup
	 * @param plateau
	 * @param pingouin
	 * @param goal
	 * @return
	 */
	public int jouerCoup(Plateau plateau, Pingouin pingouin, Position goal) {
		return -1;
	}
}
