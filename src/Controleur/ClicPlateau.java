package Controleur;
import java.awt.Point;

import Vue.Case;
import Vue.PlateauCadre;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class ClicPlateau implements EventHandler<MouseEvent> {

	PlateauCadre pc;

	public ClicPlateau(PlateauCadre pc) {
		this.pc = pc;
	}

	@Override
	public void handle(MouseEvent event) {
		if (event.getEventType() == MouseEvent.MOUSE_MOVED) {
			for (int i=0;i<pc.plateauGraphique.plateau.getSize();i++) {
				Case c = pc.plateauGraphique.cases[i][0];
				if(c.collision(new Point((int)event.getX(),(int)event.getY()))){
					c.couleur = new Color(1,0,0,1);
				}
				else{
					c.couleur = new Color(0,0,0,1);
				}
			}
		}

	}

}
