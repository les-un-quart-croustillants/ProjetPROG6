package Vue.GameObject;

import java.awt.Point;

import Modele.Plateau.Plateau;
import Vue.Cadre.PlateauCadre;

public class PlateauGraphique extends GameObject {
	public Plateau plateau;
	public Case[][] cases;
	public int tailleCase = 60; // taille d'une case en pixel

	private PlateauCadre plateauCadre;
	private float tailleRelative = 0.8f; // facteur de taille relative au cadre PlateauCadre(de 0 à 1)
	
	public PlateauGraphique(Plateau plateau, PlateauCadre pc) {
		this.plateau = plateau;
		this.plateauCadre = pc;
		init(pc);
	}

	@Override
	public void update() {
		if (plateauCadre.getWidth() / plateau.getSize() < plateauCadre.getHeight() / (plateau.getSize()*0.75)) {
			tailleCase = (int) (plateauCadre.getWidth() * tailleRelative / (plateau.getSize()*1.5));
		}
		else {
			tailleCase = (int) (plateauCadre.getHeight() * tailleRelative / (plateau.getSize()*0.75*1.5));
		}
		position.x = plateauCadre.getWidth() / 2 - plateau.getSize() * tailleCase*0.75;
		position.y = plateauCadre.getHeight() *0.45 - plateau.getSize() * tailleCase * 0;
	}

	/**
	 * init : initialisation (appelée par les constructeurs)
	 * @param pc :  le PlateauCadre dans lequel se situe le PlateauGraphique.
	 */
	public void init(PlateauCadre pc) {
		cases = new Case[plateau.getSize()][plateau.getSize()];
		for (int i = 0; i < plateau.getSize(); i++) {
			for (int j = 0; j < plateau.getSize() - (1 - i % 2); j++) {
				cases[i][j] = new Case(this, i, j);
				pc.gameObjects.add(cases[i][j]);
			}
		}
		//pc.gameObjects.add(new PingouinGraphique(new Pingouin(1, new Position(5,5)), this));
	}
	
	/**
	 * XYtoCase : Permet de passer de coordonnées (x,y) en pixel (de la souris par exemple) à une case du plateau graphique.
	 * @param p : un point
	 * @return : la case du plateau graphique qui contient ce point, ou null s'il n'y en a pas.
	 */
	public Case XYtoCase(Point p) {
		for (int i = 0; i < plateau.getSize(); i++) {
			for (int j = 0; j < plateau.getSize()-(1-i%2); j++) {
				Case c = cases[i][j];
				if (c.collision(p)) {
					return c;
				} 
			}
		}
		return null;
	}
}
