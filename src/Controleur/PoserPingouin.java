package Controleur;

import java.awt.Point;

import Joueurs.Joueur;
import Joueurs.Pingouin;
import Utils.Position;
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
		if (event.getEventType() == MouseEvent.MOUSE_PRESSED && GamePane.moteur().currentState()==State.POSER_PINGOUIN) {
			/*Si la case cliqu�e n'est pas d�truite(hors du terrain) et n'a pas deja un pingouin
			 * On ajoute un pingouin � cette case
			 */
			
			/*
			 * Ci-dessous un exemple simple d'ajout de pingouin dans les gameobjects,
			 * mais il faudra aussi l'ajouter dans le mod�le!
			 */
			Case c = pc.plateauGraphique.XYtoCase(new Point((int)event.getX(),(int)event.getY()));
			if(c!=null) {
				if(!estCellulePingouin(c.posPlateau)){
					System.out.println("Le joueur "+GamePane.moteur().indexJoueurCourant()+"pose un pinguin en "+c.posPlateau);
					GamePane.moteur().joueurSuivant();
					GamePane.getPlateauCadre().gameObjects.add(new PingouinGraphique(new Pingouin(0,new Position(c.posPlateau.i(),c.posPlateau.j())), pc.plateauGraphique));
				}
				else{
					System.out.println("Le joueur "+GamePane.moteur().indexJoueurCourant()+" ne peut pas poser un pinguin en "+c.posPlateau+" car la case est occupée");
				}
			}
		}
	}
	
	private boolean estCellulePingouin(Position c){
		for(int i=0;i<GamePane.moteur().njoueurs();i++){
			Joueur joueur = GamePane.moteur().joueurCourant();
			for (Pingouin p : joueur.squad()) {
				if(p.position().i()==c.i() && p.position().j()==c.j()){
					return true;
				}
			}
		}
		return false;
	}

}
