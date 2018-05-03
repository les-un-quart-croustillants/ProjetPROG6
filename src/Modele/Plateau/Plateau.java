package Modele.Plateau;

public class Plateau {
	private int size;
	private Cellule[][] tab;

	public Plateau(int size) {
		this.size = size;
		this.tab = new Cellule[size][size];
	}
}
