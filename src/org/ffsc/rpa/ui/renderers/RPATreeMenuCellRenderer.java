package org.ffsc.rpa.ui.renderers;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;



import org.ffsc.rpa.domain.Cliente;

public class RPATreeMenuCellRenderer extends DefaultTreeCellRenderer implements TreeCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
	{
		
		super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		
		if(value instanceof String) {
			setIcon(new ImageIcon(getClass().getResource("/resources/images/accountant.png")));
		}
		
		if(value instanceof Cliente) {
			setIcon(new ImageIcon(getClass().getResource("/resources/images/client.png")));
		}
				
		return this;
	}
}