package org.ffsc.rpa;

import java.awt.EventQueue;

import javax.swing.UIManager;

import org.ffsc.rpa.exceptions.RPAExceptionHandler;
import org.ffsc.rpa.files.FileManager;
import org.ffsc.rpa.types.RPAWindow;
import org.ffsc.rpa.ui.WindowManager;

import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;

@SuppressWarnings("deprecation")
public class Main {
 
	public static boolean init  = false;
	
	public static void main(String[] args) {
		try {
			
			//Parse arguments ...
			for(String arg: args){
				if(arg.equals("-init")){
					init = true;
				}
			}
			
			if(init){
				
				//Verify app environment ...
				FileManager.verificarEstruturaAplicativo();
			
				//Look and Feel
				UIManager.setLookAndFeel(new NimbusLookAndFeel());
				
				EventQueue.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						
						//Init: Create main window into to EDT...
						WindowManager.show(RPAWindow.MAIN_WINDOW);
					}
				});
			
			}
			
		} catch(Exception e) {
			new RPAExceptionHandler().handle(e);
		}
	}
}