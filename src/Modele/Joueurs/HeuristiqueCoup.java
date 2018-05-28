package Modele.Joueurs;

import java.util.Iterator;
import java.util.LinkedList;
import Modele.Plateau.Plateau;
import Utils.Couple;
import Utils.Position;

public class HeuristiqueCoup {
	@SuppressWarnings("unchecked")
	public static int calcul(int heuristique, Plateau pInitial, LinkedList<Couple<Position,Position>> coups,int id) {

		Plateau pCalcule = UtilsIA.plateaucoup((LinkedList<Couple<Position,Position>>) coups.clone(), pInitial.clone());
		LinkedList<LinkedList<Position>> composantesInit = UtilsIA.listeConnexeComposante(pInitial.clone());
		LinkedList<LinkedList<Position>> composantesCalcul = UtilsIA.listeConnexeComposante(pCalcule);
		
		if(pCalcule.estIsolee(coups.get(0).droit())) {
			heuristique = heuristique -50;
		}
		
		LinkedList<Position> voisincourant = pCalcule.getNeighboursSansPingouins(coups.get(0).droit());
		
		for(Iterator<Position> it = voisincourant.iterator(); it.hasNext() ; ) {
			Position pos = it.next();
			if(pCalcule.estIsolee(pos) && pCalcule.getCellule(pos).aPingouin() && pCalcule.getCellule(pos).pingouin().employeur() != id) {
				heuristique = heuristique + 50;
			}
		}		

		voisincourant = pInitial.getNeighbours(coups.get(0).gauche());
		if(voisincourant.size() == 1) {
			heuristique = heuristique+50;
		}
		
		voisincourant = pCalcule.getNeighbours(coups.get(0).droit());
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

			
		/*if(composantesCalcul.size() > composantesInit.size()) { // on regarde si on s'est pas isole 
			for(int i = 0; i < composantesCalcul.size();i++) {
				for(int j = 0; j < composantesInit.size();j++) {
					if(composantesCalcul.get(i).toString() == composantesInit.get(j).toString())
						composantesCalcul.remove(i);
				}
			}*/
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
		//}
		

		
		int nbPoissonsAccessibleAvant = 0;
		int nbPoissonsAccessibleApres = 0;
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
		int poissonsbloque = nbPoissonsAccessibleAvant - nbPoissonsAccessibleApres;
		heuristique = heuristique + poissonsbloque;
		
		
		
		return heuristique;
	}
}
