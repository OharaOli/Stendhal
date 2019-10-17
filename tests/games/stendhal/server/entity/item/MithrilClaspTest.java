package games.stendhal.server.entity.item;

import static org.junit.Assert.*;


import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.maps.MockStendlRPWorld;

public class MithrilClaspTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
	}
	
	@Test
	public void test() {
		final Item clasp = SingletonRepository.getEntityManager().getItem("mithril clasp");
		assertTrue(clasp.canBeEquippedIn("keyring"));

	}

}
