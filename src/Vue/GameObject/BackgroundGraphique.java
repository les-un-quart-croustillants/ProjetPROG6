package Vue.GameObject;

import Vue.Donnees;
import Vue.Donnees.Niveau;
import Vue.Pane.GamePane;
import javafx.scene.canvas.GraphicsContext;

public class BackgroundGraphique extends GameObject {

	Niveau niveau;
	
	public BackgroundGraphique(Niveau n) {
		niveau = n;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(Donnees.IMG_BG_HUD[niveau.getNiveau()], 0, 0,GamePane.getPlateauCadre().getWidth(),GamePane.getPlateauCadre().getHeight());
	}
	
}
