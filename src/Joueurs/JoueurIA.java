package Joueurs;

import Modele.Plateau.Plateau;
import Utils.Position;

public class JoueurIA extends Joueur {
	@Override
	public Position prochainCoup(Plateau plateau) {
		return new Position(0,0);
	}
}
