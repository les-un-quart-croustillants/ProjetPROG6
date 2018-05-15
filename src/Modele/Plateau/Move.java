package Modele.Plateau;

import Utils.Position;

public class Move {
	private Position to;
	private Position from;
	private int fishAte;

	public Move(Position to, Position from, int fishAte ) {
		this.to = to;
		this.from = from;
		this.fishAte = fishAte;
	}


	public Position getFrom() {
		return from;
	}

	public int getFishAte() {
		return fishAte;
	}

	public Position getTo() {
		return to;
	}


	public void setFrom(Position from) {
		this.from = from;
	}

	public void setFishAte(int fishAte) {
		this.fishAte = fishAte;
	}

	public void setTo(Position to) {
		this.to = to;
	}

	@Override
	public boolean equals(Object obj) {
		return this.from.equals(((Move) obj).getFrom())
				&& this.to.equals(((Move) obj).getTo())
				&& this.fishAte == ((Move) obj).getFishAte();
	}

	@Override
	public String toString() {
		return to + "," + from + "," + fishAte;
	}
}
