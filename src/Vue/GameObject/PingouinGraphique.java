package Vue.GameObject;

import com.sun.javafx.geom.Vec2d;
import com.sun.javafx.geom.Vec2f;

import Modele.Moteur.Moteur.State;
import Modele.Plateau.Pingouin;
import Utils.Position;
import Vue.Donnees;
import Vue.Pane.GamePane;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class PingouinGraphique extends GameObject {

	enum GState {
		BD,BG,D,G,HD,HG;
	}

	Image currentSprite = Donnees.IMG_PINGOUIN_BD;
	GState currentState = GState.BD;
	int tailleSprites = (int) (currentSprite.getHeight());

	Pingouin pingouin;
	PlateauGraphique pg;
	Color couleur;
	Vec2d offset = new Vec2d(-currentSprite.getWidth() / 2, -currentSprite.getHeight());
	float scale = 1f;
	int width, height;
	Position pingouinPosition;

	public PingouinGraphique(Pingouin pingouin, PlateauGraphique pg, Color c) {
		this.pingouin = pingouin;
		this.pg = pg;
		this.couleur = c;
		pg.cases[pingouin.position().i()][pingouin.position().j()].pingouinGraphique = this;
		pingouinPosition = new Position(pingouin.position().i(), pingouin.position().j());
	}

	private void setState(GState s) {
		currentState = s;
		offset = new Vec2d(-currentSprite.getWidth() / 2, -currentSprite.getHeight());
		tailleSprites = (int) (currentSprite.getHeight());
		switch(currentState) {
		case BD:
			currentSprite = Donnees.IMG_PINGOUIN_BD;
			break;
		case BG:
			currentSprite = Donnees.IMG_PINGOUIN_BG;
			break;
		case D:
			currentSprite = Donnees.IMG_PINGOUIN_D;
			break;
		case G:
			currentSprite = Donnees.IMG_PINGOUIN_G;
			break;
		case HD:
			currentSprite = Donnees.IMG_PINGOUIN_HD;
			break;
		case HG:
			currentSprite = Donnees.IMG_PINGOUIN_HG;
			break;
		}
	}
	
	private float f = 0.5f;
	
	@Override
	public void update() {
		position.x = pg.cases[pingouinPosition.i()][pingouinPosition.j()].position().x + pg.tailleCase / 2;
		position.y = (float) (pg.cases[pingouinPosition.i()][pingouinPosition.j()].position().y + pg.tailleCase * 0.35);
		offset.x = -tailleSprites / 2 * scale;
		offset.y = -tailleSprites * 0.75 * scale;
		width = (int) (currentSprite.getWidth() * scale);
		height = (int) (currentSprite.getHeight() * scale);
		if (GamePane.moteur().currentState() == State.SELECTIONNER_PINGOUIN
				&& !GamePane.moteur().joueur(pingouin.employeur()).estIA()
				&& pingouin.employeur() == GamePane.moteur().joueurCourant().id())
			f = (float) (10 + Math.cos(0.01 * System.currentTimeMillis()) % 1) / 10;
		else
			f = 1;
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(couleur);
		gc.setGlobalAlpha(0.5);
		gc.fillOval(position.x - pg.tailleCase * 0.3 * f, position.y - pg.tailleCase * 0.2 * f, pg.tailleCase * 0.6 * f,
				pg.tailleCase * 0.3 * f);
		gc.setGlobalAlpha(1);
		gc.setStroke(couleur);
		gc.strokeOval(position.x - pg.tailleCase * 0.3 * f, position.y - pg.tailleCase * 0.2 * f,
				pg.tailleCase * 0.6 * f, pg.tailleCase * 0.3 * f);
		gc.drawImage(currentSprite, position.x + offset.x, position.y + offset.y, width, height);
	}


	public void moveTo(Position p) {
		pg.cases[pingouinPosition.i()][pingouinPosition.j()].pingouinGraphique = null;
		pingouinPosition = new Position(p.i(), p.j());
		pg.cases[pingouinPosition.i()][pingouinPosition.j()].pingouinGraphique = this;
	}

	public void setViewDirection(double angle) {		
		if(angle < -90)
			setState(GState.BG);
		else if(angle < -30)
			setState(GState.BD);
		else if(angle < 30)
			setState(GState.D);
		else if(angle < 90)
			setState(GState.HD);
		else if(angle<150)
			setState(GState.HG);
		else 
			setState(GState.G);
	}

	public void transformer(){
		
	}
}
