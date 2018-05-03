package Modele.Plateau;

import Utils.Position;

import java.util.Random;

public class Plateau {
	private int size;
	private Cellule[][] tab;

	public Plateau(int size) {
		this.size = size;
		this.tab = new Cellule[size][size];
		initTab();
	}

	/**
	 * initTab : initialise le tableau selon une configuration attendue.
	 */
	private void initTab() {
		Random r = new Random();
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if(i % 2 == 0 && j == this.size-1) {
					tab[i][j] = new Cellule(new Position(i,j),true);
				}
				else {
					tab[i][j] = new Cellule(new Position(i,j));
				}
			}
		}
	}
}
