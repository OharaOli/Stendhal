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
package games.stendhal.server.core.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import games.stendhal.server.core.rp.achievement.*;
import games.stendhal.server.core.rp.achievement.condition.QuestCountCompletedCondition;
import games.stendhal.server.core.rp.achievement.condition.QuestsInRegionCompletedCondition;



import games.stendhal.server.entity.npc.condition.*;




/* Author Vlad Cojocaru*/

public final class AchievementXMLLoader extends DefaultHandler {

	/** the logger instance. */
	private static final Logger LOGGER = Logger.getLogger(AchievementXMLLoader.class);

	
//---------------------
	private String identifier;
	private String title;
	private Category category;
	private String description;
	private int baseScore;
	private boolean active;
	
	private String text;
	
	private String conditionSTR;
	private List<String> parameters = new ArrayList<String>();
//-------------------------------	
	

	private List<Achievement> list;


//method tries to read an XML file and parse it process in which it will add Achievements to a list and return it
	public List<Achievement> load(final URI uri) throws SAXException {
		list = new LinkedList<Achievement>();
		// Use the default (non-validating) parser
		final SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			// Parse the input
			final SAXParser saxParser = factory.newSAXParser();

			final InputStream is = AchievementXMLLoader.class.getResourceAsStream(uri.getPath());

			if (is == null) {
				throw new FileNotFoundException("cannot find resource '" + uri
						+ "' in classpath");
			}
			try {
				saxParser.parse(is, this);
			} finally {
				is.close();
			}
		} catch (final ParserConfigurationException t) {
			LOGGER.error(t);
		} catch (final IOException e) {
			LOGGER.error(e);
			throw new SAXException(e);
		}

		return list;
	}

	@Override
	public void startDocument() {
		// do nothing
	}

	@Override
	public void endDocument() {
		// do nothing
	}
/*
	private String identifier;
	private String title;
	private Category category;
	private String description;
	private int baseScore;
	private boolean active;
	private ChatCondition condition;
	
	*/
	//method used my the SAX parser to check on start elements and their atributes
	@Override
	public void startElement(final String namespaceURI, final String lName, final String qName,
			final Attributes attrs) {
		text = "";
		System.out.println(qName);
		System.out.println(attrs.getValue("value"));
		if (qName.equals("identifier")) {
			identifier = attrs.getValue("value");
		}	
		
		else if (qName.equals("title")) {
			title = attrs.getValue("value");
		} 
		
		else if (qName.equals("score")) {
			String scoreSTR = attrs.getValue("value");
			if (scoreSTR.contentEquals("Achievement.EASY_BASE_SCORE"))
			  baseScore = Achievement.EASY_BASE_SCORE;
			else if (scoreSTR.contentEquals("Achievement.MEDIUM_BASE_SCORE"))
			  baseScore = Achievement.MEDIUM_BASE_SCORE;
			else
			  baseScore = Achievement.HARD_BASE_SCORE;
		}		
		
		else if (qName.equals("active")) {
			String activeSTR = attrs.getValue("value");
			if(activeSTR.equals("true"))
			  active = true;
			else
			  active = false;
		} 
		else if (qName.equals("description")) {
			description = attrs.getValue("value");
			
		} 
		
		else if (qName.equals("method")) {
			conditionSTR = attrs.getValue("value");
			
		} 
		
		else if (qName.equals("parameter")) {
			String currParameter = attrs.getValue("value");
			parameters.add(currParameter);
					
		}
		
		else if (qName.equals("category")) {
			String categorySTR = attrs.getValue("value");
			if(categorySTR.equals("EXPERIENCE"))
			    category = Category.EXPERIENCE;
			else if(categorySTR.equals("FIGHTING"))
			    category = Category.FIGHTING;
			else if(categorySTR.equals("QUEST"))
				category = Category.QUEST;
			else if(categorySTR.equals("OUTSIDE_ZONE"))
				category = Category.OUTSIDE_ZONE;
			else if(categorySTR.equals("UNDERGROUND_ZONE"))
				category = Category.UNDERGROUND_ZONE;
			else if(categorySTR.equals("INTERIOR_ZONE"))
				category = Category.INTERIOR_ZONE;
			else if(categorySTR.equals("AGE"))
				category = Category.AGE;
			else if(categorySTR.equals("ITEM"))
				category = Category.ITEM;
			else if(categorySTR.equals("OBTAIN"))
				category = Category.OBTAIN;
			else if(categorySTR.equals("FRIEND"))
				category = Category.FRIEND;
			else if(categorySTR.equals("PRODUCTION"))
				category = Category.PRODUCTION;
			else if(categorySTR.equals("QUEST_ADOS_ITEMS"))
				category = Category.QUEST_ADOS_ITEMS;
			else if(categorySTR.equals("QUEST_SEMOS_MONSTER"))
				category = Category.QUEST_SEMOS_MONSTER;
			else if(categorySTR.equals("QUEST_KIRDNEH_ITEM"))
				category = Category.QUEST_KIRDNEH_ITEM;
			else if(categorySTR.equals("QUEST_MITHRILBOURGH_ENEMY_ARMY"))
				category = Category.QUEST_MITHRILBOURGH_ENEMY_ARMY;
		
		} 
	
	}

	
	
	
	//method used my the SAX parser to check on end elements
	
	@Override
	public void endElement(final String namespaceURI, final String sName, final String qName) {
		System.out.println(qName);
		if (qName.equals("achievement")) {
			//Class<?> method = Class.forName(conditionSTR);
			final Achievement achievement;
			if(conditionSTR.equals("LevelGreaterThanCondition")) 
			{
				achievement = 
				new Achievement (identifier, title, category, description, baseScore, active, new LevelGreaterThanCondition(Integer.parseInt(parameters.get(0))));
			}
			else if(conditionSTR.equals("PlayerHasKilledNumberOfCreaturesCondition"))
			{	
				achievement = 
				new Achievement (identifier, title, category, description, baseScore, active, new PlayerHasKilledNumberOfCreaturesCondition(parameters.get(0),Integer.parseInt(parameters.get(1))));
			}
			else if(conditionSTR.equals("PlayerVisitedZonesInRegionCondition"))
			{	
				achievement = 
				new Achievement (identifier, title, category, description, baseScore, active, new PlayerVisitedZonesInRegionCondition(parameters.get(0),Boolean.parseBoolean(parameters.get(1)), Boolean.parseBoolean(parameters.get(2))));
			}
			else if(conditionSTR.equals("QuestStateGreaterThanCondition"))
			{	
				achievement = 
				new Achievement (identifier, title, category, description, baseScore, active, new QuestStateGreaterThanCondition(parameters.get(0),Integer.parseInt(parameters.get(1)), Integer.parseInt(parameters.get(2))));
			}
			
			
			
			else if(conditionSTR.equals("QuestsInRegionCompletedCondition"))
			{	
				achievement = 
				new Achievement (identifier, title, category, description, baseScore, active, new QuestsInRegionCompletedCondition(parameters.get(0)));
			}
			else if(conditionSTR.equals("QuestCountCompletedCondition"))
			{	
				achievement = 
				new Achievement (identifier, title, category, description, baseScore, active, new QuestCountCompletedCondition(Integer.parseInt(parameters.get(0))));
			}
			else 
			{	
				achievement = 
				new Achievement (identifier, title, category, description, baseScore, active, new LevelGreaterThanCondition(Integer.parseInt(parameters.get(0))));
			}

			list.add(achievement);
			parameters.clear();
		}
	}

	@Override
	public void characters(final char[] buf, final int offset, final int len) {
		text = text + (new String(buf, offset, len)).trim();
	}
}