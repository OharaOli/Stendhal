/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2011 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.npc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;
import games.stendhal.server.maps.deniran.FurnitureSellerNPC;

/**
 * Test speaking to the NPC found outside the new furniture shop.
 * @author Oliver O'Hara
 */
public class FurnitureSellerNPCTest extends ZonePlayerAndNPCTestImpl {

	private static final String ZONE_NAME = "deniran_w";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}

	public FurnitureSellerNPCTest() {
		//Needs changing
		setNpcNames("Bob");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new FurnitureSellerNPC(), ZONE_NAME);
	}

	/**
	 * Tests for hiAndBye.
	 */
	@Test
	public void testHiAndBye() {
		final SpeakerNPC npc = getNPC("Bob");
		final Engine en = npc.getEngine();

		assertTrue(en.step(player, "hi Bob"));
		assertEquals("Hi! This is my new furniture shop, it isn't quite ready yet but it should be open soon", getReply(npc));

		assertTrue(en.step(player, "bye"));
		assertEquals("Come back soon!", getReply(npc));
	}

}
