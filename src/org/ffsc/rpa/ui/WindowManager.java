package org.ffsc.rpa.ui;

import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.ffsc.rpa.types.RPAWindow;

public class WindowManager {

	private static AppMainWindow mainWindow = null;
	private static InfoAboutDialog infoWindow = null;
	private static ConfigDialog configWindow = null;
	private static ClienteDialog customerWindow = null;

	static {
		mainWindow = new AppMainWindow();
	};

	private WindowManager() {
	};

	public static AppMainWindow getApplication() {
		return mainWindow;
	}

	public static void show(RPAWindow routine) {

		Window window = getWindow(routine);

		if (window != null) {

			if (window instanceof JFrame) {
				((JFrame) window).setTitle(routine.getTitle());
			}

			if (window instanceof JDialog) {
				((JDialog) window).setTitle(routine.getTitle());
				((JDialog) window)
						.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				((JDialog) window).setResizable(false);
				((JDialog) window).setModal(true);
			}

			window.setVisible(true);
		}
	}

	private static Window getWindow(RPAWindow routine) {

		if (RPAWindow.MAIN_WINDOW == routine) {
			return mainWindow;
		}

		if (RPAWindow.CONFIGURATION_WINDOW == routine) {
			if (configWindow == null) {
				configWindow = new ConfigDialog();
			}

			return configWindow;
		}

		if (RPAWindow.CUSTOMERS_WINDOW == routine) {
			if (customerWindow == null) {
				customerWindow = new ClienteDialog();
			}

			return customerWindow;
		}

		if (RPAWindow.INFO_ABOUT_WINDOW == routine) {
			if (infoWindow == null) {
				infoWindow = new InfoAboutDialog();
			}

			return infoWindow;
		}

		return null;
	}

	public static void showInfoMessage(String message) {
		JOptionPane.showMessageDialog(infoWindow, message,
				"RPA: Informação do Sistema ...",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public static void showWarningMessage(String message) {
		JOptionPane.showMessageDialog(infoWindow, message, "RPA: Alerta ...",
				JOptionPane.WARNING_MESSAGE);
	}

	public static void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(infoWindow, message, "RPA: Erro ...",
				JOptionPane.ERROR_MESSAGE);
	}
}