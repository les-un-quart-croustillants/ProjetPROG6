package Vue.GameObject;

import java.util.LinkedList;

import Modele.Moteur.Moteur;
import Modele.Moteur.Moteur.State;
import Utils.Couple;
import Utils.Position;
import Vue.Donnees;
import Vue.GameObject.MoteurGraphique.StateGraph;
import Vue.Pane.GamePane;

public class MoteurGraphique extends GameObject {

	public enum StateGraph {
		ATTENDRE_MOTEUR, POSER_PINGOUIN_GRAPH, SELECTIONNER_PINGOUIN_GRAPH, SELECTIONNER_DESTINATION_GRAPH, CHANGER_JOUEUR_GRAPH;

		static public String toString(StateGraph s) {
			switch (s) {
			case ATTENDRE_MOTEUR:
				return "ATTENDRE_MOTEUR";
			case POSER_PINGOUIN_GRAPH:
				return "POSER_PINGOUIN_GRAPH";
			case SELECTIONNER_PINGOUIN_GRAPH:
				return "SELECTIONNER_PINGOUIN_GRAPH";
			case SELECTIONNER_DESTINATION_GRAPH:
				return "SELECTIONNER_DESTINATION_GRAPH";
			case CHANGER_JOUEUR_GRAPH:
				return "CHANGER_JOUEUR_GRAPH";
			default:
				return "undefined";
			}
		}
	}

	public MoteurGraphique() {
	}

	private StateGraph currentState = StateGraph.ATTENDRE_MOTEUR;
	private double time = System.currentTimeMillis();
	private double delay = 1000;

	@Override
	public void update() {
		switch (currentState) {
		case ATTENDRE_MOTEUR:
			onStateATTENDRE_MOTEUR();
			time = System.currentTimeMillis();
			break;
		case CHANGER_JOUEUR_GRAPH:
			if (time + 250 < System.currentTimeMillis()) {
				onStateCHANGER_JOUEUR_GRAPH();
			}
			break;
		case POSER_PINGOUIN_GRAPH:
			if (time + delay < System.currentTimeMillis()) {
				onStatePOSER_PINGOUIN_GRAPH();
				time = System.currentTimeMillis();
			}
			break;
		case SELECTIONNER_PINGOUIN_GRAPH:
			if (time + delay < System.currentTimeMillis()) {
				onStateSELECTIONNER_PINGOUIN_GRAPH();
				time = System.currentTimeMillis();
			}
			break;
		case SELECTIONNER_DESTINATION_GRAPH:
			if (time + delay < System.currentTimeMillis()) {
				onStateSELECTIONNER_DESTINATION_GRAPH();
				time = System.currentTimeMillis();
			}
			break;
		default:
			break;
		}
	}

	private void onStateATTENDRE_MOTEUR() {
		Moteur moteur = GamePane.moteur();
		if (moteur.joueurCourant().estIA()) {
			switch (moteur.currentState()) {
			case POSER_PINGOUIN:
				currentState = StateGraph.POSER_PINGOUIN_GRAPH;
				break;
			case SELECTIONNER_PINGOUIN:
				currentState = StateGraph.SELECTIONNER_PINGOUIN_GRAPH;
			default:
				break;

			}
		}
	}

	private Couple<Position, Position> currentCoupIA;

	private void onStateSELECTIONNER_PINGOUIN_GRAPH() {
		if (GamePane.moteur().joueurCourant().estIA()) {
			currentCoupIA = GamePane.moteur().coupIA();
			GamePane.getPlateauCadre().plateauGraphique.cases[currentCoupIA.droit().i()][currentCoupIA.droit().j()].select();
			GamePane.getPlateauCadre().plateauGraphique.cases[currentCoupIA.gauche().i()][currentCoupIA.gauche().j()].select();
			currentState = StateGraph.SELECTIONNER_DESTINATION_GRAPH;
		}
	}

	private void onStateSELECTIONNER_DESTINATION_GRAPH() {
		GamePane.getPlateauCadre().plateauGraphique.cases[currentCoupIA.gauche().i()][currentCoupIA.gauche().j()].pingouinGraphique.moveTo(currentCoupIA.droit());
		GamePane.getPlateauCadre().plateauGraphique.cases[currentCoupIA.gauche().i()][currentCoupIA.gauche().j()]
				.detruire();
		GamePane.getPlateauCadre().plateauGraphique.cases[currentCoupIA.droit().i()][currentCoupIA.droit().j()].deselect();
		GamePane.getPlateauCadre().plateauGraphique.cases[currentCoupIA.gauche().i()][currentCoupIA.gauche().j()].deselect();
		currentState = StateGraph.CHANGER_JOUEUR_GRAPH;
	}

	private void onStatePOSER_PINGOUIN_GRAPH() {
		int i_joueur_courant = GamePane.moteur().indexJoueurCourant();
		if (GamePane.moteur().joueurCourant().estIA()) {
			Position p = GamePane.moteur().posePingouinIA();
			GamePane.getPlateauCadre().gameObjects
					.add(new PingouinGraphique(GamePane.moteur().plateau().getCellule(p).pingouin(),
							GamePane.getPlateauCadre().plateauGraphique, Donnees.COULEURS_JOUEURS[i_joueur_courant]));
			currentState = StateGraph.CHANGER_JOUEUR_GRAPH;
		} else {

		}
	}

	private void onStateCHANGER_JOUEUR_GRAPH() {
		GamePane.getPlateauCadre().joueurCourantGraphique
				.setText("Joueur " + (1 + GamePane.moteur().joueurCourant().id()) + "("
						+ GamePane.moteur().joueurCourant().scoreFish() + ")");
		GamePane.getPlateauCadre().joueurCourantGraphique
				.setCouleur(Donnees.COULEURS_JOUEURS[GamePane.moteur().joueurCourant().id()]);
		currentState = StateGraph.ATTENDRE_MOTEUR;
	}

	public StateGraph getCurrentState() {
		return currentState;
	}
	public void setCurrentState(StateGraph sg) {
		currentState = sg;
	}
}
