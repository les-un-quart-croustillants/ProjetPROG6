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
	int id;
	
	public JoueurIA(int id){
		super(id);
	}
	
	public JoueurIA(int id,int p){
		super(id,p);
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
