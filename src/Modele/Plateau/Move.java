package Modele.Plateau;

import Utils.Position;

import java.io.Serializable;

public class Move implements Serializable {
	private Position to;
	private Position from;
	private int fishAte;
	private Pingouin pingouin;

	public Move(Position to, Pingouin pingouin) {
		this(to, Plateau.source, 1, pingouin);
	}

	public Move(Position to, Position from, int fishAte) {
		this(to, from, fishAte, null);
	}

	public Move(Position to, Position from, int fishAte, Pingouin pingouin) {
		this.to = to;
		this.from = from;
		this.fishAte = fishAte;
		this.pingouin = pingouin;
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

	public boolean aPingouin() {
		return pingouin != null;
	}

	public Pingouin getPingouin() {
		return pingouin;
	}

	public void setTo(Position to) {
		this.to = to;
	}

	public void setFrom(Position from) {
		this.from = from;
	}

	public void setFishAte(int fishAte) {
		this.fishAte = fishAte;
	}

	public void setPingouin(Pingouin pingouin) {
		this.pingouin = pingouin;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Move)) return false;

		Move move = (Move) o;

		if (getFishAte() != move.getFishAte()) return false;
		if (!getTo().equals(move.getTo())) return false;
		if (!getFrom().equals(move.getFrom())) return false;
		return getPingouin() != null ? getPingouin().equals(move.getPingouin()) : move.getPingouin() == null;
	}

	@Override
	public int hashCode() {
		int result = getTo().hashCode();
		result = 31 * result + getFrom().hashCode();
		result = 31 * result + getFishAte();
		result = 31 * result + (getPingouin() != null ? getPingouin().hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Move{" +
				"to=" + to +
				", from=" + from +
				", fishAte=" + fishAte +
				", pingouin=" + pingouin +
				'}';
	}
}
