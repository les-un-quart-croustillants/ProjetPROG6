package Vue;
import java.awt.Point;
import java.awt.Polygon;

import com.sun.javafx.geom.Vec2f;

import Utils.Position;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Case extends GameObject {
	Polygon polygon;
	public Color couleur;
	PlateauGraphique pg;
	
	Position posPlateau;
	
	Case(PlateauGraphique pg,int i,int j){
		this.pg=pg;
		int[] px = new int[] { 0, 2, 4, 4, 2, 0 };
		int[] py = new int[] { 1, 0, 1, 3, 4, 3 };
		polygon = new Polygon(px,py,6);
		position.x=PlateauCadre.pixelPerUnit;
		position.y=PlateauCadre.pixelPerUnit;
		posPlateau=new Position(i,j);
	}
	
	@Override
	public void update(){
		if(posPlateau.i()%2==0){
			position.x = PlateauCadre.pixelPerUnit*4*posPlateau.j()+pg.offset.x;
			position.y = PlateauCadre.pixelPerUnit*3*posPlateau.i()+pg.offset.y;
		}
		else{
			position.x = PlateauCadre.pixelPerUnit*4*posPlateau.j()+pg.offset.x+0.5*PlateauCadre.pixelPerUnit*4;
			position.y = PlateauCadre.pixelPerUnit*3*posPlateau.i()+pg.offset.y;
		}
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		Point2D p2 = new Point2D(polygon.xpoints[0], polygon.ypoints[0]);
		Point2D p1 = new Point2D(polygon.xpoints[5], polygon.ypoints[5]);
		gc.setStroke(couleur);
		for (int i = 1; i < 6; i++) {
			double x1 = p1.getX() * PlateauCadre.pixelPerUnit+position.x;
			double y1 = p1.getY() * PlateauCadre.pixelPerUnit+position.y;
			double x2 = p2.getX() * PlateauCadre.pixelPerUnit+position.x;
			double y2 = p2.getY() * PlateauCadre.pixelPerUnit+position.y;
			gc.strokeLine(x1, y1, x2, y2);
			p1 = p2;
			p2 = new Point2D(polygon.xpoints[i], polygon.ypoints[i]);
		}
		double x1 = p1.getX() * PlateauCadre.pixelPerUnit+position.x;
		double y1 = p1.getY() * PlateauCadre.pixelPerUnit+position.y;
		double x2 = p2.getX() * PlateauCadre.pixelPerUnit+position.x;
		double y2 = p2.getY() * PlateauCadre.pixelPerUnit+position.y;
		gc.strokeLine(x1, y1, x2, y2);
	}

	public boolean collision(Point p) {
		Vec2f p2 = new Vec2f((float) (polygon.xpoints[0]*PlateauCadre.pixelPerUnit+position.x), (float) (polygon.ypoints[0]*PlateauCadre.pixelPerUnit+position.y));
		Vec2f p1 = new Vec2f((float) (polygon.xpoints[5]*(float)PlateauCadre.pixelPerUnit+position.x), (float) (polygon.ypoints[5]*(float)PlateauCadre.pixelPerUnit+position.y));
		Vec2f vpoly = new Vec2f();
		Vec2f vpoint = new Vec2f();
		for (int i = 1; i < 6; i++) {
			vpoly.x=p2.x-p1.x; vpoly.y=p2.y-p1.y;
			vpoint.x=p.x-p1.x; vpoint.y=p.y-p1.y;
			if(vpoly.x*vpoint.y-vpoly.y*vpoint.x<0){
				return false;
			}
			p1 = p2;
			p2 = new Vec2f((float) (polygon.xpoints[i]*(float)PlateauCadre.pixelPerUnit+position.x), (float) (polygon.ypoints[i]*(float)PlateauCadre.pixelPerUnit+position.y));
		}
		vpoly.x=p2.x-p1.x; vpoly.y=p2.y-p1.y;
		vpoint.x=p.x-p1.x; vpoint.y=p.y-p1.y;
		if(vpoly.x*vpoint.y-vpoly.y*vpoint.x<0){
			return false;
		}
		
		return true;
	}
}
