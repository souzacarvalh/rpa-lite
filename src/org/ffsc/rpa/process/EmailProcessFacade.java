package org.ffsc.rpa.process;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.ffsc.rpa.domain.Config;
import org.ffsc.rpa.files.FileManager;
import org.ffsc.rpa.persistence.ConfigDAOFactory;
import org.ffsc.rpa.ui.WindowManager;

public class EmailProcessFacade implements PropertyChangeListener {

	private EmailWorker emailWorker;
	
	public void iniciarProcessamentoEmails(){

		Config configuration = ConfigDAOFactory.create().get();
		
		//Envia o processamento para a SwingWorker
		emailWorker = new EmailWorker(configuration);	
		
		//Registra o PropertChangeListener
		emailWorker.addPropertyChangeListener(this);
				
		emailWorker.execute();
	}
	
	public void pararProcessamentoEmails(){
		emailWorker.cancel(true);
	}
	
	public void organizarNaoProcessados() {
		
		WindowManager.getApplication().getLogPanel().log("Reorganizando arquivos não processados ...");
		WindowManager.getApplication().getStatusBar().setMessage("Reorganizando Arquivos...");
		
		//Initialize the file handler ...
		FileManager fileManager = new FileManager();
		
		fileManager.organizeNPFiles();
		
		WindowManager.getApplication().getStatusBar().setMessage("Inativo");
		WindowManager.getApplication().getLogPanel().log("Reorganização concluída!");
	}
	
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
		//Propriedades da classe de processamento de emails
		if("status_msg".equals(evt.getPropertyName())){
		
			WindowManager.getApplication().getLogPanel().log(evt.getNewValue().toString());
		
		} else if("progress".equals(evt.getPropertyName())){
			
			int status = Integer.parseInt(evt.getNewValue().toString());
			
			WindowManager.getApplication().getStatusBar().updateProgress(status);
		
		} else if("state".equals(evt.getPropertyName())) {
			
			if("STARTED".equals(evt.getNewValue().toString())){
				
				WindowManager.getApplication().getStatusBar().setMessage("Processamento de emails Inicializado ...");
			
			} else if("DONE".equals(evt.getNewValue().toString())) {
				
				WindowManager.getApplication().getStatusBar().setMessage("Inativo");
				
				WindowManager.getApplication().getStatusBar().updateProgress(0);
			}
		}
	}
}