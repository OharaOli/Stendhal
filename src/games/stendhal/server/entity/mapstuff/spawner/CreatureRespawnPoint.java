/* $Id$ */
/***************************************************************************
 *                      (C) Copyright 2003 - Marauroa                      *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.mapstuff.spawner;

import games.stendhal.common.Rand;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.events.TurnListener;
import games.stendhal.server.core.rp.StendhalRPAction;
import games.stendhal.server.entity.creature.Creature;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * RespawnPoints are points at which creatures can appear. Several creatures can
 * be spawned, until a maximum has been reached (note that this maximum is
 * usually 1); then the RespawnPoint will stop spawning creatures until at least
 * one of the creatures has died. It will then continue to spawn creatures. A
 * certain time must pass between respawning creatures; this respawn time is
 * usually dependent of the type of the creatures that are spawned.
 * 
 * Each respawn point can only spawn one type of creature. The Prototype design
 * pattern is used; the <i>prototypeCreature</i> will be copied to create new
 * creatures.
 */
public class CreatureRespawnPoint implements TurnListener {
	// half a year
	private static final int MAX_RESPAWN_TIME = 200 * 60 * 24 * 30 * 6;

	/** the logger instance. */
	private static final Logger logger = Logger.getLogger(CreatureRespawnPoint.class);

	private final StendhalRPZone zone;

	private final int x;

	private final int y;

	/**
	 * The number of creatures spawned here that can exist at the same time.
	 */
	private final int maximum;

	/**
	 * This is the prototype; it will be copied to create new creatures that
	 * will be spawned here.
	 */
	private final Creature prototypeCreature;

	/** All creatures that were spawned here and that are still alive. */
	private final List<Creature> creatures;

	/**
	 * Stores if this respawn point is currently waiting for a creature to
	 * respawn.
	 */
	private boolean respawning;

	/**
	 * How long it takes to respawn a creature. This defaults to the creature's
	 * default respawn time.
	 */
	private int respawnTime;

	/**
	 * Creates a new RespawnPoint.
	 * 
	 * @param zone
	 * @param x
	 * @param y
	 * @param creature
	 *            The prototype creature
	 * @param maximum
	 *            The number of creatures spawned here that can exist at the
	 *            same time
	 */
	public CreatureRespawnPoint(final StendhalRPZone zone, final int x, final int y,
			final Creature creature, final int maximum) {
		this.zone = zone;
		this.x = x;
		this.y = y;
		this.prototypeCreature = creature;
		this.maximum = maximum;

		this.respawnTime = creature.getRespawnTime();
		this.creatures = new LinkedList<Creature>();

		respawning = true;
		SingletonRepository.getTurnNotifier().notifyInTurns(calculateNextRespawnTurn(), this); // don't
																		// respawn
																		// in
																		// next
																		// turn!
	}

	public Creature getPrototypeCreature() {
		return prototypeCreature;
	}

	/**
	 * Sets the time it takes to respawn a creature. Note that this value
	 * defaults to the creature's default respawn time.
	 * @param respawnTime the middled delay between spawns 
	 */
	public void setRespawnTime(final int respawnTime) {
		this.respawnTime = respawnTime;
	}

	/**
	 * Notifies this respawn point about the death of a creature that was
	 * spawned here.
	 * 
	 * @param dead
	 *            The creature that has died
	 */
	public void notifyDead(final Creature dead) {

		if (!respawning) {
			// start respawning a new creature
			respawning = true;
			SingletonRepository.getTurnNotifier().notifyInTurns(
					calculateNextRespawnTurn(), this);
		}

		creatures.remove(dead);
	}

	/**
	 * Is called when a new creature is ready to pop up.
	 * 
	 * @see games.stendhal.server.core.events.TurnListener#onTurnReached(int)
	 */
	public void onTurnReached(final int currentTurn) {
		respawn();

		// Is this all or should we spawn more creatures?
		if (creatures.size() == maximum) {
			respawning = false;
		} else {
			
			SingletonRepository.getTurnNotifier().notifyInTurns(
					calculateNextRespawnTurn(), this);
		}
	}
	
	/**
	 * Calculates a randomized respawn time in the interval of [respawnTime/2,respawnTime + respawnTime/2] .
	 * @return the amount of turns calculated
	 */
	private int calculateNextRespawnTurn() {
		int time = Rand.randExponential(respawnTime);
		
		// limit to maximum allowed
		return Math.min(time, MAX_RESPAWN_TIME);
	}

	/**
	 * Checks how many creatures which were spawned here are currently alive.
	 * 
	 * @return amount of living creatures
	 */
	public int size() {
		return creatures.size();
	}

	/**
	 * Pops up a new creature.
	 */
	private void respawn() {

		try {
			// clone the prototype creature
			final Creature newentity = prototypeCreature.getInstance();

			// A bit of randomization to make Joan and Snaketails a bit happier.
			// :)
			newentity.setATK(Rand.randGaussian(newentity.getATK(),
					newentity.getATK() / 10));
			newentity.setDEF(Rand.randGaussian(newentity.getDEF(),
					newentity.getDEF() / 10));

			StendhalRPAction.placeat(zone, newentity, x, y);

			newentity.init();
			newentity.setRespawnPoint(this);

			creatures.add(newentity);
		} catch (final Exception e) {
			logger.error("error respawning entity " + prototypeCreature, e);
		}
	}

}
