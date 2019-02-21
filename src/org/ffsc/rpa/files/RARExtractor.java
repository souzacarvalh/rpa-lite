package org.ffsc.rpa.files;

import java.io.File;

import org.ffsc.rpa.exceptions.RPAExceptionHandler;
import org.ffsc.rpa.util.PropertiesFacade;

import com.chilkatsoft.CkRar;
import com.chilkatsoft.CkRarEntry;

public class RARExtractor implements FileExtractor{

	public static final String LIB_RAR = "chilkat32.dll";
	
	private String workDirPath = "";
	private String binDirPath  = "";
		
	public RARExtractor() {
		workDirPath = PropertiesFacade.getProperty("application.work.dir");
		binDirPath  = PropertiesFacade.getProperty("application.base.dir")
							.concat("\\bin");
	}
	
	public void extract(File file){
		
		//Load chilkat library to unrar files
		System.load(binDirPath.concat("\\").concat(LIB_RAR));
		
		CkRar rar = null;
		
		try {
		
			//Open rar file
			rar = new CkRar();
			
			boolean wasOpenedOk = rar.Open(file.getAbsolutePath());
			
			if(wasOpenedOk) {
				
				//Get file count
				int numEntries = rar.get_NumEntries();
						
				//Iterate over files in rar
				for(int i = 0; i < numEntries; i++) {
				
					CkRarEntry entrada = rar.GetEntryByIndex(i);
									
					entrada.Unrar(workDirPath);
				}
	
				//Delete Rar file...
				file.delete();
			
			} else {
				System.out.println(rar.lastErrorText());
			}
			
		} catch(Exception e){
			new RPAExceptionHandler().handle(e);
		} finally {
			if(rar != null){
				rar.Close();
			}
		}
	}
}