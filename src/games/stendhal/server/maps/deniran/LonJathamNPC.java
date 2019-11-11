package games.stendhal.server.maps.deniran;

import java.util.HashMap;
import java.util.Map;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.SpeakerNPC;


public class LonJathamNPC implements ZoneConfigurator {
	
	@Override
	public void configureZone(final StendhalRPZone zone,
			final Map<String, String> attributes) {
		buildNPC(zone);
	}
	
	private void buildNPC(final StendhalRPZone zone) {
		final SpeakerNPC lonjathamnpc = new SpeakerNPC("Lon Jatham") {

			@Override
			protected void createPath() {
				setPath(null);
			}
	};
	}
	

}
