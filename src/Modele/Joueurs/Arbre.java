package Modele.Joueurs;

import java.util.LinkedList;
import Utils.Position;
import Utils.Couple;

public class Arbre {
	private Noeud racine;

	// Constructeurs

	public Arbre() {
		this.racine = new Noeud();
	}
	
	/**
	 * Constructeur pour une feuille
	 * @param  p : le plateau a donner en racine 
	 */
	public Arbre(LinkedList<Couple<Position,Position>> p) {
		this.racine = new Noeud(p);
	}

	/**
	 * Constructeur pour un arbre
	 * @param p plateau du noeud a la racine
	 * @param l liste des fils de la racine
	 */
	public Arbre(LinkedList<Couple<Position,Position>> p, LinkedList<Noeud> l) {
		this.racine = new Noeud(p);
		racine.setFils(l);
	}
	
	/**
	 * Getteur pour l'etiquette de la racine de l'arbre
	 * @return valeur de l'etiquette de la racine
	 */
	public Noeud racine() {
		return this.racine;
	}
}
