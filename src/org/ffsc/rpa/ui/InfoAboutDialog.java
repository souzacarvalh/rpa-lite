package org.ffsc.rpa.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class InfoAboutDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JButton buttonOk;
	private JLabel labelInfo;
	private JPanel panelBanner;
	private JPanel panelInfo;
	private JPanel panelButtons;
		
	//App Informations
	private static final String VERSAO        = "2.0.0b"; 
	private static final String RELEASE_DATE  = "01/12/2013";
	private static final String JAVAC  		  = "Java 1.7";
	private static final String JAVA_VERSION  = System.getProperty("java.version");
	private static final String OS_VERSION	  = System.getProperty("os.name");
	
	private static final String TEXTO_PADRAO  = "<html>" +
													"<body>" +
													"<b>Versão: </b>#VERSAO#<br/>" +
													"<b>Data da Release: </b>#RELEASE_DATE#<br/>" +
													"<b>Compilação: </b>#JAVAC#<br/>" +
													"<b>Sistema Operacional: </b>#OS#<br/>" +
													"<b>Memória Alocada: </b>#MEMORY#<br/>" +
													"<b>Versão do Java: </b>#JAVA_VERSION#<br/><br/><br/>" +
													"<b>RPA Processamento de Emails NFe</b><br/>" +
													"Copyright © 2012 - Felipe Carvalho/ Jonathan Oliveira<br/>" +
													"Todos os direitos reservados. <br/><br/>" +
													"<b>ChilkatRAR 3rd Party RAR Decompress Feature</b><br/>" +
													"Copyright © 2000-2012 - Chilkat Software, Inc.<br/>" +
													"All rights reserved" +
													"<body>" +
												 "</html>";
	
	public InfoAboutDialog() {
		
		createLabelInfo();
		createButtonOK();
		createPanelBanner();
		createPanelInfo();
		createPanelButtons();
							
		//Initialize properties...
		int x = (int) WindowManager.getApplication().getLocationOnScreen().getX() + 250;
		int y = (int) WindowManager.getApplication().getLocationOnScreen().getY() + 150;
		
		
		setLayout(new BorderLayout());
		setIconImage(getIconeJanela());
		setSize(new Dimension(450, 400));
		setLocation(x, y);
		
		getContentPane().add(this.panelBanner,  BorderLayout.NORTH);
		getContentPane().add(this.panelInfo,    BorderLayout.CENTER);
		getContentPane().add(this.panelButtons, BorderLayout.SOUTH);	
	}

	private void createPanelBanner(){
		
		panelBanner = new JPanel();
				
		panelBanner.setBorder(BorderFactory.createEtchedBorder());
		panelBanner.setBackground(new Color(101, 136, 169));
		panelBanner.setPreferredSize(new Dimension(450, 80));
	}
	
	private void createPanelInfo(){
		
		panelInfo = new JPanel();
		
		panelInfo.setBorder(BorderFactory.createEtchedBorder());	
		panelInfo.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill   = GridBagConstraints.NONE;
				
		c.insets = new Insets(7, 7, 1, 2);
		
		c.weightx = 1;
		c.weighty = 1;
		
		panelInfo.add(this.labelInfo, c);
	}
	
	private void createPanelButtons(){
		
		panelButtons = new JPanel();
		
		panelButtons.setPreferredSize(new Dimension(450, 40));
		panelButtons.setBorder(BorderFactory.createEtchedBorder());
		panelButtons.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.anchor = GridBagConstraints.EAST;
		c.fill   = GridBagConstraints.NONE;
				
		c.insets = new Insets(1, 0, 1, 2);
		c.weightx = 1;
		
		panelButtons.add(this.buttonOk, c);
	}
	
	private void createButtonOK(){
		
		buttonOk = new JButton();

		buttonOk.setPreferredSize(new Dimension(80, 30));
		buttonOk.setText("Ok");		
		
		buttonOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				InfoAboutDialog.this.setVisible(false);
			}
		});
	}

	
	private void createLabelInfo(){
		
		labelInfo = new JLabel();
		
		String textoPadrao = TEXTO_PADRAO;
		
		textoPadrao = textoPadrao.replace("#VERSAO#", VERSAO);
		textoPadrao = textoPadrao.replace("#RELEASE_DATE#", RELEASE_DATE);
		textoPadrao = textoPadrao.replace("#JAVAC#", JAVAC);
		textoPadrao = textoPadrao.replace("#OS#", OS_VERSION);
		textoPadrao = textoPadrao.replace("#MEMORY#", getMemoryInfo());
		textoPadrao = textoPadrao.replace("#JAVA_VERSION#", JAVA_VERSION);
		
		labelInfo.setText(textoPadrao);
	}
	
	
	private Image getIconeJanela(){
		
		URL iconPath = AppMainWindow.class.getResource("/resources/images/info.png");
		
		ImageIcon icon = new ImageIcon(iconPath);
		
		return icon.getImage();
	}
	
	
	private String getMemoryInfo(){
		
		/*
		 * Bytes >> 10 (KB) / bytes >> 20 (MB) / bytes >> 30 (GB) 
		 */
		int memory = (int) Runtime.getRuntime().maxMemory() >> 20;
				
		return String.valueOf(memory).concat(" mb");
	}
}