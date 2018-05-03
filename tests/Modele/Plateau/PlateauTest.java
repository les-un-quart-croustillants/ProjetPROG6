package Modele.Plateau;

import Utils.Position;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Random;

public class PlateauTest {
	Plateau p;
	@Before
	public void setUp() {
		p = new Plateau(3);
	}

	@Test
	public void initTab() {
		String expected = "{3, [ " +
				"[[(0,0), false], [(0,1), false], [(0,2), true]] " +
				"[[(1,0), false], [(1,1), false], [(1,2), false]] " +
				"[[(2,0), false], [(2,1), false], [(2,2), true]] " +
				"]}";
		Assert.assertEquals("initTab test #1/1 failed", expected, p.toString());
	}

	@Test
	public void getCellule() {
		Position pos;
		Cellule expected;

		pos = new Position(0,0);
		expected = new Cellule(pos, false);
		Assert.assertEquals("getCellule test #1/4 failed", expected, p.getCellule(pos));

		pos = new Position(0,2);
		expected = new Cellule(pos, true);
		Assert.assertEquals("getCellule test #2/4 failed", expected, p.getCellule(pos));

		pos = new Position(1,2);
		expected = new Cellule(pos, false);
		Assert.assertEquals("getCellule test #3/4 failed", expected, p.getCellule(pos));

		pos = new Position(2,2);
		expected = new Cellule(pos, true);
		Assert.assertEquals("getCellule test #4/4 failed", expected, p.getCellule(pos));
	}

	@Test
	public void isInTab() {
		Random r = new Random();
		Position pos = new Position(0,0);
		Assert.assertTrue("isInTab test #1/5 failed", p.isInTab(pos));
		pos = new Position(p.getSize() - 1, p.getSize() - 1);
		Assert.assertTrue("isInTab test #2/5 failed", p.isInTab(pos));
		pos = new Position(-1,0);
		Assert.assertFalse("isInTab test #3/5 failed", p.isInTab(pos));
		pos = new Position(0,-1);
		Assert.assertFalse("isInTab test #4/5 failed", p.isInTab(pos));
		pos = new Position(p.getSize(), p.getSize());
		Assert.assertFalse("isInTab test #5/5 failed", p.isInTab(pos));
	}

	@Test
	public void getNeighbours() {
		LinkedList<Position> expected = new LinkedList<>();
		expected.add(new Position(0,1));
		expected.add(new Position(0,2) );

		expected.add(new Position(1,0) );
		expected.add(new Position(1,2));

		expected.add(new Position(2,1));
		expected.add(new Position(2,2));
		Assert.assertEquals("getNeighbours test #1/3 failed", expected, p.getNeighbours(new Position(1,1)));

		expected = new LinkedList<>();
		expected.add(new Position(0,1));
		expected.add(new Position(1,0));
		expected.add(new Position(1,1));
		Assert.assertEquals("getNeighbours test #2/3 failed", expected, p.getNeighbours(new Position(0,0)));

		Plateau vide = new Plateau(1);
		Assert.assertTrue("getNeighbours test #3/3 failed", vide.getNeighbours(new Position(0,0)).isEmpty());
	}

	@Test
	public void accessible() {
		Plateau sujet = new Plateau(10);
		Position pos = new Position(5,0);
		LinkedList<Position> expected = new LinkedList<>();

		expected.add(new Position(0,0));
		expected.add(new Position(0,5));

		expected.add(new Position(1,0));
		expected.add(new Position(1,4));
		expected.add(new Position(5,1));

		expected.add(new Position(2,0));
		expected.add(new Position(2,3));
		expected.add(new Position(5,2));

		expected.add(new Position(3,0));
		expected.add(new Position(3,2));
		expected.add(new Position(5,3));

		expected.add(new Position(4,0));
		expected.add(new Position(4,1));
		expected.add(new Position(5,4));

		expected.add(new Position(5,5));

		expected.add(new Position(6,0));
		expected.add(new Position(6,1));
		expected.add(new Position(5,6));

		expected.add(new Position(7,0));
		expected.add(new Position(7,2));
		expected.add(new Position(5,7));

		expected.add(new Position(8,0));
		expected.add(new Position(8,3));
		expected.add(new Position(5,8));

		expected.add(new Position(9,0));
		expected.add(new Position(9,4));
		expected.add(new Position(5,9));

		Assert.assertEquals("accessible test #1/2 failed", expected, sujet.accessible(pos));

		pos = new Position(p.getSize() - 1,p.getSize() - 1);
		expected = new LinkedList<>();
		expected.add(new Position(0,2));
		expected.add(new Position(2,0));
		expected.add(new Position(1,2));
		expected.add(new Position(2,1));

		Assert.assertEquals("accessible test #2/2 failed", expected, p.accessible(pos));
	}
}
