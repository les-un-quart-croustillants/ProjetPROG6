package Vue.GameObject;

import Vue.Donnees;
import Vue.Pane.GamePane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class JoueurCourantGraphique extends GameObject{

	private String text;
	//private int size;
	private Color couleur;
	
	public JoueurCourantGraphique(String str) {
		this.text = str;
	}
	
	public void setText(String str) {
		this.text = str;
	}
	
	public void setCouleur(Color c) {
		this.couleur = c;
	}
	
	@Override
	public void update() {
		position.x = (float) (GamePane.getPlateauCadre().getWidth()/2 - text.length() * 10);
		position.y = (float) (GamePane.getPlateauCadre().getHeight()*0.07);
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		gc.setFont(Donnees.FONT_TEXT);
		gc.setFill(couleur);
		gc.setStroke(Color.WHITE);
		gc.setLineWidth(2);
		gc.fillText(text, position.x, position.y);
		gc.strokeText(text, position.x, position.y);
	}
	
}
