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


public class LonJathamNPCTest extends ZonePlayerAndNPCTestImpl{
	
	private static final String ZONE_NAME = "int_deniram";
	
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

		assertTrue(en.step(player, "hi Lon Jatham"));
		assertEquals("Good afternoon! Welcome to the DIT School of Computer Science", getReply(npc));

		assertTrue(en.step(player, "bye"));
		assertEquals("See you at the next lecture", getReply(npc));
	}
	
	@Test
	public void testQuestions() {
		final SpeakerNPC npc = getNPC("Lon Jatham");
		final Engine en = npc.getEngine();

		assertTrue(en.step(player, "hi Lon Jatham"));
		assertEquals("Good afternoon! Welcome to the DIT School of Computer Science", getReply(npc));
		
		assertTrue(en.step(player, "question"));
		assertEquals("Ask me about the School of Computer Science", getReply(npc));
		
		assertTrue(en.step(player, "Python"));
		assertEquals("You will learn and use Python for different courses, including 1st year Fundamentals of AI and 2nd year Machine Learning ", getReply(npc));
		
		assertTrue(en.step(player, "question"));
		assertEquals("Ask me about the School of Computer Science", getReply(npc));
		
		assertTrue(en.step(player, "AI"));
		assertEquals("In the first year you will study the Fundamentals of AI, while in the 2nd year you can choose Symbolic AI and Machine Learning as optional courses", getReply(npc));
		
		assertTrue(en.step(player, "question"));
		assertEquals("Ask me about the School of Computer Science", getReply(npc));
		
		assertTrue(en.step(player, "Computer Science with Business Management"));
		assertEquals("We no longer have a Computer Science with Business Management programme", getReply(npc));
		
		assertTrue(en.step(player, "bye"));
		assertEquals("See you at the next lecture", getReply(npc));
			

	}

}
