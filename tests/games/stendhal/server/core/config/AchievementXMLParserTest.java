package games.stendhal.server.core.config;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Test;
import org.xml.sax.SAXException;

import games.stendhal.server.core.rp.achievement.Achievement;
import games.stendhal.server.core.rp.achievement.Category;

public class AchievementXMLParserTest {
	
	@Test
	public void testListNotEmpty() throws URISyntaxException, SAXException, IOException {
		AchievementGroupsXMLParser parser = new AchievementGroupsXMLParser(new URI("testachievements.xml"));
		//load test XML file which holds paths to some test XMLs and store them in a list of Achievement instances
		List<Achievement> achievements = parser.load();
		//test to see if list is empty - should not be
		assertThat(Boolean.valueOf(achievements.isEmpty()), is(Boolean.FALSE));
	}
	
	@Test
	public void testListCorrectLength() throws URISyntaxException, SAXException, IOException {
		AchievementGroupsXMLParser parser = new AchievementGroupsXMLParser(new URI("testachievements.xml"));
		//load test achievements into a list of Achievement instances
		List<Achievement> achievements = parser.load();
		//test to see if list is four elements long - size of test data
		assertThat(achievements.size(), is(Integer.valueOf(4)));
	}
	
	@Test
	public void testStringValuesLoadCorrectly() throws URISyntaxException, SAXException, IOException {
		AchievementGroupsXMLParser parser = new AchievementGroupsXMLParser(new URI("testachievements.xml"));
		List<Achievement> achievements = parser.load();
		
		//get first achievement instance from list
		Achievement achievement = achievements.get(0);
		//check if string values have loaded correctly from XML
		assertThat(achievement.getIdentifier(), is(String.valueOf("xp.level.010")));
		assertThat(achievement.getTitle(), is(String.valueOf("Greenhorn")));
		assertThat(achievement.getDescription(), is(String.valueOf("Reach level 10")));
	}
	
	@Test
	public void testIntegerValuesLoadCorrectly() throws URISyntaxException, SAXException, IOException {
		AchievementGroupsXMLParser parser = new AchievementGroupsXMLParser(new URI("testachievements.xml"));
		List<Achievement> achievements = parser.load();
		
		//get first achievement instance from list
		Achievement achievement = achievements.get(0);
		//check if integer values have loaded correctly from XML
		assertThat(achievement.getBaseScore(), is(Achievement.EASY_BASE_SCORE));
	}
	
	@Test
	public void testBooleanValuesLoadCorrectly() throws URISyntaxException, SAXException, IOException {
		AchievementGroupsXMLParser parser = new AchievementGroupsXMLParser(new URI("testachievements.xml"));
		List<Achievement> achievements = parser.load();
		
		//get first achievement instance from list
		Achievement achievement = achievements.get(0);
		//check if boolean values have correctly loaded from XML
		assertThat(achievement.isActive(), is(Boolean.TRUE));
	}
	
	@Test
	public void testEnumValuesLoadCorrectly() throws URISyntaxException, SAXException, IOException {
		AchievementGroupsXMLParser parser = new AchievementGroupsXMLParser(new URI("testachievements.xml"));
		List<Achievement> achievements = parser.load();
		
		//get first achievement instance from list
		Achievement achievement = achievements.get(0);
		//check if enum values have correctly loaded from XML
		assertThat(achievement.getCategory(), is(Category.EXPERIENCE));
	}
	
	@Test
	public void testDataLoadsIntoAchievementClass() throws URISyntaxException, SAXException, IOException {
		AchievementGroupsXMLParser parser = new AchievementGroupsXMLParser(new URI("testachievements.xml"));
		List<Achievement> achievements = parser.load();
		
		//get first achievement instance from list
		Achievement achievement = achievements.get(1);
		//check if data has correctly loaded from XML
		assertThat(achievement.getCategory(), is(Category.FIGHTING));
		assertThat(achievement.getIdentifier(), is(String.valueOf("fight.general.rats")));
		assertThat(achievement.getTitle(), is(String.valueOf("Rat Hunter")));// "SOMETHING"
		assertThat(achievement.getDescription(), is(String.valueOf("Kill 15 rats")));
		assertThat(achievement.getBaseScore(), is(Achievement.EASY_BASE_SCORE));
		assertThat(achievement.isActive(), is(Boolean.TRUE));
	}

	@Test
	public void testDataLoadsIntoMultipleClasses() throws URISyntaxException, SAXException, IOException {
		AchievementGroupsXMLParser parser = new AchievementGroupsXMLParser(new URI("testachievements.xml"));
		List<Achievement> achievements = parser.load();
		Achievement achievement = null;
		
		//load the remaining test achievements to ensure parser can handle loading multiple achievements correctly
		//get second element from list and check its data
		achievement = achievements.get(2);
		assertThat(achievement.getCategory(), is(Category.QUEST));
		assertThat(achievement.getIdentifier(), is(String.valueOf("quest.special.elf_princess.0025")));
		assertThat(achievement.getTitle(), is(String.valueOf("Faiumoni's Casanova")));
		assertThat(achievement.getDescription(), is(String.valueOf("Finish elf princess quest 25 times")));
		assertThat(achievement.getBaseScore(), is(Achievement.MEDIUM_BASE_SCORE));
		assertThat(achievement.isActive(), is(Boolean.TRUE));
		
		// get third element from list and check its data
		achievement = achievements.get(3);
		assertThat(achievement.getCategory(), is(Category.OUTSIDE_ZONE));
		assertThat(achievement.getIdentifier(), is(String.valueOf("zone.outside.semos")));
		assertThat(achievement.getTitle(), is(String.valueOf("Junior Explorer")));
		assertThat(achievement.getDescription(), is(String.valueOf("Visit all outside zones in the Semos region")));
		assertThat(achievement.getBaseScore(), is(Achievement.EASY_BASE_SCORE));
		assertThat(achievement.isActive(), is(Boolean.TRUE));
	}
	
}
