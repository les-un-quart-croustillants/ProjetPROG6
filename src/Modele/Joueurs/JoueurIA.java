package Modele.Joueurs;

import Modele.Plateau.Plateau;
import Utils.Couple;
import Utils.Position;
import java.util.LinkedList;
import Modele.Plateau.Cellule;
import Modele.Plateau.Pingouin;

public class JoueurIA extends Joueur {
	int id;
	
	public JoueurIA(int id){
		super(id);
	}
	
	public JoueurIA(int id,int p){
		super(id,p);
	}
	
	/*public int calculHeuristiqueFacile(Noeud n) {
		Noeud pere  = n.pere();
		Plateau ppere = pere.plateau();
		Cellule [][] tabpere = ppere.getTab();
		
		int heur = 50;
		Plateau p = n.plateau();
		Cellule [][] tab = p.getTab();
		int size = p.getSize();
		Pingouin ping = new Pingouin( super.id());
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if( tab[i][j].aPingouin() && !tabpere[i][j].aPingouin()) {
					ping = tab[i][j].pingouin();
				}
			}
		}
		if( p.estIsolee( ping.position() ) )
			return 0;
		
		if(tab[ping.position().i()][ping.position().j()].getFish() == 3)
			heur = heur + 18;
		
		if(tab[ping.position().i()][ping.position().j()].getFish() == 2)
			heur = heur + 12;
		
		return heur;
	}
	
	public Couple<Position,Position> jouerCoupFacile(Plateau p){
		Noeud n = new Noeud(p);
		LinkedList<Noeud> fils = n.fils();
		for(int i = 0; i < fils.size(); i++) {
			fils.get(i).setHeuristic(calculHeuristiqueFacile(fils.get(i)));
		}
		int max = -1;
		for(int i = 0; i < fils.size(); i++) {
			if( fils.get(i).heuristique() > max){
				max = i;
			}
		}
		return coupCalcule(n,fils.get(max));
	}
	
	public Couple<Position,Position> coupCalcule(Noeud pere, Noeud fils){
		Position newp = new Position(0,0);
		Position oldp = new Position(0,0);
		
		Plateau ppere = pere.plateau();
		Cellule [][] tabpere = ppere.getTab();
		Plateau pfils = fils.plateau();
		Cellule [][] tabfils = pfils.getTab();
		int size = pfils.getSize();
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(tabfils[i][j].aPingouin() && !tabpere[i][j].aPingouin()) {
					newp = new Position(i,j);
				}
				if(!tabfils[i][j].aPingouin() && tabpere[i][j].aPingouin()) {
					oldp = new Position(i,j);
				}
			}
		}
		return new Couple<Position,Position>(oldp,newp);
	}*/
	
	@Override
	public Couple<Position,Position> prochainCoup(Plateau plateau) {
		return UtilsIA.jouerCoupFacile(plateau,super.id());
	}
	
	@Override
	public Position prochainePosePingouin(Plateau plateau) {
		return UtilsIA.bestplace(plateau, super.id());
	}
	
	@Override
	public boolean estIA() {
		return true;
	}
}
