/* $Id$ */
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
package games.stendhal.server.entity.mapstuff.useable;


//import org.apache.log4j.Logger;

import games.stendhal.common.Rand;
import games.stendhal.common.constants.SoundLayer;
//import games.stendhal.common.grammar.Grammar;
//import games.stendhal.server.core.engine.SingletonRepository;
//import games.stendhal.server.core.events.TurnListener;
//import games.stendhal.server.core.events.TurnNotifier;
//import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.events.SoundEvent;
import marauroa.common.game.RPClass;

/**
   A bed on which a player can sleep
 * @author Vlad Cojocaru
 */
public class SleepingBed extends PlayerActivityEntity {
	//private static final Logger logger = Logger.getLogger(SleepingBed.class);

	/**
	 * The equipment needed.
	 */
	
	public static void generateRPClass() {
		final RPClass rpclass = new RPClass("sleeping_bed");
		rpclass.isA("entity");
	}
	/**
	 * The name of the item to be found.
	 */
	

	/**
	 * Sound effects
	 */
	private final String startSound = "pick-metallic-1";
	private final String successSound = "rocks-1";
	private final int SOUND_RADIUS = 20;
    private boolean bed_is_used = false;
    
	/**
	 * Create a gold source.
	 */
	//public SleepingBed() {
		//this("coal");
	//}

	/**
	 * Create a coal source.
	 *
	 * @param itemName
	 *            The name of the item to be prospected.
	 */
	public SleepingBed() {
		

		setRPClass("useable_entity");
		put("type", "useable_entity");
		put("class", "source");
		put("name", "sleeping_bed");
		put("state", 0);
        set_bed_class();
		setMenu("Sleep|Use");
		setDescription("You see a sleeping bed");
	
	}
	
	

	/**
	 * source name.
	 */
	@Override
	public String getName() {
		return("the bed");
	}


	//
	// PlayerActivityEntity
	//

	/**
	 * Get the time it takes to perform this activity.
	 *
	 * @return The time to perform the activity (in seconds).
	 */
	@Override
	protected int getDuration() {
		return 7 + Rand.rand(4);
	}
    
	/**
	 * Decides if the activity can be done.
	 *
	 * @return <code>true</code> if successful.
	 */
	@Override
	protected boolean isPrepared(final Player player) {
		if (!bed_is_used) {
			return true;
		}
		
        return false;
	}

	/**
	 * Decides if the activity was successful.
	 *
	 * @return <code>true</code> if successful.
	 */
	@Override
	protected boolean isSuccessful(final Player player) {
		return getState() > 0;
	}

	/**
	 * Called when the activity has finished.
	 *
	 * @param player
	 *            The player that did the activity.
	 * @param successful
	 *            If the activity was successful.
	 */
	@Override
	protected void onFinished(final Player player, final boolean successful) {
		if (successful) {
	        addEvent(new SoundEvent(successSound, SOUND_RADIUS, 100, SoundLayer.AMBIENT_SOUND));
	        notifyWorldAboutChanges();
	   
		}
		player.set_bed_status();
		player.sendPrivateText("You are no longer sleeping");
		super.set_bed_used();
		bed_is_used = false;
		notifyWorldAboutChanges();
		
	}

	
	/**
	 * Called when the activity has started.
	 *
	 * @param player
	 *            The player starting the activity.
	 */
	@Override
	protected void onStarted(final Player player) {
		player.sendPrivateText("You are sleeping");
		player.set_bed_status();
		super.set_bed_used();
		bed_is_used = true;
        addEvent(new SoundEvent(startSound, SOUND_RADIUS, 100, SoundLayer.AMBIENT_SOUND));
        notifyWorldAboutChanges();
	}

	

	
}
