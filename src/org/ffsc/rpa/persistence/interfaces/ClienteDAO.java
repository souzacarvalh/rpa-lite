package org.ffsc.rpa.persistence.interfaces;

import java.util.List;

import org.ffsc.rpa.domain.Cliente;
import org.ffsc.rpa.exceptions.RPABusinessException;
import org.ffsc.rpa.persistence.db.ClienteSearchFilter;

public interface ClienteDAO {

	public Cliente save(Cliente cli) throws RPABusinessException;

	public Cliente update(Cliente cli) throws RPABusinessException;

	public boolean delete(Long id);

	public List<Cliente> find(ClienteSearchFilter filter);
	
	public boolean isCnpjJaCadastrado(Cliente cliente);
}