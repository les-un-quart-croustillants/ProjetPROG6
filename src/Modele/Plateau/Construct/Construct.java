package Modele.Plateau.Construct;

public abstract class Construct {
	int size;
	int nb_pingouins;

	public Construct(int size, int nb_pingouins) {
		this.size = size;
		this.nb_pingouins = nb_pingouins;
	}
	abstract public int getCellValue(int i, int j);
}

