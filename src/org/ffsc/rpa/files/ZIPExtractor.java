package org.ffsc.rpa.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.ffsc.rpa.exceptions.RPAExceptionHandler;
import org.ffsc.rpa.util.PropertiesFacade;

public class ZIPExtractor implements FileExtractor{

	private String workDirPath = "";
	
	public ZIPExtractor() {
		workDirPath = PropertiesFacade.getProperty("application.work.dir");
	}
	
	@Override
	public void extract(File file) {
		
		InputStream fis = null;
		
		ZipInputStream zip = null;
		
		try {	
			
			fis = new FileInputStream(file);
			
			zip = new ZipInputStream(fis);			
			
			ZipEntry zipEntry = null;
						
			while((zipEntry = zip.getNextEntry()) != null) {
				
				String fileName = zipEntry.getName();
				
				fileName = fileName.replaceAll("/", ".");
						
				//Verify if this entry is a directory
				if(zipEntry.isDirectory()) {
					
					File dir = new File(workDirPath.concat("\\").concat(fileName));
					
					if(dir.mkdir()){
						dir.setWritable(true);
					}
					
					continue;
				}
				
				String extension = fileName.substring(fileName.lastIndexOf('.') + 1);

				if("zip".equalsIgnoreCase(extension)) {
					
					OutputStream os = null;
					
					try {					
					
						os = new FileOutputStream(workDirPath.concat("\\")
									.concat(fileName));
						
						int b;
					
						while((b = zip.read()) != -1){
							os.write(b);
						}
						
						os.flush();
						
						//Recursively...
						extract(new File(workDirPath.concat("\\").concat(fileName)));
					
					} catch(IOException e) {
						new RPAExceptionHandler().handle(e);
					} finally {
						try {
							if(os != null){
								os.close();
							}
						} catch(IOException e) {
							new RPAExceptionHandler().handle(e);
						}
					}
				}
			}

			//Delete zipped file...
			file.delete();
			
		} catch (Exception e) {
			new RPAExceptionHandler().handle(e);
		} finally {
			try {
				
				if(zip != null){
					zip.close();
				}
				
				if(fis != null){
					fis.close();
				}
				
			} catch(IOException e) {
				new RPAExceptionHandler().handle(e);
			}
		}
	}
}