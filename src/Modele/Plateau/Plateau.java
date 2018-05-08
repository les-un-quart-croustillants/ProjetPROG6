package Modele.Plateau;

import Utils.Position;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import static java.lang.Integer.max;

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
				if(i % 2 == 1 && j == this.size-1) {
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

	private boolean safeAdd(LinkedList<Position> l, Position candidat) {
		if (isInTab(candidat) && !getCellule(candidat).isObstacle()) {
			l.add(candidat);
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * accessible : les positions accessibles depuis une position
	 * @param p : position courante
	 * @return : la liste des position accessibles
	 */
	public LinkedList<Position> accessible(Position p) {
		Position candidat;
		LinkedList<Position> res = new LinkedList<>();
		boolean bu = true, // diagonale arrière haute continue
				fu = true, // diagonale avant haute continue
				bd = true, // diagonale arrière basse continue
				fd = true, // diagonale avant basse continue
				b = true, // ligne arrière continue
				f = true; // ligne avant continue

		int borne = max(max(p.i(), this.size - p.i()), max(p.j(), this.size - p.j()));
		for (int i = 0; i <= borne; i++) {
			if (i != 0) {
				if (bu) { // diagonale arrière haute
					candidat = new Position(p.i() - i, p.j() - i);
					bu = safeAdd(res, candidat);
				}
				if (fu) { // diagonale avant haute
					candidat = new Position(p.i() - i, p.j() + i);
					fu = safeAdd(res, candidat);
				}
				if (bd) { // digonale arrière basse
					candidat = new Position(p.i() + i, p.j() - i);
					bd = safeAdd(res, candidat);
				}
				if (fd) { // diagonale avant basse
					candidat = new Position(p.i() + i, p.j() + i);
					fd = safeAdd(res, candidat);
				}
			}
			if (i != 0) {
				if(b) {
					candidat = new Position(p.i(), p.j() - i); // ligne arrière
					b = safeAdd(res, candidat);
				}
				if(f) {
					candidat = new Position(p.i(), p.j() + i); // ligne avant
					f = safeAdd(res, candidat);
				}
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
