package Modele.Plateau;

import Utils.Position;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class PingouinTest {
	@Test
	public void serial() {
			String filename = "tests/rsc/test_serial.bin";
			Pingouin p = new Pingouin(0, new Position(0,0));
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(filename));
			os.writeObject(p);
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			Assert.fail();
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		}

		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(filename));
			Pingouin p_lecture = (Pingouin) is.readObject();
			Assert.assertEquals(p, p_lecture);
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			Assert.fail();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
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
