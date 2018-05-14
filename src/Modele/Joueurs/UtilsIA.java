package Modele.Joueurs;

import java.util.LinkedList;
import java.util.Random;

import Modele.Plateau.Cellule;
import Modele.Plateau.Plateau;
import Utils.Position;

public class UtilsIA {
	/**
	 * 
	 * @param T : un plateau de jeu
	 * @return le nombre de poisson de la partie
	 */
	public static int nbPoissonsPlateau(Plateau T) {
		int res = 0;
		int taille = T.getSize();
		for(int i = 0; i < taille; i++) {
			for(int j = 0; j < taille; j++) {
				Cellule current = T.getCellule(new Position(i,j));
				res = res + current.getFish();
			}
		}
		return res;
	}
	/**
	 * @param i : i de la case a tester
	 * @param j : j de la case a tester
	 * @param P : plateau sur lequel tester la case i j
	 * @param compteur : à 0 initialement.
	 * @return la taille de la plus longue chaine de 3 poissons accessible depuis la case i j
	 */
	public static int nb3fishaccesible(int i, int j, Plateau P,int compteur, LinkedList<Position> visitey) {
		LinkedList<Position> link = P.getNeighbours(new Position(i,j));
		LinkedList<Position> nb3unpas = new LinkedList<Position>();
		//les visites
		visitey.addFirst(new Position(i,j));
		
		if(P.getCellule(new Position(i,j)).getFish() == 3)
			compteur++;
		
		//on met dans nb3unpas les poissons accessibles en un pas de la case courante
		for(int k =0; k< link.size();k++){
			if(P.getCellule(link.get(k)).getFish() == 3 &&  !visitey.contains(link.get(k)))
				nb3unpas.addFirst(link.get(k));
		}
		if ( nb3unpas.size() == 0 )
			return compteur;
		for(int k =0; k < nb3unpas.size();k++){
			 compteur = Math.max(nb3fishaccesible(nb3unpas.get(k).i(),nb3unpas.get(k).j(), P, compteur,visitey),compteur);
		}
		return compteur;
	}
	
	public static int heuristiqueCase(int i, int j, Plateau P,int id) {
		LinkedList<Position> voisincase = new LinkedList<Position>();
		LinkedList<Position> visitey = new LinkedList<Position>();

		Cellule current = P.getCellule(new Position(i,j));
		int heuristiquecase = 10;
		if(current.getFish() == 1) {
			//si on est sur les bords c'est nul
			if(i == 0 || j == 0 || i == P.getSize()-1 || (j == P.getSize()-1 && i%2 == 1) || ( j == P.getSize()-2 && i%2 == 0 )) {
				heuristiquecase = heuristiquecase-5;
			}
			
			//on ajoute le nombre de case a 3 poissons accessible en n pas de 1 deplacement (c'est un peu cavalier de prendre plus que 1 deplacement, d'autant que je sais pas ce que ca veut dire)
			heuristiquecase = heuristiquecase + nb3fishaccesible(i,j,P,0,visitey);
			
			//c'est pas mal de commencer a coter d'un ennemi
			voisincase = P.getNeighbours(new Position(i,j));
			for(int k = 0; k < voisincase.size(); k++) {
				if(P.getCellule(voisincase.get(k)).aPingouin() && P.getCellule(voisincase.get(k)).pingouin().employeur() != id) {
					heuristiquecase = heuristiquecase+3;
				}
			}
			//c'est pas fou de commencer a coter d'un allie
			for(int k = 0; k < voisincase.size(); k++) {
				if(P.getCellule(voisincase.get(k)).aPingouin() && P.getCellule(voisincase.get(k)).pingouin().employeur() == id) {
					heuristiquecase = heuristiquecase-1;
				}
			}
		}
		else
			return -1000;
		return heuristiquecase;
		
	}
	
	
	public static int[][] heuristiqueTab(Plateau P,int taille,int id){
		int[][] res = new int[taille][taille];
		for(int i = 0; i < taille; i++) {
			for(int j = 0; j < taille; j++) {
				res[i][j] = heuristiqueCase(i,j,P,id);
			}
		}
		return res;
	}
	
	public static Position bestplace(Plateau T,int id) {
		LinkedList<Position> bestmatch = new LinkedList<Position>();
		int [][] tab = new int[T.getSize()][T.getSize()];
		tab = heuristiqueTab(T,T.getSize(),id);
		
		// on recherche la meilleure valeur heuristique dans le tableau et on renvoie cette coordonnee
		int best = -1000;
		for(int i = 0; i< T.getSize();i++){
			for(int j = 0; j< T.getSize();j++){
				if(tab[i][j] > best && !T.getCellule(new Position(i,j)).aPingouin() && !T.getCellule(new Position(i,j)).isDestroyed()) {
					bestmatch.clear();
					best = tab[i][j];
					bestmatch.addFirst(new Position(i,j));
				}
				else if(tab[i][j] == best && !T.getCellule(new Position(i,j)).aPingouin() && !T.getCellule(new Position(i,j)).isDestroyed()) {
					bestmatch.addFirst(new Position(i,j));
				}
				
			}
		}
		// on renvoie au hasard une des meilleurs positions 
		if(bestmatch.size() != 0) {
			Random r = new Random();
			int rand = r.nextInt(bestmatch.size());
			return bestmatch.get(rand);
		}
		else 
			return new Position(0,0);
	}
	
}
