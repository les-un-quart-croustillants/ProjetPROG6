package Utils;

import java.util.LinkedList;

import Modele.Moteur.Moteur.State;

public class GameConfig {
	public enum TypeJoueur {
		HUMAIN, IA;
	}
	
	public enum difficulte {
		FACILE, MOYEN, DIFFICILE;
		
	}
	
	public class ConfigJoueur {
		public TypeJoueur type;
		public difficulte difficulte_ia;
		public Integer nb_pingouins;
	}
	
	public Integer width;
	public Integer heigth;
	
	public LinkedList<ConfigJoueur> joueurs;
}