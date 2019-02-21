package org.ffsc.rpa.ui.models;

import java.util.List;

import org.ffsc.rpa.domain.Cliente;

public class RPATreeMenuModel extends AbstractTreeModel {

	private String root = "Contador";
	private List<Cliente> clientes;
		
	public RPATreeMenuModel(List<Cliente> clientes) {
		this.clientes = clientes;
	}
	
	
	@Override
	public Object getRoot() {
		return root;
	}
	
	
	@Override
	public Object getChild(Object parent, int index) {

		if(parent == root)
			return clientes.get(index);
		
		throw new IllegalArgumentException("Invalid parent class " + parent.getClass().getSimpleName());  
	}
	
	
	@Override
	public int getChildCount(Object parent) {
		
		if(parent == root)
			return clientes.size();
		
		if(parent instanceof Cliente)
			return 10;
		
		throw new IllegalArgumentException("Invalid parent class " + parent.getClass().getSimpleName());  
	}
		
	
	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if(parent == root)
			return clientes.indexOf(child);
		
		throw new IllegalArgumentException("Invalid parent class " + parent.getClass().getSimpleName());
	}
	
	
	@Override
	public boolean isLeaf(Object node) {
		return node instanceof Cliente;
	}
	
	
	@Override
	public void fireTreeNodesChanged(Object source, Object[] path, int[] childIndices, Object[] children){
		super.fireTreeStructureChanged();
	}
}