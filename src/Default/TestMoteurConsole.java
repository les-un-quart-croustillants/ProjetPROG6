package Default;

import java.util.Scanner;

import Modele.Plateau.Plateau;
import Utils.Position;
import Vue.Moteur;
import Vue.Moteur.State;

public class TestMoteurConsole {
	public static void main(String[] args) {
		Plateau p = new Plateau(8);
		Moteur m = new Moteur(p, 2);
		m.setCurrentState(State.POSER_PINGOUIN);
		Scanner sc = new Scanner(System.in);
		boolean fin = false;
		while (!fin) {
			System.out.println(p);
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
}
