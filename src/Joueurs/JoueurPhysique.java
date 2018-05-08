package Joueurs;

import Modele.Plateau.Pingouin;
import Modele.Plateau.Plateau;
import Utils.Position;

public class JoueurPhysique extends Joueur {
	
	public JoueurPhysique(int id){
		super(id);
	}
	
	public JoueurPhysique(int id,int p){
		super(id,p);
	}
	
	@Override
	public boolean posePingouin(Plateau plateau,Position position) {
		boolean res;
		res = plateau.poserPingouin(position, new Pingouin(this.id()));
		if(res) {
			super.addScoreFish(1);
		}
		return res;
	}
	
	@Override
	public int jouerCoup(Plateau plateau,Position start, Position goal) throws Exception {
		int res;
		
		if(plateau.getCellule(start).aPingouin()) { //test si le pingouin existe
			if(plateau.getCellule(start).pingouin().employeur() == this.id()) { //test si le pingouin appartient bien a ce joueur
				res = plateau.jouer(start,goal);
				if(res > 0) {
					super.addScoreFish(res);	
				}
				if(res >= 0) {
					this.addScoreDestroyed(1);	
				}
				return res;
			} else {
				throw new Exception("Le pingouin en "+start+" n'appartient pas au joueur "+this.id()+".");
			}
		} else {
			throw new Exception("La case en "+start+" ne contient pas de pingouin.");
		}
	}
}
