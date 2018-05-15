package Vue.GameObject;

import java.awt.Point;
import java.awt.Polygon;

import com.sun.javafx.geom.Vec2f;

import Utils.Position;
import Vue.Donnees;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Case extends GameObject {
	private Polygon polygon;
	private int[] px;
	private int[] py;
	private boolean selected;
	private boolean miseEnValeur;
	private PlateauGraphique pg;

	public Color couleur;
	public Position posPlateau;

	Case(PlateauGraphique pg, int i, int j) {
		this.pg = pg;
		px = new int[] { 75, 180, 255, 255, 180, 75, 0, 0 };
		py = new int[] { 0, 0, 60, 120, 180, 180, 120, 60 };
		polygon = new Polygon(px, py, 8);
		position.x = pg.tailleCase;
		position.y = pg.tailleCase;
		posPlateau = new Position(i, j);
	}

	@Override
	public void update() {
			position.x = pg.plateau.getSize()*pg.tailleCase/2 + posPlateau.j()*pg.tailleCase - posPlateau.i()*pg.tailleCase/2;
			position.y =  posPlateau.i()*pg.tailleCase*0.7 + posPlateau.j()*pg.tailleCase/2 - pg.plateau.getSize()*pg.tailleCase/2;

			if (posPlateau.i() % 2 == 0) {
				position.x += pg.tailleCase*0.5;
				position.y += pg.tailleCase*0.25;

			}
			
			position.x += pg.position().x;
			position.y += pg.position().y;
		for (int i = 0; i < polygon.npoints; i++) {
			polygon.xpoints[i] = (int) (px[i] * pg.tailleCase / 255 + position.x);
			polygon.ypoints[i] = (int) (py[i] * pg.tailleCase / 255 + position.y);
		}

	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.save();
		gc.drawImage(Donnees.IMG_BLOC_GLACE, position.x, position.y, pg.tailleCase,
				pg.tailleCase * (Donnees.IMG_BLOC_GLACE.getHeight() / Donnees.IMG_BLOC_GLACE.getWidth()));
		
		if (selected) {
			gc.setStroke(new Color(1, 1, 0, 1));
			gc.setLineWidth(5);
			if(miseEnValeur)
				gc.setGlobalAlpha((Math.cos(0.01*System.currentTimeMillis())+1)/2);
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
			// gc.fillPolygon(dpx, dpy, polygon.npoints);
			gc.strokePolygon(dpx, dpy, polygon.npoints);
		}
		gc.setFill(Color.DEEPSKYBLUE);
		gc.setGlobalAlpha(1);
		gc.setFont(Donnees.FONT_TEXT);
		gc.scale(0.5, 0.5);
		gc.fillText(Integer.toString(pg.plateau.getCellule(posPlateau).getFish()),
				(position.x + pg.tailleCase / 2 - gc.getFont().getSize() * 0.15)*2,
				(position.y + pg.tailleCase / 2 - gc.getFont().getSize()*0.2)*2);
		/*String truc = "("+posPlateau.i()+","+posPlateau.j()+")";
		gc.fillText(truc,
		position.x + pg.tailleCase / 2 - gc.getFont().getSize() * 0.32,
		position.y + pg.tailleCase / 2 + gc.getFont().getSize() * 0.25);*/
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
		Vec2f p1 = new Vec2f(polygon.xpoints[7], polygon.ypoints[7]);
		Vec2f vpoly = new Vec2f();
		Vec2f vpoint = new Vec2f();
		for (int i = 1; i < 8; i++) {
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
	 * select : met à true la variable selected de la case (son visuel peu changer
	 * si elle est selectionné ou non).
	 */
	public void select() {
		selected = true;
	}

	/**
	 * deselect : met à false la variable selected de la case (son visuel peu
	 * changer si elle est selectionné ou non).
	 */
	public void deselect() {
		selected = false;
	}

	public void mettreEnValeur() {
		miseEnValeur = true;
	}

	public void enleverMiseEnValeur() {
		miseEnValeur = false;
	}
}
