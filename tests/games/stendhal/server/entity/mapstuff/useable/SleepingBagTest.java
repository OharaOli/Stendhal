package games.stendhal.server.entity.mapstuff.useable;

import static org.junit.Assert.*;

import org.junit.Test;

/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/


import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;


import games.stendhal.common.constants.Events;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendhalRPRuleProcessor;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import marauroa.common.game.RPEvent;
import utilities.PlayerTestHelper;


/**
 * Tests for the ViewChangeEntity
 */
public class SleepingBagTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Log4J.init();
		MockStendhalRPRuleProcessor.get();
		MockStendlRPWorld.get();
	}

	/**
	 * Test description string.
	 */
	@Test
	public void testDescribe() {
		UseableEntity entity = new SleepingBagT();

		assertEquals("Description", "You are too far",
				entity.describe());
	}

	/**
	 * Test trying to use the entity from too far away.
	 */
	@Test
	public void testUseFromTooFar() {
		UseableEntity entity = new SleepingBagT();
		Player player = PlayerTestHelper.createPlayer("spy");
		player.setPosition(1, 2);
		StendhalRPZone zone = new StendhalRPZone("testzone");
		zone.add(entity);
		zone.add(player);

		entity.onUsed(player);
		assertEquals(player.events().size(), 1);
		RPEvent event = player.events().get(0);
		assertEquals("Correct event type", Events.PRIVATE_TEXT, event.getName());
		assertEquals("You cannot reach that from here.", event.get("text"));
	}

	
}
