package Modele.Plateau;

import Utils.Position;

import java.io.Serializable;

public class Pingouin implements Serializable {
	private int employeur;
	private int nbPoissonManges;
	private Position position;


	public Pingouin(int e){
		this(e, new Position(0,0));
	}

	public Pingouin(int e, Position p) {
		this(e,p,0);
	}

	public Pingouin(int e, Position p, int nbPoissonManges){
		this.employeur = e;
		this.nbPoissonManges = nbPoissonManges;
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

	public void setNbPoissonManges(int nbPoissonManges) {
		this.nbPoissonManges = nbPoissonManges;
	}

	public void mangePoisson(int nbP) {
		this.nbPoissonManges += nbP;
	}

	public void setPosition(Position p) {
		this.position = p;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Pingouin)) return false;

		Pingouin pingouin = (Pingouin) o;

		if (employeur != pingouin.employeur) return false;
		if (nbPoissonManges != pingouin.nbPoissonManges) return false;
		return position.equals(pingouin.position);
	}

	@Override
	public int hashCode() {
		int result = employeur;
		result = 31 * result + nbPoissonManges;
		result = 31 * result + position.hashCode();
		return result;
	}

	@Override
	public Pingouin clone() {
		return new Pingouin(this.employeur, this.position.clone(), this.nbPoissonManges);
	}

	@Override
	public String toString() {
		return "{Position:"+this.position.toString()+", Joueur:"+this.employeur+", Score:"+this.nbPoissonManges+"}";
	}
}
