package Controleur;

import java.awt.Point;

import Modele.Plateau.Pingouin;
import Vue.Cadre.PlateauCadre;
import Vue.GameObject.Case;
import Vue.GameObject.PingouinGraphique;
import Vue.Pane.GamePane;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class MiseEnEvidenceCase implements EventHandler<MouseEvent> {

	public MiseEnEvidenceCase() {
	}

	@Override
	public void handle(MouseEvent event) {
		if (event.getEventType() == MouseEvent.MOUSE_MOVED) {
			PlateauCadre pc = GamePane.getPlateauCadre();
			for (int i = 0; i < pc.plateau.getSize(); i++) {
				for (int j = 0; j < pc.plateau.getSize()-(1-i%2); j++) {
					Case c = pc.plateauGraphique.cases[i][j];
					if(c==null) return;
					if (c.collision(new Point((int) event.getX(), (int) event.getY()))) {
						c.mettreEnValeur();
					} else {
						c.enleverMiseEnValeur();
					}
				}
			}
			
			Pingouin pingouin = GamePane.moteur().pingouinSelection();
			if(pingouin != null && !GamePane.moteur().joueurCourant().estIA()) {
				PingouinGraphique pg = pc.plateauGraphique.cases[pingouin.position().i()][pingouin.position().j()].pingouinGraphique;
				Point2D vec = new Point2D(event.getX() - pg.position().x,event.getY() - pg.position().y);
				double angle = Math.atan2(vec.getY(), vec.getX()) - Math.atan2(0, 1);
				pg.setViewDirection(-Math.toDegrees(angle));
			}
		}
	}
}
