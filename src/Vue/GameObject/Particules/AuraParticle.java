package Vue.GameObject.Particules;

import Vue.Donnees;
import Vue.GameObject.ParticleSystem;
import Vue.GameObject.PingouinGraphique;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Glow;

public class AuraParticle extends ParticleSystem{

	PingouinGraphique pg;
	
	public AuraParticle(PingouinGraphique pg) {
		super(Donnees.IMG_PARTICLE_AURA, (int)pg.position.x, (int)(pg.position.y- pg.height * 0.20));
		this.pg = pg;
		setSpawn(-10, -20, 20, 20);
		setRandDirection(true);
		setVitesse(50,100);
		setLifeTime(100);
		setSize(0.2f,0.35f);
		setEmission(20);
		setRotation(0,360);
		setEffet(new Glow());
		setBlendMode(BlendMode.HARD_LIGHT);
	}

	@Override
	public void update() {
		super.update();
		position.x =  pg.position.x;
		position.y = pg.position.y- pg.height * 0.20f;
	}
}
