package games.stendhal.server.maps.ados.gnomevillage;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.npc.ShopList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.behaviour.adder.BuyerAdder;
import games.stendhal.server.entity.npc.behaviour.impl.BuyerBehaviour;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Inside Gnome Village.
 */
public class GarbiddleNPC implements ZoneConfigurator {
    private ShopList shops = SingletonRepository.getShopList();

	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 */
	public void configureZone(StendhalRPZone zone, Map<String, String> attributes) {
		buildgarbiddle(zone);
	}

	private void buildgarbiddle(StendhalRPZone zone) {
		SpeakerNPC garbiddle = new SpeakerNPC("Garbiddle") {

			@Override
			protected void createPath() {
				List<Node> nodes = new LinkedList<Node>();
				nodes.add(new Node(94, 100));
				nodes.add(new Node(101, 100));
				setPath(new FixedPath(nodes, true));

			}

			@Override
			protected void createDialog() {
				addGreeting("Welcome to our wonderful village.");
				addJob("I'm here to buy supplies for a rainy day.");
				addHelp("I buy several things.  Please read the sign to see what we need.");
				addOffer("Read the sign to see what we need.");
				addQuest("Thanks for asking, but I am fine.");
				addGoodbye("Bye now. So glad you stopped in to visit us.");
 				new BuyerAdder().add(this, new BuyerBehaviour(shops.get("buy4gnomes")), false);
			}
		};

		garbiddle.setEntityClass("gnomenpc");
		garbiddle.setPosition(94, 100);
		garbiddle.initHP(100);
		zone.add(garbiddle);
	}
}
