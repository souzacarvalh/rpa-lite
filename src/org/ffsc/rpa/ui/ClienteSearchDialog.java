package org.ffsc.rpa.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import org.ffsc.rpa.domain.Cliente;
import org.ffsc.rpa.exceptions.RPAExceptionHandler;
import org.ffsc.rpa.persistence.ClienteDAOFactory;
import org.ffsc.rpa.persistence.db.ClienteSearchFilter;
import org.ffsc.rpa.persistence.interfaces.ClienteDAO;
import org.ffsc.rpa.ui.models.PesquisaClientesTableModel;

public class ClienteSearchDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JPanel mainPanel;
	private JPanel fieldPanel;
	private JScrollPane tablePanel;
	private JLabel labelCodigo;
	private JLabel labelRazao;
	private JLabel labelFantasia;
	private JLabel labelCnpj;
	private JTextField fieldCodigo;
	private JTextField fieldRazao;
	private JTextField fieldFantasia;
	private JFormattedTextField fieldCnpj;
	private JButton buttonPesquisar;
	private JTable tableResultado;
	private Cliente clienteSelecionado;
	
	public ClienteSearchDialog(Component parent){
		
		//Initialize Components...
		createLabelCodigo();
		createLabelRazao();
		createLabelFantasia();
		createLabelCnpj();
		createFieldCodigo();
		createFieldRazao();
		createFieldFantasia();
		createFieldCnpj();
		createButtonPesquisar();
		createTableResultado();
		createFieldPanel();
		createTablePanel();
		createMainPanel();
		
		//Initialize properties...
		setSize(new Dimension(500, 430));
		setLocationRelativeTo(parent);
		setIconImage(getIconeJanela());
		setTitle("Pesquisar Clientes");
		setResizable(false);
		setModal(true);
		
		getContentPane().add(mainPanel);
	}
		
	private void createMainPanel(){
		
		mainPanel = new JPanel();
		
		mainPanel.setPreferredSize(new Dimension(496, 396));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		mainPanel.setLayout(new BorderLayout());
		
		mainPanel.add(fieldPanel, BorderLayout.NORTH);
		mainPanel.add(tablePanel, BorderLayout.CENTER);
	}
	
	private void createFieldPanel(){
		
		fieldPanel = new JPanel();
		
		fieldPanel.setPreferredSize(new Dimension(488, 120));
		fieldPanel.setBorder(BorderFactory.createEtchedBorder());
		fieldPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.anchor = GridBagConstraints.WEST;
		c.fill   = GridBagConstraints.NONE;
		c.insets = new Insets(2, 3, 0, 0);
		
		c.gridx = 0;
		c.gridy = 0;
		
		fieldPanel.add(labelCodigo, c);
		
		c.gridx = 0;
		c.gridy = 1;
		
		fieldPanel.add(labelRazao, c);
		
		c.gridx = 0;
		c.gridy = 2;
		
		fieldPanel.add(labelFantasia, c);
		
		c.gridx = 0;
		c.gridy = 3;
		
		fieldPanel.add(labelCnpj, c);
		
		c.weightx = 1;
		c.gridwidth = 2;
		c.insets = new Insets(2, 6, 0, 0);
		
		c.gridx = 1;
		c.gridy = 0;
	
		fieldPanel.add(fieldCodigo, c);
		
		c.gridx = 1;
		c.gridy = 1;
		
		fieldPanel.add(fieldRazao, c);
		
		c.gridx = 1;
		c.gridy = 2;
				
		fieldPanel.add(fieldFantasia, c);
		
		c.gridx = 1;
		c.gridy = 3;
		
		c.insets = new Insets(2, 6, 2, 0);
						
		fieldPanel.add(fieldCnpj, c);
		
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(2, 6, 2, 2);
		
		c.gridx = 2;
		c.gridy = 3;
		
		fieldPanel.add(buttonPesquisar, c);
	}
	
	private void createTablePanel(){
		
		tablePanel = new JScrollPane(tableResultado);
		
		tablePanel.setPreferredSize(new Dimension(488, 258));
		tablePanel.setBorder(BorderFactory.createEtchedBorder());
	}
	
	private void createLabelCodigo(){
		labelCodigo = new JLabel("Código");
	}
	
	private void createLabelRazao(){
		labelRazao = new JLabel("Razão");
	}
	
	private void createLabelFantasia(){
		labelFantasia = new JLabel("Fantasia");
	}
	
	private void createLabelCnpj(){
		labelCnpj = new JLabel("CNPJ");
	}
	
	private void createFieldCodigo(){
		
		fieldCodigo = new JTextField();
		
		fieldCodigo.setPreferredSize(new Dimension(50,26));
	}

	private void createFieldRazao(){
		
		fieldRazao = new JTextField();
		
		fieldRazao.setPreferredSize(new Dimension(250,26));
		fieldRazao.setName("campo_razao");
	}
	
	private void createFieldFantasia(){
		
		fieldFantasia = new JTextField();
		
		fieldFantasia.setPreferredSize(new Dimension(200,26));
	}
	
	private void createFieldCnpj(){
		try {
			
			MaskFormatter mascaraCnpj = new MaskFormatter("##.###.###/####-##");
			
			mascaraCnpj.setValidCharacters("0123456789");
			
			fieldCnpj = new JFormattedTextField(mascaraCnpj);
			
			fieldCnpj.setPreferredSize(new Dimension(125,26));
			fieldCnpj.setFocusLostBehavior(JFormattedTextField.COMMIT);
		
		} catch (ParseException e) {
			new RPAExceptionHandler().handle(e);
		}
	}
	
	private void createButtonPesquisar(){
		
		buttonPesquisar = new JButton();
		
		buttonPesquisar.setText("Pesquisar");
		buttonPesquisar.addActionListener(this);
	}
	
	private void createTableResultado(){
		
		List<Cliente> dados = new ArrayList<Cliente>();
		
		tableResultado = new JTable();

		PesquisaClientesTableModel model = new PesquisaClientesTableModel();
		
		model.setLinhas(dados);
		
		tableResultado.setPreferredSize(new Dimension(488, 214));
		tableResultado.setModel(model);
		tableResultado.setFillsViewportHeight(true);
		
		//Set column widths
		tableResultado.getColumnModel().getColumn(0).setPreferredWidth(5);
		tableResultado.getColumnModel().getColumn(1).setPreferredWidth(100);
		tableResultado.getColumnModel().getColumn(3).setPreferredWidth(65);
		
		//add mouse listener
		tableResultado.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt){
				if(evt.getClickCount() == 2){
					
					int linha = tableResultado.getSelectedRow();
					
					if(linha != -1){
						
						clienteSelecionado = new Cliente();
						
						clienteSelecionado.setId(Long.valueOf(tableResultado.getValueAt(linha, 0).toString()));
						clienteSelecionado.setRazao(tableResultado.getValueAt(linha, 1).toString());
						clienteSelecionado.setFantasia(tableResultado.getValueAt(linha, 2).toString());
						clienteSelecionado.setCnpj( tableResultado.getValueAt(linha, 3).toString());
						
						//Close this window
						ClienteSearchDialog.this.dispose();
					}	
				}
			}
		});
	}
			
	private Image getIconeJanela(){
		
		URL path = this.getClass().getResource("/resources/images/search.png");
		
		ImageIcon icon = new ImageIcon(path);
		
		return icon.getImage();
	}
	
	public void updateDadosTableResultado(List<Cliente> dados){
		
		PesquisaClientesTableModel model = (PesquisaClientesTableModel) tableResultado.getModel();
		
		model.setLinhas(dados);
		
		model.fireTableDataChanged();
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if(buttonPesquisar == evt.getSource()){
			pesquisar();	
		}
	}
	
	public void pesquisar(){
		
		ClienteSearchFilter filter = new ClienteSearchFilter();
		
		if(!"".equals(fieldCodigo.getText())){
			filter.setId(Long.valueOf(fieldCodigo.getText()));
		}
		
		filter.setRazao(fieldRazao.getText());
		filter.setFantasia(fieldFantasia.getText());
		filter.setCnpj(fieldCnpj.getText());
		
		ClienteDAO dao =  ClienteDAOFactory.create();
				
		updateDadosTableResultado(dao.find(filter));
	}
	
	public Cliente getClienteSelecionado(){
		return clienteSelecionado;
	}
}