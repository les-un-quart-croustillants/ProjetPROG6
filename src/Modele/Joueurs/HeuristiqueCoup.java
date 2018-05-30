package Modele.Joueurs;

import java.util.Iterator;
import java.util.LinkedList;
import Modele.Plateau.Plateau;
import Utils.Couple;
import Utils.Position;

public class HeuristiqueCoup {
	public static int calcul(int heuristique, Plateau pInitial,Couple<Position,Position> coups,int id) {
		
		
		/*On cherche a calculer combien de cases on bloque a l'ennemi grace au deplacement simule
		 *PRINCIPE : on compte le nombre de poissons auquel notre ennemi a acces en un coup avant le deplacement, puis on recompte apres
		 *et on adapte l'heuristique en fonction du nombre de poissons bloque
		 */
		int nbPoissonsAccessibleAvant = 0;
		int nbPoissonsAccessibleApres = 0;
		// calcul du nombre de poissons accessible avant
		LinkedList<Position> current = new LinkedList<Position>();
		for (int i =0; i < pInitial.getSize();i++) {
			for (int j =0; j < pInitial.getSize();j++) {
				if(pInitial.getCellule(new Position(i,j)).aPingouin() && pInitial.getCellule(new Position(i,j)).pingouin().employeur() != id){
					current = pInitial.accessible(new Position(i,j));
				}
				for(int k = 0;k < current.size();k++) {
					nbPoissonsAccessibleAvant = nbPoissonsAccessibleAvant + pInitial.getCellule(current.get(k)).getFish();
				}
				current.clear();

			}	
		}	
		
		//on joue un coup donne sur un plateau donne 
		pInitial.jouer(coups.gauche(), coups.droit());	
		Plateau pCalcule = pInitial;
		LinkedList<LinkedList<Position>> composantesCalcul = UtilsIA.listeConnexeComposante(pCalcule);
		
		// calcul du nombre de poissons accessible apres
		for (int i =0; i < pCalcule.getSize();i++) {
			for (int j =0; j < pCalcule.getSize();j++) {
				if(pCalcule.getCellule(new Position(i,j)).aPingouin() && pCalcule.getCellule(new Position(i,j)).pingouin().employeur() != id){
					current = pCalcule.accessible(new Position(i,j));
				}
				for(int k = 0;k < current.size();k++) {
					nbPoissonsAccessibleApres = nbPoissonsAccessibleApres + pCalcule.getCellule(current.get(k)).getFish();
				}
				current.clear();
			}	
		}
		// calcul du nombre de poissons bloque
		int poissonsbloque = nbPoissonsAccessibleAvant - nbPoissonsAccessibleApres;
		heuristique = heuristique + poissonsbloque;


		//si on est isole apres s'etre deplace : pas bien
		if(pCalcule.estIsolee(coups.droit())) {
			heuristique = heuristique -50;
		}
		
		//si on a isole un pingouin ennemi : bien
		LinkedList<Position> voisincourant = pCalcule.getNeighboursSansPingouins(coups.droit());
		for(Iterator<Position> it = voisincourant.iterator(); it.hasNext() ; ) {
			Position pos = it.next();
			if(pCalcule.estIsolee(pos) && pCalcule.getCellule(pos).aPingouin() && pCalcule.getCellule(pos).pingouin().employeur() != id) {
				heuristique = heuristique + 100;
			}
		}		

		//si on a qu'une case sur laquelle aller, c'est surement une bonne idee de faire ce deplacement pour ner pas se faire bloquer
		voisincourant = pCalcule.getNeighbours(coups.gauche());
		if(voisincourant.size() == 1) {
			heuristique = heuristique+50;
		}
		
		//on verifie qu'on ne pas va condamner un pingouin allie en le privant d'un des deux deplacements possibles qu'il avait, 
		//alors que son second deplacement possible est blocable par un ennemi en 1 coup
		/*
		 * PRINCIPE :
		 * si notre case d'arrivee a comme voisin un pingouin allie, et que ce pingouin allie n'a maintenant plus qu'une seule direction possible 
		 * (a cause de notre deplacement)
		 * et que cette direction est blocable par l'ennemi en un tour : on va surement perdre un pingouin : pas bien
		 */
		voisincourant = pCalcule.getNeighbours(coups.droit());
		for(Iterator<Position> it = voisincourant.iterator(); it.hasNext() ; ) {
			Position pos = it.next();
			if(pCalcule.getCellule(pos).aPingouin() && pCalcule.getCellule(pos).pingouin().employeur() == id) {
				LinkedList<Position> voisincourantduvoisincourant = pCalcule.getNeighbours(pos);
				if(voisincourantduvoisincourant.size() == 1 ) {
					heuristique = heuristique + 10;
					LinkedList<Position> accessiblescourantduvoisincourantduvoisincourant = pCalcule.accessiblesanspingouin(voisincourantduvoisincourant.get(0));
					for(Iterator<Position> iter = accessiblescourantduvoisincourantduvoisincourant.iterator(); it.hasNext() ; ) {
						Position poscur = iter.next();
						if(pCalcule.getCellule(poscur).aPingouin() && pCalcule.getCellule(poscur).pingouin().employeur() != id) {
							heuristique = heuristique - 50;
						}
					}
				}
					
			}
		}	

		/*
		 * Meme principe que pour HeuristiqueA et HeuristiqueB, mais on ne verifie que certaines heuristiques 
		 */
		LinkedList<Integer> nbPingouinEnnemisList = new LinkedList<Integer>();
		LinkedList<Integer> nbPingouinAlliesList = new LinkedList<Integer>();
		LinkedList<Integer> nbPoissonsComposanteList = new LinkedList<Integer>();
		
		for(int i = 0; i < composantesCalcul.size();i++) {
			int nbPingouinAllies = 0;
			int nbPingouinEnnemis =0;
			int nbPoissonsComposante = 0;
			if(composantesCalcul.get(i).size() > 0 ) {
				for(int j = 0; j < composantesCalcul.get(i).size();j++) {
					if(pCalcule.getCellule(composantesCalcul.get(i).get(j)).aPingouin() && pCalcule.getCellule(composantesCalcul.get(i).get(j)).pingouin().employeur() == id) {
						nbPingouinAllies++;
					}
					else if(pCalcule.getCellule(composantesCalcul.get(i).get(j)).aPingouin() && pCalcule.getCellule(composantesCalcul.get(i).get(j)).pingouin().employeur() != id) {	
						nbPingouinEnnemis++;
					}	
					nbPoissonsComposante = nbPoissonsComposante + pCalcule.getCellule(composantesCalcul.get(i).get(j)).getFish();	
				}	
			}

			nbPingouinEnnemisList.add(nbPingouinEnnemis);
			nbPingouinAlliesList.add(nbPingouinAllies);
			nbPoissonsComposanteList.add(nbPoissonsComposante);
			
			 //si une petit ile est laissée seule
			if(nbPingouinAlliesList.get(i) == 0 && nbPingouinEnnemisList.get(i) == 0) {
				heuristique = heuristique - nbPoissonsComposanteList.get(i)*10;
			}
			
			//si une portion du plateau est laissee a l'ennemi
			if(nbPingouinAlliesList.get(i) == 0 && nbPingouinEnnemisList.get(i) > 0) {
				heuristique = heuristique - nbPoissonsComposanteList.get(i) + 10*nbPingouinEnnemisList.get(i);
			}

			// si on est seuls sur la banquise
			if(nbPingouinAlliesList.get(i) > 0 && nbPingouinEnnemisList.get(i) == 0) {
				heuristique = heuristique + nbPoissonsComposanteList.get(i) - 10*nbPingouinAlliesList.get(i);
			}
		}
		
		// on retourne en arriere
		pCalcule.undo();
		return heuristique;
	}
}
