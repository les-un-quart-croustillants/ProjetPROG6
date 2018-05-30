package Vue.GameObject.Particules;

import com.sun.javafx.geom.Vec2d;

import Vue.Donnees;
import Vue.GameObject.ParticleSystem;
import Vue.Pane.GamePane;
import javafx.scene.effect.BlendMode;

public class EnferParticle extends ParticleSystem{

	public EnferParticle() {
		super(Donnees.IMG_PARTICLE_ENFER,0,0);
		setDirection(new Vec2d(0,-1));
		setVitesse(10,20);
		setLifeTime(1000, 2000);
		setSize(0.2f,0.8f);
		setReduireTaille(true);
		setEmission(20);
		//setBlendMode(BlendMode.ADD);
	}
		
	public void update() {
		super.update();
		setSpawn(0, 0, (int) GamePane.getPlateauCadre().getWidth(), (int) (GamePane.getPlateauCadre().getHeight()));
	}
	
}
