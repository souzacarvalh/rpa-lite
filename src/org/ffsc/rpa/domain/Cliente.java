package org.ffsc.rpa.domain;

public class Cliente {

	private Long id;
	private String razao;
	private String fantasia;
	private String cnpj;
	private boolean processarSeEmit;
	private boolean processarSeDestinatario;

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
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public boolean isProcessarSeEmit() {
		return processarSeEmit;
	}

	public void setProcessarSeEmit(boolean processarSeEmit) {
		this.processarSeEmit = processarSeEmit;
	}

	public boolean isProcessarSeDestinatario() {
		return processarSeDestinatario;
	}

	public void setProcessarSeDestinatario(boolean processarSeDestinatario) {
		this.processarSeDestinatario = processarSeDestinatario;
	}
	
	@Override
	public String toString() {
		return this.fantasia;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		return true;
	}
}