package Controleur;

import java.awt.Point;

import Modele.Plateau.Pingouin;
import Utils.Position;
import Vue.Donnees;
import Vue.Moteur.State;
import Vue.Cadre.PlateauCadre;
import Vue.GameObject.Case;
import Vue.GameObject.PingouinGraphique;
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
		if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
			if (GamePane.moteur().currentState() == State.POSER_PINGOUIN) {
				Case c = pc.plateauGraphique.XYtoCase(new Point((int) event.getX(), (int) event.getY()));
				if (c != null) {
					int i_joueur_courant = GamePane.moteur().indexJoueurCourant();
					if (GamePane.moteur().poserPingouin(c.posPlateau)) {
						System.out.println("Le joueur " + GamePane.moteur().indexJoueurCourant() + "pose un pinguin en "
								+ c.posPlateau);
						GamePane.getPlateauCadre().gameObjects.add(
								new PingouinGraphique(new Pingouin(0, new Position(c.posPlateau.i(), c.posPlateau.j())),
										pc.plateauGraphique, Donnees.COULEURS_JOUEURS[i_joueur_courant]));
					} else {
						System.out.println("Le joueur " + GamePane.moteur().indexJoueurCourant()
								+ " ne peut pas poser un pinguin en " + c.posPlateau + " car la case est occupée");
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
				/*Case c = pc.plateauGraphique.XYtoCase(new Point((int) event.getX(), (int) event.getY()));
				Position lastSelection = GamePane.moteur().pingouinSelection();
				if (c != null && GamePane.moteur().selectionnerDestination(c.posPlateau)) {
					System.out.println("truc");
				}
				else {
					for (Position pos : pc.plateau.accessible(lastSelection)) {
						if(pc.plateauGraphique.cases[pos.i()][pos.j()]!=null)
							pc.plateauGraphique.cases[pos.i()][pos.j()].deselect();
					}
					System.out.println(GamePane.moteur().currentState());
				}*/
			}
		}

	}

}
