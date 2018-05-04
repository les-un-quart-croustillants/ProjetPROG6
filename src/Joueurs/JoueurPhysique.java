package Joueurs;

import Modele.Plateau.Plateau;
import Utils.Position;

public class JoueurPhysique extends Joueur {
	@Override
	public int jouerCoup(Plateau plateau,Pingouin pingouin, Position goal) throws Exception {
		int res;
		
		if(pingouin.employeur() == this.id()) { //test si le pingouin appartient bien a ce joueur
			if(this.squad().contains(pingouin)) { //test si le joueur reconnais bien le pingouin
				this.removeSquad(pingouin);
				res = plateau.jouer(pingouin, goal);
				this.addSquad(pingouin);
				return res;
			} else {
				throw new Exception("Le pingouin en "+pingouin.position()+" n'est pas reconnus par le joueur "+this.id()+" (erreur interne).");
			}
		} else {
			throw new Exception("Le pingouin en "+pingouin.position()+" n'appartient pas au joueur "+this.id()+" mais au joueur "+pingouin.employeur()+".");
		}
	}
}
