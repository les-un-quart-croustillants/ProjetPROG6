package Vue;

import Controleur.ClicPlateau;
import Modele.Plateau.Plateau;
import Vue.GameObject.PlateauGraphique;

public class PlateauCadre extends Cadre{	
	public PlateauGraphique plateauGraphique;
	public Plateau plateau;
	
	private void init(Plateau p){
		this.plateau = p;
		this.plateauGraphique = new PlateauGraphique(p,this);
		this.gameObjects.add(plateauGraphique);
		this.setOnMouseMoved(new ClicPlateau(this));
	}

	public PlateauCadre(Plateau p){
		super();
		init(p);
	}
	public PlateauCadre(Plateau p,int w,int h){
		super(w,h);
		init(p);
	}
}
