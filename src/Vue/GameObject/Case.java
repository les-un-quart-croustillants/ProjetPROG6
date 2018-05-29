package Vue.GameObject;

import java.awt.Point;
import java.awt.Polygon;
import java.util.Random;

import com.sun.javafx.geom.Vec2f;

import Utils.Position;
import Vue.Donnees;
import Vue.Donnees.Niveau;
import Vue.Pane.GamePane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Case extends GameObject {
	private Polygon polygon;
	private int[] px;
	private int[] py;
	private boolean selected;
	private boolean miseEnValeur;
	private PlateauGraphique pg;

	public PingouinGraphique pingouinGraphique;
	public Color couleurSelected = new Color(210f / 255f, 110f / 255f, 0, 1);

	public Position posPlateau;

	private int currentFrame = 0;
	private int frameRate = 10;
	private int nSpritesPerLine = 4;
	private int nSpritesPerCol = 2;
	private int nSprites = nSpritesPerCol * nSpritesPerLine;
	private double timeLastFrame = 0;
	private Image sprite;

	private Niveau niveau;

	Case(PlateauGraphique pg, int i, int j, Niveau n) {
		this.pg = pg;
		px = new int[] { 0, 128, 256, 256, 128, 0 };
		py = new int[] { 44, 0, 44, 118, 160, 118 };
		polygon = new Polygon(px, py, 6);
		position.x = pg.tailleCase;
		position.y = pg.tailleCase;
		posPlateau = new Position(i, j);
		currentFrame = new Random().nextInt(7);
		this.niveau = n;
		this.sprite = Donnees.IMG_BLOC[niveau.getNiveau()];
	}

	@Override
	public void update() {
		position.x = posPlateau.j() * (pg.tailleCase + pg.espacement)
				+ (1 - posPlateau.i() % 2) * (pg.tailleCase + pg.espacement) / 2;
		position.y = posPlateau.i() * (pg.tailleCase + pg.espacement) / 2;

		position.x += pg.position().x;
		position.y += pg.position().y;
		for (int i = 0; i < polygon.npoints; i++) {
			polygon.xpoints[i] = (int) (px[i] * pg.tailleCase / 256 + position.x);
			polygon.ypoints[i] = (int) (py[i] * pg.tailleCase / 256 + position.y);
		}
		if (GamePane.moteur().joueurCourant() != null)
			couleurSelected = Donnees.getCouleur(GamePane.moteur().joueurCourant().id());

		if (pg.plateau.getCellule(posPlateau).aPingouin()) {
			sprite = Donnees.IMG_BLOC[niveau.getNiveau()];
		} else {
			switch (pg.plateau.getCellule(posPlateau).getFish()) {
			case 1:
				sprite = Donnees.IMG_BLOC_P1[niveau.getNiveau()];
				break;
			case 2:
				sprite = Donnees.IMG_BLOC_P2[niveau.getNiveau()];
				break;
			case 3:
				sprite = Donnees.IMG_BLOC_P3[niveau.getNiveau()];
				break;
			default:
				sprite = Donnees.IMG_BLOC[niveau.getNiveau()];
				break;
			}
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.save();
		afficher_reflet(gc);
		gc.setGlobalAlpha(1);
		gc.drawImage(sprite, position.x, position.y, pg.tailleCase,
				pg.tailleCase * (sprite.getHeight() / sprite.getWidth()));
		if (selected) {
			gc.setStroke(couleurSelected);
			gc.setLineWidth(5);
			if (miseEnValeur)
				gc.setGlobalAlpha((Math.cos(0.01 * System.currentTimeMillis()) + 1) / 2);
		} else if (miseEnValeur) {
			gc.setStroke(new Color(1, 0, 0, 1));
			gc.setLineWidth(3);
		}
		if (selected || miseEnValeur) {
			double[] dpx = new double[polygon.npoints];
			double[] dpy = new double[polygon.npoints];
			for (int i = 0; i < polygon.npoints; i++) {
				dpx[i] = polygon.xpoints[i];
				dpy[i] = polygon.ypoints[i];
			}
			if (selected) {
				gc.setFill(couleurSelected);
				if (!miseEnValeur) {
					gc.setGlobalAlpha((Math.cos(0.007 * System.currentTimeMillis()) + 1) / 4+0.1);
					gc.fillPolygon(dpx, dpy, polygon.npoints);
					gc.setGlobalAlpha(1);
				}
				else {
					gc.setGlobalAlpha(0);
					gc.fillPolygon(dpx, dpy, polygon.npoints);
					gc.setGlobalAlpha(1);
				}
			}
			gc.strokePolygon(dpx, dpy, polygon.npoints);
		}
		gc.restore();
	}

	/**
	 * collision : Teste la collision de la hitbox de cet objet (polygon) avec un
	 * point
	 * 
	 * @param p
	 *            : un point
	 * @return : vrai si le point est dans le polygon, faux sinon.
	 */
	public boolean collision(Point p) {
		Vec2f p2 = new Vec2f(polygon.xpoints[0], polygon.ypoints[0]);
		Vec2f p1 = new Vec2f(polygon.xpoints[5], polygon.ypoints[5]);
		Vec2f vpoly = new Vec2f();
		Vec2f vpoint = new Vec2f();
		for (int i = 1; i < 6; i++) {
			vpoly.x = p2.x - p1.x;
			vpoly.y = p2.y - p1.y;
			vpoint.x = p.x - p1.x;
			vpoint.y = p.y - p1.y;
			if (vpoly.x * vpoint.y - vpoly.y * vpoint.x < 0) {
				return false;
			}
			p1 = p2;
			p2 = new Vec2f(polygon.xpoints[i], polygon.ypoints[i]);
		}
		vpoly.x = p2.x - p1.x;
		vpoly.y = p2.y - p1.y;
		vpoint.x = p.x - p1.x;
		vpoint.y = p.y - p1.y;
		if (vpoly.x * vpoint.y - vpoly.y * vpoint.x < 0) {
			return false;
		}
		return true;
	}

	/**
	 * select : met � true la variable selected de la case (son visuel peu changer
	 * si elle est selectionn� ou non).
	 */
	public void select() {
		selected = true;
		if(pingouinGraphique!=null)
			pingouinGraphique.transformer(true);
	}

	/**
	 * deselect : met � false la variable selected de la case (son visuel peu
	 * changer si elle est selectionn� ou non).
	 */
	public void deselect() {
		selected = false;
		if(pingouinGraphique!=null)
			pingouinGraphique.transformer(false);
	}

	public void mettreEnValeur() {
		miseEnValeur = true;
	}

	public void enleverMiseEnValeur() {
		miseEnValeur = false;
	}

	private int sx, sy, sw, sh;

	private void afficher_reflet(GraphicsContext gc) {
		if (niveau == Niveau.BANQUISE) {
			if (timeLastFrame + 1000 / frameRate < System.currentTimeMillis()) {
				currentFrame++;
				if (currentFrame >= nSprites)
					currentFrame = 0;
				sx = currentFrame % nSpritesPerLine * 256;
				sy = currentFrame / nSpritesPerLine * 221;
				sw = 256;
				sh = 221;
				timeLastFrame = System.currentTimeMillis();
			}
			gc.setGlobalAlpha(0.3);
			gc.drawImage(Donnees.IMG_BLOC_GLACE_RIPPLE, sx, sy, sw, sh, position.x, position.y + pg.tailleCase * 0.2,
					pg.tailleCase,
					pg.tailleCase * (Donnees.IMG_BLOC[niveau.getNiveau()].getHeight() / Donnees.IMG_BLOC[niveau.getNiveau()].getWidth()));
			gc.setGlobalAlpha(1);
		}
		else if (niveau == Niveau.ENFER){
			gc.setEffect(new Shadow(1,Color.BLACK));
			gc.setGlobalAlpha(0.2);
			gc.drawImage(sprite, position.x, position.y + pg.tailleCase*0.08, pg.tailleCase,
					pg.tailleCase * (sprite.getHeight() / sprite.getWidth()));
			gc.setEffect(null);
			gc.setGlobalAlpha(1);
		}
	}
	
}
