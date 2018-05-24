package Modele.Plateau.Construct;

import Utils.Position;

import static java.lang.Math.abs;
import static java.lang.Math.min;
/*
 * TODO : garantir le bon nombre de cases à 1 pour poser les pingouins
 * TODO : supporter les proportions par nombre de poissons
 */
public class KingOfTheHillConstruct extends Construct {
	private Position center;
	private int zone1, zone2;

	public KingOfTheHillConstruct(int size, int nb_pingouins) {
		super(size, nb_pingouins);
		this.size = size;
		center = new Position((size)/2, (size - 1)/2);
		set_zones();
	}

	private void set_zones() {
		int borne = min(size - center.i(), size - center.j()),
				zones_size = borne / 3;
		zone2 = zones_size;
		zone1 = zone2 + zones_size;
		System.out.println("z1 : " + zone1 + ", z2 : " + zone2);
	}

	private int distanceFromCenter(int i, int j) {
		return (abs(center.i() - i) + abs(center.j() - j)) / 2; // FIXME prendre en compte la distance graphique
	}

	@Override
	public int getCellValue(int i, int j) {
		int d = distanceFromCenter(i, j);
		if (d > zone1)
			return 1;
		else if(d > zone2)
			return 2;
		else
			return 3;
	}
}
