package games.stendhal.server.core.config;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.xml.sax.helpers.DefaultHandler;

import games.stendhal.server.core.rp.achievement.Achievement;

public final class AchievementXMLLoader extends DefaultHandler {

	public List<Achievement> load(final URI uri){
		List<Achievement> list = new LinkedList<>();
		// Use the default (non-validating) parser
		// Insert the parser code here
		
		return list;
	}

}
