package Modele.Plateau;

import Utils.Position;

public class Cellule {
	Position position;
	boolean b;

	public Cellule() {
		this(new Position(0,0), false);
	}
	public Cellule(Position position) {
		this(position, false);
	}
	public Cellule(int i, int j, boolean b) {
		this(new Position(i,j),b);
	}

	public Cellule(Position position, boolean b) {
		this.position = position;
		this.b = b;
	}

	@Override
	public boolean equals(Object obj) {
		return ((Cellule) obj).position.equals(this.position)
				&& ((Cellule) obj).b == this.b;
	}

	@Override
	public String toString() {
		return "[" + position + ", " + b + ']';
	}
}
