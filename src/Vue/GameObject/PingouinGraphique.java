package Vue.GameObject;

import com.sun.javafx.geom.Vec2d;

import Modele.Moteur.Moteur.State;
import Modele.Plateau.Pingouin;
import Utils.Position;
import Vue.Donnees;
import Vue.Pane.GamePane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PingouinGraphique extends GameObject {
	Pingouin pingouin;
	PlateauGraphique pg;
	Color couleur;
	Vec2d offset = new Vec2d(-Donnees.IMG_PINGOUIN_FACE.getWidth()/2,-Donnees.IMG_PINGOUIN_FACE.getHeight());
	float scale = 1f;
	int width,height;
	Position pingouinPosition;
	
	public PingouinGraphique(Pingouin pingouin,PlateauGraphique pg,Color c) {
		this.pingouin = pingouin;
		this.pg = pg;
		this.couleur = c;
		pg.cases[pingouin.position().i()][pingouin.position().j()].pingouinGraphique = this;
		pingouinPosition = new Position(pingouin.position().i(), pingouin.position().j());
	}
	
	private float f = 0.5f;
	@Override
	public void update() {
		position.x = pg.cases[pingouinPosition.i()][pingouinPosition.j()].position().x+pg.tailleCase/2;
		position.y = (float) (pg.cases[pingouinPosition.i()][pingouinPosition.j()].position().y+pg.tailleCase*0.35);
		offset.x = -Donnees.IMG_PINGOUIN_FACE.getWidth()/2 * scale;
		offset.y = -Donnees.IMG_PINGOUIN_FACE.getHeight() * scale;
		width = (int) (Donnees.IMG_PINGOUIN_FACE.getWidth()*scale);
		height = (int) (Donnees.IMG_PINGOUIN_FACE.getHeight()*scale);
		if(GamePane.moteur().currentState()==State.SELECTIONNER_PINGOUIN && !GamePane.moteur().joueur(pingouin.employeur()).estIA() && pingouin.employeur() == GamePane.moteur().joueurCourant().id())
			f = (float) (10 + Math.cos(0.01*System.currentTimeMillis()) % 1)/10;
		else
			f = 1;
	}
	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(couleur);
		gc.setGlobalAlpha(0.5);
		gc.fillOval(position.x-pg.tailleCase*0.3*f, position.y-pg.tailleCase*0.2*f, pg.tailleCase*0.6*f, pg.tailleCase*0.3*f);
		gc.setGlobalAlpha(1);
		gc.drawImage(Donnees.IMG_PINGOUIN_FACE, position.x+offset.x, position.y+offset.y, width, height);
	}
	
	public void moveTo(Position p){
		pg.cases[pingouinPosition.i()][pingouinPosition.j()].pingouinGraphique = null;
		pingouinPosition = new Position(p.i(), p.j());
		pg.cases[pingouinPosition.i()][pingouinPosition.j()].pingouinGraphique = this;
	}
}
