package Joueurs;


import Modele.Plateau.Pingouin;
import Modele.Plateau.Plateau;
import Utils.Position;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class JoueurPhysiqueTest {
	JoueurPhysique j;

	@Before
	public void setUp() {
		j = new JoueurPhysique(0,2);
	}

	@Test
	public void jouerCoup() {
		Plateau plateau = new Plateau(10);
		Position start,goal;
		int expected;
		setUp();
		
		fail("Not implemented.");

		/*pingouin = j.squad().get(0);
		goal = new Position(1,2);
		expected = plateau.getCellule(goal).getFish();
		try {
			Assert.assertEquals("jouerCoup test #1/5 failed", expected, j.jouerCoup(plateau, pingouin, goal));
		} catch (Exception e) {
			fail("jouerCoup test #1/5 failed");
		}

		pingouin = j.squad().get(0);
		goal = new Position(1,-2);
		expected = -1;
		try {
			Assert.assertEquals("jouerCoup test #2/5 failed", expected, j.jouerCoup(plateau, pingouin, goal));
		} catch (Exception e) {
			fail("jouerCoup test #2/5 failed");
		}

		pingouin = j.squad().get(0);
		goal = new Position(1,-2);
		expected = -1;
		try {
			Assert.assertEquals("jouerCoup test #3/5 failed", expected, j.jouerCoup(plateau, pingouin, goal));
		} catch (Exception e) {
			fail("jouerCoup test #3/5 failed");
		}

		pingouin = new Pingouin(1,new Position(4,6));
		goal = new Position(4,7);
		try {
			j.jouerCoup(plateau, pingouin, goal);
			fail("jouerCoup test #4/5 failed");
		} catch (Exception e) {
			Assert.assertEquals(e.getMessage(), "Le pingouin en (4,6) n'appartient pas au joueur 0 mais au joueur 1.");
		}

		pingouin = new Pingouin(0,new Position(4,6));
		goal = new Position(4,7);
		try {
			j.jouerCoup(plateau, pingouin, goal);
			fail("jouerCoup test #5/5 failed");
		} catch (Exception e) {
			Assert.assertEquals(e.getMessage(), "Le pingouin en (4,6) n'est pas reconnus par le joueur 0 (erreur interne).");
		}
		*/
	}

}
