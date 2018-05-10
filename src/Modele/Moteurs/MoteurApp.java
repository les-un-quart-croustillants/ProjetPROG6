package Modele.Moteurs;


import java.util.HashMap;
import Utils.Couple;

public class MoteurApp {
	private HashMap<Couple<State, Action>, State> transition;
   	private State currentState;
	
	/**
	 * Enum des etats de l'automate
	 * 
	 * @author Cyprien Eymond Laritaz
	 *
	 */
	public enum State {
		MENU,
		JEU,
		QUITTER;

		static public String toString(State s) {
			switch (s) {
			case MENU:
				return "MENU";
			case JEU:
				return "JEU";
			case QUITTER:
				return "QUITTER";
			default:
				return "undefined";
			}
		}
	}

	/**
	 * Actions possibles renvoyees par actionMoteur ou configMoteur
	 * @author Cyprien Eymond Laritaz
	 *
	 */
	public enum Action {
		NOUVELLE_PARTIE,
		RETOUR_MENU,
		QUITTER_APPLI;
		
		static public String toString(Action s) {
			switch(s) {
			case NOUVELLE_PARTIE:
				return "NOUVELLE_PARTIE";
			case RETOUR_MENU:
				return "RETOUR_MENU";
			case QUITTER_APPLI:
				return "QUITTER_APPLI";
			default:
				return "undefined";
			}
		}
	}
	
	public MoteurApp() {
		currentState = State.MENU;
		initTransitions();
	}
	
	/**
	 * Remplissage de la fonction de transition
	 */
	private void initTransitions() {
		this.transition = new HashMap<Couple<State,Action>,State>();
		
		// MENU
		this.transition.put(new Couple<State,Action>(State.MENU,Action.NOUVELLE_PARTIE),State.JEU);
		this.transition.put(new Couple<State,Action>(State.MENU,Action.QUITTER_APPLI),State.QUITTER);
		
		// JEU
		this.transition.put(new Couple<State,Action>(State.JEU,Action.RETOUR_MENU),State.MENU);
		this.transition.put(new Couple<State,Action>(State.JEU,Action.QUITTER_APPLI),State.QUITTER);
	}
	
	public void transition(Action action) {
		this.currentState = this.transition.get(new Couple<State,Action>(this.currentState,action));
		System.out.println(this.currentState);
	}

	public State currentState() {
		return this.currentState;
	}

	public void setCurrentState(State s) {
		this.currentState = s;
	}
	
	@Override
	public String toString() {
		return currentState.toString();
	}
}
