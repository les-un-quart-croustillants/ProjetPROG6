package Controleur;

import java.awt.Point;
import java.util.LinkedList;

import Modele.Moteur.Moteur.State;
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
					if (!GamePane.moteur().poserPingouin(c.posPlateau).equals(new Position(-1,-1))) {
						GamePane.getPlateauCadre().gameObjects.get(1).add(
								new PingouinGraphique(GamePane.moteur().plateau().getCellule(c.posPlateau).pingouin(),
										pc.plateauGraphique, Donnees.getCouleur(i_joueur_courant)));
						pc.moteurGraphique.setCurrentState(StateGraph.CHANGER_JOUEUR_GRAPH);
					}
				}
			} else if (GamePane.moteur().currentState() == State.SELECTIONNER_PINGOUIN) {
				Case c = pc.plateauGraphique.XYtoCase(new Point((int) event.getX(), (int) event.getY()));
				if (c != null) {
					if (!GamePane.moteur().selectionnerPingouin(c.posPlateau).equals(new Position(-1, -1))) {
						for (Position pos : pc.plateau.accessible(c.posPlateau)) {
							if(pc.plateauGraphique.cases[pos.i()][pos.j()]!=null)
								pc.plateauGraphique.cases[pos.i()][pos.j()].select();
						}
						GamePane.getPlateauCadre().infoGraphique.setText("Selectionnez une destination");
					}
				}
			}
			else if(GamePane.moteur().currentState() == State.SELECTIONNER_DESTINATION) {
				Case c = pc.plateauGraphique.XYtoCase(new Point((int) event.getX(), (int) event.getY()));
				Position lastSelection = GamePane.moteur().pingouinSelection().position();
				LinkedList<Position> lastaccessibles = pc.plateau.accessible(lastSelection);
				Position p = new Position(-1,-1);
				if(c!=null)
					p = c.posPlateau;
				if (!GamePane.moteur().selectionnerDestination(p).equals(new Position(-1,-1))) {
					GamePane.getPlateauCadre().plateauGraphique.cases[lastSelection.i()][lastSelection.j()].pingouinGraphique.moveTo(p);
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
