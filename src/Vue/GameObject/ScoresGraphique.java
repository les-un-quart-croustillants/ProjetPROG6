package Vue.GameObject;

import Vue.Donnees;
import Vue.Pane.GamePane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;

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
		gc.setFont(Donnees.FONT_SCORE);
		Lighting l = new Lighting();
		l.setSurfaceScale(3);
		l.setDiffuseConstant(3);
		gc.setEffect(l);
		for(int i=0;i<GamePane.moteur().njoueurs();i++){
			nom = GamePane.moteur().joueur(i).nom();
			score = "x"+Integer.toString(GamePane.moteur().joueur(i).scoreFish());
			gc.setFill(Donnees.COULEURS_JOUEURS[GamePane.moteur().joueur(i).id()]);
			x = (int) (GamePane.getPlateauCadre().getWidth()*0.15+(GamePane.getPlateauCadre().getWidth()*0.7/(GamePane.moteur().njoueurs()+1))*(i+1)-nom.length()*gc.getFont().getSize()*0.3);
			y = (int) (GamePane.getPlateauCadre().getHeight()*0.95);
			gc.fillText(nom, x, y);
			//gc.setStroke(Color.DARKCYAN);
			//gc.strokeText(nom, (GamePane.getPlateauCadre().getWidth()/(GamePane.moteur().njoueurs()+1))*(i+1)-nom.length()*gc.getFont().getSize()*0.25, GamePane.getPlateauCadre().getHeight()*0.9);
			gc.setFill(Color.ORANGE);
			x = (int) (GamePane.getPlateauCadre().getWidth()*0.15+(GamePane.getPlateauCadre().getWidth()*0.7/(GamePane.moteur().njoueurs()+1))*(i+1)-score.length()*gc.getFont().getSize()*0.3+Donnees.IMG_POISSON.getWidth()/2);
			y = (int) (GamePane.getPlateauCadre().getHeight()*0.98);
			gc.fillText(score, x, y);
			gc.drawImage(Donnees.IMG_POISSON, x-Donnees.IMG_POISSON.getWidth()*1.1, y-Donnees.IMG_POISSON.getHeight()*0.8);
			x = (int) (GamePane.getPlateauCadre().getWidth()*0.15+(GamePane.getPlateauCadre().getWidth()*0.7/(GamePane.moteur().njoueurs()+1))*(i+1)-GamePane.getPlateauCadre().getWidth()*0.1/2);
			y = (int) (GamePane.getPlateauCadre().getHeight()*0.88);
			gc.setEffect(null);
			if(GamePane.moteur().indexJoueurCourant()==i) {
				gc.drawImage(Donnees.IMG_SELECTEUR, x , y, GamePane.getPlateauCadre().getWidth()*0.1, GamePane.getPlateauCadre().getHeight()*0.05);
			}
			if(GamePane.moteur().joueur(i).estElimine()) {
				w = (int) (GamePane.getPlateauCadre().getWidth()*0.015);
				h = (int) (w  * (Donnees.IMG_CADENAS.getHeight()/Donnees.IMG_CADENAS.getWidth()));
				x = (int) (GamePane.getPlateauCadre().getWidth()*0.15+(GamePane.getPlateauCadre().getWidth()*0.7/(GamePane.moteur().njoueurs()+1))*(i+1)-w/2);
				y = (int) (GamePane.getPlateauCadre().getHeight()*0.88);
				gc.drawImage(Donnees.IMG_CADENAS,x,y,w,h);
			}
			gc.setEffect(l);
		}		
		gc.setEffect(null);
	}
}
