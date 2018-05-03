package Utils;

public class Position {
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
