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
	private PlateauGraphique pg;
	
	public Color couleur;
	public Position posPlateau;

	Case(PlateauGraphique pg, int i, int j) {
		this.pg = pg;
		px = new int[] { 0, 2, 4, 4, 2, 0 };
		py = new int[] { 1, 0, 1, 3, 4, 3 };
		polygon = new Polygon(px, py, 6);
		position.x = pg.tailleCase;
		position.y = pg.tailleCase;
		posPlateau = new Position(i, j);
	}

	@Override
	public void update() {
		if (posPlateau.i() % 2 == 1) {
			position.x = pg.tailleCase  * posPlateau.j() + pg.position.x;
			position.y = pg.tailleCase*0.75  * posPlateau.i() + pg.position.y;
		} else {
			position.x = pg.tailleCase  * posPlateau.j() + pg.position.x
					+ 0.5 * pg.tailleCase ;
			position.y = pg.tailleCase*0.75  * posPlateau.i() + pg.position.y;
		}
		/*position.x+=posPlateau.j()*10;
		position.y+=posPlateau.i()*10;*/
		for (int i = 0; i < polygon.npoints; i++) {
			polygon.xpoints[i] = (int) (px[i] * pg.tailleCase/4 *0.9  + position.x);
			polygon.ypoints[i] = (int) (py[i] * pg.tailleCase/4 *0.9 + position.y);
		}
		/*if(pg.plateau.getCellule(posPlateau).isDestroyed()) {
			this.detruire();
		}*/
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(Color.ALICEBLUE);
		if (selected) {
			gc.setStroke(new Color(1, 0, 0, 1));
			gc.setLineWidth(3);
		} else {
			gc.setStroke(new Color(0, 0, 0, 1));
			gc.setLineWidth(1);
		}
		double[] dpx = new double[polygon.npoints];
		double[] dpy = new double[polygon.npoints];
		for (int i = 0; i < polygon.npoints; i++) {
			dpx[i] = polygon.xpoints[i];
			dpy[i] = polygon.ypoints[i];
		}
		gc.fillPolygon(dpx, dpy, polygon.npoints);
		gc.strokePolygon(dpx, dpy, polygon.npoints);
		gc.setFill(Color.CORNFLOWERBLUE);
		gc.setFont(Donnees.FONT_TEXT);
		gc.fillText(Integer.toString(pg.plateau.getCellule(posPlateau).getFish()), position.x+pg.tailleCase/2-gc.getFont().getSize()*0.32, position.y+pg.tailleCase/2+gc.getFont().getSize()*0.25);
	}

	/**
	 * collision : Teste la collision de la hitbox de cet objet (polygon) avec un point
	 * @param p : un point
	 * @return : vrai si le point est dans le polygon,
	 * faux sinon.
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
	 * select : met à true la variable selected de la case (son visuel peu changer si elle est selectionné ou non).
	 */
	public void select() {
		selected = true;
	}

	/**
	 * deselect : met à false la variable selected de la case (son visuel peu changer si elle est selectionné ou non).
	 */
	public void deselect() {
		selected = false;
	}
}
