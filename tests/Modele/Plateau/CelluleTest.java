package Modele.Plateau;

import Utils.Position;
import org.junit.Assert;
import org.junit.Test;

public class CelluleTest {

	@Test
	public void destroy() {
		Cellule c = new Cellule();
		Assert.assertFalse(c.isDestroyed());
		c.destroy();
		Assert.assertTrue(c.isDestroyed());
	}


	@Test
	public void isDestroyed() {
		Cellule c = new Cellule();

		Assert.assertFalse(c.isDestroyed());
		c.setDestroyed(true);
		Assert.assertTrue(c.isDestroyed());
	}

	@Test
	public void aPingouin() {
		Cellule c = new Cellule(new Position(0,0), false, 1, null);
		Assert.assertFalse(c.aPingouin());
		c = new Cellule(new Position(0,0), false,1, new Pingouin(1, new Position(0,0)));
		Assert.assertTrue(c.aPingouin());
	}

	@Test
	public void isObstacle() {
		Cellule c1 = new Cellule(new Position(0,0), false, 1, null);
		Cellule c2 = new Cellule(new Position(0,0), true, 1, null);
		Cellule c3 = new Cellule(new Position(0,0), false, 1, new Pingouin(1, new Position(0,0)));
		Cellule c4 = new Cellule(new Position(0,0), true, 1, new Pingouin(1, new Position(0,0)));

		Assert.assertFalse(c1.isObstacle());
		Assert.assertTrue(c2.isObstacle());
		Assert.assertTrue(c3.isObstacle());
		Assert.assertTrue(c4.isObstacle());
	}

	@Test
	public void setPenguin() {
		Pingouin p = new Pingouin(1, new Position(0,0));
		Cellule c = new Cellule(new Position(0,0), false, 1, null);

		Assert.assertEquals(c.pingouin(), null);
		c.setPenguin(p);
		Assert.assertEquals(c.pingouin(), p);
	}


	@Test
	public void clonetests() {
		Cellule c1 = new Cellule(new Position(0,0), false, 1, null);
		Assert.assertEquals(c1, c1.clone());
	}

	@Test
	public void equals() {
		Cellule c1 = new Cellule(new Position(0,0), false, 1, null);
		Cellule c2 = new Cellule(new Position(0,0), false, 1, null);
		Cellule c3 = new Cellule(new Position(0,1), false, 1, null);
		Cellule c4 = new Cellule(new Position(0,0), true, 1, null);
		Cellule c5 = new Cellule(new Position(0,0), false, 2, null);
		Cellule c6 = new Cellule(new Position(0,0), false, 1, new Pingouin(1));
		Assert.assertEquals(c1,c2);
		Assert.assertFalse(c1.equals(c3));
		Assert.assertFalse(c1.equals(c4));
		Assert.assertFalse(c1.equals(c5));
		System.out.println(c1);
		System.out.println(c6);
		Assert.assertFalse(c1.equals(c6));
	}
}
