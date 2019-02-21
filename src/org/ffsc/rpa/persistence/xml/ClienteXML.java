package org.ffsc.rpa.persistence.xml;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.ffsc.rpa.domain.Cliente;
import org.ffsc.rpa.exceptions.RPABusinessException;
import org.ffsc.rpa.exceptions.RPAExceptionHandler;
import org.ffsc.rpa.persistence.db.ClienteSearchFilter;
import org.ffsc.rpa.persistence.interfaces.ClienteDAO;
import org.ffsc.rpa.util.PropertiesFacade;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

public class ClienteXML implements ClienteDAO {
	
	private static final String DB_FILE_NAME = "clientes.rxml";
	private static String CLIENTES_DB_FILE_PATH;
	
	public ClienteXML() {
		
		CLIENTES_DB_FILE_PATH = PropertiesFacade.getProperty("application.xml.database") 
									+ "\\" + DB_FILE_NAME;
		
		//Verify database intergrity....
		verifyXMLDatabase();
	}

	private void verifyXMLDatabase(){

		File dbDir = new File(PropertiesFacade.getProperty("application.xml.database"));
		
		try {
		
			//Verify and create (if necessary) database directory
			if(!dbDir.exists()){
				if(dbDir.mkdir()){
					dbDir.setWritable(true);
				}
			}
			
			//Verify and create (if necessary) the cliente.xrml file
			File xmlDbClientes = new File(CLIENTES_DB_FILE_PATH);
			
			if(!xmlDbClientes.exists()){
				if(xmlDbClientes.createNewFile()){
					
					xmlDbClientes.setWritable(true);
						
					//Create internal structure of clientes.rxml		
					Document doc = new Document();
					
					Element root = new Element("clientes");
			
					doc.addContent(root);
			
					XMLOutputter xmlOutput = new XMLOutputter();
					
					//Set format to xml document
					Format f = Format.getPrettyFormat();
					
					f.setEncoding(PropertiesFacade.getAppDefaultEncoding());
					
					xmlOutput.setFormat(f);
					
					xmlOutput.output(doc, new FileWriter(CLIENTES_DB_FILE_PATH));
				}
			}
			
		} catch (Exception e){
			new RPAExceptionHandler().handle(e);
		}
	}
	
	@Override
	public Cliente save(Cliente cliente) throws RPABusinessException{
		
		if(isCnpjJaCadastrado(cliente)){
			throw new RPABusinessException("CNPJ já cadastrado para outro cliente.");
		}
		
		try {

			//Get the clientes.rxml file...
			File clixml = new File(CLIENTES_DB_FILE_PATH);
			
			//Build the document...
			SAXBuilder sb = new SAXBuilder();
			
			Document doc = sb.build(clixml);
			
			Element root = doc.getRootElement();
			
			//Get count of elements...
			long count = root.getChildren().size() + 1;
			
			//Create elements
			Element clienteNode = new Element("cliente");
			Element razao    	= new Element("razao");
			Element fantasia 	= new Element("fantasia");
			Element cnpj     	= new Element("cnpj");
			Element pAsEmit 	= new Element("pAsEmit");
			Element pAsDest  	= new Element("pAsDest");
			
			//Set elements contents
			razao.setText(cliente.getRazao());
			fantasia.setText(cliente.getFantasia());
			cnpj.setText(cliente.getCnpj());
			pAsEmit.setText(String.valueOf(cliente.isProcessarSeEmit()));
			pAsDest.setText(String.valueOf(cliente.isProcessarSeDestinatario()));
			
			//Set cliente id
			clienteNode.setAttribute("id", String.valueOf(count));
			
			//Build structure...
			clienteNode.addContent(razao);
			clienteNode.addContent(fantasia);
			clienteNode.addContent(cnpj);
			clienteNode.addContent(pAsEmit);
			clienteNode.addContent(pAsDest);
			
			root.addContent(clienteNode);
			
			//Write the modified xml file ...		
			XMLOutputter xmlOutput = new XMLOutputter();
			
			//Set format to xml document
			Format f = Format.getPrettyFormat();
			f.setEncoding(PropertiesFacade.getAppDefaultEncoding());
			
			xmlOutput.setFormat(f);
			xmlOutput.output(doc, new FileWriter(CLIENTES_DB_FILE_PATH));
			
			//Set new id
			cliente.setId(count);
		
		} catch (Exception e){
			new RPAExceptionHandler().handle(e);
		}
		
		return cliente;	
	}

	@Override
	public Cliente update(Cliente cliente) throws RPABusinessException{	
		
		if(cliente != null && isCnpjJaCadastrado(cliente)){
			throw new RPABusinessException("CNPJ já cadastrado para outro cliente.");
		}		
		
		try {

			//Get the clientes.rxml file...
			File clixml = new File(CLIENTES_DB_FILE_PATH);
			
			//Build the document...
			SAXBuilder sb = new SAXBuilder();
			
			Document doc = sb.build(clixml);
			
			Element root = doc.getRootElement();
			
			//Get count of elements...
			List<Element> children = root.getChildren();
			
			//Find to update element...
			for(Element child: children){
				
				int id = Integer.parseInt((child.getAttributeValue("id")));
				
				if(id == cliente.getId()){
					
					//Update element contents
					child.getChild("razao").setText(cliente.getRazao());
					child.getChild("fantasia").setText(cliente.getFantasia());
					child.getChild("cnpj").setText(cliente.getCnpj());
					child.getChild("pAsEmit").setText(String.valueOf(cliente.isProcessarSeEmit()));
					child.getChild("pAsDest").setText(String.valueOf(cliente.isProcessarSeEmit()));
					
					break;
				}
			}
			
			//Write the modified xml file ...		
			XMLOutputter xmlOutput = new XMLOutputter();
			
			//Set format to xml document
			Format f = Format.getPrettyFormat();
			
			f.setEncoding(PropertiesFacade.getAppDefaultEncoding());
					
			xmlOutput.setFormat(f);
			
			xmlOutput.output(doc, new FileWriter(CLIENTES_DB_FILE_PATH));
		
		} catch (Exception e){
			new RPAExceptionHandler().handle(e);
		}
		
		return cliente;
	}
	
	@Override
	public List<Cliente> find(ClienteSearchFilter filter){
		
		List<Cliente> clientes = new ArrayList<Cliente>();

		try {
			
			//Get the clientes.rxml file...
			File clixml = new File(CLIENTES_DB_FILE_PATH);
					
			//Build the document...
			SAXBuilder sb = new SAXBuilder();
					
			Document doc = sb.build(clixml);
			
			//Build the search criteria
			StringBuilder search = new StringBuilder("//cliente[@id");
			
			if(filter.getId() != null && !"".equals(filter.getId())){
				search.append("=").append(filter.getId());
			}
			
			if(filter.getRazao() != null && !"".equals(filter.getRazao())){
				search.append(" and contains(razao, '")
						.append(filter.getRazao())
						.append("')");
			}
			
			if(filter.getFantasia() != null && !"".equals(filter.getFantasia())){
				search.append(" and contains(fantasia, '")
						.append(filter.getFantasia())
						.append("')");
			}
			
			if(filter.getCnpj() != null && !"".equals(filter.getCnpj().replaceAll("[.-\\\\]", ""))){
				search.append(" and cnpj='")
						.append(filter.getCnpj())
						.append("'");
			}
			
			//Close arguments...
			search.append("]");
			
			XPathExpression<Element> xpath = XPathFactory.instance()
												.compile(search.toString(), Filters.element());
			
			List<Element> children = xpath.evaluate(doc);
		
			//Iterate elements...
			for(Element child: children){
				
				Cliente cliente = new Cliente();
				
				cliente.setId(Long.parseLong((child.getAttributeValue("id"))));
				cliente.setRazao(child.getChild("razao").getText());
				cliente.setFantasia(child.getChild("fantasia").getText());
				cliente.setCnpj(child.getChild("cnpj").getText());
				cliente.setProcessarSeEmit(Boolean.parseBoolean(child.getChild("pAsEmit").getText()));
				cliente.setProcessarSeDestinatario(Boolean.parseBoolean(child.getChild("pAsDest").getText()));
				
				clientes.add(cliente);
			}
			
		} catch (Exception e){
			new RPAExceptionHandler().handle(e);
		}

		return clientes;
	}

	@Override
	public boolean delete(Long id) {
		
		try {
		
			//Get the clientes.rxml file..
			File clixml = new File(CLIENTES_DB_FILE_PATH);
			
			//Build the clientes.rxml
			SAXBuilder sb = new SAXBuilder();
			
			Document doc = sb.build(clixml);
			
			//Get root element...
			Element root = doc.getRootElement();
			
			//Get root's children ...
			List<Element> children = root.getChildren();
			
			//Find to delete element...
			for(Element child: children){
				
				Long elementId = Long.parseLong((child.getAttributeValue("id")));
				
				if(id.equals(elementId)){
					
					root.removeContent(child);
					
					break;
				}
			}
			
			//Write the modified xml file ...		
			XMLOutputter xmlOutput = new XMLOutputter();
	
			//Set format to xml document
			Format f = Format.getPrettyFormat();
			
			f.setEncoding(PropertiesFacade.getAppDefaultEncoding());
					
			xmlOutput.setFormat(f);
			
			xmlOutput.output(doc, new FileWriter(CLIENTES_DB_FILE_PATH));
		
		} catch (Exception e){
			new RPAExceptionHandler().handle(e);
		}
		
		return true;
	}
	

	public boolean isCnpjJaCadastrado(Cliente cliente) {
		
		try {
		
			//Get the clientes.rxml file..
			File clixml = new File(CLIENTES_DB_FILE_PATH);
			
			//Build the clientes.rxml
			SAXBuilder sb = new SAXBuilder();
			
			Document doc = sb.build(clixml);
			
			//Get root element...
			Element root = doc.getRootElement();
			
			//Get root's children ...
			List<Element> children = root.getChildren();
			
			//Find to delete element...
			for(Element child: children){
				
				String elementCnpj = child.getChild("cnpj").getText();
				
				if(cliente.getCnpj().equals(elementCnpj)){
					return true;
				}
			}

		} catch (Exception e){
			new RPAExceptionHandler().handle(e);
		}
		
		return false;
	}
}