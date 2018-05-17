package Utils;


import java.io.Serializable;

public class Position implements Serializable {
	private int i, j;

	public Position(int i, int j) {
		this.i = i;
		this.j = j;
	}

    public int i() {
        return i;
    }

    public int j() {
        return j;
    }

	public Position clone() {
		return new Position(this.i,this.j);
	}

	@Override
	public boolean equals(Object obj) {
		return ((Position) obj).i() == this.i
				&& ((Position) obj).j() == this.j;
	}

	@Override
	public String toString() {
		return "(" + i + "," + j + ')';
	}
}
