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
