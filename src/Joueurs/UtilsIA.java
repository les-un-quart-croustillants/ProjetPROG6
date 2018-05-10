package Joueurs;

import java.util.LinkedList;

import Modele.Plateau.Cellule;
import Modele.Plateau.Plateau;
import Utils.Position;

public class UtilsIA {
	
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
	
	public static int nb3fishaccesible(int i, int j, Plateau P,int compteur) {
		LinkedList<Position> link = P.getNeighbours(new Position(i,j));
		// ajout du nombre de poissons courrant de la case
		if(P.getCellule(new Position(i,j)).getFish() == 3)
			compteur = compteur+3;
		
		for(int k = 0; k < link.size(); k++) {
			if(P.getCellule(link.get(k)).getFish() == 3) {
				current = Math.max( nb3fishaccesible(link.get(k).i(),link.get(k).j(),P,compteur), current); // calcul du nombre de cases a trois poissons d'affile

			}
		}
		return compteur;
	}
	
	public static int heuristiqueCase(int i, int j, Plateau P) {
		Cellule current = P.getCellule(new Position(i,j));
		if(current.getFish() == 1) {
		
		}
		else
			return -1000;
		return 10;
		
	}
	
	
	public static int[][] heuristiqueTab(Plateau P,int taille){
		int[][] res = new int[taille][taille];
		for(int i = 0; i < taille; i++) {
			for(int j = 0; j < taille; j++) {
				res[i][j] = heuristiqueCase(i,j,P);
			}
		}
		return res;
	}
	
	public static Position bestplace(Plateau T) {
		Cellule[][] tab = T.getTab();
		int taille = T.getSize();
		int nbfish = nbPoissonsPlateau(T);
		
		return new Position(0,0);
	}

}
