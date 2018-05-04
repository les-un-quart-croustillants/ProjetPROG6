package Vue;

import java.awt.Point;
import java.awt.Polygon;
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
		polygon = new Polygon(px,py,6);
		position.x = PlateauCadre.pixelPerUnit;
		position.y = PlateauCadre.pixelPerUnit;
		posPlateau = new Position(i, j);
	}

	@Override
	public void update() {
		if (posPlateau.i() % 2 == 1) {
			position.x = PlateauCadre.pixelPerUnit * 4 * posPlateau.j() + pg.offset.x;
			position.y = PlateauCadre.pixelPerUnit * 3 * posPlateau.i() + pg.offset.y;
		} else {
			position.x = PlateauCadre.pixelPerUnit * 4 * posPlateau.j() + pg.offset.x
					+ 0.5 * PlateauCadre.pixelPerUnit * 4;
			position.y = PlateauCadre.pixelPerUnit * 3 * posPlateau.i() + pg.offset.y;
		}
		for (int i = 0; i < polygon.npoints; i++) {
			polygon.xpoints[i] = (int) (px[i] * PlateauCadre.pixelPerUnit + position.x);
			polygon.ypoints[i] = (int) (py[i] * PlateauCadre.pixelPerUnit + position.y);
		}
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(Color.ALICEBLUE);
		if(selected){ gc.setStroke(new Color(1,0,0,1)); gc.setLineWidth(2); }
		else{ gc.setStroke(new Color(0,0,0,1)); gc.setLineWidth(1); }
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
		return polygon.contains(p);
	}

	public void select() {
		selected = true;
	}

	public void deselect() {
		selected = false;
	}
}
