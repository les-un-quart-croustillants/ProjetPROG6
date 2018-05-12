package Modele.Plateau;

import Utils.Position;

public class Move {
	private Pingouin penguin;
	private Position target;
	private Position previous;
	private int fishAte;

	public Move(Pingouin penguin, Position target, Position previous, int fishAte) {
		this.penguin = penguin;
		this.target = target;
		this.previous = previous;
		this.fishAte = fishAte;
	}

	public Pingouin getPenguin() {
		return penguin;
	}

	public Position getPrevious() {
		return previous;
	}

	public int getFishAte() {
		return fishAte;
	}

	public Position getTarget() {
		return target;
	}

	public void setPenguin(Pingouin penguin) {
		this.penguin = penguin;
	}

	public void setPrevious(Position previous) {
		this.previous = previous;
	}

	public void setFishAte(int fishAte) {
		this.fishAte = fishAte;
	}

	public void setTarget(Position target) {
		this.target = target;
	}

	@Override
	public String toString() {
		return this.penguin + "," + target + "," + previous + "," + fishAte;
	}
}
