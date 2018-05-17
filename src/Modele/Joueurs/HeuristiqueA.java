package Modele.Joueurs;
import java.util.LinkedList;
import Modele.Plateau.Plateau;
import Utils.Position;
import Utils.Couple;


public class HeuristiqueA {
		public static int calcul(Plateau pInitial, LinkedList<Couple<Position,Position>> coups,int id) {
			int heuristique = 50;

			Plateau pCalcule = UtilsIA.plateaucoup(coups, pInitial.clone());
			LinkedList<LinkedList<Position>> composantesInit = UtilsIA.listeConnexeComposante(pInitial);
			LinkedList<LinkedList<Position>> composantesCalcul = UtilsIA.listeConnexeComposante(pCalcule);
			

			
			if(composantesCalcul.size() > composantesInit.size()) {
				for(int i = 0; i < composantesCalcul.size();i++) {
					for(int j = 0; j < composantesInit.size();j++) {
						if(composantesCalcul.get(i).toString() == composantesInit.get(j).toString())
							composantesCalcul.remove(i);
					}
				}
				
				for(int i = 0; i < composantesCalcul.size();i++) {
					if(composantesCalcul.get(i).size() != 1) {
						int nbPingouinEnnemis = 0;
						int nbPingouinAllies = 0;
						int nbPoissonsComposante = 0;
						for(int j = 0; j < composantesCalcul.get(i).size();j++) {
							if(pCalcule.getCellule(composantesCalcul.get(i).get(j)).aPingouin() && pCalcule.getCellule(composantesCalcul.get(i).get(j)).pingouin().employeur() == id) {
								nbPingouinAllies++;
								if (pCalcule.getCellule(composantesCalcul.get(i).get(j)).getFish() == 3)
									heuristique = heuristique + 3;
								if (pCalcule.getCellule(composantesCalcul.get(i).get(j)).getFish() == 2)
									heuristique = heuristique + 2;
							}
							else if(pCalcule.getCellule(composantesCalcul.get(i).get(j)).aPingouin() && pCalcule.getCellule(composantesCalcul.get(i).get(j)).pingouin().employeur() != id) {	
								nbPingouinEnnemis++;
								if (pCalcule.getCellule(composantesCalcul.get(i).get(j)).getFish() == 3)
									heuristique = heuristique - 3;
								if (pCalcule.getCellule(composantesCalcul.get(i).get(j)).getFish() == 2)
									heuristique = heuristique - 2;
							}	
							nbPoissonsComposante = nbPoissonsComposante + pCalcule.getCellule(composantesCalcul.get(i).get(j)).getFish();	
						}
						if((nbPoissonsComposante > UtilsIA.nbPoissonsPlateau(pInitial)/8) && nbPingouinAllies == 0 && nbPingouinEnnemis == 0) {
							heuristique = heuristique -5;
						}
						if((nbPoissonsComposante > UtilsIA.nbPoissonsPlateau(pInitial)/8) && nbPingouinAllies == 0 && nbPingouinEnnemis >= 1) {
							heuristique = heuristique -10;
						}
						if((nbPoissonsComposante > UtilsIA.nbPoissonsPlateau(pInitial)/12) && nbPingouinAllies > 0 && nbPingouinEnnemis == 0) {
							heuristique = heuristique +10050;
						}
						if((nbPoissonsComposante < UtilsIA.nbPoissonsPlateau(pInitial)/8) && nbPingouinAllies == 0 && nbPingouinEnnemis == 0) {
							heuristique = heuristique -2;
						}
						if((nbPoissonsComposante < UtilsIA.nbPoissonsPlateau(pInitial)/12) && nbPingouinAllies > 0 && nbPingouinEnnemis == 0) {
							heuristique = heuristique -10;
						}
						if((nbPoissonsComposante < UtilsIA.nbPoissonsPlateau(pInitial)/8) && nbPingouinAllies == 0 && nbPingouinEnnemis > 0) {
							heuristique = heuristique +10;
						}
						if((nbPoissonsComposante < UtilsIA.nbPoissonsPlateau(pInitial)/12) && nbPingouinAllies == 0 && nbPingouinEnnemis > 0) {
							heuristique = heuristique +1000;
						}
						if((nbPoissonsComposante < UtilsIA.nbPoissonsPlateau(pInitial)/8) && nbPingouinAllies > 0 && nbPingouinEnnemis > 0) {
							heuristique = heuristique -1;
						}
					}
				}	
			}else {
				for(int i = 0; i < composantesCalcul.size();i++) {
					for(int j = 0; j < composantesCalcul.get(i).size();j++) {
						if(pCalcule.getCellule(composantesCalcul.get(i).get(j)).aPingouin() && pCalcule.getCellule(composantesCalcul.get(i).get(j)).pingouin().employeur() == id) {
							if (pCalcule.getCellule(composantesCalcul.get(i).get(j)).getFish() == 3)
								heuristique = heuristique + 3;
							if (pCalcule.getCellule(composantesCalcul.get(i).get(j)).getFish() == 2)
								heuristique = heuristique + 2;
						}
						else if(pCalcule.getCellule(composantesCalcul.get(i).get(j)).aPingouin() && pCalcule.getCellule(composantesCalcul.get(i).get(j)).pingouin().employeur() != id) {	
							if (pCalcule.getCellule(composantesCalcul.get(i).get(j)).getFish() == 3)
								heuristique = heuristique - 3;
							if (pCalcule.getCellule(composantesCalcul.get(i).get(j)).getFish() == 2)
								heuristique = heuristique - 2;
						}	
						
					}
				}
			}
		return heuristique;
	}
}





































