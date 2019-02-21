package org.ffsc.rpa.persistence;

import org.ffsc.rpa.persistence.interfaces.ClienteDAO;
import org.ffsc.rpa.persistence.xml.ClienteXML;
import org.ffsc.rpa.types.PersistenceType;
import org.ffsc.rpa.util.PropertiesFacade;

public class ClienteDAOFactory {

	public static ClienteDAO create() {

		if (PropertiesFacade.getPersistenceType().equals(PersistenceType.XML)) {
			return new ClienteXML();
		}

		if (PropertiesFacade.getPersistenceType().equals(
				PersistenceType.RELATIONAL)) {
			return null;
		}

		return null;
	}
}