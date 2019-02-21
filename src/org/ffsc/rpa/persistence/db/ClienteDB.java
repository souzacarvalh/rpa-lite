package org.ffsc.rpa.persistence.db;

import java.util.List;

import org.ffsc.rpa.domain.Cliente;
import org.ffsc.rpa.persistence.interfaces.ClienteDAO;

public class ClienteDB implements ClienteDAO {

	@Override
	public Cliente save(Cliente cli) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cliente update(Cliente cli) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Cliente> find(ClienteSearchFilter filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCnpjJaCadastrado(Cliente cliente) {
		// TODO Auto-generated method stub
		return false;
	}
}