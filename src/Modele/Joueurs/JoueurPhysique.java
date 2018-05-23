package Modele.Joueurs;


public class JoueurPhysique extends Joueur {
	
	private static final long serialVersionUID = 3573725365540215957L;

	public JoueurPhysique(int id,int nbP,String n){
		super(id,nbP,n,Difficulte.PHYSIQUE);
	}
}
