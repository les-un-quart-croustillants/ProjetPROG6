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
				target = new Position(0,0);

		Assert.assertFalse(p.estAccessible(current, target));
		Assert.assertFalse(p.estAccessible(target, current));

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

		target = new Position(2,3);
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

	@Test
	public void jouer() {
		Random r = new Random();
		int expected;
		Plateau sujet = p.clone();
		Position current,
				target;

		for (int i = 0; i < 100; i++) {
			expected = -1;
			current = new Position(r.nextInt(sujet.getSize()), r.nextInt(sujet.getSize()));
			target = new Position(r.nextInt(sujet.getSize()), r.nextInt(sujet.getSize()));
			sujet.getCellule(current).setPenguin(new Pingouin(0, current));
			if (sujet.isInTab(target) && sujet.estAccessible(current, target))
				expected = sujet.getCellule(target).getFish();
			Assert.assertEquals("Jouer : test " + i + "/100 failed with config : \nc :" + current + "\nt : " + target + "\n" + sujet.pretty(), expected, sujet.jouer(current, target));
			sujet = p.clone();
		}
	}

	@Test
	public void undo() {
		Position from= new Position(0,0),
				to = new Position(0,1);
		Pingouin pingouin = new Pingouin(0, from);
		p.getCellule(from).setPenguin(pingouin);
		Plateau sujet = p.clone();
		sujet.jouer(from, to);
		Assert.assertNotEquals(p, sujet);
		Assert.assertFalse(sujet.getHistory().isEmpty());
		Assert.assertTrue(sujet.getUndoList().isEmpty());
		sujet.undo();
		Assert.assertFalse(sujet.getUndoList().isEmpty());
		Assert.assertTrue(sujet.getHistory().isEmpty());
		Assert.assertEquals(1, sujet.getUndoList().size());
		Assert.assertTrue(sujet.getUndoList().contains(new Move(to,from,p.getCellule(to).getFish())));
		Assert.assertTrue(p.tabEquals(sujet.getTab()));
		Assert.assertFalse(p.getCellule(to).aPingouin());
		Assert.assertTrue(p.getCellule(from).aPingouin());
		Assert.assertEquals(pingouin, p.getCellule(from).pingouin());
	}

	@Test
	public void redo() {
		Position from1 = new Position(0,0),
				to1 = new Position(0,1),
				from2 = to1.clone(),
				to2 = new Position(1,2);
		Move m1 = new Move(to1, from1, p.getCellule(to1).getFish()),
			m2 = new Move(to2, from2, p.getCellule(to2).getFish());
		Pingouin pingouin1 = new Pingouin(0,from1),
				pingouin2 = new Pingouin(0, from2),
				pingouin3 = new Pingouin(0, to2);
		p.getCellule(from1).setPenguin(pingouin1);
		Plateau sujet1 = p.clone(),
				sujet2;
		sujet1.jouer(from1,to1);
		sujet2 = sujet1.clone();
		sujet2.jouer(from2,to2);
		sujet2.undo();
		Assert.assertFalse(sujet2.getUndoList().isEmpty());
		Assert.assertEquals(1, sujet2.getUndoList().size());
		Assert.assertTrue(sujet2.getUndoList().contains(m2));
		Assert.assertTrue(sujet1.tabEquals(sujet2.getTab()));
		Assert.assertFalse(sujet2.getCellule(to2).aPingouin());
		Assert.assertTrue(sujet2.getCellule(from2).aPingouin());
		Assert.assertEquals(pingouin2, sujet2.getCellule(from2).pingouin());
		sujet2.undo();
		Assert.assertFalse(sujet2.getUndoList().isEmpty());
		Assert.assertEquals(2, sujet2.getUndoList().size());
		Assert.assertTrue(sujet2.getHistory().isEmpty());
		Assert.assertTrue(sujet2.getUndoList().contains(m2));
		Assert.assertTrue(sujet2.getUndoList().contains(m1));
		Assert.assertTrue(sujet2.tabEquals(sujet2.getTab()));
		Assert.assertFalse(sujet2.getCellule(to2).aPingouin());
		Assert.assertTrue(sujet2.getCellule(from1).aPingouin());
		Assert.assertEquals(pingouin1, p.getCellule(from1).pingouin());
		sujet2.redo();
		Assert.assertFalse(sujet2.getUndoList().isEmpty());
		Assert.assertEquals(1, sujet2.getUndoList().size());
		Assert.assertEquals(1,sujet2.getHistory().size());
		Assert.assertTrue(sujet2.getHistory().contains(m1));
		Assert.assertTrue(sujet2.getUndoList().contains(m2));
		Assert.assertTrue(sujet1.tabEquals(sujet2.getTab()));
		Assert.assertFalse(sujet2.getCellule(to2).aPingouin());
		Assert.assertTrue(sujet2.getCellule(from2).aPingouin());
		Assert.assertEquals(pingouin2, sujet2.getCellule(from2).pingouin());
		sujet2.redo();
		Assert.assertTrue(sujet2.getUndoList().isEmpty());
		Assert.assertFalse(sujet2.getHistory().isEmpty());
		Assert.assertEquals(2, sujet2.getHistory().size());
		Assert.assertTrue(sujet2.getHistory().contains(m2));
		Assert.assertTrue(sujet2.getHistory().contains(m1));
		Assert.assertFalse(sujet2.getCellule(from1).aPingouin());
		Assert.assertFalse(sujet2.getCellule(from2).aPingouin());
		Assert.assertFalse(sujet2.getCellule(to1).aPingouin());
		Assert.assertTrue(sujet2.getCellule(to2).aPingouin());
		Assert.assertEquals(pingouin3, sujet2.getCellule(to2).pingouin());
	}
}
