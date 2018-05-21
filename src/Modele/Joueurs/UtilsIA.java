package Modele.Joueurs;

import java.lang.System;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;
import Modele.Plateau.Cellule;
import Modele.Plateau.Plateau;
import Modele.Plateau.Pingouin;
import Utils.Position;
import Utils.Couple;

import java.util.ArrayList;
import java.util.HashMap;

public class UtilsIA {
	/**
	 * 
	 * @param T : un plateau de jeu
	 * @return le nombre de poisson de la partie
	 */
	public static int nbPoissonsPlateau(Plateau T) {
		int res = 0;
		int taille = T.getSize();
		for(int i = 0; i < taille; i++) {
			for(int j = 0; j < taille; j++) {
				Cellule current = T.getCellule(new Position(i,j));
				res = res + current.getFish();
			}
		}
		return res;
	}
	/**
	 * @param i : i de la case a tester
	 * @param j : j de la case a tester
	 * @param P : plateau sur lequel tester la case i j
	 * @param compteur : à 0 initialement.
	 * @return la taille de la plus longue chaine de 3 poissons accessible depuis la case i j
	 */
	public static int nb3fishaccesible(int i, int j, Plateau P,int compteur, LinkedList<Position> visitey) {
		LinkedList<Position> link = P.getNeighbours(new Position(i,j));
		LinkedList<Position> nb3unpas = new LinkedList<Position>();
		//les visites
		visitey.addFirst(new Position(i,j));
		
		if(P.getCellule(new Position(i,j)).getFish() == 3)
			compteur++;
		
		//on met dans nb3unpas les poissons accessibles en un pas de la case courante
		for(int k =0; k< link.size();k++){
			if(P.getCellule(link.get(k)).getFish() == 3 &&  !visitey.contains(link.get(k)))
				nb3unpas.addFirst(link.get(k));
		}
		if ( nb3unpas.size() == 0 )
			return compteur;
		for(int k =0; k < nb3unpas.size();k++){
			 compteur = Math.max(nb3fishaccesible(nb3unpas.get(k).i(),nb3unpas.get(k).j(), P, compteur,visitey),compteur);
		}
		return compteur;
	}
	
	/**
	 * 
	 * @param i : coordonee i de la case a calculer
	 * @param j : coordonee j de la case a calculer
	 * @param P : plateau sur lequel calculer
	 * @param id: id du joueur IA
	 * @return l'heuristique d'une case donnee dans une configuration
	 */
	
	public static int heuristiqueCase(int i, int j, Plateau P,int id) {
		LinkedList<Position> voisincase = new LinkedList<Position>();
		LinkedList<Position> visitey = new LinkedList<Position>();

		Cellule current = P.getCellule(new Position(i,j));
		int heuristiquecase = 10;
		if(current.getFish() == 1) {
			//si on est sur les bords c'est nul
			if(i == 0 || j == 0 || i == P.getSize()-1 || (j == P.getSize()-1 && i%2 == 1) || ( j == P.getSize()-2 && i%2 == 0 )) {
				heuristiquecase = heuristiquecase-5;
			}
			
			//on ajoute le nombre de case a 3 poissons accessible en n pas de 1 deplacement (c'est un peu cavalier de prendre plus que 1 deplacement, d'autant que je sais pas ce que ca veut dire)
			heuristiquecase = heuristiquecase + nb3fishaccesible(i,j,P,0,visitey);
			
			//c'est pas mal de commencer a coter d'un ennemi
			voisincase = P.getNeighbours(new Position(i,j));
			for(int k = 0; k < voisincase.size(); k++) {
				if(P.getCellule(voisincase.get(k)).aPingouin() && P.getCellule(voisincase.get(k)).pingouin().employeur() != id) {
					heuristiquecase = heuristiquecase+3;
				}
			}
			//c'est pas fou de commencer a coter d'un allie
			for(int k = 0; k < voisincase.size(); k++) {
				if(P.getCellule(voisincase.get(k)).aPingouin() && P.getCellule(voisincase.get(k)).pingouin().employeur() == id) {
					heuristiquecase = heuristiquecase-1;
				}
			}
		}
		else
			return -1000;
		return heuristiquecase;
		
	}
	
	/**
	 * 
	 * @param P : plateau sur lequel calculer les heuristiques
	 * @param taille : taille de ce dernier
	 * @param id: id du joueur IA
	 * @return un tableau de int, representant les heuristiques du plateau
	 */
	public static int[][] heuristiqueTab(Plateau P,int taille,int id){
		int[][] res = new int[taille][taille];
		for(int i = 0; i < taille; i++) {
			for(int j = 0; j < taille; j++) {
				res[i][j] = heuristiqueCase(i,j,P,id);
			}
		}
		return res;
	}
	
	/**
	 * @param T : le plateau de jeu 
	 * @param id : l'id du joueur IA
	 * @return La meilleure position pour placer un pingouin dans la configuration actuelle du plateau de jeu
	 */
	
	public static Position bestplace(Plateau T,int id) {
		LinkedList<Position> bestmatch = new LinkedList<Position>();
		int [][] tab = new int[T.getSize()][T.getSize()];
		tab = heuristiqueTab(T,T.getSize(),id);
		
		// on recherche la meilleure valeur heuristique dans le tableau et on renvoie cette coordonnee
		int best = -1000;
		for(int i = 0; i< T.getSize();i++){
			for(int j = 0; j< T.getSize();j++){
				if(tab[i][j] > best && !T.getCellule(new Position(i,j)).aPingouin() && !T.getCellule(new Position(i,j)).isDestroyed()) {
					bestmatch.clear();
					best = tab[i][j];
					bestmatch.addFirst(new Position(i,j));
				}
				else if(tab[i][j] == best && !T.getCellule(new Position(i,j)).aPingouin() && !T.getCellule(new Position(i,j)).isDestroyed()) {
					bestmatch.addFirst(new Position(i,j));
				}
				
			}
		}
		// on renvoie au hasard une des meilleurs positions 
		if(bestmatch.size() != 0) {
			Random r = new Random();
			int rand = r.nextInt(bestmatch.size());
			return bestmatch.get(rand);
		}
		else 
			return new Position(0,0);
	}
	/**
	 * Calcule les configurations filles d'une configuration
	 * @param n : le noeud dont il faut calculer les fils 
	 * @param id : l'id du joueur courant
	 */
	public static void calculFils(Noeud n,int id,Plateau plateau) {
		Plateau plat = plateau.clone();
		
		LinkedList<Couple<Position,Position>> list = (LinkedList<Couple<Position,Position>>)n.listcoup().clone();
		Plateau p = plateaucoup(  list,  plat );
		Cellule [][] tab = p.getTab();
		int size = p.getSize();
		Noeud cur = new Noeud();
		LinkedList<Position> accessible = new LinkedList<Position>();
		LinkedList<Couple<Position,Position>> add = new LinkedList<Couple<Position,Position>>();
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(tab[i][j].aPingouin() && tab[i][j].pingouin().employeur() == id) { // si il y a un pingouin allie sur la case courante
					Pingouin current  = tab[i][j].pingouin(); // on le recupere
					accessible = p.accessible(new Position(i,j)); // on calcule ses cases directement accessible
					for(int k = 0; k < accessible.size() ; k++) { // et pour toutes ces cases
						add.add(new Couple<Position,Position>(current.position(), accessible.get(k)));
						cur = new Noeud( (LinkedList<Couple<Position,Position>>) add.clone() ,n); // on cree un nouveau noeud avec ce coup simule, avec comme pere le noeud de base
						n.addFils(cur); //et on ajoute ce nouveau noeud comme fils du noeud de base.
						add.clear();
					}
				}
			}
		}
	}
	
	//ajoute le contenu de b qui n'a pas deja été vu dans a
	public static void mergeStacks(Stack<Position> a,LinkedList<Position> b,LinkedList<Position> c) {
		for(int i = 0; i < b.size(); i++) {
			if(!c.contains(b.get(i))) {
				a.push(b.get(i));
				c.push(b.get(i));
			}
		}
	}
	
	/**
	 * Calcule et liste le nombre de composante connexes et leur contenu 
	 * @param p Le tableau a gerer
	 * @return une linked list comportant une linked list par composante detectee, chaque linked list comporte les positions des cases qui la compose.
	 */
	public static LinkedList<LinkedList<Position>> listeConnexeComposante(Plateau p) {
		LinkedList<LinkedList<Position>> res = new LinkedList<LinkedList<Position>>();
		LinkedList<Position> current = new LinkedList<Position>();
		LinkedList<Position> checked = new LinkedList<Position>();
		Stack<Position> stack = new Stack<Position>();
		
		for(int i = 0; i < p.getSize(); i++) {
			for(int j = 0; j < p.getSize(); j++) {
				if( !checked.contains(new Position(i,j)) && !p.getCellule(new Position(i,j)).isDestroyed() ) {
					stack.push(new Position(i,j));
					while(!stack.isEmpty()) {
						Position cur = stack.pop();
						checked.add(cur);
						current.add(cur);
						mergeStacks(stack,p.accessiblesanspingouin(cur),checked);
					}
					res.add((LinkedList<Position>)current.clone());
					current.clear();
				}
			}
		}
		return res;
	}
	
	/**
	 * calcule les cases accessibles par un pingouin en n coups
	 * @param p Le tableau a gerer
	 * @param ping le pingouin
	 * @return une linked list de position accessible par le pingouin en n coups
	 */
	public static LinkedList<Position> composanteConnexePingouin(Plateau p,Pingouin ping) {
		LinkedList<LinkedList<Position>> res = new LinkedList<LinkedList<Position>>();
		LinkedList<Position> current = new LinkedList<Position>();
		LinkedList<Position> checked = new LinkedList<Position>();
		Stack<Position> stack = new Stack<Position>();
		
		stack.push(ping.position());
		while(!stack.isEmpty()) {
			Position cur = stack.pop();
			checked.add(cur);
			current.add(cur);						
			mergeStacks(stack,p.accessiblesanspingouin(cur),checked);

		}
		return current;
	}
	
	

	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	public static int calculHeuristiqueFacile(Noeud n,int id, Plateau plat) {
		LinkedList<Couple<Position,Position>> listpere = n.pere().listcoup();
		Plateau ppere = plateaucoup(  listpere ,  plat );
		Cellule [][] tabpere = ppere.getTab();
		
		int heur = 50;
		LinkedList<Couple<Position,Position>> listcur = n.listcoup();
		Plateau p = plateaucoup(  listcur,  plat );
		Cellule [][] tab = p.getTab();
		int size = p.getSize();
		Pingouin ping = new Pingouin(id);
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
		
		if(ping.position().i() == 0 || ping.position().j() == 0 || ping.position().i() == plat.getSize()-1 || (ping.position().j() == plat.getSize()-1 && ping.position().i()%2 == 1) || ( ping.position().j() == plat.getSize()-2 && ping.position().i()%2 == 0 ))
			heur = heur - 7;
		
			
		return heur;
	}

	
	public static  Couple<Position,Position> jouerCoupFacile(Plateau p,int id){
		Noeud n = new Noeud();
		calculFils(n,id,p);
		LinkedList<Noeud> fils = n.fils();
		
		for(int i = 0; i < fils.size(); i++) {
			fils.get(i).setHeuristic(calculHeuristiqueFacile(fils.get(i).clone(),id,p.clone())/*HeuristiqueA.calcul(p.clone(), fils.get(i).clone().listcoup(), id)*/ );
			//System.out.println("heuristique du fils "+i+" : "+fils.get(i).heuristique());
		}
		LinkedList<Noeud> res = new LinkedList<Noeud>();
		int max = 0;
		res.add(fils.get(0));
		for(int i = 1; i < fils.size(); i++) {
			if( fils.get(i).heuristique() > fils.get(max).heuristique()){
				max = i;
				res.clear();
				res.add(fils.get(i));
			}
			else if(fils.get(i).heuristique() == fils.get(max).heuristique())
				res.add(fils.get(i));
		}
		if(res.size() != 0) {
			Random r = new Random();
			int rand = r.nextInt(res.size());
			return res.get(rand).listcoup().get(0);
		}
		else 
			return new Couple<Position,Position>(new Position(0,0),new Position(0,0));


	}
	

	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Heuristique quand c'est au tour du joueur A
	 * 
	 * @param valeur
	 * @return
	 */
	public static int evaluerA(Noeud n,Plateau p, int id) {
		return HeuristiqueB.calcul(p.clone(), (LinkedList<Couple<Position,Position>>)n.listcoup().clone(), id);
	}
	
	/**
	 * Heuristique quand c'est au tour du joueur B
	 * 
	 * @param valeur
	 * @return
	 */
	public static int evaluerB(Noeud n,Plateau p, int id) {
		return HeuristiqueA.calcul(p.clone(), (LinkedList<Couple<Position,Position>>)n.listcoup().clone(), id);
	}
	
	public static Plateau plateaucoup(LinkedList<Couple<Position,Position>> l, Plateau p) {
		Plateau pclone = p.clone();
		//System.out.println(l);
		while(l.size() != 0) {
			Couple<Position,Position> current = l.pollFirst();
			pclone.jouer(current.gauche(), current.droit());
		}
		
		return pclone.clone();
	}
	
	public static int evaluerProfondeur(int facteur){
		if(facteur > 40)
			return 2;
		if(facteur > 30)
			return 3;
		if(facteur > 20)
			return 4;
		if(facteur > 10)
			return 5;
		return 10;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Calcule si la configuration a la racine de l'arbre des configurations
	 * est gagnante pour le joueur A en sachant que c'est au joeur A de jouer
	 * 
	 * @param n racine de l'arbre des configurations
	 * @return true si la configuration est gagnante pour le joueur A false sinon
	 */
	public static int minimaxA(Noeud n, HashMap<LinkedList<Couple<Position,Position>>,Integer> r, int profondeur,Plateau plateau,int id) {
		calculFils(n,id,plateau);	//calcul des fils
		int heuristique;

		if (n.estFeuille() || profondeur == 2) {
			// la configuration ne permet pas de jouer,
			// le joueur B gagne
			
			heuristique = evaluerA(n,plateau,id); 
		
			r.put(n.listcoup(), heuristique);
			n.setHeuristic(heuristique);
			return heuristique;
		} else {
			// Le joueur A doit jouer
			heuristique = -1000;
			r.put(n.listcoup(), heuristique);
			n.setHeuristic(heuristique);
			// On parcours l'ensemble des coups jouables par A
			Plateau pcurr = plateaucoup((LinkedList<Couple<Position,Position>>)n.listcoup().clone(), plateau.clone());
			for(int i =0; i < n.fils().size() ;i++) {
				
				LinkedList<Position> composantePingouin = composanteConnexePingouin(pcurr,pcurr.getCellule(n.fils().get(i).listcoup().get(0).gauche()).pingouin());
				int nbpingouinennemiscomposante = 0;
				for(int j =0; j < composantePingouin.size();j++) {
					if(pcurr.getCellule(composantePingouin.get(j)).aPingouin() && pcurr.getCellule(composantePingouin.get(j)).pingouin().employeur() != id)
						nbpingouinennemiscomposante++;
						
				}
				if(nbpingouinennemiscomposante != 0) {
					Noeud filsclone = n.fils().get(i).clone();
					int curr = minimaxB(filsclone, r, profondeur+1,pcurr,id);
					n.fils().get(i).setHeuristic(curr);
					/*
					//elagage alpha beta
					if(profondeur > 0 && n.pere().heuristique() != -100000 && n.fils().get(i).heuristique() > n.pere().heuristique()) {
						heuristique = filsclone.heuristique();
						r.put(n.listcoup(), heuristique);
						n.setHeuristic(heuristique);
						return heuristique;
					}*/
					
					
					// Si fils n'as pas encore ete calcule, le faire et mettre a jour r
					if(!r.containsKey(n.fils().get(i).listcoup())) {
						r.put(n.fils().get(i).listcoup(), curr);
					}
					
					if(heuristique < r.get(n.fils().get(i).listcoup())) {
						heuristique = r.get(n.fils().get(i).listcoup());
						r.put(n.listcoup(), heuristique);
						n.setHeuristic(heuristique);
					}
				}
				else {
					System.out.println("pingouin tout seul");
					heuristique = Math.min(heuristique,-1000000);
				}
			}
			return heuristique;
		}
	}
	
	
	
	
	
	
	
	
	/**
	 * Calcule si la configuration a la racine de l'arbre des configurations
	 * est gagnante pour le joueur A en sachant que c'est au joeur B de jouer
	 * 
	 * @param n racine de l'arbre des configurations
	 * @return true si la configuration est gagnante pour le joueur A false sinon
	 */
	public static int minimaxB(Noeud n,HashMap<LinkedList<Couple<Position,Position>>,Integer> r, int profondeur,Plateau plateau,int id) {
		//System.out.println("valeur heuristique du pere "+n.pere().heuristique());
		calculFils(n,id,plateau);	//calcul des fils
		int heuristique;
		if (n.estFeuille() || profondeur == 2 ) {
			// la configuration ne permet pas de jouer
			// le joueur A gagne
			heuristique = evaluerB(n,plateau,id);
			r.put(n.listcoup(), heuristique);
			n.setHeuristic(heuristique);
			return heuristique;
		} else {
			// Le joueur B doit jouer
			heuristique = 1000000; // + infini
			r.put(n.listcoup(), heuristique);
			n.setHeuristic(heuristique);
			
			// On parcours l'ensemble des coups jouables par B
			Plateau pcurr = plateaucoup((LinkedList<Couple<Position,Position>>)n.listcoup().clone(), plateau.clone());
			for(int i =0; i < n.fils().size() ;i++) {
				
				LinkedList<Position> composantePingouin = composanteConnexePingouin(pcurr,pcurr.getCellule(n.fils().get(i).listcoup().get(0).gauche()).pingouin());
				int nbpingouincomposante = 0;
				for(int j =0; j < composantePingouin.size();j++) {
					if(pcurr.getCellule(composantePingouin.get(j)).aPingouin())
						nbpingouincomposante++;	
				}
				
				if(nbpingouincomposante != 1) {
					Noeud filsclone = n.fils().get(i).clone();
					int curr = minimaxA(n.fils().get(i), r, profondeur+1,pcurr,id);
					n.fils().get(i).setHeuristic(curr);
					
					/*
					//elagage alpha beta
					if(profondeur > 0 && n.pere().heuristique() != -100000 &&  n.fils().get(i).heuristique() < n.pere().heuristique()) {
						heuristique = filsclone.heuristique();
						r.put(n.listcoup(), heuristique);
						n.setHeuristic(heuristique);
						return heuristique;
					}*/
					
					// Si fils n'as pas encore ete calcule , le faire et mettre a jour r
					if(! r.containsKey(n.fils().get(i).listcoup())) {
						r.put(n.fils().get(i).listcoup(), curr);
					}
					if(heuristique > r.get(n.fils().get(i).listcoup())) {
						heuristique = r.get(n.fils().get(i).listcoup());
						r.put(n.listcoup(), heuristique);
						n.setHeuristic(heuristique);
					}
				}
				else
					heuristique = Math.min(heuristique,1000000);
			}
			return heuristique;
		}
	}

	public static Couple<Position,Position> jouerCoupDifficile(Plateau plateau,int id) {
		Plateau plateauclone = plateau.clone();
		Random r = new Random();
		Noeud a = new Noeud(); // construction de l'arbre des configurations
		HashMap<LinkedList<Couple<Position,Position>>,Integer> memo = new HashMap<LinkedList<Couple<Position,Position>>,Integer>();
		minimaxA(a,memo,0,plateauclone,id);
		LinkedList<Noeud> cp;
		for(int i = 0;i < a.fils().size();i++) {
			System.out.println("heuristique du fils "+i+" : "+a.fils().get(i).heuristique()+" et sa liste de coups : "+a.fils().get(i).listcoup());
			//System.out.println("tableau du fils"+ i+" : ");
			//afficher_etat(plateaucoup((LinkedList<Couple<Position,Position>>)a.fils().get(i).listcoup().clone(),plateau.clone()));
		}	
		if(( a.filsTaggue().size()) != 0) {
			cp = a.clone().filsTaggue(); //recuperations des solutions
		}
		else {
			System.out.println("coup facile1 :(");
			return jouerCoupFacile(plateau,id);
		}
		System.out.println("taille fils racine apres : "+cp.size());
		if(cp.size() != 0) {
			int rand = r.nextInt(cp.size()); //choix d'une solution admissible aleatoire
			//System.out.println("et la ? "+ cp.get(rand).listcoup());
			System.out.println("l'heurstique du coup pris "+ cp.get(rand).heuristique());

			return cp.get(rand).listcoup().get(0); //renvoie du coup joue dans le fils
		}else {
			System.out.println("coup facile3 :(");
			return jouerCoupFacile(plateau,id);
		}	
	}
	
	///////////////////////////////////////////:
	static char[] sym_joueurs = { 'M', 'A', 'B', 'C' };
	static void afficher_etat(Plateau plat){
		ArrayList<ArrayList<Pingouin> > pingouins = new ArrayList<ArrayList<Pingouin> >();
		pingouins.add(new ArrayList<Pingouin>());
		pingouins.add(new ArrayList<Pingouin>());
		pingouins.add(new ArrayList<Pingouin>());
		pingouins.add(new ArrayList<Pingouin>());
		for(int i=0;i<plat.getSize();i++){
			for(int j=0;j<plat.getSize();j++){
				Position p = new Position(i, j);
				if(!plat.getCellule(p).isDestroyed() && plat.getCellule(p).aPingouin()){
					pingouins.get(plat.getCellule(p).pingouin().employeur()).add(plat.getCellule(p).pingouin());
				}
				if(plat.getCellule(p).isDestroyed()){
					if(j==0 && i%2==0)
						System.out.print("	");
					else
						System.out.print("	");
				}
				else if(i%2==1){
					if(plat.getCellule(p).aPingouin())
						System.out.print(sym_joueurs[plat.getCellule(p).pingouin().employeur()]+"   ");
					else
						System.out.print(plat.getCellule(p).getFish()+"   ");
				}
				else{
					if(j==0){
						if(plat.getCellule(p).aPingouin())
							System.out.print("  "+sym_joueurs[plat.getCellule(p).pingouin().employeur()]);
						else
							System.out.print("  "+plat.getCellule(p).getFish());
					}
					else{
						if(plat.getCellule(p).aPingouin())
							System.out.print("   "+sym_joueurs[plat.getCellule(p).pingouin().employeur()]);
						else
							System.out.print("   "+plat.getCellule(p).getFish());
					}
				}
			}
			System.out.println();
		}
		
		for(int i=0;i<4;i++){
			if(pingouins.get(i).size()>0){
				System.out.print("["+sym_joueurs[i]+"] Pingouins: ");
				for (Pingouin p : pingouins.get(i)) {
					System.out.print(p.position()+" ");
				}
				System.out.println();
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
