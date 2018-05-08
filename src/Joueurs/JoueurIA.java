package Joueurs;

import java.util.ArrayList;

import Modele.Plateau.Plateau;
import Utils.Position;

public class JoueurIA extends Joueur {
	ArrayList<Position> pingouinsPos;

	public JoueurIA(int id){
		super(id);
		this.pingouinsPos = new ArrayList<Position>();
	}
	
	public JoueurIA(int id,int p){
		super(id,p);
		this.pingouinsPos = new ArrayList<Position>();
	}
	
	public ArrayList<Position> pingouinsPos(){
		return this.pingouinsPos;
	}
	
	public void setPingouinsPos(ArrayList<Position> pp) {
		this.pingouinsPos = pp;
	}
	
	@Override
	public Position prochainCoup(Plateau plateau) {
		return new Position(-1,-1);
	}
}
