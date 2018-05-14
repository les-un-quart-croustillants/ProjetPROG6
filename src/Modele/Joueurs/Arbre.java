package Modele.Joueurs;

import java.util.LinkedList;
import Modele.Plateau.Plateau;

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
	public Arbre(Plateau p) {
		this.racine = new Noeud(p);
	}

	/**
	 * Constructeur pour un arbre
	 * @param p plateau du noeud a la racine
	 * @param l liste des fils de la racine
	 */
	public Arbre(Plateau p, LinkedList<Noeud> l) {
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
