package Controleur;

import java.awt.Point;

import Vue.PlateauCadre;
import Vue.GameObject.Case;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class ClicPlateau implements EventHandler<MouseEvent> {

	PlateauCadre pc;

	public ClicPlateau(PlateauCadre pc) {
		this.pc = pc;
	}

	@Override
	public void handle(MouseEvent event) {
		if (event.getEventType() == MouseEvent.MOUSE_MOVED) {
			for (int i = 0; i < pc.plateau.getSize(); i++) {
				for (int j = 0; j < pc.plateau.getSize()-(1-i%2); j++) {
					Case c = pc.plateauGraphique.cases[i][j];
					if (c.collision(new Point((int) event.getX(), (int) event.getY()))) {
						c.select();
					} else {
						c.deselect();
					}
				}
			}
		}

	}

}
