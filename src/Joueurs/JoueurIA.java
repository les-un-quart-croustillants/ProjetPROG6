package Joueurs;

import Modele.Plateau.Plateau;
import Utils.Position;

public class JoueurIA extends Joueur {

	public JoueurIA(int id){
		super(id);
	}
	
	public JoueurIA(int id,int p){
		super(id,p);
	}
	
	@Override
	public Position prochainCoup(Plateau plateau) {
		return new Position(-1,-1);
	}
}
