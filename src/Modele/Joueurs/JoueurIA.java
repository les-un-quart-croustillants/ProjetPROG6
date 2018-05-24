package Modele.Joueurs;

import java.util.ArrayList;
import java.lang.Thread;
import Modele.Plateau.Plateau;
import Utils.Couple;
import Utils.Position;


public class JoueurIA extends Joueur {
	
	private static final long serialVersionUID = -8134226007569319548L;

	public JoueurIA(int id,int nbP,String n,Difficulte d){
		super(id,nbP,n,d);
	}

	@Override
	public int delay() {
		switch(this.difficulte) {
			case FACILE:
				return 1000;
			case MOYEN:
				return 500;
			case DIFFICILE:
				return 0;
			default:
				return 0;
		}
	}
	
	
	
	@Override
	public Couple<Position,Position> prochainCoup(Plateau plateau,ArrayList<ArrayList<Integer>> scores) {
<<<<<<< HEAD
		switch(this.difficulte) {
			case FACILE:
				return UtilsIA.jouerCoupFacile(plateau,super.id());
			case MOYEN:
				return UtilsIA.jouerCoupFacile(plateau,super.id());
			case DIFFICILE:
				return UtilsIA.jouerCoupDifficile(plateau,super.id(),scores);
			default :
				return UtilsIA.jouerCoupFacile(plateau,super.id());
		}
=======
		return UtilsIA.jouerCoupFacile(plateau,super.id());
>>>>>>> dev
	}
	/*
	@Override
	public Couple<Position,Position> prochainCoup(Plateau plateau,ArrayList<ArrayList<Integer>> scores) {
		Thread t1;
		Couple<Position,Position> res = new Couple<Position,Position>(new Position(-1,-1),new Position(-1,-1));
		TestMultiThread test = new TestMultiThread(this.difficulte,plateau,scores,res,this.id());
		t1 = new Thread(test);
		t1.start();
		try {
			t1.join();
		} catch (InterruptedException e1) {
			   System.out.println("ca devrait vraiment pas du tout s'afficher pour l'instant");
		}
		return res;
		
	}*/
	
	
	@Override
	public Position prochainePosePingouin(Plateau plateau) {
		return UtilsIA.bestplace(plateau, super.id());
	}
	
	@Override
	public boolean estIA() {
		return true;
	}
}
