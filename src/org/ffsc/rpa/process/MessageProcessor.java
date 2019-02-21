package org.ffsc.rpa.process;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;

import org.ffsc.rpa.exceptions.RPAExceptionHandler;

public class MessageProcessor {		
	
	private AttachExtractor attachExtractor;
	
	public MessageProcessor(AttachExtractor attachExtractor) {
		this.attachExtractor = attachExtractor;
	}
	
	public int processarMensagem(Message message){
		
		int processados = 0;
		
		try {
		
			Object messageContent = message.getContent();
			
			if(messageContent instanceof Multipart){
				
				Multipart  multipart = (Multipart) messageContent;
				
				for(int i = 0; i < multipart.getCount(); i++){
					
					//Get message part ...
					Part messagePart = multipart.getBodyPart(i);
					
					//Analyse part content ...
					if(processPart(messagePart)){
						processados++;
					}
				}
			}
			
		} catch(Exception e) {
			new RPAExceptionHandler().handle(e);
		}
		
		return processados;
	}
	
	private boolean processPart(Part messagePart){
		
		try {
		
			String contentType = messagePart.getContentType().toUpperCase();
			
			String position = messagePart.getDisposition();
			
			//Verify if the part is an attachment ...
			if((position != null) && (position.equalsIgnoreCase(Part.ATTACHMENT) 
					|| position.equalsIgnoreCase(Part.INLINE))){
				
				//Verify attachment accepted types ...
				if(contentType.startsWith("APPLICATION/XML") 
						|| contentType.startsWith("TEXT/XML") 
						|| contentType.startsWith("APPLICATION/OCTET-STREAM")
						|| contentType.startsWith("APPLICATION/ZIP") 
						|| contentType.startsWith("APPLICATION/X-ZIP")
						|| contentType.startsWith("APPLICATION/RAR") 
						|| contentType.startsWith("APPLICATION/X-RAR-COMPRESSED")){
					
					return attachExtractor.extract(messagePart);
				}
			}
			
		} catch(Exception e) {
			new RPAExceptionHandler().handle(e);
		}
		
		return false;
	}
}