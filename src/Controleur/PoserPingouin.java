package Controleur;

import java.awt.Point;

import Modele.Plateau.Pingouin;
import Utils.Position;
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
			/*Si la case cliqu�e n'est pas d�truite(hors du terrain) et n'a pas deja un pingouin
			 * On ajoute un pingouin � cette case
			 */
			
			/*
			 * Ci-dessous un exemple simple d'ajout de pingouin dans les gameobjects,
			 * mais il faudra aussi l'ajouter dans le mod�le!
			 */
			Case c = pc.plateauGraphique.XYtoCase(new Point((int)event.getX(),(int)event.getY()));
			if(c!=null) {
				GamePane.getPlateauCadre().gameObjects.add(new PingouinGraphique(new Pingouin(0,new Position(c.posPlateau.i(),c.posPlateau.j())), pc.plateauGraphique));
			}
		}
	}

}
