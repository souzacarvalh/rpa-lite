package org.ffsc.rpa.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import org.ffsc.rpa.domain.Cliente;
import org.ffsc.rpa.exceptions.RPABusinessException;
import org.ffsc.rpa.exceptions.RPAExceptionHandler;
import org.ffsc.rpa.persistence.ClienteDAOFactory;
import org.ffsc.rpa.persistence.interfaces.ClienteDAO;

public class ClienteDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static final String STATUS_REGISTRO = "status_registro";

	private String statusRegistro = "novo";
	
	//Components
	private JPanel topPanel;
	private JPanel centerPanel;
	private JPanel formPanel;
	private JPanel processOptionsPanel;
	private JLabel labelId;
	private JLabel labelRazao;
	private JLabel labelFantasia;
	private JLabel labelCnpj;
	private JTextField fieldId;
	private JTextField fieldRazao;
	private JTextField fieldFantasia;
	private JFormattedTextField fieldCnpj;
	private JCheckBox checkProcessEmit;
	private JCheckBox checkProcessDest;
	private JButton buttonNovo;
	private JButton buttonEditar;
	private JButton buttonSalvar;
	private JButton buttonPesquisar;
	private JButton buttonExcluir;
	private JButton buttonSair;

	public ClienteDialog() {		
		
		//Initialize components...
		createLabelId();
		createFieldId();
		createLabelRazao();
		createFieldRazao();
		createLabelFantasia();
		createFieldFantasia();
		createLabelCnpj();
		createFieldCnpj();
		createCheckProcessEmit();
		createCheckProcessDest();
		createButtonNovo();
		createButtonEditar();
		createButtonSalvar();
		createButtonPesquisar();
		createButtonExcluir();
		createButtonSair();
		createProcessOptionsPanel();
		createFormPanel();
		createCenterPanel();
		createTopPanel();
		
		//Initialize properties...
		setLayout(new BorderLayout());
		setSize(420, 400);
		
		int x = (int) WindowManager.getApplication().getLocationOnScreen().getX() + 285;
		int y = (int) WindowManager.getApplication().getLocationOnScreen().getY() + 150;
		
		setLocation(x, y);
		
		setIconImage(getIconeJanela());
		
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		getContentPane().add(topPanel, BorderLayout.NORTH);
	}

			
	private void createCenterPanel(){
		centerPanel = new JPanel();
		
		centerPanel.setBorder(BorderFactory.createEtchedBorder());
		centerPanel.setPreferredSize(new Dimension(420,360));
		centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));		
		
		centerPanel.add(formPanel);
	}
		
	private void createTopPanel(){
		topPanel = new JPanel();
		
		topPanel.setBorder(BorderFactory.createEtchedBorder());
		topPanel.setPreferredSize(new Dimension(420,40));
		topPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.anchor = GridBagConstraints.WEST;		
		c.insets = new Insets(0, 5, 0, 0);
		
		c.gridx = 0;
		c.gridy = 0;
		
		topPanel.add(buttonNovo, c);
		
		c.gridx = 1;
		c.gridy = 0;
		
		topPanel.add(buttonEditar, c);
		
		c.gridx = 2;
		c.gridy = 0;
		
		topPanel.add(buttonSalvar, c);
		
		c.gridx = 3;
		c.gridy = 0;
		
		topPanel.add(buttonPesquisar, c);
		
		c.gridx = 4;
		c.gridy = 0;
		
		topPanel.add(buttonExcluir, c);
		
		c.weightx = 1;
		
		c.gridx   = 5;
		c.gridy   = 0;
		
		c.fill = GridBagConstraints.VERTICAL;
		
		topPanel.add(new JSeparator(SwingConstants.VERTICAL), c);
		
		c.insets = new Insets(0, 12, 0, 0);
		
		topPanel.add(buttonSair, c);
	}
		
	private void createFormPanel(){
		
		formPanel = new JPanel();
		
		formPanel.setLayout(new GridBagLayout());
				
		//Constraints do Layout
		GridBagConstraints c = new GridBagConstraints();
		
		//Alinhamento dos componentes
		c.anchor = GridBagConstraints.WEST;
		
		//Tamanho das células
		c.weightx = 0;
		c.weighty = 0;
		
		//Padding interno das células
		c.insets = new Insets(5, 5, 0, 5);
		
		//Adição dos componentes		
		c.gridx = 0;
		c.gridy = 0;
		
		formPanel.add(labelId, c);
		
		c.gridx = 1;
		c.gridy = 0;
		
		formPanel.add(fieldId, c);
		
		c.gridx = 0;
		c.gridy = 1;
		
		formPanel.add(labelRazao, c); 
				
		c.gridx = 1;
		c.gridy = 1;
		
		formPanel.add(fieldRazao, c);
		
		c.gridx = 0;
		c.gridy = 2;
		
		formPanel.add(labelFantasia, c); 
				
		c.gridx = 1;
		c.gridy = 2;
		
		formPanel.add(fieldFantasia, c);
		
		c.gridx = 0;
		c.gridy = 3;
		
		formPanel.add(labelCnpj, c); 
				
		c.gridx = 1;
		c.gridy = 3;
		
		formPanel.add(fieldCnpj, c);
		
		c.gridx = 0;
		c.gridy = 4;
		
		//colspan 2
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(20, 5, 0, 5);
		c.gridwidth = 2;
		
		formPanel.add(processOptionsPanel, c);
	}
		
	private void createButtonSalvar(){
		
		buttonSalvar = new JButton();
		
		buttonSalvar.setPreferredSize(new Dimension(30,30));
		buttonSalvar.setToolTipText("Gravar Registro");
		buttonSalvar.setIcon(new ImageIcon(getClass().getResource("/resources/images/save.png")));
		
		buttonSalvar.addActionListener(this);
	}
	
	private void createButtonPesquisar(){
		
		buttonPesquisar = new JButton();
		
		buttonPesquisar.setPreferredSize(new Dimension(30,30));
		buttonPesquisar.setToolTipText("Pesquisar Registros");
		buttonPesquisar.setIcon(new ImageIcon(getClass().getResource("/resources/images/search.png")));
		buttonPesquisar.addActionListener(this);
	}
	
	private void createButtonExcluir(){
		buttonExcluir = new JButton();

		buttonExcluir.setPreferredSize(new Dimension(30,30));
		buttonExcluir.setIcon(new ImageIcon(getClass().getResource("/resources/images/eraser.png")));
		buttonExcluir.setToolTipText("Excluir Registro");
		buttonExcluir.setEnabled(false);
		
		buttonExcluir.addActionListener(this);
	}
	
	private void createButtonEditar(){
		
		buttonEditar = new JButton();

		buttonEditar.setPreferredSize(new Dimension(30,30));
		buttonEditar.setIcon(new ImageIcon(getClass().getResource("/resources/images/edit_user.png")));
		buttonEditar.setToolTipText("Editar Registro");
		buttonEditar.setEnabled(false);
		
		buttonEditar.addActionListener(this);
	}
	
	private void createButtonNovo(){
		
		buttonNovo = new JButton();

		buttonNovo.setPreferredSize(new Dimension(30,30));
		buttonNovo.setIcon(new ImageIcon(getClass().getResource("/resources/images/user_add.png")));
		buttonNovo.setToolTipText("Novo Registro");
		buttonNovo.setEnabled(false);
		
		buttonNovo.addActionListener(this);
	}
	
	private void createButtonSair(){
		buttonSair = new JButton();
		
		buttonSair.setPreferredSize(new Dimension(30,30));
		buttonSair.setIcon(new ImageIcon(getClass().getResource("/resources/images/exit.png")));
		buttonSair.setToolTipText("Sair");
				
		buttonSair.addActionListener(this);
	}
	
	private void createLabelId(){
		labelId = new JLabel("ID");
	}
	
	private void createFieldId(){
		
		fieldId = new JTextField();
		
		fieldId.setPreferredSize(new Dimension(50,26));
		fieldId.setEnabled(false);
	}
	
	private void createLabelRazao(){
		labelRazao = new JLabel("Razão Social");
	}
	
	private void createFieldRazao(){
		
		fieldRazao = new JTextField();
		
		fieldRazao.setPreferredSize(new Dimension(250,26));
		fieldRazao.setName("campo_razao");
	}
	
	private void createLabelFantasia(){
		labelFantasia = new JLabel("Fantasia");
	}
	
	private void createFieldFantasia(){
		
		fieldFantasia = new JTextField();
		
		fieldFantasia.setPreferredSize(new Dimension(200,26));
	}
	
	private void createLabelCnpj(){
		
		labelCnpj = new JLabel("CNPJ");
	}
	
	private void createFieldCnpj(){
		try {
			
			MaskFormatter mascaraCnpj = new MaskFormatter("##.###.###/####-##");
	
			mascaraCnpj.setValidCharacters("0123456789");
			
			fieldCnpj = new JFormattedTextField(mascaraCnpj);
			
			fieldCnpj.setPreferredSize(new Dimension(125,26));
			fieldCnpj.setFocusLostBehavior(JFormattedTextField.COMMIT);

		} catch(ParseException e){
			new RPAExceptionHandler().handle(e);
		}
	}
	
	private void createProcessOptionsPanel(){
		processOptionsPanel = new JPanel();
		
		processOptionsPanel.setPreferredSize(new Dimension(390,169));
		processOptionsPanel.setBorder(BorderFactory.createEtchedBorder());
		processOptionsPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.anchor = GridBagConstraints.NORTHWEST;
		c.insets = new Insets(15, 5, 0, 0);
				
		c.weightx = 1;
		c.weighty = 0;
		
		c.gridx = 0;
		c.gridy = 0;
				
		processOptionsPanel.add(checkProcessEmit, c);
		
		c.weighty = 1;
		
		c.gridx = 0;
		c.gridy = 2;
		
		processOptionsPanel.add(checkProcessDest, c);
	}
		
	private void createCheckProcessEmit(){
		
		checkProcessEmit = new JCheckBox();
		
		checkProcessEmit.setText("Processar Notas de Saída");
		checkProcessEmit.setToolTipText("Quando está opção está desabilitada, "
						+ "o processamento de notas em que o cliente é o "
						+ "emitente não será realizado.");
		
		checkProcessEmit.setSelected(true);
	}
	
	private void createCheckProcessDest(){
		
		checkProcessDest = new JCheckBox();
		
		checkProcessDest.setText("Processar Notas de Entrada");
		checkProcessDest.setToolTipText("Quando está opção está desabilitada, "
						+ "o processamento de notas em que o cliente é o "
						+ "destinatário não será realizado.");
		
		checkProcessDest.setSelected(true);
	}
		
	public void gerenciaCamposEdicao(boolean isEdicao){
		
		fieldRazao.setEnabled(isEdicao);
		fieldFantasia.setEnabled(isEdicao);
		fieldCnpj.setEnabled(isEdicao);
		
		checkProcessEmit.setEnabled(isEdicao);
		checkProcessDest.setEnabled(isEdicao);
		
		buttonSalvar.setEnabled(isEdicao);
		buttonNovo.setEnabled(!isEdicao);
		buttonEditar.setEnabled(!isEdicao);
		buttonExcluir.setEnabled(!isEdicao);
	}
	
	
	private void resetValoresCampos(){
		
		fieldId.setText("");
		fieldRazao.setText("");
		fieldFantasia.setText("");
		fieldCnpj.setText("");
		
		checkProcessEmit.setSelected(true);
		checkProcessDest.setSelected(true);
		
		fieldRazao.requestFocus();
	}
	
	public boolean isCamposObrigatoriosPreenchidos(){
		
		if("".equals(fieldRazao.getText().trim())){
			
			JOptionPane.showMessageDialog(this, "Informe a Razão Social do Cliente", 
					"Atenção", JOptionPane.WARNING_MESSAGE);
			
			fieldRazao.requestFocus();
			
			return false;
			
		} else if("".equals(fieldFantasia.getText().trim())){
			
			JOptionPane.showMessageDialog(this, "Informe o Nome Fantasia do Cliente", 
					"Atenção", JOptionPane.WARNING_MESSAGE);
			
			fieldFantasia.requestFocus();
			
			return false;
			
		} else if("".equals(fieldCnpj.getText().replaceAll("[\\D]", "").trim())){
			
			JOptionPane.showMessageDialog(this, "Informe o CNPJ do Cliente", 
					"Atenção", JOptionPane.WARNING_MESSAGE);
			
			fieldCnpj.requestFocus();
			
			return false;
		}
		
		return true;
	}
		
	@Override
	public void actionPerformed(ActionEvent evt) {
		
		if(buttonNovo == evt.getSource())
		{
			resetValoresCampos();
			
			gerenciaCamposEdicao(true);
		
		} else if(buttonPesquisar == evt.getSource()) {
			
			pesquisar();
		
		} else if(buttonEditar == evt.getSource()) {			
			
			gerenciaCamposEdicao(true);
			
			fieldRazao.requestFocus();
		
		} else if(buttonSalvar == evt.getSource()) {

			if(isCamposObrigatoriosPreenchidos()) {
				if("".equals(fieldId.getText().trim())){
					
					Long id = salvar(false);
					
					if(id.compareTo(0L) > 0){			
						
						fieldId.setText(String.valueOf(id));
						
						gerenciaCamposEdicao(false);
						
						firePropertyChange(STATUS_REGISTRO, this.statusRegistro, "gravado");
						
						JOptionPane.showMessageDialog(this, "Registro gravado com sucesso.");
					} else {
						JOptionPane.showMessageDialog(this, "Houve um erro ao gravar o registro.");
					}
					
				} else {
					
					salvar(true);
					
					gerenciaCamposEdicao(false);
					
					//Notifica os observadores que um registro foi atualizado
					firePropertyChange(STATUS_REGISTRO, this.statusRegistro, "atualizado");
					
					JOptionPane.showMessageDialog(this, "Registro atualizado com sucesso.");
				}
			}
		
		} else if(buttonExcluir == evt.getSource()) {
			
			int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este registro?");
				
			if(confirm == 0){
				if(excluir()){
					
					resetValoresCampos();
					
					gerenciaCamposEdicao(true);
					
					//Notifica os observadores que um registro foi excluido
					firePropertyChange(STATUS_REGISTRO, this.statusRegistro, "excluido");
					
					JOptionPane.showMessageDialog(this, "Registro excluído com sucesso.");						
				}
			}
			
		} else if(buttonSair == evt.getSource()) {
			ClienteDialog.this.dispose();
		}
	}
	
	public Long salvar(boolean isAtualizacao) {
		
		Long id = null;
		
		try {
			
			Cliente cliente = new Cliente();
					
			cliente.setRazao(fieldRazao.getText());
			cliente.setFantasia(fieldFantasia.getText());
			cliente.setCnpj(fieldCnpj.getText());
			cliente.setProcessarSeEmit(checkProcessEmit.isSelected());
			cliente.setProcessarSeDestinatario(checkProcessDest.isSelected());
						
			ClienteDAO dao = ClienteDAOFactory.create();
			
			if(isAtualizacao){
				
				cliente.setId(Long.valueOf(fieldId.getText()));
				
				return dao.update(cliente).getId();
			}
			
			id = dao.save(cliente).getId();
			
		} catch(RPABusinessException e) {
			new RPAExceptionHandler().handle(e);
		}
				
		return id;
	}
		
	public void pesquisar(){
		
		ClienteSearchDialog searchDialog = new ClienteSearchDialog(this);
		
		searchDialog.setVisible(true);
		
		//When window is closed get the returned client
		Cliente cliente = searchDialog.getClienteSelecionado();
		
		if(cliente != null){
			
			fieldId.setText(cliente.getId().toString());
			fieldRazao.setText(cliente.getRazao());
			fieldFantasia.setText(cliente.getFantasia());
			fieldCnpj.setText(cliente.getCnpj());
			
			gerenciaCamposEdicao(false);
		}
	}
	
	public boolean excluir() {	
		return ClienteDAOFactory.create().delete(Long.valueOf(fieldId.getText()));
	}
	
	private Image getIconeJanela(){
		URL iconPath = AppMainWindow.class.getResource("/resources/images/client.png");
		ImageIcon icon = new ImageIcon(iconPath);
		
		return icon.getImage();
	}	
}