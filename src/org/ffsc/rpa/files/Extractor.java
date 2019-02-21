package org.ffsc.rpa.files;

import java.io.File;

public class Extractor {
	
	private FileExtractor extractor;
	
	public Extractor(FileExtractor extractorImpl) {
		this.extractor = extractorImpl;
	}
	
	public void extract(File file){
		extractor.extract(file);
	}
}