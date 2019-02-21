package org.ffsc.rpa.types;

public enum Protocolo {

	IMAP("IMAP"), 
	IMAPS("IMAPS");

	private String label;

	private Protocolo(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}
}