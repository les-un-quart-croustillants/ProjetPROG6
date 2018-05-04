package Vue;

import com.sun.javafx.geom.Vec2d;

import Modele.Plateau.Plateau;

public class PlateauGraphique extends GameObject {
	public Plateau plateau;
	public Case[][] cases;
	public Vec2d offset;

	public PlateauGraphique(Plateau plateau) {
	}

	public void start() {
		offset = new Vec2d(100, 100);
		plateau = new Plateau(8);
		cases = new Case[plateau.getSize()][plateau.getSize()];
		for (int i = 0; i < plateau.getSize(); i++) {
			for(int j=0;j<plateau.getSize()-(1-i%2);j++){
				cases[i][j] = new Case(this, i, j);
				InterfaceGraphique.pc.gameObjects.add(cases[i][j]);
			}
		}
	}
}
