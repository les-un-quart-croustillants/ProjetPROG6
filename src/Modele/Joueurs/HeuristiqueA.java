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
			
			//ICI ON MAXIMISE CE QU'ON VEUT
			
			if(composantesCalcul.size() > composantesInit.size()) {
				
				
				for(int i = 0; i < composantesCalcul.size();i++) {
					for(int j = 0; j < composantesInit.size();j++) {
						if(composantesCalcul.get(i).toString() == composantesInit.get(j).toString())
							composantesCalcul.remove(i);
					}
				}
				
				System.out.println("------------------------------------");

				for(int i = 0; i < composantesInit.size();i++) {
					System.out.println("les composantes avant : "+composantesInit.get(i));
				}
				
				for(int i = 0; i < composantesCalcul.size();i++) {
						System.out.println("les composantes apres : "+composantesCalcul.get(i));
				}
				System.out.println("------------------------------------");
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
					if(nbPoissonsComposanteList.get(i) < UtilsIA.nbPoissonsPlateau(pInitial)/12 && nbPingouinAlliesList.get(i) == 0 && nbPingouinEnnemisList.get(i) == 0) {
						heuristique = heuristique -5;
					}
					// TODO : DIVISER PAR UN TRUC EN RAPPORT AVEC LE NOMBRE DE PINGUOINS ENFERMES
					if((nbPoissonsComposanteList.get(i) < UtilsIA.nbPoissonsPlateau(pInitial)/5) && nbPingouinAlliesList.get(i) == 0 && nbPingouinEnnemisList.get(i) > 0) {
						heuristique = heuristique -10;
					}
					if((nbPoissonsComposanteList.get(i) > UtilsIA.nbPoissonsPlateau(pInitial)/12) && nbPingouinAlliesList.get(i) == 0 && nbPingouinEnnemisList.get(i) >= 1) {
						heuristique = heuristique +1000;
					}
					if((nbPoissonsComposanteList.get(i) < UtilsIA.nbPoissonsPlateau(pInitial)/8) && nbPingouinAlliesList.get(i) == 0 && nbPingouinEnnemisList.get(i) == 0) {
						heuristique = heuristique -5;
					}
					if((nbPoissonsComposanteList.get(i) < UtilsIA.nbPoissonsPlateau(pInitial)/8) && nbPingouinAlliesList.get(i) > 0 && nbPingouinEnnemisList.get(i) == 0) {
						heuristique = heuristique -1000;
					}
					if((nbPoissonsComposanteList.get(i) < UtilsIA.nbPoissonsPlateau(pInitial)/8) && nbPingouinAlliesList.get(i) == 0 && nbPingouinEnnemisList.get(i) > 0) {
						heuristique = heuristique +1000;
					}
					if((nbPoissonsComposante > UtilsIA.nbPoissonsPlateau(pInitial)/10) && nbPingouinAllies > 0 && nbPingouinEnnemis == 0) {
						heuristique = heuristique +1000;
					}
					if((nbPoissonsComposante < UtilsIA.nbPoissonsPlateau(pInitial)/8) && nbPingouinAllies > 0 && nbPingouinEnnemis > 0) {
						heuristique = heuristique +3;
					}
					
				}
				
				
				/*for(int i = 0; i < composantesCalcul.size();i++) {
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
						if((nbPoissonsComposante > UtilsIA.nbPoissonsPlateau(pInitial)/8) && nbPingouinAllies > 0 && nbPingouinEnnemis == 0) {
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
				}*/
			}
		return heuristique;
	}
}





































