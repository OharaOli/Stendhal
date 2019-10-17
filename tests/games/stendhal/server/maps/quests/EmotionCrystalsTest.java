package games.stendhal.server.maps.quests;

import static org.junit.Assert.assertEquals;
import static utilities.SpeakerNPCTestHelper.getReply;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.maps.ados.snake_pit.PurpleCrystalNPC;
import games.stendhal.server.maps.ados.wall.GreeterSoldierNPC;
import games.stendhal.server.maps.fado.hut.BlueCrystalNPC;
import games.stendhal.server.maps.nalwor.forest.RedCrystalNPC;
import games.stendhal.server.maps.nalwor.river.PinkCrystalNPC;
import games.stendhal.server.maps.semos.mountain.YellowCrystalNPC;
//import utilities.PlayerTestHelper;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

/**
 * Test for emotion crystal quest log
 *
 *@author Oliver O'Hara
 */
public class EmotionCrystalsTest extends ZonePlayerAndNPCTestImpl
{
	private static final String QUEST_SLOT = "emotion_crystals";
	private static final String ZONE_NAME = "admin_test";
	private EmotionCrystals myEmotionCrystalQuest = new EmotionCrystals();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}
	
	//Configure the zone to contain crystals and npcs that will be used
	public EmotionCrystalsTest()
	{
		setNpcNames("Julius", "Blue Crystal", "Yellow Crystal", "Red Crystal", "Purple Crystal", "Pink Crystal");
		
		setZoneForPlayer(ZONE_NAME);
		
		addZoneConfigurator(new GreeterSoldierNPC(), ZONE_NAME);
		addZoneConfigurator(new BlueCrystalNPC(), ZONE_NAME);
		addZoneConfigurator(new YellowCrystalNPC(), ZONE_NAME);
		addZoneConfigurator(new RedCrystalNPC(), ZONE_NAME);
		addZoneConfigurator(new PurpleCrystalNPC(), ZONE_NAME);
		addZoneConfigurator(new PinkCrystalNPC(), ZONE_NAME);
	}
	
	@Before
	@Override
	public void setUp() throws Exception
	{
		super.setUp();
		myEmotionCrystalQuest.addToWorld();
	}
	
	//Start the quest by talking to Julius
	@Test
	public void testStartQuest()
	{
		SpeakerNPC npc = SingletonRepository.getNPCList().get("Julius");
		Engine en = npc.getEngine();
		
		en.step(player, "hi");
		en.step(player, "quest");
		assertEquals("I don't get to see my wife very often because I am so busy guarding this entrance. I would like to do something for her. Would you help me?", getReply(npc));
		en.step(player, "ok");
		assertEquals("Thank you. I would like to gather five #emotion #crystals as a gift for my wife. Please find all that you can and bring them to me.", getReply(npc));
		en.step(player, "bye");		
	}
	
	/**
	 * Tests for quest.
	 */
	@Test
	public void testQuest()
	{
		player.setQuest(QUEST_SLOT, "start");
		
		SpeakerNPC npc = SingletonRepository.getNPCList().get("Blue Crystal");
		Engine en = npc.getEngine();
		
		//Complete the part of the quest to award the blue emotion crystal
		en.step(player, "hi");
		assertEquals("Nice to meet you! This hut here is lovely.", getReply(npc));
		en.step(player, "riddle");
		assertEquals("I do not let things bother me. I never get overly energetic. Meditation is my forte. What am I?", getReply(npc));
		en.step(player, "peace");
		//PlayerTestHelper.equipWithItem(player, "blue emotion crystal");
		assertEquals(player.isEquipped("blue emotion crystal"), true);
		
		//Check that the log reflects this change
		List<String> res = myEmotionCrystalQuest.getHistory(player);
		assertEquals("I currently have the following crystals: blue emotion crystal", res.get(res.size() - 2));
		assertEquals("I have previously found the following crystals: blue emotion crystal", res.get(res.size() - 1));
		
		//Drop the crystal
		player.drop("blue emotion crystal", 1);
		
		//Check that the log reflects this change
		res = myEmotionCrystalQuest.getHistory(player);
		assertEquals("I don't currently have any crystals but I have previously found the following crystals: blue emotion crystal", res.get(res.size() - 1));
	}
}