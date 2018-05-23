package Vue.GameObject;

import com.sun.javafx.geom.Vec2d;

import Vue.InterfaceGraphique;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Particle extends GameObject{

	private Image sprite;
	private int lifeTime = 2000; //duree de vie en ms
	private double time; //temps auquel la particule a ete creer
	private Vec2d direction; //direction dans laquelle bouge la particule
	private float vitesse = 100f; //vitesse de la particule
	private float size = 1f;
	
	Particle(Image sprite, int x,int y){
		super(x,y);
		this.sprite = sprite;
		time = System.currentTimeMillis();
		direction = new Vec2d(0,1);
	}
	
	private double length(Vec2d v) {
		return Math.sqrt(v.x*v.x+v.y*v.y);
	}
	private void normalize(Vec2d v) {
		double l = length(v);
		if(l>0) {
			v.x = v.x/l;
			v.y = v.y/l;
		}
	}
	
	public void setDirection(Vec2d v) {
		direction.set(v);
		normalize(direction);
	}
	
	public void setVitesse(float v) {
		this.vitesse = v;
	}
	
	public void setSize(float size) {
		this.size = size;
	}
	
	public void setLifeTime(int lt) {
		this.lifeTime = lt;
	}
	
	@Override
	public void update() {
		if(time + lifeTime < System.currentTimeMillis())
			this.detruire();
		else {
			this.position.x = (float) (this.position.x + direction.x*vitesse*InterfaceGraphique.dt);
			this.position.y = (float) (this.position.y + direction.y*vitesse*InterfaceGraphique.dt);
		}
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(sprite, position.x - sprite.getWidth()*size/2, position.y - sprite.getHeight()*size/2,sprite.getWidth()*size,sprite.getHeight()*size);
	}
	
}
