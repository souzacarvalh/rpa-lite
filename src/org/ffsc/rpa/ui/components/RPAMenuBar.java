package org.ffsc.rpa.ui.components;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.ffsc.rpa.types.RPAWindow;
import org.ffsc.rpa.ui.WindowManager;

public class RPAMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1L;

	private final Dimension COMPONENT_SIZE = new Dimension(900, 30);
	
	private JMenu menuCadastro;
	private JMenu menuOpcoes;
	private JMenu menuAjuda;
	
	private ArrayList<JMenuItem> itensMenuCadastro;
	private ArrayList<JMenuItem> itensMenuOpcoes;
	private ArrayList<JMenuItem> itensMenuAjuda;
	
	public RPAMenuBar() {
		
		//Init Menu Item lists
		itensMenuCadastro = new ArrayList<JMenuItem>();
		itensMenuOpcoes	  = new ArrayList<JMenuItem>();
		itensMenuAjuda	  = new ArrayList<JMenuItem>();
		
		//Create Menu Itens
		createMenuItemCliente();
		createMenuItemConfiguracoes();
		createMenuItemAbout();
		
		//Create Menus
		createMenuCadastro();
		createMenuOpcoes();
		createMenuAjuda();
		
		//Init component ...
		setPreferredSize(COMPONENT_SIZE);
		
		this.add(menuCadastro);
		this.add(menuOpcoes);
		this.add(menuAjuda);
	}
	
	
	private void createMenuCadastro(){
		
		menuCadastro = new JMenu("Cadastro");
		
		for(JMenuItem item: itensMenuCadastro){
			menuCadastro.add(item);
		}
		
		menuCadastro.setVisible(true);
	}
	

	private void createMenuOpcoes(){
			
		menuOpcoes = new JMenu("Configuração");

		for(JMenuItem item: itensMenuOpcoes){
			menuOpcoes.add(item);
		}
	}
	
	
	private void createMenuAjuda(){
		
		menuAjuda = new JMenu("Ajuda");

		for(JMenuItem item: itensMenuAjuda){
			menuAjuda.add(item);
		}
	}
	
	
	private void createMenuItemCliente(){
				
		JMenuItem clienteMenuItem = new JMenuItem("Clientes ...");	
		
		clienteMenuItem.setPreferredSize(new Dimension(150, 26));
		
		clienteMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				WindowManager.show(RPAWindow.CUSTOMERS_WINDOW);
			}
		});
				
		itensMenuCadastro.add(clienteMenuItem);	
	}
	
	
	private void createMenuItemConfiguracoes(){
		
		JMenuItem configMenuItem = new JMenuItem("Opções ...");
		
		configMenuItem.setPreferredSize(new Dimension(150, 26));
		
		configMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				WindowManager.show(RPAWindow.CONFIGURATION_WINDOW);
			}
		});
		
		itensMenuOpcoes.add(configMenuItem);
	}
	
	
	private void createMenuItemAbout(){
	
		JMenuItem aboutMenuItem = new JMenuItem("Sobre o RPA ...");
		
		aboutMenuItem.setPreferredSize(new Dimension(150, 26));
		
		aboutMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				WindowManager.show(RPAWindow.INFO_ABOUT_WINDOW);
			}
		});
		
		
		itensMenuAjuda.add(aboutMenuItem);	
	}
}