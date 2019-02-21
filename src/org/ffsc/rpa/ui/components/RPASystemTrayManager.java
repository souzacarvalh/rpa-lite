package org.ffsc.rpa.ui.components;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.ffsc.rpa.exceptions.RPAException;
import org.ffsc.rpa.exceptions.RPAExceptionHandler;
import org.ffsc.rpa.process.EmailProcessFacade;
import org.ffsc.rpa.ui.WindowManager;

public class RPASystemTrayManager {

	private static final String DEFAULT_TOOLTIP = "RPA Processamento de Emails";
	
	private SystemTray systemTray;
	private TrayIcon trayIcon;
	
	public RPASystemTrayManager() throws RPAException {
		
		if(SystemTray.isSupported()) {
			
			systemTray = SystemTray.getSystemTray();
			
			createTrayIcon();
			
		} else {
			throw new RPAException("Modo System Tray não suportado.");
		}
	}
	
	
	private void createTrayIcon(){
		
		trayIcon = new TrayIcon(WindowManager.getApplication().getWindowIcon(), DEFAULT_TOOLTIP, createPopupMenu());
		
		trayIcon.setImageAutoSize(true);
		
		//Add Mouse event listener
		trayIcon.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent evt) {
				if(evt.getClickCount() == 2) {
					
					WindowManager.getApplication().setState(Frame.MAXIMIZED_BOTH);
					
					systemTray.remove(trayIcon);
					
					WindowManager.getApplication().setVisible(true);
				}
			}
		});
	}
	
	private PopupMenu createPopupMenu(){
	
		PopupMenu popup = new PopupMenu();

		//Create popup menu itens
		MenuItem showOption = new MenuItem("Restaurar");
		
		showOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				
				systemTray.remove(trayIcon);
				
				WindowManager.getApplication().setVisible(true);
			}
		});
		
		
		MenuItem initProcessOption = new MenuItem("Iniciar Processamento");
		
		initProcessOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				new EmailProcessFacade().iniciarProcessamentoEmails();
			}
		});
		
		
		MenuItem stopProcessOption   = new MenuItem("Parar Processamento");
		
		stopProcessOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				new EmailProcessFacade().pararProcessamentoEmails();
			}
		});
		
		
		MenuItem exitOption = new MenuItem("Fechar");
				
		exitOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				
				//Remove system tray icon
				systemTray.remove(trayIcon);
				
				System.exit(0);
			}
		});
		
		popup.add(showOption);
		popup.addSeparator();
		popup.add(initProcessOption);
		popup.add(stopProcessOption);
		popup.addSeparator();
		popup.add(exitOption);
			
		return popup;
	}
	
	
	public void putAppInTray(){
		try {

			systemTray.add(trayIcon);
			
			WindowManager.getApplication().setVisible(false);
			
		} catch (AWTException e) {
			new RPAExceptionHandler().handle(e);
		}
	}
	
	
	public void setTrayTooltipText(String msg)
	{
		trayIcon.setToolTip(msg);
	}
}