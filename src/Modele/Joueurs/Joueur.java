package Modele.Joueurs;

import java.io.Serializable;
import java.util.ArrayList;

import Modele.Plateau.Pingouin;
import Modele.Plateau.Plateau;
import Utils.Couple;
import Utils.Position;

public abstract class Joueur implements Serializable {

	private static final long serialVersionUID = 4239601391177019445L;
	private int id;
	private String nom;
	private int nbPingouins;
	private int scoreFish;
	private int scoreDestroyed;
	Difficulte difficulte;
	private ArrayList<Pingouin> pingouins;
	private ArrayList<Pingouin> pingouinHistory;
	private boolean elimine;

	public enum Difficulte implements Serializable {
		PHYSIQUE, FACILE, MOYEN, DIFFICILE;

		public String toString(Difficulte d) {
			switch (d) {
			case PHYSIQUE:
				return "PHYSIQUE";
			case FACILE:
				return "FACILE";
			case MOYEN:
				return "MOYEN";
			case DIFFICILE:
				return "DIFFICILE";
			default:
				return "UNDEFINED";
			}
		}
	}

	public Joueur(int id, int nbPingouins, String nom, Difficulte d) {
		this.id = id;
		this.nom = nom;
		this.nbPingouins = nbPingouins;
		this.difficulte = d;
		this.scoreFish = 0;
		this.scoreDestroyed = 0;
		this.elimine = false;
		this.pingouinHistory = new ArrayList<Pingouin>();
		this.pingouins = new ArrayList<Pingouin>();
	}

	public int id() {
		return this.id;
	}

	public int scoreFish() {
		return this.scoreFish;
	}

	public int scoreDestroyed() {
		return this.scoreDestroyed;
	}

	public int nbPingouin() {
		return this.nbPingouins;
	}

	public String nom() {
		return this.nom;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setScoreFish(int s) {
		if (s < 0) {
			this.scoreFish = 0;
		} else {
			this.scoreFish = s;
		}
	}

	public void addScoreFish(int a) {
		if ((this.scoreFish += a) < 0) {
			this.scoreFish = 0;
		}
	}

	public void subScoreFish(int s) {
		if ((this.scoreFish -= s) < 0) {
			this.scoreFish = 0;
		}
	}

	public void setScoreDestroyed(int s) {
		if (s < 0) {
			this.scoreDestroyed = 0;
		} else {
			this.scoreDestroyed = s;
		}
	}

	public void addScoreDestroyed(int a) {
		if ((this.scoreDestroyed += a) < 0) {
			this.scoreDestroyed = 0;
		}
	}

	public void subScoreDestroyed(int s) {
		if ((this.scoreDestroyed -= s) < 0) {
			this.scoreDestroyed = 0;
		}
	}

	public void setNbPingouins(int s) {
		if (s < 0) {
			this.nbPingouins = 0;
		} else {
			this.nbPingouins = s;
		}
	}

	public void addNbPingouins(int a) {
		if ((this.nbPingouins += a) < 0) {
			this.nbPingouins = 0;
		}
	}

	public void subNbPingouins(int s) {
		if ((this.nbPingouins -= s) < 0) {
			this.nbPingouins = 0;
		}
	}

	public void setNom(String n) {
		this.nom = n;
	}

	public ArrayList<Pingouin> pingouins() {
		return this.pingouins;
	}

	public void addPingouins(Pingouin p) {
		this.pingouins.add(p);
	}

	public boolean estElimine() {
		return this.elimine;
	}

	public void eliminer() {
		this.elimine = true;
	}

	public void ressusciter() {
		this.elimine = false;
	}

	/*
	 * Renvois le delay a attendre (ms) avant de faire jouer le joueur
	 */
	public int delay() {
		return 0;
	}

	/**
	 * Fonction appelee dans l'IA pour calculer le prochain coup renvoie
	 * (PosPingouin,PosObjectif) si une erreur c'est produite
	 * 
	 * @param plateau
	 * @return
	 */
	public Couple<Position, Position> prochainCoup(Plateau plateau,ArrayList<ArrayList<Integer>> scores) {
		return new Couple<Position, Position>(new Position(-1, -1), new Position(-1, -1));
	}

	public Position prochainePosePingouin(Plateau plateau) {
		return new Position(-1, -1);
	}

	/**
	 * Fonction appellee par un joueur pour poser un pingouin sur le plateau renvois
	 * true si tout c'est bien passe, false sinon
	 * 
	 * @param plateau
	 * @param position
	 * @return
	 */
	public boolean posePingouin(Plateau plateau, Position position) {
		boolean res;
		if (this.nbPingouins > this.pingouins.size()) {
			Pingouin p = new Pingouin(this.id());
			if ((res = plateau.poserPingouin(position, p))) {
				this.addPingouins(p);
				this.addScoreFish(1);
			}
			return res;
		} else {
			return false;
		}
	}

	/**
	 * Fonction appellee par un joueur pour jouer un coup renvois le nombre de
	 * poissons mangee par le pingouin lors du coup ou -1 si une erreur c'est
	 * produite
	 * 
	 * @param plateau
	 *            le plateau de jeu
	 * @param pingouin
	 *            position du pingouin a deplacer
	 * @param goal
	 *            la destination souhaitee
	 * @return le nombre de poissons mangee par le pingouin lors du coup ou -1 si
	 *         une erreur c'est produite
	 * @throws Exception
	 */
	public int jouerCoup(Plateau plateau, Position start, Position goal) throws Exception {
		int res;
		/*
		 * System.out.println("------------------------------------------------------");
		 * for(int i= 0; i < UtilsIA.listeConnexeComposante(plateau).size();i++)
		 * System.out.println("La composante connexe "+i+" : " +
		 * UtilsIA.listeConnexeComposante(plateau).get(i));
		 * System.out.println("------------------------------------------------------");
		 * 
		 * System.out.println("------------------------------------------------------");
		 * System.out.println("Le coup joue par l'IA : " +
		 * UtilsIA.jouerCoupFacile(plateau,this.id));
		 * System.out.println("Le coup joue par l'IA dure : " +
		 * UtilsIA.jouerCoupDifficile(plateau,this.id));
		 * 
		 * System.out.println("------------------------------------------------------");
		 */
		if (plateau.getCellule(start).aPingouin()) { // test si le pingouin existe
			if (plateau.getCellule(start).pingouin().employeur() == this.id()) { // test si le pingouin appartient bien
																					// a ce joueur
				res = plateau.jouer(start, goal);
				if (res >= 0) {
					this.addScoreFish(res);
					this.addScoreDestroyed(1);
				}
				return res;
			} else {
				throw new Exception("Le pingouin en " + start + " n'appartient pas au joueur " + this.id() + ".");
			}
		} else {
			throw new Exception("La case en " + start + " ne contient pas de pingouin.");
		}
	}

	public boolean estIA() {
		return false;
	}

	public void undo(int fishUndone,boolean pingouinModifier) {
		this.subScoreFish(fishUndone);
		this.subScoreDestroyed(1);
		this.ressusciter();
		if(pingouinModifier && this.pingouins.size() > 0) {
			this.pingouinHistory.add(this.pingouins.get(this.pingouins.size()-1));
			this.pingouins.remove(this.pingouins.size()-1);
		}
	}

	public void redo(int fishRedone, int destroyedRedone) {
		this.addScoreFish(fishRedone);
		this.addScoreDestroyed(destroyedRedone);
		if(destroyedRedone == 0 && this.pingouinHistory.size() > 0) {
			this.pingouins.add(this.pingouinHistory.get(this.pingouinHistory.size()-1));
			this.pingouinHistory.remove(this.pingouinHistory.size()-1);
		}
	}

	@Override
	public String toString() {
		return "[ID:" + this.id + ", Pingouins:" + this.nbPingouins + ", Score:" + this.scoreFish + "("
				+ this.scoreDestroyed + ")]";
	}
}
