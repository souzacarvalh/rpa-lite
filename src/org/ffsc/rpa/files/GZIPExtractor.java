package org.ffsc.rpa.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.zip.GZIPInputStream;

import org.ffsc.rpa.exceptions.RPAExceptionHandler;
import org.ffsc.rpa.util.PropertiesFacade;

public class GZIPExtractor implements FileExtractor {

	private String workDirPath = "";
	
	public GZIPExtractor() {
		workDirPath = PropertiesFacade.getProperty("application.work.dir");
	}
	
	public void extract(File file) {
       
		InputStream fis = null;
		
		GZIPInputStream gzip = null;
		
		try {
         	
			fis = new FileInputStream(file);
        	
            gzip = new GZIPInputStream(fis);
  
            String fileName = file.getName();
            
            fileName = fileName.substring(0, fileName.lastIndexOf('.'));
            
            fileName = workDirPath.concat("\\").concat(fileName).concat(".xml");
            
            //Extract gzip - unique file
            FileOutputStream fos = null;
            
            try {
            	
            	fos = new FileOutputStream(fileName);
            
            	byte[] dados = new byte[1024];
            	
            	int len;
         
				while((len = gzip.read(dados)) > 0)
				{
					fos.write(dados, 0, len);
				}
				
            } catch(Exception e){
            	 new RPAExceptionHandler().handle(e);
            } finally {
            	try {
            		if(fos != null){
            			fos.close();
            		}
    			} catch (IOException e) {
    				new RPAExceptionHandler().handle(e);
    			}
            }

            //Delete gzip file...
            file.delete();
        
        } catch (Exception e) {
            new RPAExceptionHandler().handle(e);
        } finally {
        	try {
        		
        		if(fis != null){
        			fis.close();
        		}
        		
        		if(gzip != null){
        			gzip.close();
        		}
        		
			} catch (IOException e) {
				new RPAExceptionHandler().handle(e);
			}
        }
	}
	
	public static boolean isGZipped(File arquivo) {
		  
		int magic = 0;
		 
		RandomAccessFile raf = null;
		
		try {
			
		   raf = new RandomAccessFile(arquivo, "r");
		   		   	   
		   magic = raf.read() & 0xff | ((raf.read() << 8) & 0xff00);

		} catch (Exception e) {
			new RPAExceptionHandler().handle(e);
		} finally {
			try {
				if(raf != null){
					raf.close();
				}
			} catch (IOException e) {
				new RPAExceptionHandler().handle(e);
			}
		}
		  
		return (magic == GZIPInputStream.GZIP_MAGIC);
	}
}