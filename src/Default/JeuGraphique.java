package Default;
import Vue.InterfaceGraphique;
import Modele.Moteurs.MoteurApp;

public class JeuGraphique {
	public static void main(String[] args) {
		MoteurApp mApp = new MoteurApp();
		System.out.println(mApp.toString());
		InterfaceGraphique.creer(args, mApp);
	}
}
