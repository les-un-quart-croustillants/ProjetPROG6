package Vue.Pane;

import Modele.Plateau.Plateau;
import Vue.PlateauCadre;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

public class GamePane extends Pane{
	private static GamePane instance = null;
	private PlateauCadre plateauCadre;
	
	public static GamePane getInstance(){
		if(GamePane.instance==null){
			GamePane.instance = new GamePane();
			return GamePane.instance;
		}
		return GamePane.instance;
	}
	
	private void init(){
		this.getChildren().add(plateauCadre);
		new AnimationTimer() {
			@Override
			public void handle(long currentNanoTime) {
				plateauCadre.update();
				plateauCadre.draw();
			}
		}.start();
	}
	
	private GamePane(){
		this.plateauCadre = new PlateauCadre(new Plateau(8),500,500);
		init();
	}
	private GamePane(Plateau p){
		GamePane.getInstance().plateauCadre = new PlateauCadre(p,500,500);
		init();
	}
	
	public static void newInstance(Plateau p){
		GamePane.instance = new GamePane(p);
	}
	
	public static PlateauCadre getPlateauCadre(){
		return GamePane.getInstance().plateauCadre;
	}
	
}