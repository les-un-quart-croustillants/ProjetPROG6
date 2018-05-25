package Modele.Plateau;

import Modele.Plateau.Construct.Construct;
import Modele.Plateau.Exception.BewareOfOrcasException;
import Modele.Plateau.Exception.ItsOnlyYouException;
import Modele.Plateau.Exception.PlateauException;
import Utils.Couple;
import Utils.Position;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import static java.lang.Integer.max;

public class Plateau implements Serializable {
	static final Position source = new Position(-1,-1);
	private int size;
	private Cellule[][] tab;
	private LinkedList<Move> history;
	private LinkedList<Move> undoList;
	private int nb_fish;

	Plateau() {
		this(3, 1);
	}

	@Deprecated /* Pour eviter les effets silencieux -> use Plateau(size, nb_pingouins) or Plateau(size, nb_case_1, nb_case_2, nb_case_3) */
	public Plateau(int size) {
		this(size, 2);
	}

	public Plateau(int size, int nb_pingouin) {
		this.size = size;
		this.undoList = new LinkedList<>();
		this.history = new LinkedList<>();
		this.tab = new Cellule[size][size];

		initTab(nb_pingouin);
	}

	public Plateau(int size, int nb_fish_1, int nb_fish_2, int nb_fish_3) {
		this.size = size;
		this.undoList = new LinkedList<>();
		this.history = new LinkedList<>();
		this.tab = new Cellule[size][size];

		initTab(nb_fish_1, nb_fish_2, nb_fish_3);
	}

	@Deprecated
	public Plateau(int size, Construct c) {
		this(size,1,c);
	}

	public Plateau(int size, int nb_pingouins, Construct c) {
		this.size = size;
		this.tab = new Cellule[size][size];
		this.history = new LinkedList<>();
		this.undoList = new LinkedList<>();
		this.nb_fish = 0;
		initTab(c, nb_pingouins);
	}

	@SuppressWarnings("unchecked")
	public Plateau(Cellule[][] tab, LinkedList<Move> history, LinkedList<Move> undoList) {
		this.size = tab.length;
		this.tab = new Cellule[this.size][this.size];
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				this.tab[i][j] = tab[i][j].clone();
				nb_fish += this.tab[i][j].getFish();
			}
		}
		this.history = (LinkedList<Move>) history.clone();
		this.undoList = (LinkedList<Move>) undoList.clone();
	}

	/**
	 * initTab : initialise le tableau selon une configuration attendue.
	 * @param nb_fish_1 : nombre de cases à un poisson
	 * @param nb_fish_2 : nombre de cases à deux poissons
	 * @param nb_fish_3 : nombre de cases à 3 poissons
	 */
	private void initTab(int nb_fish_1, int nb_fish_2, int nb_fish_3) {
		int coeff,
			candidat,
			borne = nb_fish_1 + nb_fish_2 + nb_fish_3,
			nb_cases = (size * size) - (size + 1) / 2;
		int[] nb = new int[3];
		Arrays.fill(nb, 0);

		Random r = new Random();
		if (borne > nb_cases) {
			borne = nb_cases;
			if (nb_fish_1 > borne)
				nb_fish_1 = borne;
		}
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if(i % 2 == 0 && j == this.size-1) { // ligne courte
					tab[i][j] = new Cellule(new Position(i,j),true, 0);
				}
				else {
					candidat = -1;
					while (candidat == -1) {
						coeff = r.nextInt(borne);
						if (coeff <= nb_fish_1 && nb[0] < nb_fish_1) {
							candidat = 1;
						} else if (coeff <= (nb_fish_1 + nb_fish_2) && nb[1] < nb_fish_2) {
							candidat = 2;
						} else if (coeff <= (nb_fish_1 + nb_fish_2 + nb_fish_3)  && nb[2] < nb_fish_3) {
							candidat = 3;
						} else {
							if (nb[0] < nb_fish_1)
								candidat = 1;
							else if (nb[1] < nb_fish_2)
								candidat = 2;
							else if (nb[2] < nb_fish_3)
								candidat = 3;
							else
								candidat = r.nextInt(3)  + 1;
						}
					}
					nb[candidat - 1]++;
					tab[i][j] = new Cellule(new Position(i, j), candidat);
				}
			}
		}
		this.nb_fish = nb[0] + nb[1] * 2 + nb[2] * 3;
	}

	private void initTab(Construct c, int borne) {
		int tmp, nb_1 = 0;
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (!(((i % 2) == 0) && (j == (size - 1)))) {
					tmp = c.getCellValue(i,j);
					tab[i][j] = new Cellule(new Position(i,j), false, tmp);
					nb_fish += tmp;
					if (tmp == 1)
						nb_1++;
				}
				else {
					tab[i][j] = new Cellule(new Position(i,j), true, 0);
				}
			}
		}
		verif_borne(borne, nb_1);
	}

	private void verif_borne(int borne, int nb_1) { // TODO : add multiples bornes
		Random r = new Random();
		Position p;
		while (nb_1 <= borne) {
			p = new Position(r.nextInt(this.size),r.nextInt(this.size));
			if (!this.tab[p.i()][p.j()].isDestroyed() && this.tab[p.i()][p.j()].getFish() != 1) {
				nb_fish -= (this.tab[p.i()][p.j()].getFish() - 1);
				this.tab[p.i()][p.j()].setFish(1);
				nb_1++;
			}
		}
	}

	/**
	 * wrapper for initTab(int nb_fish_1, int nb_fish_2, int nb_fish_3)
	 * @param nb_pingouin : equibvalent à nb_fish_1
	 */
	private void initTab(int nb_pingouin) {
		int nb_autres = (((size * size) - (size + 1) / 2) - nb_pingouin) / 2;
		initTab(nb_pingouin, (nb_autres > 0)?nb_autres:0, (nb_autres > 0)?nb_autres:0);
	}

	public static Plateau parse(String filename) throws IOException {
		ArrayList<Cellule[]> list = new ArrayList<>();
		Cellule[] line;
		int line_nb = 0;
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String s = br.readLine();
		String[] splited;

		while (s != null) {
			splited = s.split(" ");
			line = new Cellule[splited.length + ((line_nb % 2 == 0)?1:0)];
			for (int i = 0; i < line.length; i++) { // Construction des cellules de la ligne
				if ((line_nb % 2 == 0) && (i == line.length - 1)) // Gestion des fins de lignes
					line[i] = new Cellule(new Position(line_nb,i),true,0);
				else
					line[i] = new Cellule(new Position(line_nb, i),false, Integer.parseInt(splited[i]));
			}
			line_nb++;
			list.add(line);
			s = br.readLine();
		}

		Cellule[][] tab = new Cellule[line_nb][line_nb];
		for (Cellule[] element : list) {
			for (Cellule c: element) {
				tab[c.getPosition().i()][c.getPosition().j()] = c.clone();
			}
		}
		return new Plateau(tab, new LinkedList<>(), new LinkedList<>());
	}

	/**
	 * isInTab : si une position est dans le tableau
	 * @param p : la position
	 * @return : vrai si la position est dans le tableau,
	 * faux sinon.
	 */
	public boolean isInTab(Position p) {
		return isInTab(p.i(),p.j());
	}

	/**
	 * isInTab : si une position est dans le tableau
	 * @param i : la ligne
	 * @param j : la colonn
	 * @return vrai si la position de coordonnées (i,j) est dans le tableau
	 * faux sinon.
	 */
	public boolean isInTab(int i, int j) {
		return 0 <= i
				&& i < size
				&& 0 <= j
				&& j < size;
	}

	/**
	 * getCellule : recupère une cellule
	 * @param p : la position de la cellule dans le tableau
	 * @return : l'objet Cellule
	 */
	public Cellule getCellule(Position p) {
		return getCellule(p.i(), p.j());
	}

	/**
	 * getCellule : recupère une cellule à la position (i,j)
	 * @param i : la ligne
	 * @param j : la colonne
	 * @return la Cellule si la position est dans le tablea
	 * null sinon.
	 */
	public Cellule getCellule(int i, int j) {
		Cellule res = null;
		if (isInTab(i,j))
			res = tab[i][j];
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
			if (isInTab(candidat))
				if (!getCellule(candidat).isObstacle())
					r.add(candidat);
		}
		return r;
	}

	/**
	 * safeAdd : ajoute une position dans la liste si elle n'y est pas déjà présente,
	 * appartient au tableau et n'est pas un obstacle (pingouins inclus ou non)
	 * @param l : la liste de position
	 * @param candidat : la position à ajouter
	 * @param mode : l'entier représentant le mode a utilisé
	 *              0..+ : l'indice du joueur courant,
	 *                  ce mode ne considère que les pingouins adverses comme obstacles
	 *              -1 : considère tous les pingouins comme obstacles
	 *              -2 : ignore tous les pingouins, seules les cases détruites sont des obstacles
	 * @return si l'ajout de candidat à été fait ou non.
	 */
	private boolean safeAdd(LinkedList<Position> l, Position candidat, int mode) {
		if (isInTab(candidat)) {

			if ((mode == -1 && !getCellule(candidat).isObstacle())
					|| (mode == -2 && !getCellule(candidat).isDestroyed())
					|| (mode >= 0 && (getCellule(candidat).aPingouin() && getCellule(candidat).pingouin().employeur() != mode))) {
				if (!l.contains(candidat))
					l.add(candidat);
				return true;
			}
		}
		return false;
	}

	/**
	 * listAccessibles : liste les positions accessibles depuis p
	 * @param p : la position de départ
	 * @param mode : l'entier représentant le mode a utilisé
	 *              0..+ : l'indice du joueur courant,
	 *                  ce mode ne considère que les pingouins adverses comme obstacles
	 *              -1 : considère tous les pingouins comme obstacles
	 *              -2 : ignore tous les pingouins, seules les cases détruites sont des obstacles
	 * @return LinkedList des positions accessible depuis p
	 */
	private LinkedList<Position> listAccessibles(Position p, int mode) {
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
				bu = safeAdd(res, candidat, mode);
			}
			if (bd) { // digonale arrière basse
				candidat = new Position(p.i() + i, p.j() - decalage_arriere);
				bd = safeAdd(res, candidat, mode);
			}
			if (p.i() % 2 == 0)
				decalage_avant = (i + 1) / 2;
			else
				decalage_avant = i / 2;
			if (fu) { // diagonale avant haute
				candidat = new Position(p.i() - i, p.j() +  decalage_avant);
				fu = safeAdd(res, candidat, mode);
			}
			if (fd) { // diagonale avant basse
				candidat = new Position(p.i() + i, p.j() + decalage_avant);
				fd = safeAdd(res, candidat, mode);
			}

			if (b) {
				candidat = new Position(p.i(), p.j() - i); // ligne arrière
				b = safeAdd(res, candidat, mode);
			}
			if (f) {
				candidat = new Position(p.i(), p.j() + i); // ligne avant
				f = safeAdd(res, candidat, mode);
			}
		}
		return res;
	}

	/**
	 * accessible : Liste des positions accessibles depuis p
	 * wrapper de listAccessible considérant les pingouins comme obstacles
	 * @param p : position courante
	 * @return : la liste des position accessibles
	 */
	public LinkedList<Position> accessible(Position p) {
		return listAccessibles(p, -1);
	}

	/**
	 * accessiblesanspingouin : Liste des positions accessibles depuis p
	 * wrapper de  listAccessibles ne considérant pas les pingouins comme obstacle
	 * @param p : position courante
	 * @return : la liste des position accessibles
	 */
	public LinkedList<Position> accessiblesanspingouin(Position p) {
		return listAccessibles(p, -2);
	}

	public LinkedList<Position> accessiblesansadverse(Position p, int id_joueur) {
		return listAccessibles(p, id_joueur);
	}

	int diffDir(int a, int b) {
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
		int coeff_i, coeff_j, dec;
		Position candidat;

		if (current.equals(target))
			return false;

		coeff_i = diffDir(current.i(), target.i());
		coeff_j = diffDir(current.j(), target.j());
		candidat = current.clone();
		int borne = Math.max(Math.abs(current.i() - target.i()), Math.abs(current.j() - target.j()));
		if(coeff_j == 0 && Math.abs(current.i() - target.i()) > 1)
			return false;
		for (int d = 1; d <= borne; d++) {
			if (coeff_i != 0) {
				if (current.i() % 2 == 0)        // pair
					if (coeff_j == 1)                // avant
						dec = (d + 1) / 2;
					else                            // arriere
						dec = d / 2;
				else                            // impair
					if (coeff_j == 1)                // avant
						dec = d / 2;
					else                            // arriere
						dec = (d + 1) / 2;

				candidat = new Position(current.i() + coeff_i * d, current.j() + coeff_j * dec);
			}
			else
				candidat = new Position(current.i(), current.j() + (coeff_j * d));
			if (!isInTab(candidat) || (isInTab(candidat) && getCellule(candidat).isObstacle()))
				return false;
		}
		return candidat.equals(target);
	}

	private void clearUndoList() {
		if (!this.undoList.isEmpty())
			this.undoList = new LinkedList<>();
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
			int res = jouer_pr(current,target);
			if (res >= 0)
				clearUndoList();
			return res;
		} catch (PlateauException e) {
			System.err.println(e.getMessage());
			return -1;
		}
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
			int res = jouer_pr(current,target);
			if (res >= 0)
				clearUndoList();
			return res;
		} catch (PlateauException e) {
			System.err.println(e.getMessage());
			return -1;
		}
	}

	/* Implementation pour Plateau.redo() */
	private int jouer(Move m) {
		try {
			return jouer_pr(m.getFrom(), m.getTo());
		} catch (PlateauException e) {
			System.err.println(e.getMessage());
			return -1;
		}
	}

	private int jouer_pr(Position current, Position target) throws PlateauException {
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
			history.addLast(new Move(target, current, targetCell.getFish(), pingouin));
			currentCell = getCellule(current);
			currentCell.destroy();
			currentCell.setPenguin(null);
			pingouin.setPosition(target);
			targetCell.setPenguin(pingouin);
			res = targetCell.getFish();
			targetCell.setFish(0);
		}
		nb_fish -= res;
		return res;
	}

	/**
	 * undoPossible : si un undo est possible
	 * @return vrai si l'historique n'est pas vide et que l'on peut undo
	 * faux sinon
	 */
	public boolean undoPossible() {
		return !this.history.isEmpty();
	}

	/**
	 * redoPossible : si un redo est possible
	 * @return vrai si l'undoList n'est pas vide et que l'on peut redo
	 * faux sinon
	 */
	public boolean redoPossible() {
		return !this.undoList.isEmpty();
	}

	/**
	 * undo : Annule le coup précédent si possible
	 * @return : un couple de boolean et couple <integer, integer>
	 *     boolean : si on a undo la pose d'un pingouin
	 *     couple<integer,integer> : le nombre de poissons mangés et l'id du pingouin
	 */
	public Couple<Boolean, Couple<Integer,Integer>> undo() {
		if (history.isEmpty())
			return new Couple<>(false, new Couple<>(-1, -1));

		Move m = history.removeLast();
		Position from = m.getFrom(),
				to = m.getTo();
		boolean undoPosePingouin = from.equals(Plateau.source);
		Pingouin pingouin = m.getPingouin();
		int fishAte = m.getFishAte();

		this.undoList.addLast(m);
		if (!undoPosePingouin) // Si from != (-1,-1)
			tab[from.i()][from.j()].setDestroyed(false); // Restore old cell
		getCellule(to).setPenguin(null); // remove pingouin from its current cell
		tab[to.i()][to.j()].setFish(fishAte); // restore fish on left cell
		pingouin.setPosition(from); // set pingouin to old position
		if (!undoPosePingouin) // Si from != (-1,-1)
			getCellule(from).setPenguin(pingouin); // set pingouin on old cell
		nb_fish += fishAte;
		return new Couple<>(undoPosePingouin, new Couple<>(fishAte, pingouin.employeur()));
	}

	/**
	 * redo : ré-exécute le dernier coup annulé, si il y en a
	 * @return la valeur du coup exécuté si il y'en a un,
	 * -1 sinon
	 */
	public int redo() {
		if (undoList.isEmpty())
			return -1;
		Move m = undoList.removeLast();
		int res;
		if (m.getFrom().equals(Plateau.source))
			res = (poserPingouin_pr(m.getTo(),m.getPingouin())) ? 1 : -1;
		else
			res = jouer(m);
		return res;
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
	 * Efface la undoList si la pose du pingouin s'est bien passée
	 * @param p position ou ajouter le pingouin
	 * @return true si tout c'est bien passe false sinon
	 * @author Louka Soret, Gaëtan Sorin
	 */
	public boolean poserPingouin(Position p, Pingouin pingouin) {
		boolean b = poserPingouin_pr(p, pingouin);
		if (b)
			clearUndoList();
		return b;
	}

	/**
	 * Pose un pingouin sur un case si les lunes sont alignées
	 * @param p position ou ajouter le pingouin
	 * @return true si tout c'est bien passe false sinon
	 * @author Louka Soret, Gaëtan Sorin
	 */
	private boolean poserPingouin_pr(Position p, Pingouin pingouin) {
		// Si la case en p n'est pas fondue et n'a pas de pingouin
		if(isInTab(p) && !this.getCellule(p).isObstacle()) {
			//Si la case en p a un seul poisson
			if(this.getCellule(p).getFish() == 1) {
				getCellule(p).setPenguin(pingouin);
				pingouin.setPosition(p);
				history.addLast(new Move(p, pingouin));
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

	public int getNbFish() {
		return nb_fish;
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

	private String tabToString() {
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

	boolean tabEquals(Cellule[][] tab) {
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
		return "{" + size + ", " +  nb_fish  + ", " + tabToString() + ",h:" + history + ",u:" + undoList + "]" + '}';
	}
}
