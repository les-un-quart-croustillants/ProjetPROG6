package Joueurs;

import java.util.ArrayList;

import Modele.Plateau.Pingouin;
import Modele.Plateau.Plateau;
import Utils.Position;

public class JoueurIA extends Joueur {

	public JoueurIA(){
		super();
	}
	
	public JoueurIA(int id){
		super(id);
	}

	public JoueurIA(int id, ArrayList<Pingouin> s){
		super(id,s);
	}
	
	@Override
	public Position prochainCoup(Plateau plateau) {
		return new Position(-1,-1);
	}
}
