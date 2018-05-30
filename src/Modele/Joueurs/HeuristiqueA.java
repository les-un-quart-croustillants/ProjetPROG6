package Modele.Joueurs;
import java.util.LinkedList;
import Modele.Plateau.Plateau;
import Utils.Position;
import Utils.Couple;
import java.util.ArrayList;


public class HeuristiqueA {
	/**
	 * @param pCalcule : le plateau de jeu
	 * @param coup : le dernier coup a jouer
	 * @param id : l'id du joueur courant
	 * @param debase : le plateau initial
	 * @param scores : les scores courants
	 * @return : l'heuristique de la feuille
	 */
	public static int calcul(Plateau pCalcule,Couple<Position,Position> coup,int id,Plateau debase,ArrayList<ArrayList<Integer>> scores) {
		int heuristique = 50;

		//on compte le nombre de pingouins
		int nbPingouins = 0;
		for(int i = 0; i < debase.getSize();i++) {
			for(int j = 0; j < debase.getSize();j++) {
				if(debase.getCellule(new Position(i,j)).aPingouin())
					nbPingouins++;
			}
		}	
		
		// on calcule les composantes connexes du plateau de jeu donne
		LinkedList<LinkedList<Position>> composantesCalcul = UtilsIA.listeConnexeComposante(pCalcule);
		
		//on adapte l'heuristique en fonction des scores simules
		for(int i = 0; i < scores.size();i++) {
			if(scores.get(i).get(0) != id) {
				heuristique = heuristique + (scores.get(id).get(1)-scores.get(i).get(1))*2; 
			}
		}
		

		// on regarde si la partie est finie
		boolean finish = true;
		for(int i = 0; i < pCalcule.getSize();i++) {
			for(int j = 0; j < pCalcule.getSize();j++) {
				if(pCalcule.getCellule(new Position(i,j)).aPingouin() && !pCalcule.estIsolee(new Position(i,j)))
					finish = false;
			}
		}
		//on regarde si on a le plus grand score
		int monscore = scores.get(id).get(1);
		boolean gagne = true;
		for(int i = 0; i < scores.size();i++) {
			if(scores.get(i).get(0) != id) {
				if(scores.get(i).get(1) > monscore) {
					gagne = false;
				}
			}
		}
		//si la partie est finie et qu'on a le score maximal : on gagne
		if(gagne && finish) {
			return 10000;
		}
		
		//on adapte l'heuristique en fonction du nombre de pingouins isolés sur un seul bout de banquise
		for(int i = 0;i < pCalcule.getSize();i++) {
			for(int j = 0;j < pCalcule.getSize();j++) {
				LinkedList<Position> voisincourant = pCalcule.getNeighbours(new Position(i,j));
				if(pCalcule.getCellule(new Position(i,j)).aPingouin() && pCalcule.getCellule(new Position(i,j)).pingouin().employeur() == id) {
					if(voisincourant.size() == 0)
						heuristique = heuristique-50;
				}
				if(pCalcule.getCellule(new Position(i,j)).aPingouin() && pCalcule.getCellule(new Position(i,j)).pingouin().employeur() != id) {
					if(voisincourant.size() == 0)
						heuristique = heuristique+50;

				}
			}
		}

		//ICI ON MAXIMISE CE QU'ON VEUT 

		

		LinkedList<Integer> nbPingouinEnnemisList = new LinkedList<Integer>();
		LinkedList<Integer> nbPingouinAlliesList = new LinkedList<Integer>();
		LinkedList<Integer> nbPoissonsComposanteList = new LinkedList<Integer>();
		
		//pour toute les composantes connexes
		for(int i = 0; i < composantesCalcul.size();i++) {
			int nbPingouinAllies = 0;
			int nbPingouinEnnemis =0;
			int nbPoissonsComposante = 0;
			//on compte leur nombre de pingouins allie, ennemis et leur nombre de poissons
			if(composantesCalcul.get(i).size() > 0 ) {
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
			}
			nbPingouinEnnemisList.add(nbPingouinEnnemis);
			nbPingouinAlliesList.add(nbPingouinAllies);
			nbPoissonsComposanteList.add(nbPoissonsComposante);
			
			//et on compare ces chiffres pour juger la configuration en adaptant l'heuristique
			
			//si on perd
			if((nbPoissonsComposanteList.get(i) > UtilsIA.nbPoissonsPlateau(debase)/2) && nbPingouinAlliesList.get(i) == 0 && nbPingouinEnnemisList.get(i) > 0) {
				heuristique = -10000;
			}
			
			 //si une petit ile est laissée seule
			if(nbPingouinAlliesList.get(i) == 0 && nbPingouinEnnemisList.get(i) == 0) {
				heuristique = heuristique - nbPoissonsComposanteList.get(i)*10;
			}
			
			//si une portion du plateau est laissee a l'ennemi
			if(nbPingouinAlliesList.get(i) == 0 && nbPingouinEnnemisList.get(i) > 0) {
				heuristique = heuristique - nbPoissonsComposanteList.get(i) + 10*nbPingouinEnnemisList.get(i);
			}
			
			//si on a un pingouinennemi est seul sur une grosse partie de banquise
			if((nbPoissonsComposanteList.get(i) > UtilsIA.nbPoissonsPlateau(debase)/nbPingouins) && nbPingouinAlliesList.get(i) == 0 && nbPingouinEnnemisList.get(i) == 1) {
				heuristique = heuristique - nbPoissonsComposanteList.get(i);
			}
			
			//si on a un pingouinennemi est seul sur une petite partie de banquise
			if((nbPoissonsComposanteList.get(i) < UtilsIA.nbPoissonsPlateau(debase)/nbPingouins) && nbPingouinAlliesList.get(i) == 0 && nbPingouinEnnemisList.get(i) == 1) {
				heuristique = heuristique + nbPoissonsComposanteList.get(i);
			}
			
			// si on est seuls sur la banquise
			if(nbPingouinAlliesList.get(i) > 0 && nbPingouinEnnemisList.get(i) == 0) {
				heuristique = heuristique + nbPoissonsComposanteList.get(i) - 10*nbPingouinAlliesList.get(i);
			}
			
			//si on a un pingouin seul sur une grosse pasrtie de banquise
			if((nbPoissonsComposanteList.get(i) > UtilsIA.nbPoissonsPlateau(debase)/nbPingouins) && nbPingouinAlliesList.get(i) == 1 && nbPingouinEnnemisList.get(i) == 0) {
				heuristique = heuristique + 2*nbPoissonsComposanteList.get(i);
			}
			
		}
		return heuristique;
	}
}





































