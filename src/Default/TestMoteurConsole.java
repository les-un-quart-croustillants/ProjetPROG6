package Default;

import java.util.ArrayList;
import java.util.Scanner;

import Modele.Plateau.Pingouin;
import Modele.Plateau.Plateau;
import Utils.Position;
import Vue.Moteur;
import Vue.Moteur.State;

public class TestMoteurConsole {
	public static Moteur m;
	private static char[] sym_joueurs = { 'M', 'A', 'B', 'C' };
	public static void main(String[] args) {
		Plateau p = new Plateau(8);
		m = new Moteur(p, 2);
		m.setCurrentState(State.POSER_PINGOUIN);
		Scanner sc = new Scanner(System.in);
		boolean fin = false;
		while (!fin) {
			afficher_etat();
			System.out.println("[Joueur "+m.indexJoueurCourant()+"]");
			if(m.currentState()==State.POSER_PINGOUIN) {
				System.out.println("Poser un pingouin (ecrire 2 entier):");
				int a, b;
				a = sc.nextInt();
				b = sc.nextInt();
				System.out.println(m.currentState());
				if (m.poserPingouin(new Position(a, b))) {
					System.out.println("Pingouin en ("+a+","+b+")");
				}
				else {
					System.out.println("Position invalide.");
				}
				System.out.println(m.currentState());
			}
			else if(m.currentState()==State.SELECTIONNER_PINGOUIN) {
				System.out.println("Selectionner un pingouin (ecrire 2 entier):");
				int a, b;
				a = sc.nextInt();
				b = sc.nextInt();
				if (m.selectionnerPingouin(new Position(a, b))) {
					System.out.println("Selection du pingouin en ("+a+","+b+")");
				}
				else {
					System.out.println("Selection impossible.");
				}
			}
			else if(m.currentState()==State.SELECTIONNER_DESTINATION) {
				System.out.println("Selectionner une destination pour le pingouin en ("+m.pingouinSelection().position().i()+","+m.pingouinSelection().position().j()+"):");
				int a, b;
				a = sc.nextInt();
				b = sc.nextInt();
				if (m.selectionnerDestination(new Position(a, b))) {
					System.out.println("Pingouin deplace en ("+a+","+b+")");
				}
				else {
					System.out.println("Deplacement impossible.");
				}
			}
			else {
				System.out.println(m.currentState());
				return;
			}
		}
		
		sc.close();
	}
	
	private static void afficher_etat(){
		ArrayList<ArrayList<Pingouin> > pingouins = new ArrayList<ArrayList<Pingouin> >();
		pingouins.add(new ArrayList<Pingouin>());
		pingouins.add(new ArrayList<Pingouin>());
		pingouins.add(new ArrayList<Pingouin>());
		pingouins.add(new ArrayList<Pingouin>());
		for(int i=0;i<m.plateau().getSize();i++){
			for(int j=0;j<m.plateau().getSize();j++){
				Position p = new Position(i, j);
				if(!m.plateau().getCellule(p).isDestroyed() && m.plateau().getCellule(p).aPingouin()){
					pingouins.get(m.plateau().getCellule(p).pingouin().employeur()).add(m.plateau().getCellule(p).pingouin());
				}
				if(m.plateau().getCellule(p).isDestroyed()){
					System.out.print("    ");
				}
				else if(i%2==0){
					System.out.print(m.plateau().getCellule(p).getFish()+"   ");
				}
				else{
					if(j==0)
						System.out.print("  "+m.plateau().getCellule(p).getFish());
					else
						System.out.print("   "+m.plateau().getCellule(p).getFish());
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
