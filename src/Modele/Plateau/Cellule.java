package Modele.Plateau;

import Utils.Position;

import java.io.Serializable;

public class Cellule implements Serializable {
	private Position position;
	private boolean destroyed;
	private int fish;
	private Pingouin pingouin;

	public Cellule() {
		this(new Position(0,0), false, 0, null);
	}
	public Cellule(Position position, int fish) {
		this(position, false, fish, null);
	}
	public Cellule(Position position, boolean destroyed, int fish) {
		this(position, destroyed, fish, null);
	}
	public Cellule(Position position, boolean destroyed, int fish, Pingouin pingouin) {
		this.position = position;
		this.destroyed = destroyed;
		this.fish = fish;
		this.pingouin = pingouin;
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

	public Position getPosition() {
		return position;
	}

	public boolean aPingouin() {
		return this.pingouin != null;
	}

	public boolean isObstacle() {
		return this.isDestroyed() || this.aPingouin();
	}

	public Pingouin pingouin() {
		return this.pingouin;
	}

	public void setDestroyed(boolean b) {
		this.destroyed = b;
	}

	public void setFish(int fish) {
		this.fish = fish;
	}

	public void setPenguin(Pingouin pingouin) {
		this.pingouin = pingouin;
	}

	public Cellule clone() {
		return new Cellule(this.position.clone(), this.destroyed, this.fish, (this.aPingouin()) ? this.pingouin.clone() : null);
	}

	public String pretty() {
		return !destroyed ? Integer.toString(fish) + "," : "X,";
	}

	@Override
	public boolean equals(Object obj) {
		return ((Cellule) obj).position.equals(this.position)
				&& ((Cellule) obj).destroyed == this.destroyed
				&& ((Cellule) obj).fish == this.fish
				&& ((this.pingouin == null && ((Cellule) obj).pingouin == null)
					|| ((this.pingouin != null) && this.pingouin.equals(((Cellule) obj).pingouin)));
	}

	@Override
	public String toString() {
		return "[" + position + "," + destroyed + "," + fish + "," + pingouin + ']';
	}
}
