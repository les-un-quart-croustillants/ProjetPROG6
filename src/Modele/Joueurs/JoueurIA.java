package Modele.Joueurs;

import Modele.Plateau.Plateau;
import Utils.Couple;
import Utils.Position;
//import java.util.LinkedList;
//import Modele.Plateau.Cellule;
//import Modele.Plateau.Pingouin;
//import java.util.HashMap;
//import java.util.Random;

public class JoueurIA extends Joueur {
	
	public JoueurIA(int id,Difficulte d){
		super(id,d);
	}

	@Override
	public int delay() {
		switch(this.difficulte) {
			case FACILE:
				return 0;
			case MOYEN:
				return 500;
			case DIFFICILE:
				return 1000;
			default:
				return 0;
		}
	}
	
	public Couple<Position,Position> coupDifficile(Plateau plateau) {
		return UtilsIA.jouerCoupDifficile(plateau,super.id());
	}
	
	@Override
	public Couple<Position,Position> prochainCoup(Plateau plateau) {
		return UtilsIA.jouerCoupFacile(plateau,super.id());
	}
	
	@Override
	public Position prochainePosePingouin(Plateau plateau) {
		return UtilsIA.bestplace(plateau, super.id());
	}
	
	@Override
	public boolean estIA() {
		return true;
	}
}
