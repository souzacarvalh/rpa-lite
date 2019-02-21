package org.ffsc.rpa.util;

import java.util.ResourceBundle;

import org.ffsc.rpa.types.PersistenceType;

public class PropertiesFacade {

	static ResourceBundle bundle = null;
	
	static {
		bundle = ResourceBundle.getBundle("resources/configuration/application");
	}
	
	public static String getProperty(String key){
		return bundle.getString(key).trim();
	}
	
	public static String getAppDefaultEncoding(){
		return bundle.getString("application.encoding").trim();
	}
	
	public static PersistenceType getPersistenceType() {

		String persistenceType = PropertiesFacade.getProperty("application.record.mode").trim();

		if(persistenceType.equalsIgnoreCase("RELATIONAL")){
			return PersistenceType.RELATIONAL;
		} else if(persistenceType.equalsIgnoreCase("XML")){
			return PersistenceType.XML;
		}

		return null;
	}
}