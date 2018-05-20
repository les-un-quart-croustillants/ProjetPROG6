package Modele.Joueurs;

import java.util.LinkedList;
import Modele.Plateau.Plateau;
import Utils.Position;
import Utils.Couple;


public class HeuristiqueB {
		public static int calcul(Plateau pInitial, LinkedList<Couple<Position,Position>> coups,int id) {

			int heuristique = 50;

			Plateau pCalcule = UtilsIA.plateaucoup(coups, pInitial.clone());
			LinkedList<LinkedList<Position>> composantesInit = UtilsIA.listeConnexeComposante(pInitial);
			LinkedList<LinkedList<Position>> composantesCalcul = UtilsIA.listeConnexeComposante(pCalcule);
			
			//IC ON MAXIMISE CE QU'ON VEUT
			
			if(composantesCalcul.size() > composantesInit.size()) {
				
				
				for(int i = 0; i < composantesCalcul.size();i++) {
					for(int j = 0; j < composantesInit.size();j++) {
						if(composantesCalcul.get(i).toString() == composantesInit.get(j).toString())
							composantesCalcul.remove(i);
					}
				}
				

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
								if (pCalcule.getCellule(composantesCalcul.get(i).get(j)).getFish() == 3)
									heuristique = heuristique - 3;
								if (pCalcule.getCellule(composantesCalcul.get(i).get(j)).getFish() == 2)
									heuristique = heuristique - 2;
							}
							else if(pCalcule.getCellule(composantesCalcul.get(i).get(j)).aPingouin() && pCalcule.getCellule(composantesCalcul.get(i).get(j)).pingouin().employeur() != id) {	
								nbPingouinEnnemis++;
								if (pCalcule.getCellule(composantesCalcul.get(i).get(j)).getFish() == 3)
									heuristique = heuristique + 3;
								if (pCalcule.getCellule(composantesCalcul.get(i).get(j)).getFish() == 2)
									heuristique = heuristique + 2;
							}	
							nbPoissonsComposante = nbPoissonsComposante + pCalcule.getCellule(composantesCalcul.get(i).get(j)).getFish();	
						}	
					}
					nbPingouinEnnemisList.add(nbPingouinEnnemis);
					nbPingouinAlliesList.add(nbPingouinAllies);
					nbPoissonsComposanteList.add(nbPoissonsComposante);
					// TODO : DIVISER PAR UN TRUC EN RAPPORT AVEC LE NOMBRE DE PINGUOINS ENFERMES

					//si une petit ile est laissée seule
					if(nbPingouinAlliesList.get(i) == 0 && nbPingouinEnnemisList.get(i) == 0) {
						heuristique = heuristique + nbPoissonsComposanteList.get(i);
					}
					
					//si une portion du plateau est laissee a l'ennemi
					if(nbPingouinAlliesList.get(i) == 0 && nbPingouinEnnemisList.get(i) > 0) {
						heuristique = heuristique + nbPoissonsComposanteList.get(i) - 3*nbPingouinEnnemisList.get(i) ;
					}
					//si on a un pingouinennemi est seul sur une grosse partie de banquise
					if((nbPoissonsComposante > UtilsIA.nbPoissonsPlateau(pInitial)/8) && nbPingouinAlliesList.get(i) == 0 && nbPingouinEnnemisList.get(i) == 1) {
						heuristique = heuristique + 20;
					}
					
					// si on est seuls sur la banquise
					if(nbPingouinAlliesList.get(i) > 0 && nbPingouinEnnemisList.get(i) == 0) {
						heuristique = heuristique - nbPoissonsComposanteList.get(i) + 3*nbPingouinEnnemisList.get(i);
					}
					//si on a un pingouin seul sur une grosse pasrtie de banquise
					if((nbPoissonsComposante > UtilsIA.nbPoissonsPlateau(pInitial)/8) && nbPingouinAlliesList.get(i) == 1 && nbPingouinEnnemisList.get(i) == 0) {
						heuristique = heuristique - 20;
					}
					
				}
			}else {
				for(int i = 0; i < composantesCalcul.size();i++) {
					for(int j = 0; j < composantesCalcul.get(i).size();j++) {
						if(pCalcule.getCellule(composantesCalcul.get(i).get(j)).aPingouin() && pCalcule.getCellule(composantesCalcul.get(i).get(j)).pingouin().employeur() == id) {
							if (pCalcule.getCellule(composantesCalcul.get(i).get(j)).getFish() == 3)
								heuristique = heuristique - 3;
							if (pCalcule.getCellule(composantesCalcul.get(i).get(j)).getFish() == 2)
								heuristique = heuristique - 2;
						}
						else if(pCalcule.getCellule(composantesCalcul.get(i).get(j)).aPingouin() && pCalcule.getCellule(composantesCalcul.get(i).get(j)).pingouin().employeur() != id) {	
							if (pCalcule.getCellule(composantesCalcul.get(i).get(j)).getFish() == 3)
								heuristique = heuristique + 3;
							if (pCalcule.getCellule(composantesCalcul.get(i).get(j)).getFish() == 2)
								heuristique = heuristique + 2;
						}	
						
					}
				}
			}
		return heuristique;
	}
}






































