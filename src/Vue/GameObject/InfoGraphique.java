package Vue.GameObject;


import Vue.Donnees;
import Vue.Donnees.Niveau;
import Vue.Pane.GamePane;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Lighting;
import javafx.scene.text.TextAlignment;

public class InfoGraphique extends GameObject {
	private String str = "info";
	private Niveau niveau;
	
	public InfoGraphique(String str,Niveau n) {
		this.str = str;
		niveau = n;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		gc.save();
		gc.setTextAlign(TextAlignment.CENTER);
		Lighting effect = new Lighting();
		effect.setSurfaceScale(3);
		effect.setDiffuseConstant(3);
		gc.setEffect(effect);
		gc.setFont(Donnees.FONT_TEXT);
		gc.setFill(Donnees.COULEURS_TEXTES_NIVEAUX[niveau.getNiveau()]);
		gc.fillText(str, GamePane.getPlateauCadre().getWidth()/2, GamePane.getPlateauCadre().getHeight()*0.06, GamePane.getPlateauCadre().getWidth()*0.4);
		gc.restore();
	}
	
	public void setText(String str) {
		this.str = str;
	}
}
