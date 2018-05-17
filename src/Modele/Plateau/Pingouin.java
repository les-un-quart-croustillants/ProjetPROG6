package Modele.Plateau;

import Utils.Position;

import java.io.Serializable;

public class Pingouin implements Serializable {
	private int employeur;
	private int nbPoissonManges;
	private Position position;


	public Pingouin(int e){
		this.employeur = e;
		this.nbPoissonManges = 0;
		this.position = new Position(0,0);
	}

	public Pingouin(int e, Position p){
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

	@Override
	public boolean equals(Object obj) {
		return ((Pingouin) obj).employeur == this.employeur
				&& ((Pingouin) obj).nbPoissonManges == this.nbPoissonManges
				&& ((this.position != null) && this.position.equals(((Pingouin) obj).position));
	}

	@Override
	public Pingouin clone() {
		return new Pingouin(this.employeur, this.position.clone());
	}

	@Override
	public String toString() {
		return "{Position:"+this.position.toString()+", Joueur:"+this.employeur+", Score:"+this.nbPoissonManges+"}";
	}
}
