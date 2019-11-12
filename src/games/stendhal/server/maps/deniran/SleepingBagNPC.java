package games.stendhal.server.maps.deniran;

import games.stendhal.server.core.config.ZoneConfigurator;

import games.stendhal.server.core.engine.StendhalRPZone;
//import games.stendhal.server.core.pathfinder.FixedPath;
//import games.stendhal.server.core.pathfinder.Node;
// this one is just because our NPC is a seller

import games.stendhal.server.entity.npc.SpeakerNPC;
// this one is just because our NPC is a seller
//import games.stendhal.server.entity.npc.behaviour.adder.SellerAdder;
// this one is just because our NPC is a seller
//import games.stendhal.server.entity.npc.behaviour.impl.SellerBehaviour;

//import java.util.Arrays;
//import java.util.LinkedList;
//import java.util.List;
import java.util.Map;

public class SleepingBagNPC implements ZoneConfigurator {

	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 */
	
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildNPC(zone);
	}
	
	private void buildNPC(final StendhalRPZone zone) {
	    final SpeakerNPC npc = new SpeakerNPC("Sleeping Bag Seller") {
	    	
	    	protected void createPath() {
	            // NPC does not move
	            setPath(null);
	        }

        protected void createDialog() {
            // Lets the NPC reply with "Hello" when a player greets him. But we could have set a custom greeting inside the ()
            addGreeting("Hello fellow tired traveller");
            // Lets the NPC reply when a player says "job"
            addJob("I sell sleeping bags but currently have none in stock. You can use the bed here but it is magical and kinda wonky (WIP).");
            // Lets the NPC reply when a player asks for help
            addHelp("Can't help you right now");
            
            
            // use standard goodbye, but you can also set one inside the ()
            addGoodbye();
        }
    };

    // This determines how the NPC will look like. welcomernpc.png is a picture in data/sprites/npc/
    npc.setEntityClass("welcomernpc");
    // set a description for when a player does 'Look'
    npc.setDescription("You see a sleeping bag seller.");
    // Set the initial position to be the first node on the Path you defined above.
    npc.setPosition(7, 15);
    npc.initHP(100);

    zone.add(npc);   
}
}
	
	
