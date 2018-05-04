package Vue.GameObject;

import java.awt.Point;
import java.awt.Polygon;

import com.sun.javafx.geom.Vec2f;

import Utils.Position;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Case extends GameObject {
	Polygon polygon;
	int[] px;
	int[] py;
	boolean selected;

	public Color couleur;
	PlateauGraphique pg;

	Position posPlateau;

	Case(PlateauGraphique pg, int i, int j) {
		this.pg = pg;
		px = new int[] { 0, 2, 4, 4, 2, 0 };
		py = new int[] { 1, 0, 1, 3, 4, 3 };
		polygon = new Polygon(px, py, 6);
		position.x = pg.pixelPerUnit;
		position.y = pg.pixelPerUnit;
		posPlateau = new Position(i, j);
	}

	@Override
	public void update() {
		if (posPlateau.i() % 2 == 1) {
			position.x = pg.pixelPerUnit * 4 * posPlateau.j() + pg.offset.x;
			position.y = pg.pixelPerUnit * 3 * posPlateau.i() + pg.offset.y;
		} else {
			position.x = pg.pixelPerUnit * 4 * posPlateau.j() + pg.offset.x
					+ 0.5 * pg.pixelPerUnit * 4;
			position.y = pg.pixelPerUnit * 3 * posPlateau.i() + pg.offset.y;
		}
		for (int i = 0; i < polygon.npoints; i++) {
			polygon.xpoints[i] = (int) (px[i] * pg.pixelPerUnit + position.x);
			polygon.ypoints[i] = (int) (py[i] * pg.pixelPerUnit + position.y);
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(Color.ALICEBLUE);
		if (selected) {
			gc.setStroke(new Color(1, 0, 0, 1));
			gc.setLineWidth(2);
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
	}

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

	public void select() {
		selected = true;
	}

	public void deselect() {
		selected = false;
	}
}
