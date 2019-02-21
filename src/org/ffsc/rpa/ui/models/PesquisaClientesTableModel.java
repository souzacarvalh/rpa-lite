package org.ffsc.rpa.ui.models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.ffsc.rpa.domain.Cliente;

public class PesquisaClientesTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private List<Cliente> linhas = null;
	private String[] colunas = {"Id", "Razão", "Fantasia", "CNPJ"};

	public String getColumnName(int col) {
		return colunas[col];
	}

	public String[] getColunas() {
		return colunas;
	}

	public List<Cliente> getLinhas() {
		return linhas;
	}

	public void setLinhas(List<Cliente> list) {
		linhas = list;
	}
	
	public int getColumnCount() {
		return getColunas().length;
	}

	public int getRowCount() {
		return getLinhas().size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		
		switch (columnIndex) {
			case 0:
				return ((Cliente) getLinhas().get(rowIndex)).getId();
			case 1:
				return ((Cliente) getLinhas().get(rowIndex)).getRazao();
			case 2:
				return ((Cliente) getLinhas().get(rowIndex)).getFantasia();
			case 3:
				return ((Cliente) getLinhas().get(rowIndex)).getCnpj();

			default: return "<Não Encontrado>";
		}
	}
}