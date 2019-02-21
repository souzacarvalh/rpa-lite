package org.ffsc.rpa.ui.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import org.ffsc.rpa.process.EmailProcessFacade;
import org.ffsc.rpa.types.RPAWindow;
import org.ffsc.rpa.ui.WindowManager;

public class RPAToolBar extends JPanel {

	private static final long serialVersionUID = 1L;

	private final Dimension COMPONENT_SIZE = new Dimension(900, 40);
	
	private JButton buttonPlay;
	private JButton buttonStop;
	private JButton buttonOrganize;
	private JButton buttonSchedule;
	private JButton buttonSeeLog;
	
	private JLabel toolBarIcon;
	
	public RPAToolBar() {
		
		//Creating buttons ...
		createPlayButton();
		createStopButton();
		createOrganizeButton();
		createScheduleButton();
		createSeeLogButton();	
		createToolBarIcon();
		
		//Init component ...
		this.setPreferredSize(COMPONENT_SIZE);		
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0, 5, 0, 0);
		
		c.gridx = 0;
		c.gridy = 0;
		
		this.add(buttonPlay, c);
				
		c.gridx   = 1;
		c.gridy   = 0;
		
		this.add(buttonStop, c);
		
		c.gridx   = 2;
		c.gridy   = 0;
				
		c.fill = GridBagConstraints.VERTICAL;
		
		c.insets = new Insets(0, 4, 0, 0);
		
		this.add(new JSeparator(SwingConstants.VERTICAL), c);
		
		c.fill = GridBagConstraints.NONE;
		
		c.gridx   = 3;
		c.gridy   = 0;
		
		this.add(buttonOrganize, c);
		
		c.gridx   = 4;
		c.gridy   = 0;
		
		this.add(buttonSchedule, c);
		
		c.gridx   = 5;
		c.gridy   = 0;
		
		this.add(buttonSeeLog, c);
		
		c.weightx = 1;
		c.anchor  = GridBagConstraints.EAST;
		c.insets  = new Insets(1, 0, 1, 3);
			
		c.gridx = 6;
		c.gridy = 0;
		
		this.add(toolBarIcon, c);
	}
		
	
	private void createPlayButton(){
		
		JButton jb = new JButton();
		
		jb.setPreferredSize(new Dimension(30, 30));	
		jb.setIcon(new ImageIcon(getClass().getResource("/resources/images/play.png")));
		jb.setToolTipText("Iniciar processamento de emails");
		
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				new EmailProcessFacade().iniciarProcessamentoEmails();
			}
		});
		
		buttonPlay = jb;
	}
	
	
	private void createStopButton(){
		
		JButton jb = new JButton();
		
		jb.setPreferredSize(new Dimension(30, 30));	
		jb.setIcon(new ImageIcon(getClass().getResource("/resources/images/stop.png")));
		jb.setToolTipText("Parar processamento de emails");
		
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				new EmailProcessFacade().pararProcessamentoEmails();
			}
		});
		
		buttonStop = jb;
	}
		
	
	private void createOrganizeButton(){
		
		JButton jb = new JButton();
		
		jb.setPreferredSize(new Dimension(30, 30));	
		jb.setIcon(new ImageIcon(getClass().getResource("/resources/images/organize.png")));
		jb.setToolTipText("Reorganizar arquivos não processados");
		
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				new EmailProcessFacade().organizarNaoProcessados();
			}
		});
		
		buttonOrganize = jb;
	}
	
	
	private void createScheduleButton(){
		
		JButton jb = new JButton();
		
		jb.setPreferredSize(new Dimension(30, 30));	
		jb.setIcon(new ImageIcon(getClass().getResource("/resources/images/schedule.png")));
		jb.setToolTipText("Agendar execução do processamento de emails nf-e");
		
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				WindowManager.show(RPAWindow.INFO_ABOUT_WINDOW);
			}
		});
		
		buttonSchedule = jb;
	}
	
	
	private void createSeeLogButton(){
		
		JButton jb = new JButton();
		
		jb.setPreferredSize(new Dimension(30, 30));	
		jb.setIcon(new ImageIcon(getClass().getResource("/resources/images/log.png")));
		jb.setToolTipText("Verificar arquivo de log");
		
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				//TODO Develop See Log Button
			}
		});
		
		buttonSeeLog = jb;
	}
	
	
	private void createToolBarIcon(){
		
		ImageIcon icon = new ImageIcon(getClass().getResource("/resources/images/nfe.png"));
		
		toolBarIcon = new JLabel(icon);
	}
}