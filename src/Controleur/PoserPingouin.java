package Controleur;

import java.awt.Point;
import java.util.LinkedList;

import Modele.Moteur.Moteur.State;
import Modele.Plateau.Pingouin;
import Utils.Position;
import Vue.Donnees;
import Vue.Cadre.PlateauCadre;
import Vue.GameObject.Case;
import Vue.GameObject.PingouinGraphique;
import Vue.GameObject.MoteurGraphique.StateGraph;
import Vue.Pane.GamePane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class PoserPingouin implements EventHandler<MouseEvent> {

	private PlateauCadre pc;

	public PoserPingouin(PlateauCadre pc) {
		this.pc = pc;
	}

	@Override
	public void handle(MouseEvent event) {
		if(GamePane.moteur().joueurCourant().estIA()){
			return;
		}
		if (event.getEventType() == MouseEvent.MOUSE_PRESSED && pc.moteurGraphique.getCurrentState()==StateGraph.ATTENDRE_MOTEUR) {
			if (GamePane.moteur().currentState() == State.POSER_PINGOUIN) {
				Case c = pc.plateauGraphique.XYtoCase(new Point((int) event.getX(), (int) event.getY()));
				if (c != null) {
					int i_joueur_courant = GamePane.moteur().indexJoueurCourant();
					if (GamePane.moteur().poserPingouin(c.posPlateau)) {
						GamePane.getPlateauCadre().gameObjects.add(
								new PingouinGraphique(GamePane.moteur().plateau().getCellule(c.posPlateau).pingouin(),
										pc.plateauGraphique, Donnees.COULEURS_JOUEURS[i_joueur_courant]));
						pc.moteurGraphique.setCurrentState(StateGraph.CHANGER_JOUEUR_GRAPH);
					}
				}
			} else if (GamePane.moteur().currentState() == State.SELECTIONNER_PINGOUIN) {
				Case c = pc.plateauGraphique.XYtoCase(new Point((int) event.getX(), (int) event.getY()));
				if (c != null) {
					if (GamePane.moteur().selectionnerPingouin(c.posPlateau)) {
						for (Position pos : pc.plateau.accessible(c.posPlateau)) {
							if(pc.plateauGraphique.cases[pos.i()][pos.j()]!=null)
								pc.plateauGraphique.cases[pos.i()][pos.j()].select();
						}
					}
				}
			}
			else if(GamePane.moteur().currentState() == State.SELECTIONNER_DESTINATION) {
				Case c = pc.plateauGraphique.XYtoCase(new Point((int) event.getX(), (int) event.getY()));
				Position lastSelection = GamePane.moteur().pingouinSelection().position();
				Pingouin ping = GamePane.moteur().pingouinSelection();
				LinkedList<Position> lastaccessibles = pc.plateau.accessible(lastSelection);
				if (c != null && GamePane.moteur().selectionnerDestination(c.posPlateau)) {
					GamePane.getPlateauCadre().plateauGraphique.cases[lastSelection.i()][lastSelection.j()].pingouinGraphique.moveTo(c.posPlateau);
					GamePane.getPlateauCadre().plateauGraphique.cases[lastSelection.i()][lastSelection.j()].detruire();
					for (Position pos : lastaccessibles) {
						if(pc.plateauGraphique.cases[pos.i()][pos.j()]!=null)
							pc.plateauGraphique.cases[pos.i()][pos.j()].deselect();
					}
					pc.moteurGraphique.setCurrentState(StateGraph.CHANGER_JOUEUR_GRAPH);
				}
				else {
					for (Position pos : lastaccessibles) {
						if(pc.plateauGraphique.cases[pos.i()][pos.j()]!=null)
							pc.plateauGraphique.cases[pos.i()][pos.j()].deselect();
					}
				}
			}
		}
		
	}

}
