package Modele.Moteur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;

import Modele.Joueurs.Joueur;
import Modele.Joueurs.UtilsIA;
import Modele.Plateau.Pingouin;
import Modele.Plateau.Plateau;
import Utils.Couple;
import Utils.Position;

public class Moteur implements Serializable {

	private static final long serialVersionUID = -840627639546108849L;
	// DATA
	private ArrayList<Joueur> joueurs;
	private Plateau plateau;

	// ETAT MOTEUR
	private int njoueurs;
	private int indexJoueurCourant = 0;
	private Position selected;
	private boolean undoRedoAutorise;

	// AUTOMATE
	private State currentState;
	private HashMap<Couple<State, Action>, State> transition;

	// AUTRE
	private Position prochainCLicIA;

	/**
	 * Enum des etats de l'automate
	 * 
	 * @author Louka Soret
	 *
	 */
	public enum State {
		INIT, // Etat du moteur apres initialisation
		POSER_PINGOUIN, // Phase de pose des pingouins
		SELECTIONNER_PINGOUIN, // Deroulement du jeu: selection pingouin
		SELECTIONNER_DESTINATION, // Deroulement du jeu: selection destination
		RESULTATS; // Etat en fin de partie

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
	 * 
	 * @author Louka Soret
	 *
	 */
	public enum Action {
		MAUVAIS_ETAT, // La machine a etat a deraillee
		SELECTION_VALIDE, // La selection faite par le joueur etait invalide
		SELECTION_INVALIDE, // La selection faite par le joueur etait valide
		PINGOUINPOSES, // La phase de pose de pingouin est terminee
		FIN_PARTIE, // Tous les pingouins sont bloquees
		UNDO, // Undo demande par un joueur
		REDO, // Redo demande par un joueur
		UNDOPHASEMODIFIER, // Undo qui change la phase de jeu
		REDOPHASEMODIFIER; // Redo qui change la phase de jeu

		static public String toString(Action s) {
			switch (s) {
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

	public Moteur(Plateau p, ArrayList<Joueur> joueurs) {
		this.plateau = p;
		this.njoueurs = joueurs.size();
		this.joueurs = joueurs;
		this.undoRedoAutorise = false;
		this.selected = null;
		for (Joueur j : joueurs) {
			if (!j.estIA()) {
				this.undoRedoAutorise = true;
			}
		}

		currentState = State.INIT;
		initTransitions();
	}

	/**
	 * Remplissage de la fonction de transition
	 */
	private void initTransitions() {
		this.transition = new HashMap<Couple<State, Action>, State>();

		// POSER_PINGOUIN
		this.transition.put(new Couple<State, Action>(State.POSER_PINGOUIN, Action.PINGOUINPOSES),
				State.SELECTIONNER_PINGOUIN);
		this.transition.put(new Couple<State, Action>(State.POSER_PINGOUIN, Action.SELECTION_VALIDE),
				State.POSER_PINGOUIN);
		this.transition.put(new Couple<State, Action>(State.POSER_PINGOUIN, Action.SELECTION_INVALIDE),
				State.POSER_PINGOUIN);
		this.transition.put(new Couple<State, Action>(State.POSER_PINGOUIN, Action.MAUVAIS_ETAT), State.POSER_PINGOUIN);
		this.transition.put(new Couple<State, Action>(State.POSER_PINGOUIN, Action.FIN_PARTIE), State.RESULTATS);
		this.transition.put(new Couple<State, Action>(State.POSER_PINGOUIN, Action.UNDO), State.POSER_PINGOUIN);
		this.transition.put(new Couple<State, Action>(State.POSER_PINGOUIN, Action.REDO), State.POSER_PINGOUIN);
		this.transition.put(new Couple<State, Action>(State.POSER_PINGOUIN, Action.UNDOPHASEMODIFIER),
				State.POSER_PINGOUIN);
		this.transition.put(new Couple<State, Action>(State.POSER_PINGOUIN, Action.REDOPHASEMODIFIER),
				State.SELECTIONNER_PINGOUIN);

		// SELECTIONNER_PINGOUIN
		this.transition.put(new Couple<State, Action>(State.SELECTIONNER_PINGOUIN, Action.SELECTION_VALIDE),
				State.SELECTIONNER_DESTINATION);
		this.transition.put(new Couple<State, Action>(State.SELECTIONNER_PINGOUIN, Action.SELECTION_INVALIDE),
				State.SELECTIONNER_PINGOUIN);
		this.transition.put(new Couple<State, Action>(State.SELECTIONNER_PINGOUIN, Action.MAUVAIS_ETAT),
				State.SELECTIONNER_PINGOUIN);
		this.transition.put(new Couple<State, Action>(State.SELECTIONNER_PINGOUIN, Action.FIN_PARTIE), State.RESULTATS);
		this.transition.put(new Couple<State, Action>(State.SELECTIONNER_PINGOUIN, Action.UNDO),
				State.SELECTIONNER_PINGOUIN);
		this.transition.put(new Couple<State, Action>(State.SELECTIONNER_PINGOUIN, Action.REDO),
				State.SELECTIONNER_PINGOUIN);
		this.transition.put(new Couple<State, Action>(State.SELECTIONNER_PINGOUIN, Action.UNDOPHASEMODIFIER),
				State.POSER_PINGOUIN);
		this.transition.put(new Couple<State, Action>(State.POSER_PINGOUIN, Action.REDOPHASEMODIFIER),
				State.SELECTIONNER_PINGOUIN);

		// SELECTIONNER_DESTINATION
		this.transition.put(new Couple<State, Action>(State.SELECTIONNER_DESTINATION, Action.SELECTION_VALIDE),
				State.SELECTIONNER_PINGOUIN);
		this.transition.put(new Couple<State, Action>(State.SELECTIONNER_DESTINATION, Action.SELECTION_INVALIDE),
				State.SELECTIONNER_PINGOUIN);
		this.transition.put(new Couple<State, Action>(State.SELECTIONNER_DESTINATION, Action.MAUVAIS_ETAT),
				State.SELECTIONNER_DESTINATION);
		this.transition.put(new Couple<State, Action>(State.SELECTIONNER_DESTINATION, Action.FIN_PARTIE),
				State.RESULTATS);
		this.transition.put(new Couple<State, Action>(State.SELECTIONNER_DESTINATION, Action.UNDO),
				State.SELECTIONNER_PINGOUIN);
		this.transition.put(new Couple<State, Action>(State.SELECTIONNER_DESTINATION, Action.REDO),
				State.SELECTIONNER_PINGOUIN);
		this.transition.put(new Couple<State, Action>(State.SELECTIONNER_DESTINATION, Action.UNDOPHASEMODIFIER),
				State.POSER_PINGOUIN);
		this.transition.put(new Couple<State, Action>(State.POSER_PINGOUIN, Action.REDOPHASEMODIFIER),
				State.SELECTIONNER_PINGOUIN);

	}

	/**
	 * Change l'etat courant en appliquant la transition (etat courrant,action) sur
	 * la machine a etat
	 * 
	 * @param action
	 */
	public void transition(Action action) {
		Couple<State, Action> newkey = new Couple<State, Action>(this.currentState, action);
		for (Couple<State, Action> key : this.transition.keySet()) {
			if (key.equals(newkey)) {
				this.currentState = this.transition.get(key);
			}
		}
	}

	public boolean undoPossible() {
		return this.plateau.undoPossible() || this.undoRedoAutorise;
	}
	
	public boolean redoPossible() {
		return this.plateau.redoPossible() || this.undoRedoAutorise;
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
		if (!tousElimines())
			return this.joueurs.get(indexJoueurCourant);
		else
			return null;
	}

	public int indexJoueurCourant() {
		if (!tousElimines())
			return this.indexJoueurCourant;
		else
			return -1;
	}

	public boolean tousElimines() {
		int i = 0;
		for (Joueur j : this.joueurs) {
			if (j.estElimine())
				i++;
		}
		if (i == this.joueurs.size())
			return true;
		else
			return false;
	}

	private boolean pingouinsPoses() {
		for (Joueur j : this.joueurs) {
			if (j.nbPingouin() != j.pingouins().size()) {
				return false;
			}
		}
		return true;
	}

	private int indexJoueur(int id) {
		for (int i = 0; i < this.joueurs.size(); i++) {
			if (this.joueurs.get(i).id() == id) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Renvois un tableau d'entier a deux dimentions, chaque ligne du tableau
	 * représente un rang dans le podium (le gagnant est a l'indice 0), dans chaque
	 * ligne il y a: l'ID du joueur, son score de poissons et son score de cases.
	 * 
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ArrayList<Integer>> scores(boolean sorted) {
		@SuppressWarnings("unchecked")
		ArrayList<Joueur> tmp = (ArrayList<Joueur>) this.joueurs.clone();
		if(sorted) {
			// Tri les joueurs elimines en vue du calcul du podium
			Collections.sort(tmp, new Comparator<Joueur>() {
				@Override
				public int compare(Joueur a, Joueur b) {
					if (a.scoreFish() == b.scoreFish()) {
						return a.scoreDestroyed() - b.scoreDestroyed();
					} else {
						return a.scoreFish() - b.scoreFish();
					}
				}
			});
			Collections.reverse(tmp);	
		}
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
		for (Joueur j : tmp) {
			res.add(new ArrayList<Integer>() {
				private static final long serialVersionUID = 1L;
				{
					add(j.id());
					add(j.scoreFish());
					add(j.scoreDestroyed());
				}
			});
		}
		return res;
	}

	/**
	 * Passe au joueur suivant. Si selui ci ne peut plus jouer, le supprime des
	 * joueurs et passe au suivant. Si il n'y a plus de joueurs retourne null
	 * 
	 * @return
	 */
	public Joueur joueurSuivant() {
		if (tousElimines())
			return null;

		do {
			this.indexJoueurCourant = (this.indexJoueurCourant + 1) % this.joueurs.size();
		} while (this.joueurCourant().estElimine() || ((this.joueurCourant().nbPingouin() == joueurCourant().pingouins().size()) && this.currentState == State.POSER_PINGOUIN));
		
		if (this.currentState() == State.POSER_PINGOUIN) {
			return joueurCourant();
		} else {
			for (Pingouin p : this.joueurCourant().pingouins()) {
				if (!this.plateau.estIsolee(p.position())) {
					return joueurCourant();
				}
			}
			this.joueurCourant().eliminer();
			return joueurSuivant();
		}
	}

	/**
	 * pingouinSelection : renvoie le pingouin actuellement selectionn�
	 * 
	 * @return le pingouin actuellement selectionn� (ou null s'il n'y en a pas)
	 */
	public Pingouin pingouinSelection() {
		if ((this.selected != null) && (this.plateau.getCellule(this.selected).aPingouin())) {
			return this.plateau.getCellule(this.selected).pingouin();
		} else {
			return null;
		}
	}

	/**
	 * poserPingouin : pose un pingouin � la position donn�e en param�tre et change
	 * l'�tat du moteur (joueur courant + �tat courant)
	 * 
	 * @param p
	 *            position ou poser le pingouin
	 * @return p si le pingouin a �t� pos�, (-1,-1) sinon
	 */
	public Position poserPingouin(Position position) {
		Position tmp = position;

		if (currentState == State.POSER_PINGOUIN) {
			// Si le joueur est une IA
			if (this.joueurCourant().estIA()) {
				Position calculated = this.joueurCourant().prochainePosePingouin(this.plateau);
				// Si le calcule de l'IA a reussis
				if (!calculated.equals(new Position(-1, -1))) {
					tmp = calculated;
				} else {
					transition(Action.SELECTION_INVALIDE);
					return new Position(-1, -1);
				}
			}
			// Si la pose reussis
			if (this.joueurCourant().posePingouin(this.plateau, tmp)) {
				// Si tout les pingouins ont ete poses
				if (pingouinsPoses()) {
					transition(Action.PINGOUINPOSES);
					this.indexJoueurCourant = 0;
				} else {
					transition(Action.SELECTION_VALIDE);
					this.joueurSuivant();
				}
				return tmp;
			} else {
				transition(Action.SELECTION_INVALIDE);
				return new Position(-1, -1);
			}
		} else {
			transition(Action.MAUVAIS_ETAT);
			return new Position(-1, -1);
		}
	}

	/**
	 * selectionnerPingouin : le moteur retiendra le pingouin selectionn� (et change
	 * son �tat en cons�quence)
	 * 
	 * @param p
	 *            : position du pingouin � selectionner
	 * @return true si le pingouin a �t� s�l�ctionn�, false sinon
	 */
	public Position selectionnerPingouin(Position p) {
		Position tmp = p;

		if (currentState == State.SELECTIONNER_PINGOUIN) {
			// Si le joueur est une IA
			if (this.joueurCourant().estIA()) {
				Couple<Position, Position> calculated = this.joueurCourant().prochainCoup(plateau,this.scores(false));
				if (!calculated.equals(new Couple<Position, Position>(new Position(-1, -1), new Position(-1, -1)))) {
					// Si choix du pingouin effectue
					tmp = calculated.gauche();
					this.prochainCLicIA = calculated.droit();
				} else {
					transition(Action.SELECTION_INVALIDE);
					return new Position(-1, -1);
				}
			}
			// Si le pingouin n'est pas isole
			if (!plateau.estIsolee(tmp)) {
				// Si La cellule en p a un pingouin et que ce pingouin appartient au joueur
				// courrant
				if (plateau.getCellule(tmp).aPingouin()
						&& (plateau.getCellule(tmp).pingouin().employeur() == joueurCourant().id())) {
					this.selected = tmp;
					transition(Action.SELECTION_VALIDE);
					return tmp;
				}
			}
			transition(Action.SELECTION_INVALIDE);
			return new Position(-1, -1);
		} else {
			transition(Action.MAUVAIS_ETAT);
			return new Position(-1, -1);
		}
	}

	/**
	 * selectionnerDestinnation : si possible, d�place le pingouin actuellement
	 * selectionn� � la destination
	 * 
	 * @param p
	 *            : destination
	 * @return true si le pingouin s�l�ctionn� a �t� d�plac�, false sinon
	 * @throws Exception
	 */
	public Position selectionnerDestination(Position destination) {
		Position tmp = destination;

		if (currentState == State.SELECTIONNER_DESTINATION) {
			if (this.joueurCourant().estIA()) {
				tmp = this.prochainCLicIA;
			}
			try {
				if (this.joueurCourant().jouerCoup(this.plateau, selected, tmp) < 0) {
					transition(Action.SELECTION_INVALIDE);
					return new Position(-1, -1);
				} else {
					if (joueurSuivant() == null) {
						transition(Action.FIN_PARTIE);
					} else {
						transition(Action.SELECTION_VALIDE);
					}
					return tmp;
				}
			} catch (Exception e) {
				transition(Action.SELECTION_INVALIDE);
				return new Position(-1, -1);
			}
		} else {
			transition(Action.MAUVAIS_ETAT);
			return new Position(-1, -1);
		}
	}

	/**
	 * Reviens au coup precedent, s'arrete au premier joueur physique
	 * @return
	 * @throws Exception
	 */
	public Joueur undo() throws Exception {

		if (this.undoRedoAutorise) {
			Couple<Integer, Integer> res;

			do { // On remonte dans les joueurs jusqu'a en trouver un humain
				res = plateau.undo();
				if (res.gauche() >= 0) {
					if ((indexJoueurCourant = indexJoueur(res.droit())) >= 0) {
						joueurCourant().undo(res.gauche());
					} else {
						throw new Exception("Le joueur renvoyé par undo est introuvable");
					}
				} else {
					return null;
				}
			} while (joueurCourant().estIA());

			if (pingouinsPoses()) {
				transition(Action.UNDO);
			} else {
				transition(Action.UNDOPHASEMODIFIER);
			}

			return joueurCourant();
		} else {
			return null;
		}

	}

	/**
	 * Rejoue le coup qui a ete undo en dernier
	 * @return
	 */
	public Joueur redo() {

		if (this.undoRedoAutorise) {
			int fishAte;

			do {
				if ((fishAte = plateau.redo()) > 0) {
					if (currentState == State.POSER_PINGOUIN) {
						joueurSuivant().redo(fishAte, 0);
					} else {
						joueurSuivant().redo(fishAte, 1);
					}
				} else {
					return null;
				}
			} while (this.joueurCourant().estIA());

			if (!pingouinsPoses()) {
				transition(Action.REDO);
			} else {
				transition(Action.REDOPHASEMODIFIER);
			}

			return joueurCourant();
		} else {
			return null;
		}
	}
	
	/**
	 * sauvegarde l'etat courant du moteur dans rsc/save/filename
	 * @param filename
	 * @return
	 */
	public boolean sauvegarder(String filename) {
		try {
			File file = new File("rsc/save/"+filename);
			file.getParentFile().mkdirs();
			file.createNewFile(); // if file already exists will do nothing 
			ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file,false));
			stream.writeObject(this);
			stream.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean sauvegarder() {
		return sauvegarder("newSave");
	}
	
	/**
	 * charge un moteur depuis rsc/save/filename et le renvois
	 * @param filename
	 * @return le moteur charge
	 */
	static public Moteur charger(String filename) {
		Moteur m;
		try {
			File file = new File("rsc/save/"+filename);
			file.getParentFile().mkdirs();
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file));
			m = (Moteur) stream.readObject();
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			m = null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			m = null;
		}
		return m;
	}
	
	public Moteur charger() {
		return charger("newSave");
	}
	
	public Couple<Position,Position> sugestion() {
		 return UtilsIA.jouerCoupDifficile(this.plateau,joueurCourant().id());
	}
}
