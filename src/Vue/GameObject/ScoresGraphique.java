package Vue.GameObject;

import Vue.Pane.GamePane;
import javafx.scene.canvas.GraphicsContext;

public class ScoresGraphique extends GameObject{
	
	
	public ScoresGraphique(){
	}
	
	@Override
	public void update(){
		
	}
	
	@Override
	public void draw(GraphicsContext gc){
		for(int i=0;i<GamePane.moteur().njoueurs();i++){
			gc.fillText("TRUC", (GamePane.getPlateauCadre().getWidth()/(GamePane.moteur().njoueurs()+1))*(i+1), GamePane.getPlateauCadre().getHeight()*0.9);
		}
		
		
	}
}
