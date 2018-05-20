package Vue.GameObject;

import Vue.Donnees;
import Vue.Pane.GamePane;
import javafx.scene.canvas.GraphicsContext;

public class BackgroundGraphique extends GameObject {

	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(Donnees.IMG_BG_NUAGES, 0, 0,GamePane.getPlateauCadre().getWidth(),GamePane.getPlateauCadre().getHeight());
	}
	
}
