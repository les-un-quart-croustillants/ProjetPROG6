package Vue.GameObject;

import java.awt.Point;

import Modele.Plateau.Plateau;
import Utils.Position;
import Vue.Donnees.Niveau;
import Vue.Cadre.PlateauCadre;

public class PlateauGraphique extends GameObject {
	public Plateau plateau;
	public Case[][] cases;
	public int tailleCase = 60; // taille d'une case en pixel
	public int espacement = 10;

	private PlateauCadre plateauCadre;
	private float tailleRelative = 0.8f; // facteur de taille relative au cadre
											// PlateauCadre(de 0 � 1)
	private Niveau niveau;
	public PlateauGraphique(Plateau plateau, PlateauCadre pc, Niveau n) {
		this.plateau = plateau;
		this.plateauCadre = pc;
		this.niveau = n;
		init(pc);
	}

	@Override
	public void start() {
		update();
	}
	
	@Override
	public void update() {
		if (plateauCadre.getWidth() / plateau.getSize() < plateauCadre.getHeight() / (plateau.getSize() * 0.75)) {
			tailleCase = (int) (plateauCadre.getWidth() * tailleRelative / plateau.getSize());
		} else {
			tailleCase = (int) (plateauCadre.getHeight() * tailleRelative / (plateau.getSize() * 0.75));
		}
		position.x = (float) (plateauCadre.getWidth() / 2 - plateau.getSize() * (tailleCase + espacement) / 2);
		position.y = (float) (plateauCadre.getHeight() / 2 - plateau.getSize() * (tailleCase + espacement) * 0.25);
	}

	/**
	 * init : initialisation (appel�e par les constructeurs)
	 * 
	 * @param pc
	 *            : le PlateauCadre dans lequel se situe le PlateauGraphique.
	 */
	public void init(PlateauCadre pc) {
		cases = new Case[plateau.getSize()][plateau.getSize()];
		for (int i = 0; i < plateau.getSize(); i++) {
			for (int j = 0; j < plateau.getSize() - (1 - i % 2); j++) {
				cases[i][j] = new Case(this, i, j, niveau);
				pc.gameObjects.get(0).add(cases[i][j]);
				if (plateau.getCellule(new Position(i, j)).isDestroyed()) {
					cases[i][j].detruire();
				}
			}
		}
		this.setOnDestroyHandler(new OnDestroyHandler() {
			@Override
			public void handle() {
				for (int i = 0; i < plateau.getSize(); i++) {
					for (int j = 0; j < plateau.getSize() - (1 - i % 2); j++) {
						cases[i][j].detruire();
					}
				}
			}
		});
	}

	/**
	 * XYtoCase : Permet de passer de coordonn�es (x,y) en pixel (de la souris
	 * par exemple) � une case du plateau graphique.
	 * 
	 * @param p
	 *            : un point
	 * @return : la case du plateau graphique qui contient ce point, ou null
	 *         s'il n'y en a pas.
	 */
	public Case XYtoCase(Point p) {
		for (int i = 0; i < plateau.getSize(); i++) {
			for (int j = 0; j < plateau.getSize() - (1 - i % 2); j++) {
				Case c = cases[i][j];
				if (!plateau.getCellule(new Position(i,j)).isDestroyed() && c.collision(p)) {
					return c;
				}
			}
		}
		return null;
	}

}
