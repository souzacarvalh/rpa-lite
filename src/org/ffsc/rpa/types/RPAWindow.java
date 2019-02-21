package org.ffsc.rpa.types;

public enum RPAWindow {

	MAIN_WINDOW("RPA - Lite Version"),
	CONFIGURATION_WINDOW("RPA - Opções de Processamento"),
	CUSTOMERS_WINDOW("RPA - Cadastro de Clientes"),
	INFO_ABOUT_WINDOW("Sobre o RPA ...");
	
	private String title;
	
	private RPAWindow(String title) {
		this.title = title;
	}
	
	public String getTitle(){
		return this.title;
	}
}