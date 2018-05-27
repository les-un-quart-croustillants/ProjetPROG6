package Vue.GameObject;

import Vue.Donnees;
import Vue.Donnees.Niveau;
import Vue.InterfaceGraphique;
import Vue.Pane.GamePane;
import javafx.scene.canvas.GraphicsContext;

public class Brume extends GameObject {

	private float vitesse = 50f;
	private Niveau niveau;
	
	public Brume(Niveau niveau) {
		this.niveau = niveau;
	}
	
	@Override
	public void update() {
		position.x = position.x - vitesse * InterfaceGraphique.dt;
		if(position.x <= - GamePane.getPlateauCadre().getWidth())
			position.x = 0;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		gc.setGlobalAlpha(1);
		gc.drawImage(Donnees.IMG_BRUME[niveau.getNiveau()], position.x, position.y,GamePane.getPlateauCadre().getWidth(),GamePane.getPlateauCadre().getHeight());
		gc.drawImage(Donnees.IMG_BRUME[niveau.getNiveau()], position.x+GamePane.getPlateauCadre().getWidth(), position.y,GamePane.getPlateauCadre().getWidth(),GamePane.getPlateauCadre().getHeight());
		gc.setGlobalAlpha(1);
	}
}
