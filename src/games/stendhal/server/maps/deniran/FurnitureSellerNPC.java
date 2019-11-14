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
package games.stendhal.server.maps.deniran;

import java.util.Map;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.CollisionAction;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.behaviour.impl.TeleporterBehaviour;

/**
 * Builds a FurnitureSellerNPC to go outside the new shop.
 *
 * @author Oliver O'Hara
 */
public class FurnitureSellerNPC implements ZoneConfigurator {
	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
            //Needs to change to zone
        	new TeleporterBehaviour(buildFurnitureStoreArea(), null, "0", "Furniture, coming soon!");
	}
    //Change to zone
	private SpeakerNPC buildFurnitureStoreArea() {

	    final SpeakerNPC bob = new SpeakerNPC("Bob") {
	                @Override
			protected void createPath() {
				// npc does not move
				setPath(null);
			}
	        @Override
	        protected void createDialog() {
	    		addGreeting("Hi! This is my new furniture shop, it isn't quite ready yet but it should be open soon");
	    		addJob("I'm the owner of the new furniture shop");
	    		addGoodbye("Come back soon!");
	    	}
		};

		bob.setEntityClass("beardmannpc");
		bob.initHP(100);
		bob.setCollisionAction(CollisionAction.REVERSE);
		bob.setDescription("You see Bob. He's opening the new furniture store in town soon.");

		final StendhalRPZone zone = SingletonRepository.getRPWorld().getZone("deniran_w");
		bob.setPosition(94, 30);
		zone.add(bob);

		return bob;
	}
}
