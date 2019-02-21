package org.ffsc.rpa.ui.components;

import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreeSelectionModel;

import org.ffsc.rpa.domain.Cliente;
import org.ffsc.rpa.persistence.ClienteDAOFactory;
import org.ffsc.rpa.persistence.db.ClienteSearchFilter;
import org.ffsc.rpa.persistence.interfaces.ClienteDAO;
import org.ffsc.rpa.ui.models.RPATreeMenuModel;
import org.ffsc.rpa.ui.renderers.RPATreeMenuCellRenderer;

public class RPATreeMenu extends JScrollPane {

	private static final long serialVersionUID = 1L;

	private JTree treeMenu;
	
	public RPATreeMenu() {
		
		createTreeMenu();
		
		setBorder(BorderFactory.createEtchedBorder());
		setPreferredSize(new Dimension(220, 560));
		
		this.setViewportView(treeMenu);
	}
	
	
	private void createTreeMenu() {
		
		//Recupera clientes cadastrados
		ClienteDAO dao = ClienteDAOFactory.create();		
		
		List<Cliente> clientes = dao.find(new ClienteSearchFilter());
				
		treeMenu = new JTree(new RPATreeMenuModel(clientes));
		
		//Seta um CellRenderer customizado para poder mudar icones
		treeMenu.setCellRenderer(new RPATreeMenuCellRenderer());
		
		treeMenu.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	}
	
	
	public void refresh() {
		
		ClienteDAO dao = ClienteDAOFactory.create();		
		
		List<Cliente> clientes = dao.find(new ClienteSearchFilter());
		
		treeMenu.setModel(new RPATreeMenuModel(clientes));
		
		RPATreeMenuModel model = (RPATreeMenuModel) treeMenu.getModel();
		
		model.fireTreeStructureChanged();
	}
}