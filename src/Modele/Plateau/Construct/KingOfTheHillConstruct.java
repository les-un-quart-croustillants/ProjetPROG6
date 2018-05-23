package Modele.Plateau.Construct;

import Utils.Position;

import static java.lang.Math.abs;

public class KingOfTheHillConstruct extends Construct {
	private int size;
	private Position center;
	private Position current;
	private int zone1, zone2;
	private int[][] tab;

	public KingOfTheHillConstruct(int size) {
		this.size = size;
		center = new Position((size)/2, (size - 1)/2);
		zone2 = size / 9;
		zone1 = size / 6;
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
