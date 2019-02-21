package org.ffsc.rpa.files;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;
import org.ffsc.rpa.domain.Cliente;
import org.ffsc.rpa.domain.Config;
import org.ffsc.rpa.domain.NFe;
import org.ffsc.rpa.exceptions.RPAExceptionHandler;
import org.ffsc.rpa.persistence.ClienteDAOFactory;
import org.ffsc.rpa.persistence.ConfigDAOFactory;
import org.ffsc.rpa.persistence.db.ClienteSearchFilter;
import org.ffsc.rpa.util.DateUtils;
import org.ffsc.rpa.util.PropertiesFacade;

public class FileManager {	
	
	private static final String NP_DIR_NAME = "Não Processadas";
	
	private Config config;
	
	private String recordDirPath;
	private String workDirPath;
	private String npDirPath;
	
	private List<Cliente> emitentes;
	private List<Cliente> destinatarios;
	
	public FileManager() {
		
		//Get default system configurations
		config = ConfigDAOFactory.create().get();
						
		//Initialize directory paths ...
		recordDirPath = config.getDirGravacao();
		
		workDirPath = PropertiesFacade.getProperty("application.work.dir");
		
		npDirPath = recordDirPath.concat("\\").concat(NP_DIR_NAME);
		
		//Verify and create (if necessary) default directories
		verificarEstruturaGravacao();
		
		//Customers informations ...
		ClienteSearchFilter filter = new ClienteSearchFilter();
		
		filter.setSomenteEmitentes(true);
		
		emitentes = ClienteDAOFactory.create().find(filter);
		
		filter.setSomenteDestinatarios(true);
		
		destinatarios = ClienteDAOFactory.create().find(filter);
	}
		
	public int organize(String dirToWorkPath){
		
		int numArquivosProcessados = 0;
		
		File workDir = null; 
		
		if(dirToWorkPath != null){
			workDir = new File(dirToWorkPath);
		} else {
			
			workDir = new File(workDirPath);

			if(!workDir.exists()){
				if(workDir.mkdir()){
					workDir.setWritable(true);
				}
			}
		}
		
		//List only compressed files...
		File[] files = workDir.listFiles(new CompressedFileFilter());

		//Extract...
		for(File file: files) {	
			
			String fileName  = file.getName();
			String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
			
			if("zip".equalsIgnoreCase(extension)) {
				
				/*
				 * Some zipped files in Windows, in truth are gzipped files ... 
				 * we need verify to avoid problems at extract moment
				 */
				if(GZIPExtractor.isGZipped(file)){
					new Extractor(new GZIPExtractor()).extract(file);
				} else {
					new Extractor(new ZIPExtractor()).extract(file);
				}
				
			} else if("gz".equalsIgnoreCase(extension)) {
				new Extractor(new GZIPExtractor()).extract(file);
			} else if("rar".equalsIgnoreCase(extension)) {
				new Extractor(new RARExtractor()).extract(file);
			}
		}
		
		//Relist files and continue to process xml
		files = workDir.listFiles();
		
		for(File file: files) {	
			String fileName  = file.getName();
			String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
			
			if(file.isDirectory()) {
				
				//Recursively...
				numArquivosProcessados += organize(file.getAbsolutePath());
			
			} else if("xml".equalsIgnoreCase(extension)) {
				
				recordXMLFile(file);
				
				numArquivosProcessados++;
			}
		}

		//After process delete temp directory "Work"
		workDir.delete();
		
		return numArquivosProcessados;
	}
	
	public void organizeNPFiles() {
		
		File dir = new File(npDirPath);
		
		File[] files = dir.listFiles();
		
		for(File file: files)
		{
			String fileName  = file.getName();
			String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
			
			if("xml".equalsIgnoreCase(extension)){
				recordXMLFile(file);
			}
		}
	}	
	
	private void recordXMLFile(File file) {
		
		//Get informations ...
		NFe nota = new NFe(file);
		
		Cliente clienteEmit = new Cliente();
		
		clienteEmit.setCnpj(nota.getCnpjEmit());
		
		Cliente clienteDest = new Cliente();
		
		clienteDest.setCnpj(nota.getCnpjDest());
		
		if(emitentes.contains(clienteEmit)) {
			
			int index = emitentes.indexOf(clienteEmit);
			
			String cnpj       = nota.getCnpjEmit();
			String fantasia   = emitentes.get(index).getFantasia();
			String mesEmissao = DateUtils.getMonthName(nota.getDataEmissao());
			String anoEmissao = DateUtils.getYear(nota.getDataEmissao());
			
			//Verify directories for this customer ...
			verificarEstruturaCliente(cnpj, fantasia, mesEmissao, anoEmissao);
			
			//Set directory to move file ...
			String recordDir = recordDirPath.concat("\\").concat(fantasia)
								.concat(" (" + cnpj + ")").concat("\\")
								.concat(anoEmissao).concat("\\")
								.concat(mesEmissao)
								.concat("\\Saídas");
			
			file.renameTo(new File(recordDir, file.getName()));
		
		} else if(destinatarios.contains(clienteDest)){
			
			int index = destinatarios.indexOf(clienteDest);
			
			String cnpj        = nota.getCnpjDest();
			String fantasia    = destinatarios.get(index).getFantasia();
			String mesEmissao = DateUtils.getMonthName(nota.getDataEmissao());
			String anoEmissao = DateUtils.getYear(nota.getDataEmissao());
			
			//Verify directories for this customer ...
			verificarEstruturaCliente(cnpj, fantasia, anoEmissao, mesEmissao);
			
			//Set directory to move file ...
			String recordDir = recordDirPath.concat("\\").concat(fantasia)
								.concat(" (" + cnpj + ")").concat("\\")
								.concat(anoEmissao).concat("\\")
								.concat(mesEmissao)
								.concat("\\Entradas");
			
			file.renameTo(new File(recordDir, file.getName()));
		
		} else {									
			file.renameTo(new File(npDirPath.concat("\\").concat(file.getName())));
		}
	}
	
	public static void verificarEstruturaAplicativo() {
		
		//Verify initial enviroment ...
		String baseDirPath   = PropertiesFacade.getProperty("application.base.dir");
		String binDirPath    = baseDirPath.concat("\\bin");
		String dbDirPath 	 = baseDirPath.concat("\\database");
		String configDirPath = baseDirPath.concat("\\config");
		String dataDirPath   = baseDirPath.concat("\\data");
		
		String[] directories = new String[]{
			baseDirPath,
			binDirPath,
			dbDirPath,
			configDirPath,
			dataDirPath
		};
		
		for(String dirPath: directories){
			
			File dir = new File(dirPath);
			
			if(!dir.exists()){
				if(dir.mkdir()){
					dir.setWritable(true);
				}
			}
		}
		
		File cktdll = new File(binDirPath.concat("\\" + RARExtractor.LIB_RAR));
		
		if(!cktdll.exists()){
				
			//Copy binary files to dir
			InputStream is = FileManager.class.getResourceAsStream("/resources/bin/".concat(RARExtractor.LIB_RAR));

			try {
				FileUtils.copyInputStreamToFile(is, cktdll);
			} catch (IOException e) {
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

		File cfgxml = new File(dbDirPath.concat("\\config.rxml"));
		
		if(!cfgxml.exists()){
			
			//Copy the default config.rxml file...
			InputStream is = FileManager.class.getResourceAsStream("/resources/configuration/config.rxml");
			
			try {
				FileUtils.copyInputStreamToFile(is, cfgxml);
			} catch (IOException e) {
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
		
		File properties = new File(configDirPath.concat("\\application.properties"));	
		
		if(!properties.exists()){
		
			//Copy the rpa.properties file...
			InputStream is = FileManager.class.getResourceAsStream("/resources/configuration/application.properties");
			
			try {
				FileUtils.copyInputStreamToFile(is, properties);
			} catch (IOException e) {
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
	
	private void verificarEstruturaGravacao() {
		
		String[] directories = new String[]{
			recordDirPath,
			npDirPath
		};
		
		for(String dirPath: directories){
			
			File dir = new File(dirPath);
			
			if(!dir.exists()){
				if(dir.mkdir()){
					dir.setWritable(true);
				}
			}
		}
	}
	
	private void verificarEstruturaCliente(String cnpj, String fantasia, String ano, String mes){				

		//Create directories paths...
		String clienteDirPath = recordDirPath.concat("\\").concat(fantasia)
								.concat("(" + cnpj + ")");
		
		String anoDirPath      = clienteDirPath.concat("\\").concat(ano);
		String mesDirPath      = anoDirPath.concat("\\").concat(mes);
		String entradasDirPath = mesDirPath.concat("\\").concat("Saídas");
		String saidasDirPath   = mesDirPath.concat("\\").concat("Entradas");
				
		String[] directories = new String[]{
			clienteDirPath,
			anoDirPath,
			mesDirPath,
			entradasDirPath,
			saidasDirPath
		};
		
		for(String dirPath: directories){
			
			File dir = new File(dirPath);
			
			if(!dir.exists()){
				if(dir.mkdir()){
					dir.setWritable(true);
				}
			}
		}
	}
	
	class CompressedFileFilter implements FileFilter{
			
		FileNameExtensionFilter filtro = 
				new FileNameExtensionFilter("Arquivos Comprimidos", "zip", "rar", "gz");
		
		@Override
		public boolean accept(File arquivo) {
			return filtro.accept(arquivo);
		}
	}
}