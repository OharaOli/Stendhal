package games.stendhal.server.core.rp.achievement;


import static org.junit.Assert.assertEquals;


import org.junit.Test;

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
	
}
