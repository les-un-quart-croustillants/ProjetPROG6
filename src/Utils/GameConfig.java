package Utils;

import java.util.LinkedList;

import Modele.Moteur.Moteur.State;

public class GameConfig {
	public enum TypeJoueur {
		HUMAIN, IA;
		
		@Override
		public String toString() {
			if(this == HUMAIN)
				return "Humain";
			else
				return "IA";
		}
	}
	
	public enum difficulte {
		FACILE, MOYEN, DIFFICILE;
		
		@Override
		public String toString() {
			if(this == FACILE)
				return "Facile";
			else if(this == MOYEN)
				return "Moyen";
			else
				return "Difficile";
		}
	}
	
	public static class ConfigJoueur {
		public TypeJoueur type;
		public difficulte difficulte_ia;
		public Integer nb_pingouins;
		
		public ConfigJoueur(TypeJoueur t, difficulte d, Integer n) {
			type = t;
			difficulte_ia = d;
			nb_pingouins = n;
		}
		
		@Override
		public String toString() {
			String str = type.toString();
			if(type == TypeJoueur.IA)
				str += " " + difficulte_ia.toString();
			
			str += " (" + nb_pingouins.toString() + " pingouin(s))";
			return str;
		}
	}
	
	public Integer dim;
	public LinkedList<ConfigJoueur> joueurs;
	
	public GameConfig(Integer dim) {
		this.dim = dim;
		joueurs = new LinkedList<ConfigJoueur>();
	}
	
	@Override
	public String toString() {
		String str = "Plateau : " + dim.toString() + "x" + dim.toString();
		str += "\nJoueurs:\n";
		for(ConfigJoueur cj : joueurs) {
			str += "- " + cj.toString() + "\n";
		}
		return str;
	}
}