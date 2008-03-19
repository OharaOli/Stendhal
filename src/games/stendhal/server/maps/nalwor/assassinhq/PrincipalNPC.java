package games.stendhal.server.maps.nalwor.assassinhq;

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
 * Inside Nalwor Assassin Headquarters - Level 0 .
 */
public class PrincipalNPC implements ZoneConfigurator {
    private ShopList shops = SingletonRepository.getShopList();

	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 */
	public void configureZone(StendhalRPZone zone, Map<String, String> attributes) {
		buildprincipal(zone);
	}

	private void buildprincipal(StendhalRPZone zone) {
		SpeakerNPC principal = new SpeakerNPC("Femme Fatale") {

			@Override
			protected void createPath() {
				List<Node> nodes = new LinkedList<Node>();
				nodes.add(new Node(2, 18));
				nodes.add(new Node(2, 14));
				nodes.add(new Node(3, 14));
				nodes.add(new Node(3, 18));
				setPath(new FixedPath(nodes, true));

			}

			@Override
			protected void createDialog() {
				addGreeting();
				addJob("It is my job to keep all my little guys armed and equipped. Please help me.");
				addHelp("I can buy your surplus items.  Please see blackboard on wall for what i need.");
				addOffer("Look at blackboard on wall to see my offer.");
				addQuest("Other than selling me what I need, I don't require anything from you.");
				addGoodbye();
 				new BuyerAdder().add(this, new BuyerBehaviour(shops.get("buy4assassins")), false);
			}
		};

		principal.setEntityClass("principalnpc");
		principal.setPosition(2, 18);
		principal.initHP(100);
		zone.add(principal);
	}
}
