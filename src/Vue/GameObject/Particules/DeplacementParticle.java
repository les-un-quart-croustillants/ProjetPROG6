package Vue.GameObject.Particules;

import java.awt.geom.Point2D;

import com.sun.javafx.geom.Vec2d;
import com.sun.javafx.geom.Vec2f;

import Vue.Donnees;
import Vue.GameObject.Case;
import Vue.GameObject.GameObject;
import Vue.GameObject.ParticleSystem;
import Vue.Pane.GamePane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.BlendMode;
import javafx.util.Duration;

public class DeplacementParticle extends GameObject{

	ParticleSystem p_jaune;
	Vec2f from,to,direction;
	int vitesse = 6;
	boolean arrived = false;
	
	public DeplacementParticle(Case cfrom,Case cto){
		super((int)cfrom.position.x,(int)cfrom.position.y);
		from = new Vec2f(cfrom.position.x,cfrom.position.y);
		to = new Vec2f(cto.position.x,cto.position.y);
		direction = new Vec2f(to.x-from.x,to.y-from.y);
		
		p_jaune = creer_p_jaune();
		
		GamePane.getPlateauCadre().gameObjects.get(1).add(p_jaune);
		
		Timeline tl = new Timeline(new KeyFrame(new Duration(200)));
		tl.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DeplacementParticle.this.detruire();
				p_jaune.detruire();
			}
		});
		tl.playFromStart();
	}
	
	@Override
	public void update() {
		super.update();
		if(!arrived) {
			position.x += direction.x * (1.0/60.0) * vitesse;
			position.y += direction.y * (1.0/60.0) * vitesse;
			double d = Point2D.distance(to.x, to.y, position.x, position.y);
			if(d>-1 && d< 1)
				arrived = true;
		}
		p_jaune.position = position;
		
	}
	
	private ParticleSystem creer_p_jaune() {
		ParticleSystem p = new ParticleSystem(Donnees.IMG_PARTICLE_AURA, (int)position.x, (int)position.y);
		double d = Point2D.distance(to.x, to.y, from.x, from.y);
		p.setDirection(new Vec2d(from.x-to.x,from.y-to.y));
		p.setVitesse((int) d);
		p.setLifeTime(200);
		p.setSize(0.1f,0.25f);
		p.setEmission((int) (d*0.4));
		p.setRotation(0,360);
		p.setBlendMode(BlendMode.HARD_LIGHT);
		p.setReduireTaille(true);	
		return p;
	}
	
}
