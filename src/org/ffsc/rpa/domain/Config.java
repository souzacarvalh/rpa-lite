package org.ffsc.rpa.domain;

import org.ffsc.rpa.types.Protocolo;

public class Config {

	private Protocolo protocolo;
	private String servidor;
	private String email;
	private String senha;
	private String dirCaixaEmail;
	private String dirGravacao;
	private Boolean apagarJaProcessados;

	public Protocolo getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(Protocolo protocolo) {
		this.protocolo = protocolo;
	}

	public String getServidor() {
		return servidor;
	}

	public void setServidor(String servidor) {
		this.servidor = servidor;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getDirCaixaEmail() {
		return dirCaixaEmail;
	}

	public void setDirCaixaEmail(String dirCaixaEmail) {
		this.dirCaixaEmail = dirCaixaEmail;
	}

	public String getDirGravacao() {
		return dirGravacao;
	}

	public void setDirGravacao(String dirGravacao) {
		this.dirGravacao = dirGravacao;
	}

	public Boolean getApagarJaProcessados() {
		return apagarJaProcessados;
	}

	public void setApagarJaProcessados(Boolean apagarJaProcessados) {
		this.apagarJaProcessados = apagarJaProcessados;
	}
}