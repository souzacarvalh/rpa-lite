package org.ffsc.rpa.persistence.xml;

import java.io.File;
import java.io.FileWriter;

import org.ffsc.rpa.domain.Config;
import org.ffsc.rpa.exceptions.RPAExceptionHandler;
import org.ffsc.rpa.persistence.interfaces.ConfigDAO;
import org.ffsc.rpa.types.Protocolo;
import org.ffsc.rpa.util.PropertiesFacade;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ConfigXML implements ConfigDAO {

	private static final String DB_FILE_NAME = "config.rxml";
	private static String CONFIG_DB_FILE_PATH;

	public ConfigXML() {
		
		CONFIG_DB_FILE_PATH = PropertiesFacade.getProperty("application.xml.database") 
								+ "\\" + DB_FILE_NAME;
	}

	@Override
	public boolean save(Config cfg) {
		try {

			File configXml = new File(CONFIG_DB_FILE_PATH);

			// Build the document...
			SAXBuilder sb = new SAXBuilder();

			Document doc = sb.build(configXml);

			Element root = doc.getRootElement();

			Element child = root.getChild("configuracoes");
			
			Element config = child.getChild("config");

			// Update element contents
			config.getChild("protocolo").setText(cfg.getProtocolo().toString());
			config.getChild("servidor").setText(cfg.getServidor());
			config.getChild("email").setText(cfg.getEmail());
			config.getChild("senha").setText(cfg.getSenha());
			config.getChild("pasta").setText(cfg.getDirCaixaEmail());
			config.getChild("apagarProc").setText(String.valueOf(cfg.getApagarJaProcessados()));
			config.getChild("dirGravacaoXML").setText(cfg.getDirGravacao());

			// Write the modified xml file ...
			XMLOutputter xmlOutput = new XMLOutputter();

			xmlOutput.setFormat(Format.getPrettyFormat()
						.setEncoding(PropertiesFacade.getAppDefaultEncoding()));

			xmlOutput.output(doc, new FileWriter(CONFIG_DB_FILE_PATH));

		} catch (Exception e) {
			new RPAExceptionHandler().handle(e);
		}

		return true;
	}

	@Override
	public Config get() {

		Config configuration = null;

		try {

			File configXml = new File(CONFIG_DB_FILE_PATH);

			// Build the config.rxml
			SAXBuilder sb = new SAXBuilder();

			Document doc = sb.build(configXml);

			// Get root element...
			Element root = doc.getRootElement();

			// Get root's child ...
			Element child = root.getChild("configuracoes");

			Element config = child.getChild("config");
			
			// Set config values...
			configuration = new Config();

			configuration.setProtocolo(Protocolo.valueOf(config.getChildText("protocolo").toUpperCase()));
			configuration.setServidor(config.getChildTextTrim("servidor"));
			configuration.setEmail(config.getChildTextTrim("email"));
			configuration.setSenha(config.getChildTextTrim("senha"));
			configuration.setDirCaixaEmail((config.getChildTextTrim("pasta")));
			configuration.setApagarJaProcessados((Boolean.parseBoolean(config.getChildText("apagarProc"))));
			configuration.setDirGravacao(config.getChildTextTrim("dirGravacaoXML"));

		} catch (Exception e) {
			new RPAExceptionHandler().handle(e);
		}

		return configuration;
	}
}