package Modele.Plateau;

import Utils.Position;
import org.junit.Assert;
import org.junit.Test;

public class PingouinTest {

	@Test
	public void equals() {
		Pingouin p1 = new Pingouin(1, new Position(0,0));
		Pingouin p2 = new Pingouin(1, new Position(0,0));
		Pingouin p3 = new Pingouin(2, new Position(0,0));
		Pingouin p4 = new Pingouin(1, new Position(0,1));

		Assert.assertEquals(p1,p2);
		Assert.assertFalse(p1.equals(p3));
		Assert.assertFalse(p1.equals(p4));
	}
}
