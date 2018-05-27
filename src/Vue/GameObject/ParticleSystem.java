package Vue.GameObject;

import java.util.Random;

import com.sun.javafx.geom.Rectangle;
import com.sun.javafx.geom.Vec2d;

import Vue.InterfaceGraphique;
import Vue.Pane.GamePane;
import javafx.scene.image.Image;

public class ParticleSystem extends GameObject {

	private Image sprite;
	private int emission = 100; // nombre de particules emisent en 1sec
	private int delayEmission; // delay entre 2 emission en ms
	private double lastEmission; // temps auquel a eu lieu la derniere emission

	private Rectangle zoneSpawn;
	private Random r = new Random();
	
	private int layer = 3;
	private float minSize = 0.02f;
	private float maxSize = 0.02f;
	private Vec2d direction = new Vec2d();
	private boolean randDirection = false;
	private int minVitesse = 100;
	private int maxVitesse = 100;
	public int minLifeTime = 2000;
	public int maxLifeTime = 2000;
	

	//private ArrayList<Particle> particles;

	public ParticleSystem(Image sprite, int x, int y) {
		super(x, y);
		this.sprite = sprite;
		delayEmission = (int) (1f / emission * 1000f);
		setSpawn(0, 0, 0, 0);
	}

	int rw, rh;

	@Override
	public void update() {
		if (lastEmission + delayEmission < System.currentTimeMillis()) {
			for (int i = 0; i < emission * InterfaceGraphique.dt; i++) {
				rw = (int) (r.nextInt(zoneSpawn.width+1) + position.x + zoneSpawn.x);
                rh = (int) (r.nextInt(zoneSpawn.height+1) + position.y + zoneSpawn.y);
				Particle p = new Particle(sprite, rw, rh);
				p.setSize(minSize+r.nextFloat()*(maxSize-minSize));
				if(randDirection) {
					setDirection(new Vec2d(r.nextInt(100)-50,r.nextInt(100)-50));
				}
				p.setDirection(direction);
				p.setVitesse(r.nextInt(maxVitesse+1)+minVitesse);
				p.setLifeTime(r.nextInt(maxLifeTime+1)+minLifeTime);
				GamePane.getPlateauCadre().gameObjects.get(layer).add(p);
			}
			lastEmission = System.currentTimeMillis();
		}
	}

	public void setSpawn(int x, int y, int w, int h) {
		zoneSpawn = new Rectangle(x, y, w, h);
	}

	public void setSize(float s) {
		this.minSize = s;
		this.maxSize = s;
	}
	
	public void setSize(float smin, float smax) {
		this.minSize = smin;
		this.maxSize = smax;
	}
	
	public void setLayer(int l) {
		this.layer = l;
	}
	
	public void setDirection(Vec2d v) {
		direction.set(v);
		normalize(direction);
	}
	
	public void setRandDirection(boolean b) {
		randDirection = b;
	}
	
	private void normalize(Vec2d v) {
		double l = length(v);
		if(l>0) {
			v.x = v.x/l;
			v.y = v.y/l;
		}
	}
	
	private double length(Vec2d v) {
		return Math.sqrt(v.x*v.x+v.y*v.y);
	}
	
	public void setVitesse(int v) {
		this.minVitesse = v;
		this.maxVitesse = v;
	}
	public void setVitesse(int vmin, int vmax) {
		this.minVitesse = vmin;
		this.maxVitesse = vmax;
	}
	
	public void setLifeTime(int t) {
		minLifeTime = t;
		maxLifeTime = t;
	}
	public void setLifeTime(int tmin, int tmax) {
		minLifeTime = tmin;
		maxLifeTime = tmax;
	}
	
	public void setEmission(int e) {
		this.emission = e;
		delayEmission = (int) (1f / emission * 1000f);
	}
	
}
