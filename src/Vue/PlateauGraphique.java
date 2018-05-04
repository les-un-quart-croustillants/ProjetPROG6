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
		plateau = new Plateau(5);
		cases = new Case[plateau.getSize()][plateau.getSize()];
		for (int i = 0; i < plateau.getSize(); i++) {
				cases[i][0] = new Case(this, i, 0);
				InterfaceGraphique.pc.gameObjects.add(cases[i][0]);
		}
	}
}
