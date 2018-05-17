package Modele.Joueurs;

import java.util.ArrayList;

import Modele.Plateau.Pingouin;
import Modele.Plateau.Plateau;
import Utils.Couple;
import Utils.Position;

public abstract class Joueur {
	private int id;
	private int nbPingouins;
	private int scoreFish;
	private int scoreDestroyed;
	Difficulte difficulte;
	private ArrayList<Pingouin> pingouins;
	
	public enum Difficulte{
		PHYSIQUE,
		FACILE,
		MOYEN,
		DIFFICILE;
		
		public String toString(Difficulte d) {
			switch(d) {
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
	
	
	public Joueur(int id,Difficulte d){
		this.id = id;
		this.scoreFish = 0;
		this.scoreDestroyed = 0;
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
	
	public void setId(int id) {
		this.id = id;
	}

	public void setScoreFish(int s) {
		this.scoreFish = s;
	}
	
	public void addScoreFish(int a) {
		this.scoreFish += a;
	}
	
	public void subScoreFish(int l) {
		this.scoreFish -= l;
	}
	
	public void setScoreDestroyed(int s) {
		this.scoreDestroyed = s;
	}
	
	public void addScoreDestroyed(int a) {
		this.scoreDestroyed += a;
	}
	
	public void subScoreDestroyed(int l) {
		this.scoreDestroyed -= l;
	}
	
	public void setNbPingouins(int s) {
		this.nbPingouins = s;
	}
	
	public void addNbPingouins(int a) {
		this.nbPingouins += a;
	}
	
	public void subNbPingouins(int l) {
		this.nbPingouins -= l;
	}

	public ArrayList<Pingouin> pingouins(){
		return this.pingouins;
	}
	
	public void addPingouins(Pingouin p) {
		this.pingouins.add(p);
	}
	
	/*
	 * Renvois le delay a attendre (ms) avant de faire jouer le joueur
	 */
	public int delay() {
		return 0;
	}
	
	/**
	 * Fonction appelee dans l'IA pour calculer le prochain coup
	 * renvoie (PosPingouin,PosObjectif) si une erreur c'est produite
	 * @param plateau
	 * @return
	 */
	public Couple<Position,Position> prochainCoup(Plateau plateau) {
		return new Couple<Position,Position>(new Position(-1,-1),new Position(-1,-1));
	}
	
	public Position prochainePosePingouin(Plateau plateau) {
		return new Position(-1,-1);
	}
	
	/**
	 * Fonction appellee par un joueur pour poser un pingouin sur le plateau
	 * renvois true si tout c'est bien passe, false sinon
	 * @param plateau
	 * @param position
	 * @return
	 */
	public boolean posePingouin(Plateau plateau,Position position) {
		boolean res;
		Pingouin p = new Pingouin(this.id());
		res = plateau.poserPingouin(position, p);
		if(res) {
			this.addPingouins(p);
			this.nbPingouins++;
			this.addScoreFish(1);
		}
		return res;
	}

	/**
	 * Fonction appellee par un joueur pour jouer un coup
	 * renvois le nombre de poissons mangee par le pingouin lors du coup
	 * ou -1 si une erreur c'est produite
	 * 
	 * @param plateau le plateau de jeu
	 * @param pingouin position du pingouin a deplacer
	 * @param goal la destination souhaitee
	 * @return le nombre de poissons mangee par le pingouin lors du coup ou -1 si une erreur c'est produite
	 * @throws Exception 
	 */
	public int jouerCoup(Plateau plateau,Position start, Position goal) throws Exception {
		int res;
		/*
		System.out.println("------------------------------------------------------");
		for(int i= 0; i < UtilsIA.listeConnexeComposante(plateau).size();i++)
			System.out.println("La composante connexe "+i+" : " + UtilsIA.listeConnexeComposante(plateau).get(i));
		System.out.println("------------------------------------------------------");
		
		System.out.println("------------------------------------------------------");
		System.out.println("Le coup joue par l'IA : " + UtilsIA.jouerCoupFacile(plateau,this.id));
		System.out.println("Le coup joue par l'IA dure : " + UtilsIA.jouerCoupDifficile(plateau,this.id));

		System.out.println("------------------------------------------------------");
		*/
		if(plateau.getCellule(start).aPingouin()) { //test si le pingouin existe
			if(plateau.getCellule(start).pingouin().employeur() == this.id()) { //test si le pingouin appartient bien a ce joueur
				res = plateau.jouer(start,goal);
				if(res >= 0) {
					this.addScoreFish(res);	
					this.addScoreDestroyed(1);
				}
				return res;
			} else {
				throw new Exception("Le pingouin en "+start+" n'appartient pas au joueur "+this.id()+".");
			}
		} else {
			throw new Exception("La case en "+start+" ne contient pas de pingouin.");
		}
	}
	
	public boolean estIA() {
		return false;
	}
	
	@Override
	public String toString() {
		return "[ID:"+this.id+", Pingouins:"+this.nbPingouins+", Score:"+this.scoreFish+"("+this.scoreDestroyed+")]";
	}
}
