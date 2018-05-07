package Vue;

import java.util.HashMap;

import Joueurs.Joueur;
import Joueurs.JoueurPhysique;
import Modele.Plateau.Plateau;
import Utils.Couple;

public class Moteur {
	private Joueur joueurs[];
	private Plateau plateau;
	
	private int njoueurs;
	private int indexJoueurCourant=0;
	
	private State currentState;
	private HashMap<Couple<State, Action>, State> transition;
	
	/**
	 * Enum des etats de l'automate
	 * 
	 * @author Louka Soret
	 *
	 */
	public enum State {
		INIT,
		MAINMENU;
		
		
		static public String toString(State s) {
			switch(s) {
			case INIT:
				return "INIT";
			case MAINMENU:
				return "MAINMENU";
			default:
				return "undefined";
			}
		}
		
		/**
		 * determine si un etat est final
		 * @param s
		 * @return
		 */
		static public boolean estFinal(State s) {
			switch(s) {
				default:
					return false;
			}
		}
		
		static public boolean estDebutJeu(State s) {
			switch(s) {
				default:
					return false;
			}
		}
	}
	
	/**
	 * Actions possibles renvoyees par actionMoteur ou configMoteur
	 * @author Louka Soret
	 *
	 */
	public enum Action {
		ERROR;
		
		static public String toString(Action s) {
			switch(s) {
			case ERROR:
				return "ERROR";
			default:
				return "undefined";
			}
		}
	}
	
	public Moteur() {
		
		joueurs = new Joueur[njoueurs];
		currentState = State.INIT;
		initTransitions();
		
		//par defaut, on met que des joueurs physiques
		for(int i=0;i<njoueurs;i++) {
			joueurs[i] = new JoueurPhysique(i);
		}
	}
	
	/**
	 * Remplissage de la fonction de transition
	 */
	private void initTransitions() {
		this.transition = new HashMap<Couple<State,Action>,State>();
		this.transition.put(new Couple<State,Action>(State.INIT,Action.ERROR), State.MAINMENU);
	}
	
	public void transition(Action action) {
		this.currentState = this.transition.get(new Couple<State,Action>(this.currentState,action));
	}
	
	public State currentState() {
		return this.currentState;
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
		this.indexJoueurCourant = (this.indexJoueurCourant +1)%this.njoueurs();
		return joueurCourant();
	}
}
