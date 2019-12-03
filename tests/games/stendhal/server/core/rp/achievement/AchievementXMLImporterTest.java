package games.stendhal.server.core.rp.achievement;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.junit.Test;

import games.stendhal.server.core.rp.achievement.factory.AbstractAchievementFactory;
/**
 * Test suite for {@link AchievementImporter}
 * This should include :
 * mergeAchievements tests
 * readAchievementXML tests
 * 
 * @author q19754wk
 *
 */
public class AchievementXMLImporterTest {
	
	AchievementImporter importer = AchievementImporter.get();
	
	/*
	 * Tests for the existance of only one AchievementImporter.
	 */
	@Test
	public void singletonTest()
	{
		AchievementImporter otherImporter = AchievementImporter.get();
		assertEquals(importer, otherImporter);
	}
	/*
	 * Tests for the ability to get achievements from the XML
	 * using the AchievementImporter class.
	 */
	@Test
	public void getAchievementsTest()
	{
		List<AbstractAchievementFactory> factories = AbstractAchievementFactory.createFactories();
		List<Achievement> importerAchievements = importer.getAchievements();
		
		for(AbstractAchievementFactory factory : factories)
		{
			Collection<Achievement> achievementCollection = factory.createAchievements();
			Achievement[] achievements = (Achievement[]) achievementCollection.toArray();
			assertNotNull(achievements);
			assertTrue(importerAchievements.contains(achievements[0]));
		}
	}
}
