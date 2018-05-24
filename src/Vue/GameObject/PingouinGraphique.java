package Vue.GameObject;

import com.sun.javafx.geom.Vec2d;

import Modele.Moteur.Moteur.State;
import Modele.Plateau.Pingouin;
import Utils.Position;
import Vue.Donnees;
import Vue.Pane.GamePane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class PingouinGraphique extends GameObject {

	public enum GState {
		IDLE, TRANSITION, BOUBOULE;

		static public String toString(GState s) {
			switch (s) {
			case IDLE:
				return "IDLE";
			case TRANSITION:
				return "TRANSITION";
			case BOUBOULE:
				return "BOUBOULE";
			default:
				return "undefined";
			}
		}
	}

	GState etat = GState.IDLE;
	Image currentSprite = Donnees.IMG_PINGOUIN_FACE;
	int nFrames = 7; // nb frame du sprite
	int frameRate = 24; // nb frames/sec
	double delayFrame = 1000.0 / ((double) frameRate);
	int tailleSprites = (int) (Donnees.IMG_PINGOUIN_ANIM.getHeight());
	int currentFrame = 0;
	double timeLastFrame;

	Pingouin pingouin;
	PlateauGraphique pg;
	Color couleur;
	Vec2d offset = new Vec2d(-Donnees.IMG_PINGOUIN_FACE.getWidth() / 2, -Donnees.IMG_PINGOUIN_FACE.getHeight());
	float scale = 1f;
	int width, height;
	Position pingouinPosition;

	public PingouinGraphique(Pingouin pingouin, PlateauGraphique pg, Color c) {
		this.pingouin = pingouin;
		this.pg = pg;
		this.couleur = c;
		pg.cases[pingouin.position().i()][pingouin.position().j()].pingouinGraphique = this;
		pingouinPosition = new Position(pingouin.position().i(), pingouin.position().j());
		setEtat(GState.IDLE);
	}

	private void setEtat(GState e) {
		this.etat = e;
		switch (etat) {
		case IDLE:
			currentSprite = Donnees.IMG_PINGOUIN_FACE;
			break;
		case TRANSITION:
			currentSprite = Donnees.IMG_PINGOUIN_ANIM;
			break;
		case BOUBOULE:
			currentSprite = Donnees.IMG_PINGOUIN_BOULE;
			break;
		}
	}

	private float f = 0.5f;

	@Override
	public void update() {
		updateFrame();
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
		if(etat==GState.TRANSITION)
			drawFrame(gc);
		else
			gc.drawImage(currentSprite, position.x + offset.x, position.y + offset.y, width, height);
	}

	private void updateFrame() {
		if (etat == GState.TRANSITION) {
			if (timeLastFrame + delayFrame < System.currentTimeMillis()) {
				currentFrame++;
				timeLastFrame = System.currentTimeMillis();
			}
			if (currentFrame >= nFrames){
				setEtat(GState.BOUBOULE);
				currentFrame = 0;
			}
		}
	}

	private void drawFrame(GraphicsContext gc) {
		gc.drawImage(currentSprite, currentFrame * tailleSprites, 0, tailleSprites, tailleSprites,
				position.x + offset.x, position.y + offset.y,  tailleSprites*scale, height);
	}

	public void moveTo(Position p) {
		pg.cases[pingouinPosition.i()][pingouinPosition.j()].pingouinGraphique = null;
		pingouinPosition = new Position(p.i(), p.j());
		pg.cases[pingouinPosition.i()][pingouinPosition.j()].pingouinGraphique = this;
	}

	public void transformer(){
		timeLastFrame = System.currentTimeMillis();
		System.out.println("truc");
		if(etat==GState.IDLE)
			setEtat(GState.TRANSITION);
		else
			setEtat(GState.IDLE);
	}
}
