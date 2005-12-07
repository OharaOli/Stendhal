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
package games.stendhal.server;

import games.stendhal.server.entity.*;
import games.stendhal.server.entity.creature.Creature;
import games.stendhal.server.entity.creature.Sheep;
import games.stendhal.server.entity.item.*;
import games.stendhal.server.entity.npc.NPC;
import games.stendhal.server.rule.RuleManager;
import games.stendhal.server.rule.RuleSetFactory;
import games.stendhal.server.maps.IContent;
import marauroa.common.Log4J;
import marauroa.common.game.RPClass;
import marauroa.server.game.RPWorld;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FilenameFilter;

public class StendhalRPWorld extends RPWorld
  {
  /** the logger instance. */
  private static final Logger logger = Log4J.getLogger(StendhalRPWorld.class);
  
  /** The pathfinder thread.*/
  private PathfinderThread pathfinderThread;
  
  /** The rule system manager */
  private RuleManager ruleManager;
  
  public StendhalRPWorld() throws Exception
    {
    super();

    Log4J.startMethod(logger,"StendhalRPWorld");
    createRPClasses();
    ruleManager = RuleSetFactory.getRuleSet("default");
    Log4J.finishMethod(logger,"StendhalRPWorld");
    }
  
  /** 
   * Returns the pathfinder. The return value is undefined until onInit() is
   * called.
   * @return the pathfinder
   */
  public PathfinderThread getPathfinder()
  {
    return pathfinderThread;
  }
  
  /** 
   * checks if the pathfinder thread is still alive. If it is not, it is 
   * restarted.
   */
  public void checkPathfinder()
  {
    if (pathfinderThread == null || !pathfinderThread.isAlive())
    {
      logger.fatal("Pathfinderthread died");
      pathfinderThread = new PathfinderThread(this);
      pathfinderThread.start();
    }
  }


  /** returns the current rulemanager. */
  public RuleManager getRuleManager()
  {
    return ruleManager;
  }
  
  private void createRPClasses()
    {
    Log4J.startMethod(logger,"createRPClasses");
    
    Entity.generateRPClass();

    Sign.generateRPClass();
    Portal.generateRPClass();
    Door.generateRPClass();
    SheepFood.generateRPClass();
    Corpse.generateRPClass();
    Item.generateRPClass();
    Chest.generateRPClass();
        
    RPEntity.generateRPClass();
    
    NPC.generateRPClass();

    Creature.generateRPClass();
    Sheep.generateRPClass();

    Player.generateRPClass();
    
    RPClass chatAction=new RPClass("chat");
    chatAction.add("text",RPClass.LONG_STRING);
    
        
    Log4J.finishMethod(logger,"createRPClasses");
    }
    
  public void onInit() throws Exception
    {
    // create the pathfinder thread and start it
    pathfinderThread = new PathfinderThread(this);
    pathfinderThread.start();
   
    // Load zones
    addArea("0_semos_village");
    addArea("0_semos_city");
    addArea("0_semos_road_ados");

    addArea("0_semos_south_plains");

    addArea("0_semos_forest");

    addArea("0_orril_castle");
    addArea("0_orril_forest");
    addArea("0_nalwor_forest");

    addArea("0_orril_river");
    addArea("0_orril_forest_river");

    addArea("-1_semos_dungeon");

    addArea("-2_semos_dungeon");

    addArea("int_afterlife");
    addArea("int_semos_tavern");


//    // Populate zones
//    File questsFolder=new File("games/stendhal/server/maps");
//    String[] files=questsFolder.list(new FilenameFilter()
//      {
//      public boolean accept(File dir, String name)
//        {
//        return name.contains(".class");
//        }
//      });
//    
//    if(files == null)
//      {
//      logger.error("Maps folder not found. should be "+questsFolder.getAbsolutePath());
//      return;
//      }
//    
//    for(String file: files)
//      {
//      String className=file.substring(0,file.indexOf("."));
//      if(!className.equals("IContent"))
//        {
//        populateZone(className);
//        }
//      }
    populateZone("Afterlife");
    populateZone("Semos");
    }
  
  private boolean populateZone(String name)
    {
    try
      {
      Class entityClass=Class.forName("games.stendhal.server.maps."+name);
      
      boolean implementsIContent=false;
      
      Class[] interfaces=entityClass.getInterfaces();
      for(Class interf: interfaces)
        {
        if(interf.equals(IContent.class))
          {
          implementsIContent=true;
          break;
          }        
        }
      
      if(implementsIContent==false)
        {
        logger.debug("Class don't implement IContent interface.");
        return false;
        }
      
      logger.info("Loading Zone populate class: "+name);
      java.lang.reflect.Constructor constr=entityClass.getConstructor(StendhalRPWorld.class);

      // simply creatre a new instance. The constructor creates all additionally objects  
      constr.newInstance(this);
      return true;
      }
    catch(Exception e)
      {
      logger.warn("Zone Populate class("+name+") loading failed.",e);
      return false;
      }
    }

  public StendhalRPZone addArea(String name) throws org.xml.sax.SAXException, java.io.IOException
    {
    return addArea(name,name.replace("-","sub_"));
    }
    
  public StendhalRPZone addArea(String name, String content) throws org.xml.sax.SAXException, java.io.IOException
    {
    StendhalRPZone area=new StendhalRPZone(name, this);
    
    ZoneXMLLoader instance=ZoneXMLLoader.get();
    ZoneXMLLoader.XMLZone xmlzone=instance.load("games/stendhal/server/maps/"+content+".xstend");
    
    area.addLayer(name+"_0_floor",xmlzone.getLayer("0_floor"));
    area.addLayer(name+"_1_terrain",xmlzone.getLayer("1_terrain"));
    area.addLayer(name+"_2_object",xmlzone.getLayer("2_object"));
    area.addLayer(name+"_3_roof",xmlzone.getLayer("3_roof"));
    
    area.addCollisionLayer(name+"_collision",xmlzone.getLayer("collision"));
    area.addNavigationLayer(name+"_navigation",xmlzone.getLayer("navigation"));
    
    if(xmlzone.isInterior())
      {
      area.setPosition();
      }
    else
      {
      area.setPosition(xmlzone.getLevel(),xmlzone.getx(),xmlzone.gety());
      }
    
    area.populate(xmlzone.getLayer("objects"));
    addRPZone(area);
    
//    if(populate)
//      {
//      try
//        {
//        Class entityClass=Class.forName("games.stendhal.server.maps.Zone_"+name.replace("-","sub_"));
//        java.lang.reflect.Constructor constr=entityClass.getConstructor(StendhalRPWorld.class, StendhalRPZone.class);
//  
//        // simply creatre a new instance. The constructor creates all additionally objects  
//        constr.newInstance(this, area);
//        }
//      catch(ClassNotFoundException e)
//        {
//        logger.debug("Zone '"+name+"' doesn't have an extra populate method");
//        }
//      catch(Exception e)
//        {
//        logger.info("Zone '"+name+"' fails to load extra populate method",e);
//        }
//      }

    return area;
    }
    
  /** Creates a new house and add it to the zone.
   *  num is the unique idenfier for portals 
   *  x and y are the position of the door of the house. 
   */
  public void createHouse(int num, StendhalRPZone zone, int x, int y) throws org.xml.sax.SAXException, java.io.IOException
    {
    String name="int_"+zone.getID().getID()+"_house_"+Integer.toString(num);
    
    Door door=new Door();
    door.setx(x);
    door.sety(y);
    door.setNumber(num);
    door.setDestination(name,0);
    zone.assignRPObjectID(door);
    zone.add(door);
    zone.addPortal(door);
    
    StendhalRPZone house=addArea(name,"int_house_000");
    Portal portal=new Portal();
    portal.setDestination(zone.getID().getID(),num);
    portal.setx(7);
    portal.sety(1);
    portal.setNumber(0);
    house.assignRPObjectID(portal);
    house.add(portal);
    house.addPortal(portal);    
    }

  public void onFinish() throws Exception
    {
    }
   
  }
