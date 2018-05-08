package Joueurs;

import java.util.ArrayList;

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
	public int jouerCoup(Plateau plateau,Position start, Position goal) throws Exception {
		int res;
		
		if(plateau.foo(start)) { //test si le pingouin existe
			if(plateau.foo2(start).employeur() == this.id()) { //test si le pingouin appartient bien a ce joueur
				res = plateau.jouer(start,goal);
				if(res > 0) {
					super.addScoreFish(res);	
				}
				this.addScoreDestroyed(1);
				return res;
			} else {
				throw new Exception("Le pingouin en "+start+" n'appartient pas au joueur "+this.id()+".");
			}
		} else {
			throw new Exception("La case en "+start+" ne contient pas de pingouin.");
		}
	}
}
