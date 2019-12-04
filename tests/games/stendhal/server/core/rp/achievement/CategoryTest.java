package games.stendhal.server.core.rp.achievement;
import games.stendhal.server.core.rp.achievement.Category;

import static org.junit.Assert.*;

import org.junit.Test;

public class CategoryTest {
	
	@Test
	public void EnumCategoryTest()
	{
		assertEquals(Category.EXPERIENCE,0);
		
		assertEquals(Category.FIGHTING,1);
		
		assertEquals(Category.QUEST,2);
		
		assertEquals(Category.OUTSIDE_ZONE,3);
		
		assertEquals(Category.UNDERGROUND_ZONE,4);
		
		assertEquals(Category.INTERIOR_ZONE,5);
		
		assertEquals(Category.AGE,6);
		
		assertEquals(Category.ITEM,7);
		/** getting items */
		assertEquals(Category.OBTAIN,8);
		/** helping others and being friendly */
		assertEquals(Category.FRIEND,9);
		/** producing items */
		assertEquals(Category.PRODUCTION,10);
		/** ados item quests */
		assertEquals(Category.QUEST_ADOS_ITEMS,11);
		/** semos monster quest */
		assertEquals(Category.QUEST_SEMOS_MONSTER,12);
		/** kirdneh item quest */
		assertEquals(Category.QUEST_KIRDNEH_ITEM,13);
		/** mithrilbourgh kill enemy army quest */
		assertEquals(Category.QUEST_MITHRILBOURGH_ENEMY_ARMY,14);
	}

}
