package org.ffsc.rpa.domain;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.ffsc.rpa.exceptions.RPAExceptionHandler;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

public class NFe {

	private static final Namespace NFE_XML_NS = Namespace.getNamespace("http://www.portalfiscal.inf.br/nfe");
	
	// Informações da nota
	private Long id;
	private String dataEmissao;

	// Emitente
	private String cnpjEmit;
	private String fantasiaEmit;

	// Destinatário ...
	private String cnpjDest;
	private String fantasiaDest;

	public NFe() {
		
	}
	
	/**
	 * Cria um bean do tipo NFe a partir de um objeto java.io.File
	 * no formato XML
	 * 
	 * @param xml Arquivo File contendo no formato NF-e 2.0
	 */
	public NFe(File xml) {
		
		if(xml != null){
		
			InputStream is = null;
			
			try {
				
				//Get default root path (NFe 2.0 only)
				SAXBuilder builder = new SAXBuilder();
							
				Document doc = builder.build(is);
				
				Element root = doc.getRootElement();
				
				Element nfe  = root.getChild("NFe", NFE_XML_NS);
			
				Element infNFe = nfe.getChild("infNFe", NFE_XML_NS);
				
				//Get informations about "NFe" ...
				Element ide = infNFe.getChild("ide", NFE_XML_NS);
				
				//Read Informations...
				Element dataEmissao = ide.getChild("dEmi", NFE_XML_NS);
				
				this.setDataEmissao(dataEmissao.getValue());
				
				//Get informations about "Emitente" ...
				Element emit = infNFe.getChild("emit", NFE_XML_NS);
				
				//Read Informations...
				Element cnpjEmit     = emit.getChild("CNPJ", NFE_XML_NS);
				Element fantasiaEmit = emit.getChild("xFant", NFE_XML_NS);
				
				this.setCnpjEmit(cnpjEmit.getValue());
				this.setFantasiaEmit(fantasiaEmit.getValue());
				
				//Get informations about "Destinatário" ...
				Element dest = infNFe.getChild("dest", NFE_XML_NS);
				
				//Read Informations...
				Element cnpjDest 	 = dest.getChild("CNPJ", NFE_XML_NS);
				Element fantasiaDest = dest.getChild("xNome", NFE_XML_NS);
				
				this.setCnpjDest(cnpjDest.getValue());
				this.setFantasiaDest(fantasiaDest.getValue());
				
			} catch(Exception e) {
				new RPAExceptionHandler().handle(e);
			} finally {
				if(is != null){
					try {
						is.close();
					} catch (IOException e) {
						new RPAExceptionHandler().handle(e);
					}
				}
			}
		}
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(String dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public String getCnpjEmit() {
		return cnpjEmit;
	}

	public void setCnpjEmit(String cnpjEmit) {
		this.cnpjEmit = cnpjEmit;
	}

	public String getFantasiaEmit() {
		return fantasiaEmit;
	}

	public void setFantasiaEmit(String fantasiaEmit) {
		this.fantasiaEmit = fantasiaEmit;
	}

	public String getCnpjDest() {
		return cnpjDest;
	}

	public void setCnpjDest(String cnpjDest) {
		this.cnpjDest = cnpjDest;
	}

	public String getFantasiaDest() {
		return fantasiaDest;
	}

	public void setFantasiaDest(String fantasiaDest) {
		this.fantasiaDest = fantasiaDest;
	}
}