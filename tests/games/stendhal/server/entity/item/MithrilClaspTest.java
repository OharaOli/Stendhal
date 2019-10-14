package games.stendhal.server.entity.item;

import static org.junit.Assert.*;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.RPClass.ItemTestHelper;

public class MithrilClaspTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
		ItemTestHelper.generateRPClasses();
	}
	/**
	 * Testing that the mithril clasp item can be equipped to the key ring lost
	 */
	@Test
	public void addMithrilClaspToKeyring() {
		final Item clasp = SingletonRepository.getEntityManager().getItem("mithril clasp");
		final Boolean test = clasp.canBeEquippedIn("keyring");
		assertTrue(test);
	}

}
