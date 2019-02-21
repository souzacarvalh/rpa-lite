package org.ffsc.rpa.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.ffsc.rpa.exceptions.RPAException;
import org.ffsc.rpa.exceptions.RPAExceptionHandler;
import org.ffsc.rpa.types.RPAWindow;
import org.ffsc.rpa.ui.components.RPALogPanel;
import org.ffsc.rpa.ui.components.RPAMenuBar;
import org.ffsc.rpa.ui.components.RPAStatusBar;
import org.ffsc.rpa.ui.components.RPASystemTrayManager;
import org.ffsc.rpa.ui.components.RPAToolBar;
import org.ffsc.rpa.ui.components.RPATreeMenu;
import org.jdom2.JDOMException;

public class AppMainWindow extends JFrame implements WindowStateListener {

	private static final long serialVersionUID = 1L;
	
	public static final Integer MAIN_WINDOW_WIDTH  = 900;
	public static final Integer MAIN_WINDOW_HEIGHT = 800;
	
	private JPanel mainPanel;
	
	private JPanel centerPanel;
	private JPanel footerPanel;
	
	private RPAMenuBar menuBar;
	private RPAToolBar toolBar;
	private RPATreeMenu treeMenu;
	private RPALogPanel logPanel;
	private RPAStatusBar statusBar;

	public AppMainWindow(){	
		try {
			
			//Creating window components ...
			createTreeMenu();
			createCenterPanel();
			createFooterPanel();
			createMainMenu();
			createMainPanel();
			
			//Initializing window properties...			
			setTitle(RPAWindow.MAIN_WINDOW.getTitle());
			setSize(new Dimension(MAIN_WINDOW_WIDTH, MAIN_WINDOW_HEIGHT));
			setLayout(new BorderLayout());
			setDefaultLookAndFeelDecorated(true);
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			setResizable(false);
			setLocationRelativeTo(null);
			setIconImage(getWindowIcon());
			
			//Add window state listener
			addWindowStateListener(this);
			
			//Add components...		
			getContentPane().add(menuBar,   BorderLayout.NORTH);
			getContentPane().add(mainPanel, BorderLayout.CENTER);
			
			pack();	
		
		} catch (Exception e) {
			new RPAExceptionHandler().handle(e);
		}
	}
		
	
	private void createTreeMenu() throws IOException, JDOMException, SQLException, ClassNotFoundException{
		treeMenu = new RPATreeMenu();
	}
	
	
	private void createCenterPanel(){
		
		centerPanel = new JPanel();
		
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setPreferredSize(new Dimension(680, 400));
	}
	
	
	private void createFooterPanel(){
	
		footerPanel = new JPanel();
		
		footerPanel.setLayout(new BorderLayout());
		footerPanel.setPreferredSize(new Dimension(900, 200));
		
		logPanel = new RPALogPanel();
		
		footerPanel.add(logPanel, BorderLayout.CENTER);
		
		statusBar = new RPAStatusBar();
		
		footerPanel.add(statusBar, BorderLayout.SOUTH);
	}

	
	private void createMainMenu(){
		menuBar = new RPAMenuBar();
	}

	
	private void createMainPanel(){
		
		mainPanel = new JPanel();
		
		mainPanel.setLayout(new BorderLayout());
		
		toolBar = new RPAToolBar();
		
		mainPanel.add(toolBar, 	   BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(treeMenu,    BorderLayout.WEST);
		mainPanel.add(footerPanel, BorderLayout.SOUTH);
		
		mainPanel.setPreferredSize(new Dimension(900, 770));
		
		mainPanel.setVisible(true);
	}
	
	
	public Image getWindowIcon(){
		
		URL iconPath = AppMainWindow.class.getResource("/resources/images/email.png");
		ImageIcon icon = new ImageIcon(iconPath);
		
		return icon.getImage();
	}

	
	public RPATreeMenu getTreeMenu(){
		return treeMenu;
	}
	
	
	public RPALogPanel getLogPanel(){
		return logPanel;
	}
	
	
	public RPAStatusBar getStatusBar(){
		return statusBar;
	}

	
	@Override
	public void windowStateChanged(WindowEvent evt) {
		
		if(evt.getNewState() == ICONIFIED || evt.getNewState() == 7) {
			try {
				
				new RPASystemTrayManager().putAppInTray();
				
			} catch (RPAException e) {
				new RPAExceptionHandler().handle(e);
			}
		}
	}
}