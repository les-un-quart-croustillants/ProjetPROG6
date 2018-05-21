package Vue.GameObject;

import Modele.Moteur.Moteur;
import Utils.Position;
import Vue.Donnees;
import Vue.Pane.GamePane;
import Vue.Pane.ScorePane;

public class MoteurGraphique extends GameObject {

	public enum StateGraph {
		ATTENDRE_MOTEUR, POSER_PINGOUIN_GRAPH, SELECTIONNER_PINGOUIN_GRAPH, SELECTIONNER_DESTINATION_GRAPH, CHANGER_JOUEUR_GRAPH, FIN;

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
			case FIN:
				return "FIN";
			default:
				return "undefined";
			}
		}
	}

	public MoteurGraphique() {
	}

	private StateGraph currentState = StateGraph.ATTENDRE_MOTEUR;
	private double time = System.currentTimeMillis();
	private double delay = 250;

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
		switch (moteur.currentState()) {
		case POSER_PINGOUIN:
			if (moteur.joueurCourant().estIA()) {
				GamePane.getPlateauCadre().infoGraphique.setText("Attendez votre tour");
				currentState = StateGraph.POSER_PINGOUIN_GRAPH;
			}
			else
				GamePane.getPlateauCadre().infoGraphique.setText("Cliquez sur une case à 1 poisson pour poser un pingouin");
			break;
		case SELECTIONNER_PINGOUIN:
			if (moteur.joueurCourant().estIA()) {
				GamePane.getPlateauCadre().infoGraphique.setText("Attendez votre tour");
				currentState = StateGraph.SELECTIONNER_PINGOUIN_GRAPH;
			}
			else
				GamePane.getPlateauCadre().infoGraphique.setText("Selectionnez un pingouin");
			break;
		case RESULTATS:
			GamePane.getPlateauCadre().infoGraphique.setText("Fin de la partie");
			GamePane.getInstance().getChildren().add(new ScorePane());
			currentState = StateGraph.FIN;
			break;
		default:
			break;

		}
	}

	private Position pingouinCoupIA;

	private void onStateSELECTIONNER_PINGOUIN_GRAPH() {
		if (GamePane.moteur().joueurCourant().estIA()) {
			Position p = GamePane.moteur().selectionnerPingouin(new Position(-1, -1));
			pingouinCoupIA = p;
			GamePane.getPlateauCadre().plateauGraphique.cases[p.i()][p.j()].select();
			currentState = StateGraph.SELECTIONNER_DESTINATION_GRAPH;
		}
	}

	private void onStateSELECTIONNER_DESTINATION_GRAPH() {
		if (GamePane.moteur().joueurCourant().estIA()) {
			Position p = GamePane.moteur().selectionnerDestination(new Position(-1, -1));
			GamePane.getPlateauCadre().plateauGraphique.cases[pingouinCoupIA.i()][pingouinCoupIA.j()].deselect();
			GamePane.getPlateauCadre().plateauGraphique.cases[pingouinCoupIA.i()][pingouinCoupIA.j()].pingouinGraphique
					.moveTo(p);
			GamePane.getPlateauCadre().plateauGraphique.cases[pingouinCoupIA.i()][pingouinCoupIA.j()].detruire();
			currentState = StateGraph.CHANGER_JOUEUR_GRAPH;
		}
	}

	private void onStatePOSER_PINGOUIN_GRAPH() {
		int i_joueur_courant = GamePane.moteur().indexJoueurCourant();
		if (GamePane.moteur().joueurCourant().estIA()) {
			Position p = GamePane.moteur().poserPingouin(new Position(-1, -1));
			GamePane.getPlateauCadre().gameObjects.get(1)
					.add(new PingouinGraphique(GamePane.moteur().plateau().getCellule(p).pingouin(),
							GamePane.getPlateauCadre().plateauGraphique, Donnees.COULEURS_JOUEURS[i_joueur_courant]));
			currentState = StateGraph.CHANGER_JOUEUR_GRAPH;
		} else {

		}
	}

	private void onStateCHANGER_JOUEUR_GRAPH() {
		if (GamePane.moteur().currentState() == State.SELECTIONNER_PINGOUIN
				|| GamePane.moteur().currentState() == State.POSER_PINGOUIN) {
			GamePane.getPlateauCadre().joueurCourantGraphique
					.setText("Joueur " + (1 + GamePane.moteur().joueurCourant().id()) + "("
							+ GamePane.moteur().joueurCourant().scoreFish() + ")");
			GamePane.getPlateauCadre().joueurCourantGraphique
					.setCouleur(Donnees.COULEURS_JOUEURS[GamePane.moteur().joueurCourant().id()]);
		}
		currentState = StateGraph.ATTENDRE_MOTEUR;
	}

	public StateGraph getCurrentState() {
		return currentState;
	}

	public void setCurrentState(StateGraph sg) {
		currentState = sg;
	}
}
