package Vue.GameObject;

import Modele.Moteur.Moteur.State;
import Vue.Donnees;
import Vue.Pane.GamePane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Shadow;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class ScoresGraphique extends GameObject{
		
	public ScoresGraphique(){
	}
	
	@Override
	public void update(){
		
	}
	
	private int x,y,w,h;
	@Override
	public void draw(GraphicsContext gc){
		String nom,score;
		gc.setFont(Donnees.FONT_SCORES_FINAUX);
		for(int i=0;i<GamePane.moteur().njoueurs();i++){
			nom = GamePane.moteur().joueur(i).nom();
			score = Integer.toString(GamePane.moteur().joueur(i).scoreFish());
			gc.setFill(Donnees.getCouleur(GamePane.moteur().joueur(i).id()%4));
			gc.setTextAlign(TextAlignment.CENTER);
			x = (int) (GamePane.getPlateauCadre().getWidth()*0.15+(GamePane.getPlateauCadre().getWidth()*0.7/(GamePane.moteur().njoueurs()+1))*(i+1));
			y = (int) (GamePane.getPlateauCadre().getHeight()*0.95);
			gc.fillText(nom, x, y);
			gc.setFill(Color.ORANGE);
			x = (int) (GamePane.getPlateauCadre().getWidth()*0.15+(GamePane.getPlateauCadre().getWidth()*0.7/(GamePane.moteur().njoueurs()+1))*(i+1)+Donnees.IMG_POISSON.getWidth()/2);
			y = (int) (GamePane.getPlateauCadre().getHeight()*0.99);
			gc.drawImage(Donnees.IMG_POISSON, x-Donnees.IMG_POISSON.getWidth()*1.3, y-Donnees.IMG_POISSON.getHeight()*0.9);
			gc.fillText(score, x, y);
			w = (int) (GamePane.getPlateauCadre().getWidth()*0.05);
			h = (int) (GamePane.getPlateauCadre().getHeight()*0.04);
			x = (int) (GamePane.getPlateauCadre().getWidth()*0.15+(GamePane.getPlateauCadre().getWidth()*0.7/(GamePane.moteur().njoueurs()+1))*(i+1)-w/2);
			y = (int) (GamePane.getPlateauCadre().getHeight()*0.860);
			gc.setEffect(null);
			if(GamePane.moteur().indexJoueurCourant()==i) {	
				gc.setEffect(new Shadow(1,Donnees.getCouleur(i)));
				gc.drawImage(Donnees.IMG_SELECTEUR, x , y, w, h);
				gc.setEffect(null);
			}
			x = (int) (GamePane.getPlateauCadre().getWidth()*0.15+(GamePane.getPlateauCadre().getWidth()*0.7/(GamePane.moteur().njoueurs()+1))*(i+1));
			w =  (int) (GamePane.getPlateauCadre().getWidth()*0.025);
			h = (int) (GamePane.getPlateauCadre().getHeight()*0.04);
			y = (int) (GamePane.getPlateauCadre().getHeight()*0.890);
			if(GamePane.moteur().currentState() == State.POSER_PINGOUIN){
				int n = GamePane.moteur().joueur(i).nbPingouin()-GamePane.moteur().joueur(i).pingouins().size();
				for(int j = 0;j<n;j++)
					gc.drawImage(Donnees.IMG_PINGOUIN_BD, x+(j-n*0.55)*w*0.8, y, w, h);
			}
			if(GamePane.moteur().joueur(i).estElimine()) {
				w = (int) (GamePane.getPlateauCadre().getWidth()*0.015);
				h = (int) (w  * (Donnees.IMG_CADENAS.getHeight()/Donnees.IMG_CADENAS.getWidth()));
				x = (int) (GamePane.getPlateauCadre().getWidth()*0.15+(GamePane.getPlateauCadre().getWidth()*0.7/(GamePane.moteur().njoueurs()+1))*(i+1)-w/2);
				y = (int) (GamePane.getPlateauCadre().getHeight()*0.88);
				gc.drawImage(Donnees.IMG_CADENAS,x,y,w,h);
			}
		}		
		gc.setEffect(null);
	}
}
