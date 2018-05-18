package Modele.Plateau;

import Utils.Position;
import javafx.geometry.Pos;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class MoveTest {
	@Test
	public void getFrom() {
		Random r = new Random();
		Position from,
				 to = new Position(r.nextInt(),r.nextInt());
		Move m;

		for (int i = 0; i < 100; i++) {
			from = new Position(r.nextInt(),r.nextInt());
			m = new Move(to, from, r.nextInt());
			Assert.assertEquals(from, m.getFrom());
		}
	}

	@Test
	public void getTo() {
		Random r = new Random();
		Position to,
				 from = new Position(r.nextInt(),r.nextInt());
		Move m;

		for (int i = 0; i < 100; i++) {
			to = new Position(r.nextInt(),r.nextInt());
			m = new Move(to, from, r.nextInt());
			Assert.assertEquals(to, m.getTo());
		}
	}

	@Test
	public void getFishAte() throws Exception {
		Random r = new Random();
		int nb_fish;
		Position to = new Position(r.nextInt(),r.nextInt()),
				from = new Position(r.nextInt(),r.nextInt());
		Move m;

		for (int i = 0; i < 100; i++) {
			nb_fish = r.nextInt();
			m = new Move(to, from, nb_fish);
			Assert.assertEquals(nb_fish, m.getFishAte());
		}
	}


	private void move_equals_to() {
		Random r = new Random();
		Position to1 = new Position(0,0),
				to2 = new Position(0,0),
				from = new Position(r.nextInt(), r.nextInt());
		int nb_fish = r.nextInt();
		Move m1, m2;
		for (int i = 0; i < 100; i++) {
			m1 = new Move(to1, from, nb_fish);
			m2 = new Move(to2, from, nb_fish);
			Assert.assertEquals(m1, m1);
			Assert.assertEquals(m2,m2);
			Assert.assertEquals(to1.equals(to2),m1.equals(m2));
			Assert.assertEquals(to2.equals(to1),m2.equals(m1));
			to1 = new Position(r.nextInt(),r.nextInt());
			to2 = new Position(r.nextInt(),r.nextInt());
		}
	}

	private void move_equals_from() {
		Random r = new Random();
		Position from1 = new Position(0,0),
				from2 = new Position(0,0),
				to = new Position(r.nextInt(), r.nextInt());
		Move m1, m2;
		int nb_fish = r.nextInt();
		for (int i = 0; i < 100; i++) {
			m1 = new Move(to, from1, nb_fish);
			m2 = new Move(to, from2, nb_fish);
			Assert.assertEquals(m1, m1);
			Assert.assertEquals(m2,m2);
			Assert.assertEquals(from1.equals(from2),m1.equals(m2));
			Assert.assertEquals(from2.equals(from1),m2.equals(m1));
			from1 = new Position(r.nextInt(),r.nextInt());
			from2 = new Position(r.nextInt(),r.nextInt());
		}
	}

	private void move_equals_nb_fish() {
		Random r = new Random();
		Position to = new Position(r.nextInt(), r.nextInt()),
				from = new Position(r.nextInt(), r.nextInt());
		Move m1, m2;
		int nb_1 = 0,
			nb_2 = 0;
		for (int i = 0; i < 100; i++) {
			m1 = new Move(to, from, nb_1);
			m2 = new Move(to, from, nb_2);
			Assert.assertEquals((nb_1 == nb_2), m1.equals(m2));
			nb_2 = r.nextInt();
			nb_1 = r.nextInt();
		}

	}

	@Test
	public void equals() {
		move_equals_to();
		move_equals_from();
		move_equals_nb_fish();
	}

}
