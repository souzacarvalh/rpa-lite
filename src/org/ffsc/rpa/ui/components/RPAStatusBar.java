package org.ffsc.rpa.ui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class RPAStatusBar extends JPanel {

	private static final long serialVersionUID = 1L;

	private final Dimension COMPONENT_SIZE = new Dimension(888, 30);
	
	private static final String DEFAULT_MESSAGE = new String("Status do Serviço: Inativo");
		
	private JLabel labelStatusMessage;
	private JProgressBar progressBar;
	
	public RPAStatusBar() {
		
		//Creating components ...
		createLabelStatusMessage();
		createProgressBar();
		
		//Initializing component ...
		setPreferredSize(new Dimension(COMPONENT_SIZE));		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEtchedBorder());
		setVisible(true);
		
		this.add(progressBar, BorderLayout.EAST);
		this.add(labelStatusMessage, BorderLayout.WEST);
	}	
	
	
	private void createLabelStatusMessage() {
		
		labelStatusMessage = new JLabel(DEFAULT_MESSAGE);
	}

	
	private void createProgressBar() {
		
		progressBar = new JProgressBar();
		
		progressBar.setPreferredSize(new Dimension(200, 28));					
	}
	
	
	public void setMessage(String status){
		labelStatusMessage.setText("Status do Serviço: " + status);
	}
	
	
	public void updateProgress(Integer progress){
		progressBar.setValue(progress);
	}
}