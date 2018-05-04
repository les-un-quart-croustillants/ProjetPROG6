package Joueurs;

import java.util.ArrayList;

import Modele.Plateau.Plateau;
import Utils.Position;

public class JoueurPhysique extends Joueur {
	public JoueurPhysique(){
		super();
	}
	
	public JoueurPhysique(int id){
		super(id);
	}

	public JoueurPhysique(int id, ArrayList<Pingouin> s){
		super(id,s);
	}
	
	@Override
	public int jouerCoup(Plateau plateau,Pingouin pingouin, Position goal) throws Exception {
		int res;
		
		if(pingouin.employeur() == this.id()) { //test si le pingouin appartient bien a ce joueur
			if(this.squad().contains(pingouin)) { //test si le joueur reconnais bien le pingouin
				res = plateau.jouer(this.squad().get(this.squad().indexOf(pingouin)), goal);
				return res;
			} else {
				throw new Exception("Le pingouin en "+pingouin.position()+" n'est pas reconnus par le joueur "+this.id()+" (erreur interne).");
			}
		} else {
			throw new Exception("Le pingouin en "+pingouin.position()+" n'appartient pas au joueur "+this.id()+" mais au joueur "+pingouin.employeur()+".");
		}
	}
}
