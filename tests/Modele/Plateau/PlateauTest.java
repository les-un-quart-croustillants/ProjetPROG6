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
		for (int i = 0; i < p.getSize(); i++) {
			if (i % 2 == 0) {
				Assert.assertTrue("initTab : test fin de ligne" + i + "/" + p.getSize() + " failed.", p.getCellule(new Position(i,p.getSize() - 1)).isDestroyed());
			}
			else {
				Assert.assertFalse("initTab : test fin de ligne" + i + "/" + p.getSize() + " failed.", p.getCellule(new Position(i,p.getSize() - 1)).isDestroyed());
			}
			for (int j = 0; j < p.getSize(); j++) {
				Position pos = new Position(i,j);
				Cellule cell = p.getCellule(pos);
				boolean condition = cell.isDestroyed()
									|| (0 < cell.getFish()
									&& cell.getFish() <= 3);
				Assert.assertTrue("initTab : test nombre de poisson " + (i+j) + "/" + (2*p.getSize()) + " failed.", condition);
			}
		}
	}

	@Test
	public void getCellule() {
		Position pos;
		Cellule expected;

		pos = new Position(0,0);
		expected = new Cellule(pos, false, p.getTab()[pos.i()][pos.j()].getFish());
		Assert.assertEquals("getCellule test #1/4 failed", expected, p.getCellule(pos));

		pos = new Position(0,2);
		expected = new Cellule(pos, true,  p.getTab()[pos.i()][pos.j()].getFish());
		Assert.assertEquals("getCellule test #2/4 failed", expected, p.getCellule(pos));

		pos = new Position(1,2);
		expected = new Cellule(pos, false,  p.getTab()[pos.i()][pos.j()].getFish());
		Assert.assertEquals("getCellule test #3/4 failed", expected, p.getCellule(pos));

		pos = new Position(2,2);
		expected = new Cellule(pos, true,  p.getTab()[pos.i()][pos.j()].getFish());
		Assert.assertEquals("getCellule test #4/4 failed", expected, p.getCellule(pos));

		Cellule c = p.getTab()[0][0];
		Assert.assertEquals((Object) c, (Object) p.getCellule(new Position(0,0)));
		Assert.assertEquals(c, p.getCellule(new Position(0,0)));
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
		int nb_test = 4;
		LinkedList<Position> expected = new LinkedList<>();
		expected.add(new Position(0,0));
		expected.add(new Position(0,1) );

		expected.add(new Position(1,0) );
		expected.add(new Position(1,2));

		expected.add(new Position(2,0));
		expected.add(new Position(2,1));
		Assert.assertEquals("getNeighbours test #1/" + nb_test + " failed", expected, p.getNeighbours(new Position(1,1)));

		expected = new LinkedList<>();
		expected.add(new Position(0,1));
		expected.add(new Position(1,0));
		expected.add(new Position(1,1));
		Assert.assertEquals("getNeighbours test #2/" + nb_test + " failed", expected, p.getNeighbours(new Position(0,0)));

		Plateau vide = new Plateau(1);
		Assert.assertTrue("getNeighbours test #3/" + nb_test + " failed", vide.getNeighbours(new Position(0,0)).isEmpty());

		Plateau sujet = new Plateau(5);
		expected = new LinkedList<>();
		expected.add(new Position(1,1));
		expected.add(new Position(1,2));
		expected.add(new Position(2,0));
		expected.add(new Position(2,2));
		expected.add(new Position(3,1));
		expected.add(new Position(3,2));
		Assert.assertEquals("getNeighbours test #4/" + nb_test + " failed", expected, sujet.getNeighbours(new Position(2,1)));
	}

	@Test
	public void accessible() {
		int nb_tests = 4;
		Plateau sujet = new Plateau(10);
		Position pos = new Position(5,0);
		LinkedList<Position> expected = new LinkedList<>();

		// diff de 1
		expected.add(new Position(4,0));
		expected.add(new Position(6,0));
		expected.add(new Position(5,1));
		// diff de 2
		expected.add(new Position(3,1));
		expected.add(new Position(7,1));
		expected.add(new Position(5,2));
		// diff de 3
		expected.add(new Position(2,1));
		expected.add(new Position(8,1));
		expected.add(new Position(5,3));
		// diff de 4
		expected.add(new Position(1,2));
		expected.add(new Position(9,2));
		expected.add(new Position(5,4));
		// diff de 5
		expected.add(new Position(0,2));
		expected.add(new Position(5,5));
		// diff de 6
		expected.add(new Position(5,6));
		// diff de 7
		expected.add(new Position(5,7));
		// diff de 8
		expected.add(new Position(5,8));
		// diff de 9
		expected.add(new Position(5,9));
		Assert.assertEquals("accessible test #1/"+ nb_tests + " failed", expected, sujet.accessible(pos));

		pos = new Position(5,5);
		expected = new LinkedList<>();

		// diff de 1
		expected.add(new Position(4,4));
		expected.add(new Position(6,4));
		expected.add(new Position(4,5));
		expected.add(new Position(6,5));
		expected.add(new Position(5,4));
		expected.add(new Position(5,6));
		// diff de 2
		expected.add(new Position(3,4));
		expected.add(new Position(7,4));
		expected.add(new Position(3,6));
		expected.add(new Position(7,6));
		expected.add(new Position(5,3));
		expected.add(new Position(5,7));
		// diff de 3
		expected.add(new Position(2,3));
		expected.add(new Position(8,3));
		expected.add(new Position(2,6));
		expected.add(new Position(8,6));
		expected.add(new Position(5,2));
		expected.add(new Position(5,8));
		// diff de 4
		expected.add(new Position(1,3));
		expected.add(new Position(9,3));
		expected.add(new Position(1,7));
		expected.add(new Position(9,7));
		expected.add(new Position(5,1));
		expected.add(new Position(5,9));
		// diff de 5
		expected.add(new Position(0,2));
		expected.add(new Position(0,7));
		expected.add(new Position(5,0));
		Assert.assertEquals("accessible test #2/"+ nb_tests + "  failed", expected, sujet.accessible(pos));

		pos = new Position(5,5);
		expected = new LinkedList<>();
		sujet.getTab()[3][6].destroy();
		// diff de 1
		expected.add(new Position(4,4));
		expected.add(new Position(6,4));
		expected.add(new Position(4,5));
		expected.add(new Position(6,5));
		expected.add(new Position(5,4));
		expected.add(new Position(5,6));
		// diff de 2
		expected.add(new Position(3,4));
		expected.add(new Position(7,4));
		expected.add(new Position(7,6));
		expected.add(new Position(5,3));
		expected.add(new Position(5,7));
		// diff de 3
		expected.add(new Position(2,3));
		expected.add(new Position(8,3));
		expected.add(new Position(8,6));
		expected.add(new Position(5,2));
		expected.add(new Position(5,8));
		// diff de 4
		expected.add(new Position(1,3));
		expected.add(new Position(9,3));
		expected.add(new Position(9,7));
		expected.add(new Position(5,1));
		expected.add(new Position(5,9));
		// diff de 5
		expected.add(new Position(0,2));
		expected.add(new Position(5,0));
		Assert.assertEquals("accessible test #3/"+ nb_tests + "  failed", expected, sujet.accessible(pos));

		sujet = new Plateau(8);
		pos = new Position(4,0);
		expected = new LinkedList<>();

		sujet.destroyCell(new Position(3,0));
		sujet.destroyCell(new Position(4,2));

		expected.add(new Position(5,0));
		expected.add(new Position(3,1));
		expected.add(new Position(5,1));
		expected.add(new Position(4,1));
		expected.add(new Position(2,1));
		expected.add(new Position(6,1));
		expected.add(new Position(1,2));
		expected.add(new Position(7,2));
		expected.add(new Position(0,2));

		Assert.assertEquals("accessible test #4/"+ nb_tests + "  failed", expected, sujet.accessible(pos));
	}

	@Test
	public void diffDir() {
		Assert.assertEquals(1, p.diffDir(0,1));
		Assert.assertEquals(0, p.diffDir(0,0));
		Assert.assertEquals(-1, p.diffDir(1,0));
	}

	@Test
	public void estAccessible() {
		Plateau sujet = new Plateau(10);
		Position current = new Position(0,0),
				target = new Position(1,2);

		Assert.assertFalse(p.estAccessible(current, target));
		Assert.assertFalse(p.estAccessible(target, current));

		target = new Position(2,1);
		Assert.assertTrue(p.estAccessible(current, target));
		Assert.assertTrue(p.estAccessible(target, current));

		p.destroyCell(new Position(1,1));
		Assert.assertFalse(p.estAccessible(current, target));
		Assert.assertFalse(p.estAccessible(target, current));

		current = new Position(1,0);
		target = new Position(2,0);
		Assert.assertTrue(p.estAccessible(current, target));
		Assert.assertTrue(p.estAccessible(target, current));

		target = new Position(0,1);
		Assert.assertFalse(p.estAccessible(current, target));
		Assert.assertFalse(p.estAccessible(target, current));

		target = new Position(1,2);
		Assert.assertFalse(p.estAccessible(current, target));
		Assert.assertFalse(p.estAccessible(target, current));

		p.getCellule(new Position(1,1)).setDestroyed(false);
		Assert.assertTrue(p.estAccessible(current, target));
		Assert.assertTrue(p.estAccessible(target, current));
	}

	@Test
	public void poserPingouin() {
		boolean test = false;
		Pingouin pingouin = new Pingouin(1);
		int i = 0,
			j = 0;
		Cellule c = p.getCellule(new Position(i,j));
		while(!test) {
			while (i < p.getSize() && j < p.getSize() && c.getFish() != 1) {
				j++;
				if (j == p.getSize()) {
					j = 0;
					i++;
				}
				c = p.getCellule(new Position(i, j));
			}
			if (i < p.getSize() && j < p.getSize()) {
				test = true;
				Position pos = new Position(i, j);
				p.poserPingouin(pos, pingouin);
				pingouin.setPosition(pos);
				Assert.assertEquals(pingouin, p.getCellule(pos).pingouin());
				Assert.assertEquals(pingouin, p.getTab()[i][j].pingouin());
			}
		}
	}

	@Test
	public void clonetest() {
		Plateau sujet = p.clone();
		Assert.assertEquals(p, p.clone());
		Assert.assertEquals(p,sujet);
		sujet.getCellule(new Position(0,0)).destroy();
		Assert.assertFalse(p.equals(sujet));
		sujet = new Plateau(4);
		Assert.assertFalse(p.clone().equals(sujet.clone()));

	}

	@Test
	public void equals() {
		Plateau sujet = new Plateau(p.getSize());
		Assert.assertEquals(p,p);
		Assert.assertFalse(p.equals(sujet));
	}
}
