package games.stendhal.server.core.config;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Test;
import org.xml.sax.SAXException;

import games.stendhal.server.core.rp.achievement.Achievement;

public class AchievementXMLParserTest {
	@Test
	public void testListNotEmpty() throws URISyntaxException, SAXException {
		AchievementXMLParser parser = new AchievementXMLParser();
		List<Achievement> achievements = parser.load(new URI("testachievements.xml"));
		assertThat(Boolean.valueOf(achievements.isEmpty()), is(Boolean.FALSE));
	}
	@Test
	public void testDataLoadsIntoAchievementClass() throws URISyntaxException, SAXException {
		AchievementXMLParser parser = new AchievementXMLParser();
		List<Achievement> achievements = parser.load(new URI("testachievements.xml"));
		Achievement achievement = achievements.get(0);
		assertThat(achievement.getCategory(), is(String.valueOf(""))); // Category.SOMETHING 
		assertThat(achievement.getIdentifier(), is(String.valueOf(""))); // "SOMETHING"
		assertThat(achievement.getTitle(), is(String.valueOf("")));// "SOMETHING"
		assertThat(achievement.getDescription(), is(String.valueOf(""))); // "SOMETHING"
		assertThat(achievement.getBaseScore(), is(Integer.valueOf(0))); // SOME VALUE
		assertThat(achievement.isActive(), is(Boolean.FALSE)); // SOME BOOLEAN
	}
	
}
