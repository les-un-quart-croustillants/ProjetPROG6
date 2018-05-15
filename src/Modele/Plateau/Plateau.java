package Modele.Plateau;

import Modele.Plateau.Exception.BewareOfOrcasException;
import Modele.Plateau.Exception.ItsOnlyYouException;
import Modele.Plateau.Exception.PlateauException;
import Utils.Position;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import static java.lang.Integer.max;

public class Plateau {
	private int size;
	private Cellule[][] tab;
	private LinkedList<Move> history;
	private LinkedList<Move> undoList;

	public Plateau(int size) {
		this.size = size;
		this.undoList = new LinkedList<>();
		this.history = new LinkedList<>();
		this.tab = new Cellule[size][size];
		initTab();
	}

	public Plateau(Cellule[][] tab, LinkedList<Move> history, LinkedList<Move> undoList) {
		this.size = tab.length;
		this.tab = new Cellule[this.size][this.size];
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				this.tab[i][j] = tab[i][j].clone();
			}
		}
		this.history = (LinkedList<Move>) history.clone();
		this.undoList = (LinkedList<Move>) undoList.clone();
	}

	/**
	 * initTab : initialise le tableau selon une configuration attendue.
	 */
	private void initTab() {
		Random r = new Random();
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if(i % 2 == 0 && j == this.size-1) { // ligne courte
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
		int dec = (p.i() % 2 == 0) ? 0 : 1;

		for (Position candidat: new Position[]{

				new Position(p.i() - 1,p.j() - dec),
				new Position(p.i() - 1,p.j() + 1 - dec),
				new Position(p.i(),p.j() - 1),
				new Position(p.i(),p.j() + 1),
				new Position(p.i() + 1,p.j() - dec),
				new Position(p.i() + 1,p.j() + 1 - dec)}) {
			if (isInTab(p) && (getCellule(candidat) != null))
				r.add(candidat);
		}
		return r;
	}

	private boolean safeAdd(LinkedList<Position> l, Position candidat) {
		if (isInTab(candidat) && !getCellule(candidat).isObstacle()) {
			if (!l.contains(candidat))
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
		int decalage_arriere, decalage_avant;

		int borne = max(max(p.i(), this.size - p.i()), max(p.j(), this.size - p.j()));
		for (int i = 1; i <= borne && (bu || fu || bd || fd || b || f); i++) {
			if (p.i() % 2 == 0)
				decalage_arriere = i / 2;
			else
				decalage_arriere = (i + 1) / 2;
			if (bu) { // diagonale arrière haute
				candidat = new Position(p.i() - i, p.j() - decalage_arriere);
				bu = safeAdd(res, candidat);
			}
			if (bd) { // digonale arrière basse
				candidat = new Position(p.i() + i, p.j() - decalage_arriere);
				bd = safeAdd(res, candidat);
			}
			if (p.i() % 2 == 0)
				decalage_avant = (i + 1) / 2;
			else
				decalage_avant = i / 2;
			if (fu) { // diagonale avant haute
				candidat = new Position(p.i() - i, p.j() +  decalage_avant);
				fu = safeAdd(res, candidat);
			}
			if (fd) { // diagonale avant basse
				candidat = new Position(p.i() + i, p.j() + decalage_avant);
				fd = safeAdd(res, candidat);
			}

			if (b) {
				candidat = new Position(p.i(), p.j() - i); // ligne arrière
				b = safeAdd(res, candidat);
			}
			if (f) {
				candidat = new Position(p.i(), p.j() + i); // ligne avant
				f = safeAdd(res, candidat);
			}
		}
		return res;
	}

	protected int diffDir(int a, int b) {
		int res = 0;
		if (a < b)
			res = 1;
		if (b < a)
			res = -1;
		return res;
	}

	/**
	 * estAccessible : si une position est accessible depuis une autre
	 * @param current : position de départ
	 * @param target : position souhaitée
	 * @return : vrai si la position est accessible (aucun obstacle sur la trajectoire)
	 * faux sinon.
	 */
	public boolean estAccessible(Position current, Position target) {
		int coeff_i = diffDir(current.i(), target.i()),
			coeff_j = diffDir(current.j(), target.j()),
			dec;
		Position candidat = current.clone();
		int borne = Math.max(Math.abs(current.i() - target.i()), Math.abs(current.j() - target.j()));
		for (int d = 1; d <= borne; d++) {
			if (coeff_i != 0) {
				if (current.i() % 2 == 0)		// pair
					if (coeff_j == 1)				// avant
						dec = (d + 1) / 2;
					else							// arriere
						dec = d / 2;
				else							// impair
					if (coeff_j == 1)				// avant
						dec = d / 2;
					else							// arriere
						dec = (d + 1) / 2;

				candidat = new Position(current.i() + coeff_i * d, current.j() + coeff_j * dec);
			}
			else
				candidat = new Position(current.i(), current.j() + coeff_j * d);
			if (isInTab(candidat) && getCellule(candidat).isObstacle())
					return false;
			}
		return candidat.equals(target);
	}

	/**
	 * jouer : déplace un pingouin si possible
	 * @param current : position du pingouin à déplacer
	 * @param target : position cible
	 * @return le nombre de poissons mangés si le déplacement est possible
	 * -1 sinon.
	 */
	public int jouer(Position current, Position target) {
		try {
			return jouer_exp(current,target);
		} catch (PlateauException e) {
			System.err.println(e.getMessage());
			return -1;
		}
	}

	private int jouer_exp(Position current, Position target) throws PlateauException {
		int res = -1;
		Cellule currentCell, targetCell;
		Pingouin pingouin;
		if (!isInTab(current) || !isInTab(target))
			throw new BewareOfOrcasException();
		if (!getCellule(current).aPingouin())
			throw new ItsOnlyYouException(current);

		pingouin = getCellule(current).pingouin();
		targetCell = getCellule(target);

		if (estAccessible(current, target) && !targetCell.isDestroyed()) {
			history.addLast(new Move(target, current, targetCell.getFish()));
			currentCell = getCellule(current);
			currentCell.destroy();
			currentCell.setPenguin(null);
			pingouin.setPosition(target);
			targetCell.setPenguin(pingouin);
			res = targetCell.getFish();
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
		Position current = penguin.position();
		try {
			return jouer_exp(current, target);
		} catch (PlateauException e) {
			System.err.println(e.getMessage());
			return -1;
		}
	}

	/* Implementation pour Plateau.redo() */
	private int jouer(Move m) {
		try {
			return jouer_exp(m.getFrom(), m.getTo());
		} catch (PlateauException e) {
			System.err.println(e.getMessage());
			return -1;
		}
	}

	public int undo() {
		if(history.isEmpty())
			return -1;
		Move lastMove = history.removeLast();
		Position from = lastMove.getFrom(),
				to = lastMove.getTo();

		Pingouin pingouin = getCellule(to).pingouin();
		int fishAte = lastMove.getFishAte();

		this.undoList.addLast(lastMove);

		tab[from.i()][from.j()].setDestroyed(false); // Restore old cell
		getCellule(to).setPenguin(null); // remove pingouin from its current cell
		tab[to.i()][to.j()].setFish(fishAte); // restore fish on left cell
		pingouin.setPosition(from); // set pingouin to old position
		getCellule(from).setPenguin(pingouin); // set pingouin on old cell

		return fishAte;
	}

	public int redo() {
		if (undoList.isEmpty())
			return -1;
		return jouer(undoList.removeLast());
	}

	/**
	 * estIsolee : si une position est entourée d'obstacles
	 * @param p : la position
	 * @return : vrai si tous les voisins de la position p sont des obstacles
	 */
	public boolean estIsolee(Position p) {
		for(Position n : getNeighbours(p)) {
			if(! getCellule(n).isObstacle()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * destroyCell : detruit une cellule
	 * @param p : la position de la cellule
	 */
	public void destroyCell(Position p) {
		tab[p.i()][p.j()].destroy();
	}

	/**
	 * Pose un pingouin sur un case si les lunes sont alignées
	 * @param p position ou ajouter le pingouin
	 * @return true si tout c'est bien passe false sinon
	 * @author Louka Soret
	 */
	public boolean poserPingouin(Position p, Pingouin pingouin) {
		// Si la case en p n'est pas fondue et n'a pas de pingouin
		if(!this.getCellule(p).isObstacle()) {
			//Si la case en p a un seul poisson
			if(this.getCellule(p).getFish() == 1) {
				getCellule(p).setPenguin(pingouin);
				pingouin.setPosition(p);
				return true;
			}
		}
		return false;
	}

	public int getSize() {
		return size;
	}

	public Cellule[][] getTab() {
		return tab;
	}

	public LinkedList<Move> getHistory() {
		return history;
	}

	public LinkedList<Move> getUndoList() {
		return undoList;
	}

	public String pretty() {
		String res = "";
		for (Cellule[] line: this.tab) {
			for (Cellule c : line)
				res += c.pretty();
			res += "\n";
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

	@Override
	public Plateau clone() {
		return new Plateau(this.tab, this.history, this.undoList);
	}

	public boolean tabEquals(Cellule[][] tab) {
		boolean b = tab.length == this.size;
		if (b)
			for (int i = 0; i < this.size; i++) {
				for (int j = 0; j < this.size; j++) {
					b = b && tab[i][j].equals(this.tab[i][j]);
				}
			}
		return b;
	}

	@Override
	public boolean equals(Object obj) {
		return this.size == ((Plateau) obj).getSize()
				&& this.history.equals(((Plateau) obj).history)
				&& this.undoList.equals(((Plateau) obj).undoList)
				&& tabEquals(((Plateau) obj).getTab());
	}

	@Override
	public String toString() {
		return "{" + size + ", " + tabToString() + ",h:" + history + ",u:" + undoList + "]" + '}';
	}
}
