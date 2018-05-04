package Joueurs;

import Utils.Position;

public class Pingouin {
	private int employeur;
	private int nbPoissonManges;
	private Position position;

	Pingouin(int e){
		this.employeur = e;
		this.nbPoissonManges = 0;
		this.position = new Position(0,0);
	}

	Pingouin(int e, Position p){
		this.employeur = e;
		this.nbPoissonManges = 0;
		this.position = p;
	}

	public int employeur() {
		return this.employeur;
	}

	public int nbPoissonManges() {
		return this.nbPoissonManges;
	}

	public Position position() {
		return this.position;
	}

	public void setEmployeur(int e) {
		this.employeur = e;
	}

	public void setNbPoissonManges(int nbP) {
		this.nbPoissonManges = nbP;
	}

	public void setPosition(Position p) {
		this.position = p;
	}
}
