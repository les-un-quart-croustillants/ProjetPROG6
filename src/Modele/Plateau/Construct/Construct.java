package Modele.Plateau.Construct;

import Modele.Plateau.Cellule;

public abstract class Construct {
	int size;
	int nb_pingouins;

	public Construct(int size, int nb_pingouins) {
		this.size = size;
		this.nb_pingouins = nb_pingouins;
	}

	abstract public Cellule[][] constructTab();
}

