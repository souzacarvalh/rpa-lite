package org.ffsc.rpa.domain;

import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

public class EmailClient {

	private boolean connected;
	private boolean tryingToConnect;
	private String server;
	private String user;
	private String password;
	private String protocol;
	private Properties props;
	private Session session;
	private Store store;

	public EmailClient(Config configuration) {
		if(configuration != null){
			this.server = configuration.getServidor();
			this.user   = configuration.getEmail();
			this.password = configuration.getSenha();
			this.protocol = configuration.getProtocolo()
								.getLabel().toLowerCase();
		}
	}

	public void conectar() {
		try {
			// Set state to trying to connect now...
			this.setTryingToConnect(true);

			this.props = new Properties();

			// Especific properties to supress imap login/text-plain
			this.props.put("mail.imap.auth.login.disable", "true");
			this.props.put("mail.imap.auth.plain.disable", "true");

			this.session = Session.getInstance(props, null);

			this.store = session.getStore(this.protocol);

			this.store.connect(this.server, this.user, this.password);

			if (this.store.isConnected()) {
				this.setConnected(true);
			}

		} catch (MessagingException e) {
			this.setConnected(false);
		} finally {
			this.setTryingToConnect(false);
		}
	}

	public void desconectar() throws MessagingException {
		this.setConnected(false);
		this.store.close();
	}

	public Message[] getMensagensCaixaEntrada() throws MessagingException {
		Folder pasta = this.store.getDefaultFolder();

		pasta = this.store.getFolder("INBOX");

		pasta.open(Folder.READ_WRITE);

		Message mensagens[] = pasta.getMessages();

		return mensagens;
	}

	public void copiarParaPasta(Folder sourceDir, String dirTo, Message[] msgs)
			throws MessagingException {
		Folder folder = this.store.getFolder(dirTo);

		sourceDir.copyMessages(msgs, folder);
	}

	public void excluirMensagem(Message msg) throws MessagingException {
		msg.setFlag(Flags.Flag.DELETED, true);
	}

	public void marcarComoLido(Message msg) throws MessagingException {
		msg.setFlag(Flags.Flag.SEEN, true);
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	private void setTryingToConnect(boolean tryingToConnect) {
		this.tryingToConnect = tryingToConnect;
	}

	public boolean isTryingToConnect() {
		return this.tryingToConnect;
	}
}