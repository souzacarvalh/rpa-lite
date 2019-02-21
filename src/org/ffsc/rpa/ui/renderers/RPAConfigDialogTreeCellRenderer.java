package org.ffsc.rpa.ui.renderers;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

public class RPAConfigDialogTreeCellRenderer extends DefaultTreeCellRenderer
		implements TreeCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		super.getTreeCellRendererComponent(tree, value, selected, expanded,
				leaf, row, hasFocus);

		if ("Configurações".equals(value.toString())) {

			setIcon(new ImageIcon(getClass().getResource(
					"/resources/images/config.png")));
		}

		if ("Conta de Email".equals(value.toString())) {

			setIcon(new ImageIcon(getClass().getResource(
					"/resources/images/email.png")));
		}

		if ("Diretórios".equals(value.toString())) {

			setIcon(new ImageIcon(getClass().getResource(
					"/resources/images/dir.png")));
		}

		if ("Proxy".equals(value.toString())) {

			setIcon(new ImageIcon(getClass().getResource(
					"/resources/images/proxy.png")));
		}

		return this;
	}
}