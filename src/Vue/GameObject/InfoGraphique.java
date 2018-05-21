package Vue.GameObject;


import Vue.Donnees;
import Vue.Pane.GamePane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class InfoGraphique extends GameObject {
	private String str = "info";
	
	public InfoGraphique(String str) {
		this.str = str;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		gc.save();
		gc.setTextAlign(TextAlignment.CENTER);
		Lighting effect = new Lighting();
		effect.setSurfaceScale(3);
		effect.setDiffuseConstant(3);
		gc.setEffect(effect);
		gc.setFont(Donnees.FONT_SCORE);
		gc.setFill(Color.CORNFLOWERBLUE);
		gc.fillText(str, GamePane.getPlateauCadre().getWidth()/2, GamePane.getPlateauCadre().getHeight()*0.06);
		gc.restore();
	}
	
	public void setText(String str) {
		this.str = str;
	}
}
