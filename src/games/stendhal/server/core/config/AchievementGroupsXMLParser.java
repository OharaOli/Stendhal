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

import java.io.IOException;

import java.net.URI;

import java.util.LinkedList;

import java.util.List;



import org.apache.log4j.Logger;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import games.stendhal.server.core.rp.achievement.*;





/**
 * Load and configure items via an XML configuration file.
 */
public class AchievementGroupsXMLParser extends DefaultHandler {


	private static final Logger LOGGER = Logger.getLogger(AchievementGroupsXMLParser.class);

	/** The main item configuration file. */
	protected URI uri;

	/**
	 * Create an xml based loader of item groups.
	 *
	 * @param uri
	 *            The location of the configuration file.
	 */
	public AchievementGroupsXMLParser(final URI uri) {
		this.uri = uri;
	}

	/**
	 * Load items.
	 *
	 * @return list of items
	 * @throws SAXException
	 *             If a SAX error occurred.
	 * @throws IOException
	 *             If an I/O error occurred.
	 */
	public List<Achievement> load() throws SAXException, IOException {
		final GroupsXMLLoader groupsLoader = new GroupsXMLLoader(uri);
		final List<URI> groups = groupsLoader.load();

		final AchievementXMLParser loader = new AchievementXMLParser();
		final List<Achievement> list = new LinkedList<Achievement>();
		for (final URI groupUri : groups) {
			LOGGER.debug("Loading item group [" + groupUri + "]");
			list.addAll(loader.load(groupUri));
		}

		return list;
	}
}
