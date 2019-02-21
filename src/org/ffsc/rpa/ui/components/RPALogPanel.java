package org.ffsc.rpa.ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.ffsc.rpa.util.DateUtils;

public class RPALogPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private final Dimension COMPONENT_SIZE = new Dimension(900, 170);
	
	private static final String INIT_TEXT    = new String("# Log de Atividade RPA");
	private static final String LINE_PATTERN = new String("\n[ #TIME# ] > #MESSAGE#");
	
	private JButton buttonCleanLog;
	private JTextArea logConsole;
	private JPanel panelLogOptions;
	private JScrollPane panelLog;
		
	public RPALogPanel() {
		
		//Creating components ...
		createConsoleLog();
		createCleanLogButton();
		createPanelLog();
		createPanelLogOption();
		
		//Initializing component ...
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEtchedBorder());
        setPreferredSize(COMPONENT_SIZE);
			
		this.add(panelLogOptions, BorderLayout.NORTH);
		this.add(panelLog, BorderLayout.CENTER);
	}
	
	
	private void createConsoleLog(){
		
		logConsole = new JTextArea("# Log de Atividade RPA");
		
		logConsole.setBackground(Color.BLACK);
		logConsole.setForeground(Color.GREEN);
		logConsole.setLineWrap(true);
		logConsole.setWrapStyleWord(true);
		logConsole.setEditable(false);
		logConsole.setVisible(true);
	}
	
	
	private void createCleanLogButton(){
		
		buttonCleanLog = new JButton();
		
		buttonCleanLog.setPreferredSize(new Dimension(25, 26));
							
		buttonCleanLog.setIcon(new ImageIcon(this.getClass().getResource("/resources/images/broom.png")));
		buttonCleanLog.setToolTipText("Limpar Log");
		
		buttonCleanLog.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				clean();
			}
		});
	}
	
	
	private void createPanelLogOption(){
		
		panelLogOptions = new JPanel();
		
		panelLogOptions.setPreferredSize(new Dimension(888, 30));				
		panelLogOptions.setLayout(new GridBagLayout());
		panelLogOptions.setBorder(BorderFactory.createEmptyBorder());
		panelLogOptions.setVisible(true);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.NONE;
		c.weightx = 1;
		c.anchor  = GridBagConstraints.EAST;
				
		c.gridx = 0;
		c.gridy = 0;
		
		panelLogOptions.add(buttonCleanLog, c);
	}
		
	
	private void createPanelLog(){
		
		panelLog =  new JScrollPane(logConsole);
		
		panelLog.setPreferredSize(new Dimension(888, 140));
	}
	
	
	public void log(String msg){
		
		String message = new String(LINE_PATTERN);
		
		message = message.replace("#TIME#", DateUtils.getTimeString());
		message = message.replace("#MESSAGE#", msg);
		
		logConsole.append(message);
		
		logConsole.setCaretPosition(logConsole.getText().length());
	}
	
	
	public void clean(){
		logConsole.setText(INIT_TEXT);
	}
}