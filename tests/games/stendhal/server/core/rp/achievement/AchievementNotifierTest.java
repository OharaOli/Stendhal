package games.stendhal.server.core.rp.achievement;
import static org.junit.Assert.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;


import games.stendhal.server.entity.npc.condition.LevelGreaterThanCondition;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.core.rp.achievement.Category;
import games.stendhal.server.events.ReachedAchievementEvent;
import marauroa.common.game.RPEvent;
import utilities.PlayerTestHelper;

/**
 * Testing suite for {@link AchievementNotifier}}
 * 
 * Testing all the methods from achievement notifier 
 * @author q19754wk
 *	
 */

public class AchievementNotifierTest {

	private final AchievementNotifier achievementNotifier = AchievementNotifier.get();
	private Player player;
	
	
	
	@Before
	public void setup()
	{
		//StendhalRPZone zone = new StendhalRPZone("admin_test");
		player = PlayerTestHelper.createPlayer("player");
	}
	
	@Test
	public void awardAchievementIfNotYetReachedTest()
	{
		achievementNotifier.awardAchievementIfNotYetReached(player,"achievement");
		List<RPEvent> events = player.events();
		Achievement fakeAchievement = new Achievement("fake","fake-ach",Category.EXPERIENCE,
													  "fake achievement",0,true,new LevelGreaterThanCondition(0));
		RPEvent actualEvent = new ReachedAchievementEvent(fakeAchievement);
		for(RPEvent currentEvent : events)
		{
			assertEquals(actualEvent,currentEvent);
		}
	}
	
	@Test
	public void createAchievementsTest()
	{
		Map<String, Achievement> expectedListOfAchievements = new HashMap<>();
		Map<String, Achievement> resultedMapAchievements = achievementNotifier.createAchievements();
		assertEquals(expectedListOfAchievements,resultedMapAchievements);
	}
	
	@Test
	public void onLoginTest()
	{
		achievementNotifier.awardAchievementIfNotYetReached(player,"Greenhorn");

		achievementNotifier.onLogin(player);
		assertEquals(player.getLastPrivateChatter(),"database");
	}
	
	
}
