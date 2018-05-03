package Modele.Plateau;

import Utils.Position;

public class Cellule {
	private Position position;
	private boolean destroyed;
	private int fish;

	public Cellule() {
		this(new Position(0,0), false, 0);
	}
	public Cellule(Position position, boolean destroyed) {
		this(position, destroyed, 0);
	}
	public Cellule(Position position, int fish) {
		this(position, false, fish);
	}
	public Cellule(Position position, boolean destroyed, int fish) {
		this.position = position;
		this.destroyed = destroyed;
		this.fish = fish;
	}

	public void destroy() {
		this.destroyed = true;
	}

	public boolean isDestroyed() {
		return destroyed;
	}

	public int getFish() {
		return fish;
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
