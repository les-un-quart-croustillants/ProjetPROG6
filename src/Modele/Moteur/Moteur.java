package Modele.Moteur;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;

import Modele.Joueurs.Joueur;
import Modele.Joueurs.JoueurPhysique;
import Modele.Plateau.Pingouin;
import Modele.Plateau.Plateau;
import Utils.Couple;
import Utils.Position;

public class Moteur {
	//DATA
	private ArrayList<Joueur> joueurs;
	private ArrayList<Joueur> eliminees;
	private Plateau plateau;
	
	//ETAT MOTEUR
	private int njoueurs;
	private int nbPingouin;
	private int indexJoueurCourant=0;
	private Position selected;
	
	//AUTOMATE
    private State currentState;
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
		FIN_PARTIE;			// Tous les pingouins sont bloquees
		
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
		this.joueurs = new ArrayList<Joueur>();
		this.eliminees = new ArrayList<Joueur>();
		this.nbPingouin = 0;
		currentState = State.INIT;
		initTransitions();

		// par defaut, on met que des joueurs physiques
		for (int i = 0; i < njoueurs; i++) {
			this.joueurs.add(new JoueurPhysique(i));
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
		this.transition.put(new Couple<State,Action>(State.SELECTIONNER_PINGOUIN,Action.FIN_PARTIE),State.RESULTATS);
		
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
		return joueurs.get(index);
	}

	public ArrayList<Joueur> joueurs() {
		return this.joueurs;
	}

	public int njoueurs() {
		return njoueurs;
	}

	public Joueur joueurCourant() {
		return this.joueurs.get(indexJoueurCourant);
	}

	public int indexJoueurCourant() {
		return this.indexJoueurCourant;
	}
	
	public ArrayList<ArrayList<Integer>> podium() {
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
		for(Joueur j : this.eliminees) {
			res.add(			new ArrayList<Integer>() {
			private static final long serialVersionUID = 1L;
			{
			    add(j.id());
			    add(j.scoreFish());
			    add(j.scoreDestroyed());
			}});
		}
		return res;
	}

	/**
	 * Passe au joueur suivant. Si selui ci ne peut plus jouer, le supprime des joueurs
	 * et passe au suivant. Si il n'y a plus de joueurs retourne null
	 * @return
	 */
	public Joueur joueurSuivant() {
		this.indexJoueurCourant = (this.indexJoueurCourant + 1) % this.joueurs.size();
		if(this.joueurCourant().pingouins().size() == 0) {
			return joueurCourant();
		}
		for(Pingouin p: this.joueurCourant().pingouins()) {
			if(!this.plateau.estIsolee(p.position())) {
				return joueurCourant();
			}
		}
		Joueur j = this.joueurCourant();
		this.eliminees.add(j);
		//Tri les joueurs elimine en vue du calcul du gagnant
		Collections.sort(this.eliminees, new Comparator<Joueur>() {
			@Override
		    public int compare(Joueur a, Joueur b) {
		        if(a.scoreFish() == b.scoreFish()) {
		        	return Math.max(a.scoreDestroyed(),b.scoreDestroyed());
		        } else {
		        	return Math.max(a.scoreFish(),b.scoreFish());
		        }
		    }
		});
		this.joueurs.remove(j);
		if(this.joueurs.size() < 0) {
			return joueurCourant();
		} else {
			return null;
		}
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
			//Si le pingouin n'est pas isole
			if(!plateau.estIsolee(p)) {
				//Si La cellule en p a un pingouin et que ce pingouin appartient au joueur courrant
				if(plateau.getCellule(p).aPingouin() && (plateau.getCellule(p).pingouin().employeur() == joueurCourant().id())) {
					this.selected = p;
					transition(Action.SELECTION_VALIDE);
					return true;
				}
			}
			transition(Action.SELECTION_INVALIDE);
			return false;
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
					if (joueurSuivant() == null) {
						transition(Action.FIN_PARTIE);
						System.out.println("FIN PARTIE");
					} else {
						transition(Action.SELECTION_VALIDE);
					}
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
					//Si la pose a reussi (Change de joueur)
					if(poserPingouin(calculated)) {
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
						//Si choix de la destination effectuee (change de joueur)
						if(selectionnerDestination(calculated.droit())) {
							return calculated;
						}
					}
				}
			}
			transition(Action.SELECTION_INVALIDE);
			return new Couple<Position,Position>(new Position(-1,-1),new Position(-1,-1));
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
