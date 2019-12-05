package games.stendhal.server.core.rp.achievement;
import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import games.stendhal.server.entity.npc.condition.QuestStateGreaterThanCondition;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.core.rp.achievement.Category;

import games.stendhal.server.maps.MockStendlRPWorld;

import utilities.PlayerTestHelper;

public class AchievementTest {

	private Player playerA;
	
	
	@BeforeClass
	public void setUpBeforeClass()
	{
		MockStendlRPWorld.get();
	}
	
	@Before
	public void setUp()
	{
		playerA = PlayerTestHelper.createPlayer("player");
		playerA.initReachedAchievements();
	}
	
	@Test
	public void AchievementCreationTest()
	{
		String identifier = "identifier";
		String title = "title";
		Category category = Category.EXPERIENCE;
		String description = "description";
		int baseScore = 2;
		boolean active = true;
		String tostring = "Achievement<id: " + identifier + ", title: " + title+">";
		QuestStateGreaterThanCondition condition = new QuestStateGreaterThanCondition("daily_item",2,499);
		Achievement testAchievement = new Achievement(identifier, title, category, description, baseScore,active,condition);
		assertEquals(identifier,testAchievement.getIdentifier());
		assertEquals(title,testAchievement.getTitle());
		assertEquals(category,testAchievement.getCategory());
		assertEquals(description,testAchievement.getDescription());
		assertEquals(baseScore,testAchievement.getBaseScore());
		assertEquals(active,testAchievement.isActive());
		assertEquals(tostring,testAchievement.toString());
	}
}
