package games.stendhal.server.entity.mapstuff.useable;

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
import static org.junit.Assert.*;

import org.junit.Test;
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
		SleepingBed entity = new SleepingBed();

		assertEquals("Description", "You see a sleeping bed",
				entity.describe());
	}

	/**
	 * Test trying to use the entity from too far away.
	 */
	@Test
	public void testUseFromTooFar() {
		SleepingBed entity = new SleepingBed();
		Player player = PlayerTestHelper.createPlayer("player");
		player.setPosition(1, 2);
		StendhalRPZone zone = new StendhalRPZone("testzone");
		zone.add(entity);
		zone.add(player);

		entity.onUsed(player);
		assertEquals(player.events().size(), 1);
		RPEvent event = player.events().get(0);
		assertEquals("Correct event type", Events.PRIVATE_TEXT, event.getName());
		assertEquals("You are too far away from the bed, try to come closer.", event.get("text"));
	}
	/**
	 * Tests for onUsed.
	 */
	@Test
	public void testOnUsed() {
		
		
		SleepingBed entity = new SleepingBed();
		final Player player = PlayerTestHelper.createPlayer("bob");

		entity.onUsed(player);
		
		assertEquals("You are sleeping", player.events().get(0).get("text"));
		player.clearEvents();
		entity.onUsed(player);
		
		assertEquals("You are no longer sleeping", player.events().get(0).get("text"));
		player.clearEvents();
		
		final Player player2 = PlayerTestHelper.createPlayer("bob");

		entity.onUsed(player);
		entity.onUsed(player2);
		assertEquals("Bed is used by someone", player2.events().get(0).get("text"));
	}
	
}
