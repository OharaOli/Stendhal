package games.stendhal.server.entity.npc;

import static org.junit.Assert.*;

import org.junit.Test;

public class FishShopListTest {

	@Test
	public void testFish() {
		final ShopList shops = ShopList.get();
		java.util.Map<String, Integer> testList = shops.get("buyfishes");
		assertEquals(testList.get("redlionfish"), (Integer)120);
	}

}
