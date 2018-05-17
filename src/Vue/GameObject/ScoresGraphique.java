package Vue.GameObject;

import Vue.Donnees;
import Vue.Pane.GamePane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ScoresGraphique extends GameObject{
		
	public ScoresGraphique(){
	}
	
	@Override
	public void update(){
		
	}
	
	@Override
	public void draw(GraphicsContext gc){
		String nom,score;
		int w = (int) (GamePane.getPlateauCadre().getWidth() * 0.15);
		for(int i=0;i<GamePane.moteur().njoueurs();i++){
			nom = GamePane.moteur().joueur(i).nom();
			score = "x"+Integer.toString(GamePane.moteur().joueur(i).scoreFish());
			gc.setFill(Donnees.COULEURS_JOUEURS[GamePane.moteur().joueur(i).id()]);
			gc.fillText(nom, (GamePane.getPlateauCadre().getWidth()/(GamePane.moteur().njoueurs()+1))*(i+1)-nom.length()*gc.getFont().getSize()*0.25, GamePane.getPlateauCadre().getHeight()*0.9,w);
			gc.setFill(Color.WHITE);
			gc.fillText(score, (GamePane.getPlateauCadre().getWidth()/(GamePane.moteur().njoueurs()+1))*(i+1)-score.length()*gc.getFont().getSize()*0.25, GamePane.getPlateauCadre().getHeight()*0.95,w);
		}
		
		
	}
}
