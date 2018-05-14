package Modele.Joueurs;

import java.util.LinkedList;
import Modele.Plateau.Plateau;

public class Noeud {

		private Plateau plat;
		private int heuristique;
		private Noeud pere ;
		private LinkedList<Noeud> fils;
		
		public Noeud() {
			this.plat = new Plateau(0);
			this.heuristique = 0;
			this.pere = null;
			this.fils = new LinkedList<Noeud>();
		}
		
		public Noeud(Plateau p) {
			this.plat = p;
			this.pere = null;
			this.fils = new LinkedList<Noeud>();
		}
		
		public Noeud(Plateau p, Noeud pere) {
			this.plat = p;
			this.heuristique = 0;
			this.pere = pere;
			this.fils = new LinkedList<Noeud>();
		}
		
		/**
		 * @return si le neoud est une feuille
		 */
		public boolean estFeuille() {
			return this.fils.isEmpty();
		}
		
		/**
		 * @return l'heuristique du noeud
		 */
		public int heuristic() {
			return this.heuristique;
		}
		
		/**
		 * @return valeur du noeud
		 */
		public Plateau valeur() {
			return this.plat;
		}
		
		/**
		 * @return pere du noeud
		 */
		public Noeud pere() {
			return this.pere;
		}
		
		/**
		 * @return liste des fils du noeud
		 */
		public LinkedList<Noeud> fils(){
			return this.fils;
		}
		
		/**
		 * @param i indice du fils a recupere
		 * @return fils i du noeud
		 */
		public Noeud fils(int i) {
			return this.fils.get(i);
		}
		
		public LinkedList<Noeud> filsTaggue(){
			LinkedList<Noeud> res = new LinkedList<Noeud>();
			for(Noeud n : this.fils) {
				if (n.heuristic() == this.heuristic()) {
					res.add(n);
				}
			}
			return res;
		}
		
		/**
		 * @param v nouvelle valeur pour le noeud
		 */
		public void setValeur(Plateau p) {
			this.plat = p;
		}
		
		/**
		 * Change la valeur de l'heuristique
		 * @param h nouvelle heuristique
		 */
		public void setHeuristic(int h) {
			this.heuristique = h;
		}
		
		/**
		 * @param p nouveau pere pour le noeud
		 */
		public void setPere(Noeud p) {
			this.pere = p;
		}
		
		/**
		 * Remplace la liste des fils
		 * @param l nouvelle liste des noeud
		 */
		public void setFils(LinkedList<Noeud> l) {
			this.fils = l;
		}
		
		/**
		 * @param n Noeud a ajouter a la liste des fils
		 */
		public void addFils(Noeud n) {
			this.fils.add(n);
		}
		
		/**
		 * 
		 * @param i indice du noeud a supprimer de la liste des fils
		 */
		public void removeFils(int i) {
			this.fils.remove(i);
		}
}
