package games.stendhal.server.maps.deniran;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;
import java.util.Map;

/**
 * It holds the implementation for
 * Popular Java lecturer, Dr Lon Jatham, has been head-hunted 
 * by the new Deniran Insitute of Technology to set up a new School of Computer Science.
 * He needs your help in recruiting potential students.
 * 
 * Initial release
 * @author armandanusca
 *
 */
public class LonJathamNPC implements ZoneConfigurator {

	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 */
	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		
		buildLonJatham(zone);
	}
	
	/**
	 * Method for building the NPC
	 * @param zone
	 */
	private void buildLonJatham(final StendhalRPZone zone) {
		
	    final SpeakerNPC lonJatham = new SpeakerNPC("Lon Jatham") {
	    
	    @Override
	    protected void createPath() {
	    	
	       // NPC does not move
	       setPath(null);
	    }
	    
	    @Override
        protected void createDialog() {
	    	
            // His specific greeting 
            addGreeting("Good morning ! Are you ready for a lecture ?");
            
            // Lets the NPC reply when a player asks for help
            addHelp("Ask me anything about the Deniran Institute of Tehnology and I will try to answer if I don't get lost in my thoughts.");
            
            // Respond about a special trigger word for the specific questions 
            // needed for the quest, asked by NPCs who will become students
            addReply("question","Ask me about the School of Computer Science.");
            addReply("Python","You will learn and use Python for different courses, including 1st year Fundamentals of AI and 2nd year Machine Learning ");
            addReply("AI","In the first year you will study the Fundamentals of AI, while in the 2nd year you can choose Symbolic AI and Machine Learning as optional courses");
            addReply("Computer Science with Business Management","We no longer have a Computer Science with Business Management programme");
            
            // His specific goodbye
            addGoodbye("That's all for today, see you next time, whenever that will be !");
        }
    };

    // This determines how the NPC will look like 
    lonJatham.setEntityClass("lonjatham");
    
    // Description for when a player does 'Look' to get to know Lon
    lonJatham.setDescription("You see the most popular Java lecturer that this world ever saw, Dr Lon Jatham, come and say hi, if you want to have a great chat.");
    
    // Set the initial position of Lon
    lonJatham.setPosition(19, 33);
    
    // Set the HP on a random value because NPCs can't be attacked or killed
    lonJatham.initHP(69);

    // Add the NPC to the map
    zone.add(lonJatham);
   }
}