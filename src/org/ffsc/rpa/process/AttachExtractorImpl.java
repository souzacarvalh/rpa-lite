package org.ffsc.rpa.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.regex.Pattern;

import javax.mail.Part;

import org.ffsc.rpa.exceptions.RPAExceptionHandler;
import org.ffsc.rpa.util.PropertiesFacade;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

public class AttachExtractorImpl implements AttachExtractor{
	
	private final Pattern xmlPattern = Pattern.compile(".*\\.xml", Pattern.CASE_INSENSITIVE);
	private final Pattern zipPattern = Pattern.compile(".*\\.zip", Pattern.CASE_INSENSITIVE);
	private final Pattern rarPattern = Pattern.compile(".*\\.zip", Pattern.CASE_INSENSITIVE);
	
	private String workDirPath = "";
	
	public AttachExtractorImpl() {
		workDirPath = PropertiesFacade.getProperty("application.work.dir");
	}
	
	@Override
	public boolean extract(Part anexo){	
		
		try {
		
			String nomeAnexo = MimeUtility.decodeText(anexo.getFileName());
			
			if(xmlPattern.matcher(nomeAnexo).matches()
					|| zipPattern.matcher(nomeAnexo).matches()
					|| rarPattern.matcher(nomeAnexo).matches()){
				
				InputStream stream = anexo.getInputStream();
																
				saveExtractedFile(stream, nomeAnexo, false);
				
				return true;
			}
			
		} catch(Exception e){
			new RPAExceptionHandler().handle(e);
		}
		
		return false;
	}
	
	@Override
	public void saveExtractedFile(InputStream is, String name, Boolean saveAsCharacter){	

		File workDir = new File(workDirPath);
		
		OutputStream outStream = null;
		
		InputStreamReader isr = null;
		
		try {
			
			if(!workDir.exists()){
				if(workDir.mkdir()){
					workDir.setWritable(true);
				}
			}
			
			//Save file...
			outStream = new FileOutputStream(new File(workDirPath.concat("\\").concat(name)));	
			
			//If file is to save using characters .. for example a xml file
			if(saveAsCharacter){
				
				isr = new InputStreamReader(is, "UTF-8");
			
				int read = 0;
					 
				while ((read = isr.read()) != -1) {
					outStream.write(read);
				}
				
				outStream.flush();
				
			} else {
				
				byte[] dados = new byte[2048];
			
				while((is.read(dados)) != -1){
					outStream.write(dados, 0, 2048);
				}
				
				outStream.flush();	
			}
			
		} catch(Exception e) {
			new RPAExceptionHandler().handle(e);
		} finally {
			
			try {
				
				if(is != null){
					is.close();
				}
				
				if(isr != null){
					isr.close();
				}
				
				if(outStream != null){
					outStream.close();
				}
				
			} catch (IOException e) {
				new RPAExceptionHandler().handle(e);
			}
		}
	}
}