package Vue;


import Joueurs.Joueur;
import Joueurs.JoueurPhysique;
import Joueurs.Pingouin;
import Modele.Plateau.Plateau;
import Utils.Position;

public class Moteur {
	private Joueur joueurs[];
	private Plateau plateau;
	private int njoueurs;
	private int indexJoueurCourant = 0;
	private State currentState;
	private Position pingouinSelection;

	/**
	 * Enum des etats de l'automate
	 * 
	 * @author Louka Soret
	 *
	 */
	public enum State {
		INIT, POSER_PINGOUIN, SELECTIONNER_PINGOUIN, SELECTIONNER_DESTINATION;

		static public String toString(State s) {
			switch (s) {
			case INIT:
				return "INIT";
			case POSER_PINGOUIN:
				return "POSER_PINGOUIN";
			case SELECTIONNER_PINGOUIN:
				return "SELECTIONNER_PINGOUIN";
			case SELECTIONNER_DESTINATION:
				return "SELECTIONNER_DESTINATION";
			default:
				return "undefined";
			}
		}

	}

	public Moteur(Plateau p, int njoueurs) {
		this.plateau = p;
		this.njoueurs = njoueurs;
		joueurs = new Joueur[njoueurs];
		currentState = State.INIT;

		// par defaut, on met que des joueurs physiques
		for (int i = 0; i < njoueurs; i++) {
			joueurs[i] = new JoueurPhysique(i);
		}
	}

	public State currentState() {
		return this.currentState;
	}

	public void setCurrentState(State s) {
		this.currentState = s;
	}

	public Plateau plateau() {
		return this.plateau;
	}

	public Joueur joueur(int index) {
		return joueurs[index];
	}

	public Joueur[] joueurs() {
		return this.joueurs;
	}

	public int njoueurs() {
		return njoueurs;
	}

	public Joueur joueurCourant() {
		return joueurs[indexJoueurCourant];
	}

	public int indexJoueurCourant() {
		return this.indexJoueurCourant;
	}

	public Joueur joueurSuivant() {
		this.indexJoueurCourant = (this.indexJoueurCourant + 1) % this.njoueurs();
		return joueurCourant();
	}

	/*
	 * FONCTION A REMPLACER PAR QQCHOSE DE PLUS PRATIQUE
	 */
	private boolean contientPingouin(Position p) {
		for (int i = 0; i < njoueurs; i++) {
			Joueur joueur = joueurs[i];
			for (Pingouin pingouin : joueur.squad()) {
				if (pingouin.position().i() == p.i() && pingouin.position().j() == p.j()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * poserPingouin : pose un pingouin à la position donnée en paramètre et change
	 * l'état du moteur (joueur courant + état courant)
	 * 
	 * @param p
	 *            : position où poser le pingouin
	 * @return true si le pingouin a été posé, false sinon
	 */
	public boolean poserPingouin(Position p) {
		if (currentState == State.POSER_PINGOUIN) {
			if (!contientPingouin(p)) {
				pingouinSelection = p;
				try {
					joueurCourant().addSquad(new Pingouin(joueurCourant().id(), p));
				} catch (Exception e) {
					return false;
				}
				joueurSuivant();

				int npingouins = 0;
				for (int i = 0; i < njoueurs; i++) {
					npingouins += joueurs[i].squadSize();
				}
				if (njoueurs == 3 && npingouins == 9 || npingouins == 8) {
					currentState = State.SELECTIONNER_PINGOUIN;
				}

				return true;
			}
		}
		return false;
	}

	
	/*
	 * FONCTION A REMPLACER PAR QQCHOSE DE PLUS PRATIQUE
	 */
	/**
	 * pingouinAJoueurCourant : test si le joueur courant a un pingouin sur la position donnée en paramètre
	 * 
	 * @param p : position du pingouin
	 * @return true le joueur courant a un pingouin à cette position, false sinon
	 */
	private boolean pingouinAJoueurCourant(Position p) {
		Joueur joueur = joueurCourant();
		for (Pingouin pingouin : joueur.squad()) {
			if (pingouin.position().i() == p.i() && pingouin.position().j() == p.j()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * selectionnerPingouin : le moteur retiendra le pingouin selectionné (et change son état en conséquence)
	 * 
	 * @param p : position du pingouin à selectionner
	 * @return true si le pingouin a été séléctionné, false sinon
	 */
	public boolean selectionnerPingouin(Position p) {
		if (currentState == State.SELECTIONNER_PINGOUIN) {
			if (pingouinAJoueurCourant(p)) {
				pingouinSelection = p;
				currentState = State.SELECTIONNER_DESTINATION;
				return true;
			}
		}
		pingouinSelection = null;
		return false;
	}
	
	/*
	 * FONCTION A REMPLACER PAR QQCHOSE DE PLUS PRATIQUE
	 */
	private Pingouin getPingouin(Joueur j,Position p) {
		for (Pingouin pingouin : j.squad()) {
			if (pingouin.position().i() == p.i() && pingouin.position().j() == p.j()) {
				return pingouin;
			}
		}
		return null;
	}
	
	/**
	 * selectionnerDestinnation : si possible, déplace le pingouin actuellement selectionné à la destination
	 * 
	 * @param p : destination
	 * @return true si le pingouin séléctionné a été déplacé, false sinon
	 */
	public boolean selectionnerDestination(Position p) {
		if (currentState == State.SELECTIONNER_DESTINATION) {
			if (!contientPingouin(p)) {
				Pingouin ping = getPingouin(joueurCourant(), pingouinSelection);
				if(ping!=null) {
					plateau.jouer(getPingouin(joueurCourant(), pingouinSelection),p);
					joueurSuivant();
					currentState = State.SELECTIONNER_PINGOUIN;
					return true;
				}
			}
		}
		currentState = State.SELECTIONNER_PINGOUIN;
		return false;
	}
	
	/**
	 * pingouinSelection : renvoie le pingouin actuellement selectionné
	 * 
	 * @return le pingouin actuellement selectionné (ou null s'il n'y en a pas)
	 */
	public Position pingouinSelection() {
		return pingouinSelection;
	}
}
