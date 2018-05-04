package Vue;

import Controleur.ClicPlateau;
import Modele.Plateau.Plateau;

public class PlateauCadre extends Cadre{
	public static int pixelPerUnit=10;
	
	public PlateauGraphique plateauGraphique;
	
	private void init(){
		plateauGraphique = new PlateauGraphique(new Plateau(5));
		this.gameObjects.add(plateauGraphique);
		this.setOnMouseMoved(new ClicPlateau(this));
	}
	
	public PlateauCadre(){
		super();
		init();
	}
	public PlateauCadre(int w,int h){
		super(w,h);
		init();
	}
}
