package Vue;


import java.util.HashMap;

import Joueurs.Joueur;
import Joueurs.JoueurPhysique;
import Modele.Plateau.Pingouin;
import Modele.Plateau.Plateau;
import Utils.Couple;
import Utils.Position;

public class Moteur {
	private Joueur joueurs[];
	private Plateau plateau;
	private int njoueurs;
	private int nbPingouin;
	private int indexJoueurCourant=0;
	private Position selected;
       	private State currentState;
	private Position pingouinSelection;
	private HashMap<Couple<State, Action>, State> transition;
	
	/**
	 * Enum des etats de l'automate
	 * 
	 * @author Louka Soret
	 *
	 */
	public enum State {
		INIT, 						//Etat du moteur apres initialisation
		POSER_PINGOUIN, 			//Phase de pose des pingouins
		SELECTIONNER_PINGOUIN, 		//Deroulement du jeu: selection pingouin
		SELECTIONNER_DESTINATION,	//Deroulement du jeu: selection destination
		RESULTATS;					//Etat en fin de partie

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
			case RESULTATS:
				return "RESULTATS";
			default:
				return "undefined";
			}
		}
	}

	/**
	 * Actions possibles renvoyees par actionMoteur ou configMoteur
	 * @author Louka Soret
	 *
	 */
	public enum Action {
		MAUVAIS_ETAT,		// La machine a etat a deraillee
		SELECTION_VALIDE,	// La selection faite par le joueur etait invalide
		SELECTION_INVALIDE, // La selection faite par le joueur etait valide
		PINGOUINPOSES,		// La phase de pose de pingouin est terminee
		POSEIA_VALIDE;		// L'IA a reussis sa pose de pingouin
		
		static public String toString(Action s) {
			switch(s) {
			case MAUVAIS_ETAT:
				return "MAUVAIS_ETAT";
			case SELECTION_VALIDE:
				return "SELECTION_VALIDE";
			case SELECTION_INVALIDE:
				return "SELECTION_INVALIDE";
			case PINGOUINPOSES:
				return "PINGOUINPOSES";
			default:
				return "undefined";
			}
		}
	}
	
	public Moteur(Plateau p, int njoueurs) {
		this.plateau = p;
		this.njoueurs = njoueurs;
		joueurs = new Joueur[njoueurs];
		this.nbPingouin = 0;
		currentState = State.INIT;
		initTransitions();

		// par defaut, on met que des joueurs physiques
		for (int i = 0; i < njoueurs; i++) {
			joueurs[i] = new JoueurPhysique(i);
		}
	}
	
	/**
	 * Remplissage de la fonction de transition
	 */
	private void initTransitions() {
		this.transition = new HashMap<Couple<State,Action>,State>();
		
		// POSER_PINGOUIN
		this.transition.put(new Couple<State,Action>(State.POSER_PINGOUIN,Action.PINGOUINPOSES),State.SELECTIONNER_PINGOUIN);
		this.transition.put(new Couple<State,Action>(State.POSER_PINGOUIN,Action.SELECTION_VALIDE),State.POSER_PINGOUIN);
		this.transition.put(new Couple<State,Action>(State.POSER_PINGOUIN,Action.SELECTION_INVALIDE),State.POSER_PINGOUIN);
		this.transition.put(new Couple<State,Action>(State.POSER_PINGOUIN,Action.MAUVAIS_ETAT),State.POSER_PINGOUIN);
		
		// SELECTIONNER_PINGOUIN
		this.transition.put(new Couple<State,Action>(State.SELECTIONNER_PINGOUIN,Action.SELECTION_VALIDE),State.SELECTIONNER_DESTINATION);
		this.transition.put(new Couple<State,Action>(State.SELECTIONNER_PINGOUIN,Action.SELECTION_INVALIDE),State.SELECTIONNER_PINGOUIN);
		this.transition.put(new Couple<State,Action>(State.SELECTIONNER_PINGOUIN,Action.MAUVAIS_ETAT),State.SELECTIONNER_PINGOUIN);
		
		// SELECTIONNER_DESTINATION
		this.transition.put(new Couple<State,Action>(State.SELECTIONNER_DESTINATION,Action.SELECTION_VALIDE),State.SELECTIONNER_PINGOUIN);
		this.transition.put(new Couple<State,Action>(State.SELECTIONNER_DESTINATION,Action.SELECTION_INVALIDE),State.SELECTIONNER_PINGOUIN);
		this.transition.put(new Couple<State,Action>(State.SELECTIONNER_DESTINATION,Action.MAUVAIS_ETAT),State.SELECTIONNER_DESTINATION);
	}
	
	public void transition(Action action) {
		Couple<State,Action> newkey = new Couple<State,Action>(this.currentState,action);
		for(Couple<State,Action> key: this.transition.keySet()) {
			if(key.equals(newkey)) {
				this.currentState = this.transition.get(key);
			}
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

	/**
	 * poserPingouin : pose un pingouin � la position donn�e en param�tre et change
	 * l'�tat du moteur (joueur courant + �tat courant)
	 * 
	 * @param p
	 *            : position o� poser le pingouin
	 * @return true si le pingouin a �t� pos�, false sinon
	 */
	public boolean poserPingouin(Position position) {
		if (currentState == State.POSER_PINGOUIN) {
			//Si la pose reussis
			if (this.joueurCourant().posePingouin(this.plateau, position)) {
				this.nbPingouin++;
				this.joueurSuivant();
				// Si tout les pingouins ont ete poses
				if(this.njoueurs == 3 && this.nbPingouin==9 || this.njoueurs != 3 && this.nbPingouin==8 ) {
					transition(Action.PINGOUINPOSES);
				} else {
					transition(Action.SELECTION_VALIDE);
				}
				return true;
			} else {
				transition(Action.SELECTION_INVALIDE);
				return false;
			}
		} else {
			transition(Action.MAUVAIS_ETAT);
			return false;
		}
	}

	/**
	 * selectionnerPingouin : le moteur retiendra le pingouin selectionn� (et change son �tat en cons�quence)
	 * 
	 * @param p : position du pingouin � selectionner
	 * @return true si le pingouin a �t� s�l�ctionn�, false sinon
	 */
	public boolean selectionnerPingouin(Position p) {
		if (currentState == State.SELECTIONNER_PINGOUIN) {
			//Si La cellule en p a un pingouin et que ce pingouin appartient au joueur courrant
			if(plateau.getCellule(p).aPingouin() && (plateau.getCellule(p).pingouin().employeur() == joueurCourant().id())) {
				this.selected = p;
				transition(Action.SELECTION_VALIDE);
				return true;
			} else {
				transition(Action.SELECTION_INVALIDE);
				return false;
			}
		} else {
			transition(Action.MAUVAIS_ETAT);
			return false;
		}
	}
	
	/**
	 * selectionnerDestinnation : si possible, d�place le pingouin actuellement selectionn� � la destination
	 * 
	 * @param p : destination
	 * @return true si le pingouin s�l�ctionn� a �t� d�plac�, false sinon
	 * @throws Exception
	 */
	public boolean selectionnerDestination(Position destination){
		if (currentState == State.SELECTIONNER_DESTINATION) {
			try {
				if (this.joueurCourant().jouerCoup(this.plateau,selected,destination) < 0) {
					transition(Action.SELECTION_INVALIDE);
					return false;
				} else {
					joueurSuivant();
					transition(Action.SELECTION_VALIDE);
					return true;
				}
			} catch (Exception e) {
				transition(Action.SELECTION_INVALIDE);
				return false;
			}
		} else {
			transition(Action.MAUVAIS_ETAT);
			return false;
		}
	}
	
	public Position posePingouinIA() {
		if(this.currentState == State.POSER_PINGOUIN) {
			//Si le joueur est une IA
			if(this.joueurCourant().estIA()) {
				Position calculated = this.joueurCourant().prochainePosePingouin(this.plateau);
				//Si le calcule de l'IA a reussis
				if(!calculated.equals(new Position(-1,-1))) {
					if(poserPingouin(calculated)) {
						transition(Action.POSEIA_VALIDE);
						return calculated;
					}
				}
			}
			transition(Action.SELECTION_INVALIDE);
			return new Position(-1,-1);
		} else {
			transition(Action.MAUVAIS_ETAT);
			return new Position(-1,-1);
		}
	}
	
	public Couple<Position,Position> coupIA(){
		if(this.currentState == State.SELECTIONNER_PINGOUIN) {
			//Si le joueur est une IA
			if(this.joueurCourant().estIA()) {
				Couple<Position,Position> calculated = this.joueurCourant().prochainCoup(plateau);
				if(!calculated.equals(new Couple<Position,Position>(new Position(-1,-1),new Position(-1,-1)))) {
					//Si choix du pingouin effectue
					if(selectionnerPingouin(calculated.gauche())) {
						//Si choix de la destination effectuee
						if(selectionnerDestination(calculated.droit())) {
							
						}
					}
				}
			}
		} else {
			transition(Action.MAUVAIS_ETAT);
			return new Couple<Position,Position>(new Position(-1,-1),new Position(-1,-1));
		}
	}
	
	/**
	 * pingouinSelection : renvoie le pingouin actuellement selectionn�
	 * 
	 * @return le pingouin actuellement selectionn� (ou null s'il n'y en a pas)
	 */
	public Pingouin pingouinSelection() {
		if(this.plateau.getCellule(this.selected).aPingouin()) {
			return this.plateau.getCellule(this.selected).pingouin();
		} else {
			return null;
		}
	}
}
