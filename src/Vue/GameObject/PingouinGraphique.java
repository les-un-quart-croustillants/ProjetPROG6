package Vue.GameObject;

import com.sun.javafx.geom.Vec2d;
import Modele.Moteur.Moteur.State;
import Modele.Plateau.Pingouin;
import Utils.Position;
import Vue.Donnees;
import Vue.GameObject.Particules.AuraParticle;
import Vue.GameObject.Particules.DeplacementParticle;
import Vue.Pane.GamePane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class PingouinGraphique extends GameObject {

	enum GState {
		BD, BG, D, G, HD, HG;
	}

	Image currentSprite = Donnees.IMG_PINGOUIN_BD;
	GState currentState = GState.BD;
	int tailleSprites = (int) (currentSprite.getHeight());

	Pingouin pingouin;
	PlateauGraphique pg;
	Color couleur;
	Vec2d offset = new Vec2d(-currentSprite.getWidth() / 2, -currentSprite.getHeight());
	float scale = 1f;
	public int width, height;
	Position pingouinPosition;
	boolean visible = true;

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
		switch (currentState) {
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
		scale = Math.min(0.5f, 0.35f + ((float)(pingouin.nbPoissonManges()))*0.005f);
		position.x = pg.cases[pingouinPosition.i()][pingouinPosition.j()].position().x + pg.tailleCase/ 2;
		position.y = (float) (pg.cases[pingouinPosition.i()][pingouinPosition.j()].position().y + pg.tailleCase * 0.35);
		width = (int) (currentSprite.getWidth() * scale);
		height = (int) (currentSprite.getHeight() * scale);
		offset.x = -width / 2 ;
		offset.y = -height * 0.80 ;
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
		if(!visible)
			return;
		if (GamePane.moteur().pingouinSelection() != pingouin) {
			gc.setFill(couleur);
			gc.setGlobalAlpha(0.5);
			gc.fillOval(position.x - pg.tailleCase * 0.3 * f, position.y - pg.tailleCase * 0.2 * f,
					pg.tailleCase * 0.6 * f, pg.tailleCase * 0.3 * f);
			gc.setGlobalAlpha(1);
			gc.setStroke(couleur);
			gc.strokeOval(position.x - pg.tailleCase * 0.3 * f, position.y - pg.tailleCase * 0.2 * f,
					pg.tailleCase * 0.6 * f, pg.tailleCase * 0.3 * f);
		}
		gc.drawImage(currentSprite, position.x + offset.x, position.y + offset.y, width, height);
	}

	public void moveTo(Position p) {
		visible = false;
		Case lc = pg.cases[pingouinPosition.i()][pingouinPosition.j()];
		lc.pingouinGraphique = null;
		pingouinPosition = new Position(p.i(), p.j());
		Case nc = pg.cases[pingouinPosition.i()][pingouinPosition.j()];
		nc.pingouinGraphique = this;
		Point2D vec = new Point2D(nc.position.x-lc.position.x,nc.position.y-lc.position.y);
		GamePane.getPlateauCadre().gameObjects.get(1).add(new DeplacementParticle(lc,nc));
		Timeline tl = new Timeline(new KeyFrame(new Duration(200)));
		tl.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				double angle = Math.atan2(vec.getY(), vec.getX()) - Math.atan2(0, 1);
				setViewDirection(-Math.toDegrees(angle));
				visible = true;
			}
		});
		tl.playFromStart();
	}

	public void setViewDirection(double angle) {
		if(angle >= 160 || angle < -165)
			setState(GState.G);
		else if (angle < -90)
			setState(GState.BG);
		else if (angle < -20)
			setState(GState.BD);
		else if (angle < 20)
			setState(GState.D);
		else if (angle < 90)
			setState(GState.HD);
		else if (angle < 160)
			setState(GState.HG);
	}

	private AuraParticle ap;

	public void transformer(boolean b) {
		if (b && ap == null) {
			ap = new AuraParticle(this);//(int) position.x, (int) (position.y - height * 0.20));
			GamePane.getPlateauCadre().gameObjects.get(1).add(ap);
			ap.setLayer(2);
		} else if (!b && ap != null){
			ap.detruire();
			ap = null;
		}
	}
}
