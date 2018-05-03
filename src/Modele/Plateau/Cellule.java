package Modele.Plateau;

import Utils.Position;

public class Cellule {
	private Position position;
	private boolean destroyed;

	public Cellule() {
		this(new Position(0,0), false);
	}
	public Cellule(Position position) {
		this(position, false);
	}
	public Cellule(int i, int j, boolean b) {
		this(new Position(i,j),b);
	}
	public Cellule(Position position, boolean destroyed, int fish) {
		this.position = position;
		this.destroyed = destroyed;
	}

	public void destroy() {
		this.destroyed = true;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	@Override
	public boolean equals(Object obj) {
		return ((Cellule) obj).position.equals(this.position)
				&& ((Cellule) obj).destroyed == this.destroyed;
	}

	@Override
	public String toString() {
		return "[" + position + "," + destroyed + "," + fish + ']';
	}
}
