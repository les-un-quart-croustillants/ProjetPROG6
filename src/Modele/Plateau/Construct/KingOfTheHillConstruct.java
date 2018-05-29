package Modele.Plateau.Construct;

import Modele.Plateau.Cellule;
import Utils.Position;

import java.util.Arrays;

import static java.lang.Math.abs;
/*
 * TODO : garantir le bon nombre de cases à 1 pour poser les pingouins
 * TODO : supporter les proportions par nombre de poissons
 */
public class KingOfTheHillConstruct extends Construct {
	private Position center;
	private int nb_fish_1, nb_fish_2, nb_fish_3;
	private int[] nb;
	private Cellule[][] tab;

	public KingOfTheHillConstruct(int size, int nb_pingouins) {
		super(size, nb_pingouins);
		this.size = size;
		this.nb = new int[3];
		Arrays.fill(this.nb, 0);
		this.tab = new Cellule[size][size];
		center = new Position((size)/2, (size - 1)/2);
		set_nb_fishs();
	}

	private void set_nb_fishs() {
		int nb_cases = ((size * size) - (size + 1) / 2);
		nb_fish_1 = (nb_pingouins < (nb_cases / 3))?nb_cases / 3 : nb_pingouins;
		nb_fish_2 = ((nb_cases - nb_fish_1) / 3) * 2;
		nb_fish_3 = nb_cases - nb_fish_1 - nb_fish_2;
	}

	private int distanceFromCenter(int i, int j) {
		return (abs(center.i() - i) + abs(center.j() - j)) / 2; // FIXME prendre en compte la distance graphique
	}

	private int getCellValue() {
		int res;
		if (nb[0] < nb_fish_1)
			res = 1;
		else if(nb[1] < nb_fish_2)
			res =  2;
		else
			res = 3;
		return res;
	}

	private Cellule getCell(int i, int j) {
		int nb_fish;
		if (i % 2 == 0 && j == size -1) {
			return new Cellule(new Position(i, j), true, 0);
		}
		else {
			nb_fish = getCellValue();
			return new Cellule(new Position(i, j), (nb_fish == 0), nb_fish);
		}
	}

	private int[] square(int dec) {
		int r[] = {0,0,0};
		for (int i = dec; i < size - dec; i++) { // lignes
			tab[dec][i] = getCell(dec, i);
			if (tab[dec][i].getFish() != 0)
				r[tab[dec][i].getFish() - 1]++;
			tab[size - 1 - dec][i] = getCell(size - dec - 1, i);
			if (tab[size - 1 - dec][i].getFish() != 0)
				r[tab[size - 1 - dec][i].getFish() - 1]++;
		}
		for (int i = dec; i < size - dec; i++) { // colonnes
			tab[i][size - dec - 1] = getCell(i, size - dec - 1);
			if (tab[i][size - 1 - dec].getFish() != 0)
				r[tab[i][size - 1 - dec].getFish() - 1]++;
			tab[i][dec] = getCell(i,dec);
			if (tab[i][dec].getFish() != 0)
				r[tab[i][dec].getFish() - 1]++;
		}
		return r;
	}

	@Override
	public Cellule[][] constructTab() {
		int[] tmp;
		for (int i = 0; i < size - 1; i++) {
			tmp = square(i);
			for (int j = 0; j < tmp.length; j++) {
				nb[j] += tmp[j];
			}
		}
		return tab;
	}

}
