package Vue.GameObject;

import com.sun.javafx.geom.Vec2d;
import Vue.InterfaceGraphique;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

public class Particle extends GameObject{

	private Image sprite;
	private int lifeTime = 2000; //duree de vie en ms
	private double time; //temps auquel la particule a ete creer
	private Vec2d direction; //direction dans laquelle bouge la particule
	private float vitesse = 100f; //vitesse de la particule
	private float size = 1f;
	private float initSize = 1f;
	private Rotate rotate;
	private double rotation = 0;
	private Effect effet = null;
	private BlendMode blendMode ;
	private boolean reduireTaille = false;
	
	Particle(Image sprite, int x,int y){
		super(x,y);
		this.sprite = sprite;
		time = System.currentTimeMillis();
		direction = new Vec2d(0,0);
	}
	
	public void setDirection(Vec2d d) {
		direction.set(d);
	}
	
	public void setVitesse(float v) {
		this.vitesse = v;
	}
	
	public void setSize(float size) {
		this.size = size;
		this.initSize = size;
	}
	
	public void setLifeTime(int lt) {
		this.lifeTime = lt;
	}
	
	public void setRotation(double r) {
		this.rotation = r;
	}
	
	public void setEffet(Effect e) {
		this.effet = e;
	}
	
	public void setBlendMode(BlendMode bm) {
		this.blendMode = bm;
	}
	
	public void setReduireTaille(boolean b) {
		this.reduireTaille = b;
	}
	
	@Override
	public void update() {
		if(time + lifeTime < System.currentTimeMillis())
			this.detruire();
		else {
			this.position.x = (float) (this.position.x + direction.x*vitesse*InterfaceGraphique.dt);
			this.position.y = (float) (this.position.y + direction.y*vitesse*InterfaceGraphique.dt);
		}
		this.rotate = new Rotate(rotation,position().x,position().y);
		if(reduireTaille) {
			size =  initSize - (float) (initSize * (System.currentTimeMillis()-time)/lifeTime) ;
		}
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		gc.save();
		gc.setEffect(null);
		gc.setGlobalBlendMode(null);
		gc.setEffect(effet);
		gc.setGlobalBlendMode(blendMode);
        gc.setTransform(rotate.getMxx(), rotate.getMyx(), rotate.getMxy(), rotate.getMyy(), rotate.getTx(), rotate.getTy());
		if(sprite!=null)
			gc.drawImage(sprite, position.x - sprite.getWidth()*size/2, position.y - sprite.getHeight()*size/2,sprite.getWidth()*size,sprite.getHeight()*size);
		gc.restore();
	}
	
}
