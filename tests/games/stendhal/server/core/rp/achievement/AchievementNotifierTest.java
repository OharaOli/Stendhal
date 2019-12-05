package games.stendhal.server.core.rp.achievement;
import static org.junit.Assert.*;



import java.util.List;


import org.junit.Before;
import org.junit.Test;


//import games.stendhal.server.entity.npc.condition.LevelGreaterThanCondition;
import games.stendhal.server.entity.npc.condition.PlayerHasKilledNumberOfCreaturesCondition;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.core.rp.achievement.Category;
import games.stendhal.server.events.ReachedAchievementEvent;
import marauroa.common.game.RPEvent;
import utilities.PlayerTestHelper;

/**
 * Testing suite for {@link AchievementNotifier}
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
		player = PlayerTestHelper.createPlayer("player");
		player.initReachedAchievements();
		achievementNotifier.initialize();
	}
	
	@Test
	/*
	 * test to check if awardAchievementIfNotYetReached function 
	 * works as intended
	 */
	public void awardAchievementIfNotYetReachedTest()
	{
		achievementNotifier.awardAchievementIfNotYetReached(player,"fight.general.rats");
		List<RPEvent> events = player.events();
		Achievement fakeAchievement = new Achievement("fight.general.rats", "Rat Hunter",Category.EXPERIENCE, "Kill 15 rats", Achievement.EASY_BASE_SCORE, true,
				new PlayerHasKilledNumberOfCreaturesCondition("rat", 15));
		RPEvent actualEvent = new ReachedAchievementEvent(fakeAchievement);
		assertTrue(events.contains(actualEvent));
	}
	
	
	@Test
	/*
	 * test to check if onlogin functionality works
	 */
	public void onLoginTest()
	{
		achievementNotifier.awardAchievementIfNotYetReached(player,"Greenhorn");

		achievementNotifier.onLogin(player);
		List<RPEvent> events = player.events();
		String message = "You have reached ";
		boolean found = false;
		for(RPEvent event : events)
		{
			if(event.get("text").contains(message))
				found = true;
		}
		assertTrue(found);
	}
	
	@Test
	/*
	 * test to check if achievement is awarded on correct number of kills
	 */
	public void onKillTest()
	{
		Achievement killAchievement = new Achievement("fight.general.rats", "Rat Hunter",Category.EXPERIENCE, "Kill 15 rats", Achievement.EASY_BASE_SCORE, true,
				new PlayerHasKilledNumberOfCreaturesCondition("rat", 15));
		RPEvent actualEvent = new ReachedAchievementEvent(killAchievement);
		player.setSoloKillCount("rat", 15);
		achievementNotifier.onKill(player);
		assertTrue(player.events().contains(actualEvent));
	}
	
}
