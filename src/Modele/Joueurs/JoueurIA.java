package Modele.Joueurs;

import Modele.Plateau.Plateau;
import Utils.Couple;
import Utils.Position;

public class JoueurIA extends Joueur {
	int id;
	
	public JoueurIA(int id){
		super(id);
	}
	
	public JoueurIA(int id,int p){
		super(id,p);
	}
	
	@Override
	public Couple<Position,Position> prochainCoup(Plateau plateau) {
		return new Couple<Position,Position>(new Position(-1,-1),new Position(-1,-1));
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
