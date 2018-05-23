package Modele.Joueurs;
import java.util.LinkedList;
import Modele.Plateau.Plateau;
import Utils.Position;
import Utils.Couple;
import java.util.ArrayList;


public class HeuristiqueA {
		public static int calcul(Plateau pInitial, LinkedList<Couple<Position,Position>> coups,int id,Plateau debase,ArrayList<ArrayList<Integer>> score) {
			ArrayList<ArrayList<Integer>> scores = (ArrayList<ArrayList<Integer>>) score.clone();
			int heuristique = 50;

			/*LinkedList<Position> composantePingouin = UtilsIA.composanteConnexePingouin(pInitial,pInitial.getCellule(coups.get(0).gauche()).pingouin());
			
			int nbpingouinennemiscomposante = 0;
			for(int j =0; j < composantePingouin.size();j++) {
				if(pInitial.getCellule(composantePingouin.get(j)).aPingouin() && pInitial.getCellule(composantePingouin.get(j)).pingouin().employeur() != id)
					nbpingouinennemiscomposante++;
			}
			if(nbpingouinennemiscomposante == 0)
				return -1000;*/
			
			for(int i = 0; i < scores.size();i++) {
				if(scores.get(i).get(0) != id) {
					heuristique = heuristique + (scores.get(id).get(1)-scores.get(i).get(1))*2; 
				}
			}
			Plateau pCalcule = UtilsIA.plateaucoup(coups, pInitial.clone());
			LinkedList<LinkedList<Position>> composantesInit = UtilsIA.listeConnexeComposante(debase);
			LinkedList<LinkedList<Position>> composantesCalcul = UtilsIA.listeConnexeComposante(pCalcule);
			
			boolean finish = true;
			for(int i = 0; i < pCalcule.getSize();i++) {
				for(int j = 0; j < pCalcule.getSize();j++) {
					if(pCalcule.getCellule(new Position(i,j)).aPingouin() && !pCalcule.estIsolee(new Position(i,j)))
						finish = false;
				}
			}
			
			
			int monscore = scores.get(id).get(1);
			boolean gagne = true;
			for(int i = 0; i < scores.size();i++) {
				if(scores.get(i).get(0) != id) {
					if(scores.get(i).get(1) > id) {
						gagne = false;
					}
				}
			}
			if(gagne && finish)
				return 100000;
			//ICI ON MAXIMISE CE QU'ON VEUT 
			
			
			
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
									heuristique = heuristique + 9;
								if (pCalcule.getCellule(composantesCalcul.get(i).get(j)).getFish() == 2)
									heuristique = heuristique + 6;
							}
							else if(pCalcule.getCellule(composantesCalcul.get(i).get(j)).aPingouin() && pCalcule.getCellule(composantesCalcul.get(i).get(j)).pingouin().employeur() != id) {	
								nbPingouinEnnemis++;
								if (pCalcule.getCellule(composantesCalcul.get(i).get(j)).getFish() == 3)
									heuristique = heuristique - 9;
								if (pCalcule.getCellule(composantesCalcul.get(i).get(j)).getFish() == 2)
									heuristique = heuristique - 6;
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
						heuristique = heuristique - nbPoissonsComposanteList.get(i)*2;
					}
					
					//si une portion du plateau est laissee a l'ennemi
					if(nbPingouinAlliesList.get(i) == 0 && nbPingouinEnnemisList.get(i) > 0) {
						heuristique = heuristique - nbPoissonsComposanteList.get(i) + 10*nbPingouinEnnemisList.get(i);
					}
					//si on a un pingouinennemi est seul sur une grosse partie de banquise
					if((nbPoissonsComposanteList.get(i) > UtilsIA.nbPoissonsPlateau(pInitial)/4) && nbPingouinAlliesList.get(i) == 0 && nbPingouinEnnemisList.get(i) == 1) {
						heuristique = heuristique - 100;
					}
					//si on a un pingouinennemi est seul sur une petite partie de banquise
					if((nbPoissonsComposanteList.get(i) < UtilsIA.nbPoissonsPlateau(pInitial)/8) && nbPingouinAlliesList.get(i) == 0 && nbPingouinEnnemisList.get(i) == 1) {
						heuristique = heuristique + 200;
					}
					// si on est seuls sur la banquise
					if(nbPingouinAlliesList.get(i) > 0 && nbPingouinEnnemisList.get(i) == 0) {
						heuristique = heuristique + nbPoissonsComposanteList.get(i) - 4*nbPingouinAlliesList.get(i);
					}
					//si on a un pingouin seul sur une grosse pasrtie de banquise
					if((nbPoissonsComposanteList.get(i) > UtilsIA.nbPoissonsPlateau(pInitial)/8) && nbPingouinAlliesList.get(i) == 1 && nbPingouinEnnemisList.get(i) == 0) {
						heuristique = heuristique + 200;
					}
					

				}
			}else {
				for(int i = 0; i < composantesCalcul.size();i++) {
					for(int j = 0; j < composantesCalcul.get(i).size();j++) {
						if(pCalcule.getCellule(composantesCalcul.get(i).get(j)).aPingouin() && pCalcule.getCellule(composantesCalcul.get(i).get(j)).pingouin().employeur() == id) {
							if (pCalcule.getCellule(composantesCalcul.get(i).get(j)).getFish() == 3)
								heuristique = heuristique + 3;
							if (pCalcule.getCellule(composantesCalcul.get(i).get(j)).getFish() == 2)
								heuristique = heuristique + 3;
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





































