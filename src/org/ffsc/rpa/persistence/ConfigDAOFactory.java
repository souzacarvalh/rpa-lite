package org.ffsc.rpa.persistence;

import org.ffsc.rpa.persistence.db.ConfigDB;
import org.ffsc.rpa.persistence.interfaces.ConfigDAO;
import org.ffsc.rpa.persistence.xml.ConfigXML;
import org.ffsc.rpa.types.PersistenceType;
import org.ffsc.rpa.util.PropertiesFacade;

public class ConfigDAOFactory {

	public static ConfigDAO create() {

		if (PropertiesFacade.getPersistenceType().equals(PersistenceType.XML)) {
			return new ConfigXML();
		}

		if (PropertiesFacade.getPersistenceType().equals(
				PersistenceType.RELATIONAL)) {
			return new ConfigDB();
		}

		return null;
	}
}