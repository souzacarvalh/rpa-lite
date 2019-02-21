package org.ffsc.rpa.persistence.db;

public class ClienteSearchFilter {

	private Long id;
	private String razao;
	private String fantasia;
	private String cnpj;
	private boolean somenteEmitentes;
	private boolean somenteDestinatarios;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRazao() {
		return razao;
	}

	public void setRazao(String razao) {
		this.razao = razao;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public String getCnpj() {
		return (cnpj != null) ? cnpj.replaceAll("[\\D]", "") : "";
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public boolean isSomenteEmitentes() {
		return somenteEmitentes;
	}

	public void setSomenteEmitentes(boolean somenteEmitentes) {
		this.somenteEmitentes = somenteEmitentes;
	}

	public boolean isSomenteDestinatarios() {
		return somenteDestinatarios;
	}

	public void setSomenteDestinatarios(boolean somenteDestinatarios) {
		this.somenteDestinatarios = somenteDestinatarios;
	}
}