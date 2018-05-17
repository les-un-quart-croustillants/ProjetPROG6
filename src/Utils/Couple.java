package Utils;

import java.io.Serializable;

public class Couple<G,D> implements Serializable {
	private G i;
	private D j;
	
	public Couple(G i,D j) {
		this.i = i;
		this.j = j;
	}
	
	public G gauche() {
		return this.i;
	}
	
	public D droit() {
		return this.j;
	}
	
	public void setGauche(G o) {
		this.i = o;
	}
	
	public void setDroit(D o) {
		this.j = o;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (!(o instanceof Couple)) return false;
	    @SuppressWarnings("rawtypes")
		Couple pairo = (Couple) o;
	    return this.gauche().equals(pairo.gauche()) &&
	           this.droit().equals(pairo.droit());
	}

	@Override
	public String toString() {
		return "(" + i.toString() + "," + j.toString() + ')';
	}

	@Override
	public int hashCode() {
	    int hash = this.i.hashCode();
	    hash = hash * 31 + this.j.hashCode();
	    return hash;
	}
}
