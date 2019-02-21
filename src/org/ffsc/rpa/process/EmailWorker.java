package org.ffsc.rpa.process;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.swing.SwingWorker;

import org.ffsc.rpa.domain.Config;
import org.ffsc.rpa.domain.EmailClient;
import org.ffsc.rpa.exceptions.RPAExceptionHandler;
import org.ffsc.rpa.files.FileManager;

public class EmailWorker extends SwingWorker<Integer, Void> {

	private static final String STATUS_MSG_PROPERTY = "status_msg";

	private String statusMessage;
	
	private int numEmailsProcessados;
	private int numArquivosProcessados;
	
	private Config configuration;

	public EmailWorker(Config config) {
		this.configuration = config;
	}

	@Override
	protected Integer doInBackground() {
		
		setStatusMsg("Processamento de emails inicializado ...");

		try {
		
			// Log informations about the email client connection
			setStatusMsg("Conta de Email: " + configuration.getEmail());
			setStatusMsg("Servidor: "  + configuration.getServidor());
			setStatusMsg("Protocolo: " + configuration.getProtocolo().getLabel());
	
			EmailClient client = new EmailClient(configuration);
					
			// Connecting...
			client.conectar();
	
			if (client.isConnected()) {
				
				setStatusMsg("Conectado ... ");
	
				Message[] messages = client.getMensagensCaixaEntrada();
	
				setStatusMsg("Número de emails encontrados: " + messages.length);
	
				// Estimate progress percent of each processed message
				int progressIndicator = 100 / messages.length - 2;
	
				MessageProcessor messageProcessor = new MessageProcessor(new AttachExtractorImpl());
	
				FileManager fileManager = new FileManager();
	
				for (Message message : messages) {
					if (!message.isSet(Flags.Flag.SEEN)) {
	
						String remetente = ((InternetAddress) message.getFrom()[0])
												.getAddress().toString();
	
						setStatusMsg("Processando mensagem ... Remetente: " + remetente);
	
						this.numEmailsProcessados += messageProcessor.processarMensagem(message);
	
						client.marcarComoLido(message);
	
						if (configuration.getApagarJaProcessados()){
							client.excluirMensagem(message);
						}
					}
	
					// Update progress bar indication
					setProgress(getProgress() + progressIndicator);
				}
	
				client.desconectar();
	
				// All messages were dowloaded ... now it's time to organize
				setStatusMsg("Realizando organização dos arquivos ... aguarde");
	
				numArquivosProcessados = fileManager.organize(null);
	
				setProgress(getProgress() + progressIndicator);
			
			} else {
				
				setStatusMsg("*ERRO: Não foi possível conectar. Verifique suas configurações de rede e email.");
				
				this.cancel(true);
			}
			
		} catch(Exception e) {
			
			this.cancel(true);
			
			new RPAExceptionHandler().handle(e);
		}

		return this.numArquivosProcessados;
	}

	@Override
	public void done() {
		
		setStatusMsg("OK. Número de Emails Processados: " + this.numEmailsProcessados);

		setStatusMsg("Número de NFes: " + this.numArquivosProcessados);

		if (isCancelled()){
			setStatusMsg("Processamento de emails Cancelado ...");
		} else {
			setStatusMsg("Processamento de emails concluído ...");
		}
		
		setProgress(0);
	}

	// Atualiza a mensagem de status do trabalho
	public void setStatusMsg(String statusMessage) {
		
		String old_value = this.statusMessage;

		this.statusMessage = statusMessage;

		firePropertyChange(STATUS_MSG_PROPERTY, old_value, this.statusMessage);
	}
}