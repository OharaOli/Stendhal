package games.stendhal.server.maps.deniran;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.maps.deniran.LonJathamNPC;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

/**
 * Test suite for the Lon Jatham NPC
 * It tests every specific functionality of this NPC
 * @author armandanusca
 *
 */
public class LonJathamNPCTest extends ZonePlayerAndNPCTestImpl{
	
	private static final String ZONE_NAME = "0_deniran";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}
	
	public LonJathamNPCTest() {
		setNpcNames("Lon Jatham");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new LonJathamNPC(), ZONE_NAME);
	}
	
	@Test
	public void testHiAndBye() {
		final SpeakerNPC npc = getNPC("Lon Jatham");
		final Engine en = npc.getEngine();

		assertTrue(en.step(player, "Hi"));
		assertEquals("Good morning ! Are you ready for a lecture ?", getReply(npc));

		assertTrue(en.step(player, "Bye"));
		assertEquals("That's all for today, see you next time, whenever that will be !", getReply(npc));
	}
	
	/**
	 * Tests if Lon can answers all the questions needed
	 */
	@Test
	public void testQuestions() {
		
		// Getting the NPC
		final SpeakerNPC npc = getNPC("Lon Jatham");
		final Engine en = npc.getEngine();

		assertTrue(en.step(player, "Hi"));
		assertEquals("Good morning ! Are you ready for a lecture ?", getReply(npc));
		
		assertTrue(en.step(player, "Question"));
		assertEquals("Ask me about the School of Computer Science.", getReply(npc));
		
		assertTrue(en.step(player, "Python"));
		assertEquals("You will learn and use Python for different courses, including 1st year Fundamentals of AI and 2nd year Machine Learning ", getReply(npc));
		
		assertTrue(en.step(player, "Question"));
		assertEquals("Ask me about the School of Computer Science.", getReply(npc));
		
		assertTrue(en.step(player, "AI"));
		assertEquals("In the first year you will study the Fundamentals of AI, while in the 2nd year you can choose Symbolic AI and Machine Learning as optional courses", getReply(npc));
		
		assertTrue(en.step(player, "question"));
		assertEquals("Ask me about the School of Computer Science.", getReply(npc));
		
		assertTrue(en.step(player, "Computer Science with Business Management"));
		assertEquals("We no longer have a Computer Science with Business Management programme", getReply(npc));
		
		assertTrue(en.step(player, "Bye"));
		assertEquals("That's all for today, see you next time, whenever that will be !", getReply(npc));
			

	}

}
