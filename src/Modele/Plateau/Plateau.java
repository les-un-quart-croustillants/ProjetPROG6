package Modele.Plateau;

import IA.Pingouins;

import Utils.Position;

import java.util.Arrays;
import java.util.LinkedList;
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
				if(i % 2 == 0 && j == this.size-1) { // TODO : inverser les lignes longues/courtes
					tab[i][j] = new Cellule(new Position(i,j),true, 0);
				}
				else {
					tab[i][j] = new Cellule(new Position(i,j), r.nextInt(3) + 1);
				}
			}
		}
	}

	/**
	 * isInTab : si une position est dans le tableau
	 * @param p : la position
	 * @return : vrai si la position est dans le tableau,
	 * faux sinon.
	 */
	public boolean isInTab(Position p) {
		return 0 <= p.i()
				&& p.i() < size
				&& 0 <= p.j()
				&& p.j() < size;
	}

	/**
	 * getCellule : recupère une cellule
	 * @param p : la position de la cellule dans le tableau
	 * @return : l'objet Cellule
	 */
	public Cellule getCellule(Position p) {
		Cellule res = null;
		if (isInTab(p))
			res = tab[p.i()][p.j()];
		return res;
	}

	/**
	 * getNeighbours : récupère la liste des cases voisine de {@code p}
	 * @param p : la position de la case courante
	 * @return : une LinkedList de Cellule
	 * @See Cellule
	 */
	public LinkedList<Position> getNeighbours(Position p) {
		LinkedList<Position> r = new LinkedList<>();
		for (Position candidat: new Position[]{
				new Position(p.i() - 1,p.j()),
				new Position(p.i() - 1,p.j() + 1),
				new Position(p.i(),p.j() - 1),
				new Position(p.i(),p.j() + 1),
				new Position(p.i() + 1,p.j()),
				new Position(p.i() + 1,p.j() + 1)}) {
			if (isInTab(p) && (getCellule(candidat) != null))
				r.add(candidat);
		}
		return r;
	}

	private void safeAdd(LinkedList<Position> l, Position candidat) {
		if (isInTab(candidat))
			l.add(candidat);
	}

	/**
	 * accessible : les positions accessibles depuis une position
	 * @param p : position courante
	 * @return : la liste des position accessibles
	 */
	public LinkedList<Position> accessible(Position p) {
		Position candidat;
		LinkedList<Position> res = new LinkedList<>();
		for (int i = 0; i < this.size; i++) {
			if (i != p.i()) {
				candidat = new Position(i, p.j() + ((p.i()) - i));
				safeAdd(res, candidat);
				candidat = new Position(i, p.j() + ((i - p.i())));
				safeAdd(res, candidat);
			}
			if (i != p.j()) {
				candidat = new Position(p.i(), i); // ligne
				safeAdd(res, candidat);
			}
		}

		return res;
	}

	/**
	 * jouer : déplace un pinguoin si possible
	 * @param penguin : le pinguoin à déplacer
	 * @param target : la position cible
	 * @return le nombre de poissons mangés si le déplacement est possible
	 * -1 sinon.
	 */
	public int jouer(Pingouin penguin, Position target) {
		int res = -1;
		Cellule currentCell, targetCell;
		Position current = penguin.position();

		if (isInTab(target)) {
			targetCell = getCellule(target);
			if (accessible(current).contains(target) && !targetCell.isDestroyed()) {
				currentCell = getCellule(current);
				currentCell.destroy();
				penguin.setPosition(target);
				// targetCell.addPenguin(penguin);
				res = targetCell.getFish();
			}
		}
		return res;
	}

	public String tabToString() {
		String res = "[ ";
		for (Cellule[] line: this.tab) {
			res += Arrays.toString(line) + " ";
		}
		return res + "]";
	}

	public int getSize() {
		return size;
	}

	public Cellule[][] getTab() {
		return tab;
	}

	@Override
	public String toString() {
		return "{" + size + ", " + tabToString() + '}';
	}
}
