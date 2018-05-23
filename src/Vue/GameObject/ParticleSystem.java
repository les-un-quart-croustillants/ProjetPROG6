package Vue.GameObject;

import java.util.Random;

import com.sun.javafx.geom.Rectangle;

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

	//private ArrayList<Particle> particles;

	public ParticleSystem(Image sprite, int x, int y) {
		super(x, y);
		this.sprite = sprite;
		delayEmission = (int) (1f / emission * 1000f);
		setSpawn(0, 0, 200, 200);
	}

	int rw, rh;

	@Override
	public void update() {
		if (lastEmission + delayEmission < System.currentTimeMillis()) {
			setSpawn(0, 0, (int) GamePane.getPlateauCadre().getWidth(), (int) GamePane.getPlateauCadre().getHeight());
			for (int i = 0; i < emission * InterfaceGraphique.dt; i++) {
				rw = (int) (r.nextInt(zoneSpawn.width+1) + position.x + zoneSpawn.x);
                rh = (int) (r.nextInt(zoneSpawn.height+1) + position.y + zoneSpawn.y);
				Particle p = new Particle(sprite, rw, rh);
				p.setSize(0.02f);
				GamePane.getPlateauCadre().gameObjects.get(3).add(p);
			}
			lastEmission = System.currentTimeMillis();
		}
	}

	public void setSpawn(int x, int y, int w, int h) {
		zoneSpawn = new Rectangle(x, y, w, h);
	}

}
