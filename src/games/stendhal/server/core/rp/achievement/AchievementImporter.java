package games.stendhal.server.core.rp.achievement;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import games.stendhal.server.core.config.AchievementGroupsXMLLoader;

/**
 * 
 * @author armandanusca
 *
 */
public class AchievementImporter {
	
	private static AchievementImporter instance;
	
	private List<Achievement> importedAchievements = new ArrayList<>();
	
	private static final Logger LOGGER = Logger.getLogger(AchievementImporter.class);
	
	private AchievementImporter() {
		
	}
	
	public static AchievementImporter get() {
    	synchronized(AchievementImporter.class) {
			if(instance == null) {
				instance = new AchievementImporter();
			}
    	}
		return instance;
	}
	
	public List<Achievement> getAchievements(){
		
		importAchievements();
		return importedAchievements;
	}
	
	public void importAchievements(){
		try {
			final AchievementGroupsXMLLoader loader = new AchievementGroupsXMLLoader(new URI("/data/conf/achievements.xml"));
			if(loader == null)
			{
				
				LOGGER.error("loader is null");
			}
		
			
			System.out.println("size " + loader.load().size());
			
			importedAchievements.addAll(loader.load());
		} catch (final Exception e) {
			LOGGER.error("achievement.xml could not be loaded", e);
			
		}
	}
	
	

}
