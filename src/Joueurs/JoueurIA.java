package Joueurs;

import java.util.ArrayList;

import Modele.Plateau.Plateau;
import Utils.Position;

public class JoueurIA extends Joueur {

	public JoueurIA(int id){
		super(id);
	}
	
	@Override
	public Position prochainCoup(Plateau plateau) {
		return new Position(-1,-1);
	}
}
