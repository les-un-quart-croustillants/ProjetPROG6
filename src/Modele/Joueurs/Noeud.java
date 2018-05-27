package Modele.Joueurs;

import java.util.LinkedList;
import Utils.Position;
import Utils.Couple;

public class Noeud {

		private LinkedList<Couple<Position,Position>> listcoup;
		private int heuristique = -100000;
		private Noeud pere ;
		private LinkedList<Noeud> fils;

		
		public Noeud() {
			this.listcoup = new LinkedList<Couple<Position,Position>>() ;
			this.heuristique = 0;
			this.pere = null;
			this.fils = new LinkedList<Noeud>();
		}
		
		public Noeud(LinkedList<Couple<Position,Position>> l) {
			this.listcoup = l;
			this.pere = null;
			this.fils = new LinkedList<Noeud>();
		}
		
		public Noeud(LinkedList<Couple<Position,Position>> l, Noeud pere) {
			this.listcoup = l;
			this.heuristique = 0;
			this.pere = pere;
			this.fils = new LinkedList<Noeud>();
		}
		public Noeud(LinkedList<Couple<Position,Position>> l, Noeud pere,int heur, LinkedList<Noeud> lfils) {
			this.listcoup = l;
			this.heuristique = heur;
			this.pere = pere;
			this.fils = lfils;
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
		public int heuristique() {
			return this.heuristique;
		}
		
		/**
		 * @return valeur du noeud
		 */
		public LinkedList<Couple<Position,Position>> listcoup() {
			return this.listcoup;
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
				if (n.heuristique() == this.heuristique()) {
					res.add(n);
				}
			}
			return res;
		}
		
		/**
		 * @param v nouvelle valeur pour le noeud
		 */
		public void setValeur(LinkedList<Couple<Position,Position>> l) {
			this.listcoup = l;
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
		
		@SuppressWarnings("unchecked")
		public Noeud clone() {
			return new Noeud(((LinkedList<Couple<Position,Position>>) this.listcoup.clone()),this.pere,this.heuristique,this.fils);
		}
}
