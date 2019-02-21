package org.ffsc.rpa.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.ffsc.rpa.domain.Config;
import org.ffsc.rpa.domain.EmailClient;
import org.ffsc.rpa.persistence.ConfigDAOFactory;
import org.ffsc.rpa.persistence.interfaces.ConfigDAO;
import org.ffsc.rpa.types.Protocolo;
import org.ffsc.rpa.ui.renderers.RPAConfigDialogTreeCellRenderer;

public class ConfigDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JScrollPane leftPanel;
	private JPanel centerPanel;
	private JPanel topPanel;
	private JPanel buttonPanel;
	
	private JPanel configEmailPanel;
	private JPanel configDirPanel;
	private JPanel configProxyPanel;
	
	private JTree menuTreeview;
	private JLabel labelPathGravacao;
	private JLabel labelProtocolo;
	private JLabel labelServidor;
	private JLabel labelEmail;
	private JLabel labelSenha;
	private JLabel labelPastaEmail;
	private JLabel labelTesteConexao;
	private JTextField fieldPathGravacao;
	private JTextField fieldServidor;
	private JTextField fieldEmail;
	private JPasswordField fieldSenha;
	private JComboBox<Protocolo> comboProtocolo;
	private JComboBox<String> comboPastasEmail;
	private JCheckBox checkApagarProcessados;
	private JButton buttonEscolhaPath;
	private JButton buttonSalvar;
	private JButton buttonSair;
	private JButton buttonTestConnection;
	private JFileChooser fileChooser;

	private static final Config cfg = ConfigDAOFactory.create().get();

	public ConfigDialog() {

		// Create components...
		createLabelProtocolo();
		createLabelServidor();
		createLabelEmail();
		createLabelSenha();
		createLabelPasta();
		createLabelPathDir();
		createLabelTesteConexao();
		createCampoPathDir();
		createCampoServidor();
		createCampoEmail();
		createCampoSenha();
		createBotaoEscolhaPath();
		createComboProtocolo();
		createComboPastasEmail();
		createCheckApagarProcessados();
		createBotaoTestarConexao();
		createBotaoSalvar();
		createBotalSair();
		createConfigEmailPanel();
		createConfigDirPanel();
		createConfigProxyPanel();
		createMenuTreeView();
		createButtonPanel();
		createLeftPanel();
		createCenterPanel();
		createTopPanel();

		// Initialize properties...
		int x = (int) WindowManager.getApplication().getLocationOnScreen().getX() + 210;
		int y = (int) WindowManager.getApplication().getLocationOnScreen().getY() + 150;

		setSize(500, 400);
		setLayout(new BorderLayout());
		setIconImage(getIconeJanela());
		setLocation(x, y);
		
		// Add Components...
		getContentPane().add(this.leftPanel, BorderLayout.WEST);
		getContentPane().add(this.centerPanel, BorderLayout.CENTER);
		getContentPane().add(this.topPanel, BorderLayout.NORTH);
		getContentPane().add(this.buttonPanel, BorderLayout.SOUTH);
	}


	private void createLeftPanel() {
		
		leftPanel = new JScrollPane(menuTreeview);

		leftPanel.setPreferredSize(new Dimension(165, 400));
		leftPanel.setBorder(BorderFactory.createEtchedBorder());
		leftPanel.setBackground(Color.WHITE);
		leftPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	}

	private void createButtonPanel() {
		
		buttonPanel = new JPanel();

		buttonPanel.setPreferredSize(new Dimension(400, 35));
		buttonPanel.setLayout(new GridBagLayout());
		buttonPanel.setBorder(BorderFactory.createEtchedBorder());

		GridBagConstraints c = new GridBagConstraints();

		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(4, 0, 5, 0);

		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;

		buttonPanel.add(buttonSalvar, c);

		c.insets = new Insets(4, 2, 5, 5);
		c.weightx = 0;
		c.gridx = 1;
		c.gridy = 0;

		buttonPanel.add(buttonSair, c);
	}

	private void createCenterPanel() {
		
		centerPanel = new JPanel();

		centerPanel.setLayout(new CardLayout());
		centerPanel.setPreferredSize(new Dimension(345, 400));
		centerPanel.setBorder(BorderFactory.createEtchedBorder());

		centerPanel.add(configEmailPanel, "Conta de Email");
		centerPanel.add(configDirPanel,   "Diretórios");
		centerPanel.add(configProxyPanel, "Proxy");
	}

	private void createTopPanel() {
		
		topPanel = new JPanel();

		topPanel.setBorder(BorderFactory.createEtchedBorder());
		topPanel.setPreferredSize(new Dimension(500, 35));
	}

	private void createConfigEmailPanel() {
		
		configEmailPanel = new JPanel();

		configEmailPanel.setPreferredSize(new Dimension(400, 400));
		configEmailPanel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(5, 5, 0, 0);

		c.gridx = 0;
		c.gridy = 0;

		configEmailPanel.add(this.labelProtocolo, c);

		c.gridx = 1;
		c.gridy = 0;

		configEmailPanel.add(this.comboProtocolo, c);

		c.gridx = 0;
		c.gridy = 1;

		configEmailPanel.add(this.labelServidor, c);

		c.gridx = 1;
		c.gridy = 1;

		configEmailPanel.add(this.fieldServidor, c);

		c.gridx = 0;
		c.gridy = 2;

		configEmailPanel.add(this.labelEmail, c);

		c.gridx = 1;
		c.gridy = 2;

		configEmailPanel.add(this.fieldEmail, c);

		c.gridx = 0;
		c.gridy = 3;

		configEmailPanel.add(this.labelSenha, c);

		c.gridx = 1;
		c.gridy = 3;

		configEmailPanel.add(this.fieldSenha, c);

		c.gridx = 0;
		c.gridy = 4;

		configEmailPanel.add(this.labelPastaEmail, c);

		c.gridx = 1;
		c.gridy = 4;

		configEmailPanel.add(this.comboPastasEmail, c);

		c.weightx = 1;
		c.weighty = 1;

		c.gridwidth = 2;
		c.anchor = GridBagConstraints.NORTHWEST;

		c.gridx = 1;
		c.gridy = 5;

		configEmailPanel.add(checkApagarProcessados, c);

		c.gridx = 0;
		c.gridy = 6;

		configEmailPanel.add(buttonTestConnection, c);

		c.insets = new Insets(15, 5, 0, 0);

		c.gridx = 1;
		c.gridy = 6;

		configEmailPanel.add(labelTesteConexao, c);
	}

	private void createBotaoEscolhaPath() {
		
		buttonEscolhaPath = new JButton();

		buttonEscolhaPath.setPreferredSize(new Dimension(30, 25));
		buttonEscolhaPath.setIcon(new ImageIcon(getClass().getResource(
				"/resources/images/fileChooser.png")));

		buttonEscolhaPath.addActionListener(this);

	}

	private void createBotaoTestarConexao() {
		
		buttonTestConnection = new JButton();

		buttonTestConnection.setIcon(new ImageIcon(getClass().getResource(
				"/resources/images/connect.png")));
		
		buttonTestConnection.setToolTipText("Testar suas configurações de conexão");
		buttonTestConnection.addActionListener(this);
	}

	private void createBotaoSalvar() {
		
		buttonSalvar = new JButton();

		buttonSalvar.setText("Salvar");
		buttonSalvar.addActionListener(this);
	}

	private void createBotalSair() {
		
		buttonSair = new JButton();

		buttonSair.setText("Sair");

		buttonSair.addActionListener(this);
	}

	private void createConfigDirPanel() {
		
		configDirPanel = new JPanel();

		configDirPanel.setPreferredSize(new Dimension(400, 400));
		configDirPanel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.insets = new Insets(5, 5, 5, 5);
		c.anchor = GridBagConstraints.WEST;

		c.weightx = 1;
		c.weighty = 0;

		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 2;

		c.gridx = 0;
		c.gridy = 0;

		configDirPanel.add(this.labelPathGravacao, c);

		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTHWEST;

		c.gridx = 0;
		c.gridy = 1;

		configDirPanel.add(this.fieldPathGravacao, c);

		c.insets = new Insets(5, 0, 0, 0);
		c.weighty = 1;

		c.gridx = 2;
		c.gridy = 1;

		configDirPanel.add(this.buttonEscolhaPath, c);
	}
	
	private void createConfigProxyPanel() {
		
		configProxyPanel = new JPanel();

		configProxyPanel.setPreferredSize(new Dimension(400, 400));
		configProxyPanel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.insets = new Insets(5, 5, 5, 5);
		c.anchor = GridBagConstraints.WEST;

		c.weightx = 1;
		c.weighty = 0;

		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 2;

		c.gridx = 0;
		c.gridy = 0;
	}

	private void createLabelPathDir() {
		labelPathGravacao = new JLabel("Pasta de Gravação dos Arquivos");
	}

	private void createLabelProtocolo() {
		labelProtocolo = new JLabel("Protocolo");
	}

	private void createLabelServidor() {
		labelServidor = new JLabel("Servidor");
	}

	private void createLabelEmail() {
		labelEmail = new JLabel("Email");
	}

	private void createLabelSenha() {
		labelSenha = new JLabel("Senha");
	}

	private void createLabelPasta() {
		labelPastaEmail = new JLabel("Pasta");
	}

	private void createLabelTesteConexao() {
		labelTesteConexao = new JLabel("Testar Configurações");
	}

	private void createComboProtocolo() {
		comboProtocolo = new JComboBox<Protocolo>(Protocolo.values());
		comboProtocolo.setSelectedItem(cfg.getProtocolo());
	}

	private void createCheckApagarProcessados() {
		checkApagarProcessados = new JCheckBox("Apagar emails processados");
		checkApagarProcessados.setSelected(cfg.getApagarJaProcessados());
	}

	private void createCampoServidor() {
		
		fieldServidor = new JTextField();

		fieldServidor.setPreferredSize(new Dimension(180, 26));
		fieldServidor.setText(cfg.getServidor());
	}

	private void createCampoEmail() {
		
		fieldEmail = new JTextField();

		fieldEmail.setPreferredSize(new Dimension(230, 28));
		fieldEmail.setText(cfg.getEmail());
	}

	private void createCampoSenha() {
		
		fieldSenha = new JPasswordField();

		fieldSenha.setPreferredSize(new Dimension(150, 27));
		fieldSenha.setText(cfg.getSenha());
	}

	private void createComboPastasEmail() {
		
		String pastas[] = { "Caixa de Entrada" };

		comboPastasEmail = new JComboBox<String>(pastas);
		
		comboPastasEmail.setSelectedItem(cfg.getDirCaixaEmail());
		comboPastasEmail.setPreferredSize(new Dimension(150, 30));
	}

	private void createCampoPathDir() {
		
		fieldPathGravacao = new JTextField();

		fieldPathGravacao.setPreferredSize(new Dimension(270, 26));
		fieldPathGravacao.setText(cfg.getDirGravacao());
		fieldPathGravacao.setEditable(false);
	}

	private Image getIconeJanela() {
		
		URL iconPath = AppMainWindow.class.getResource("/resources/images/config.png");
		
		ImageIcon icon = new ImageIcon(iconPath);

		return icon.getImage();
	}
	
	private void createMenuTreeView() {

		// Cria nodo root
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Configurações");

		// Cria nodos secundários
		List<DefaultMutableTreeNode> nodes = getOpcoesMenuTreeview();

		for (DefaultMutableTreeNode node : nodes) {
			root.add(node);
		}

		// Cria a treeview
		menuTreeview = new JTree(root);

		// Seta um CellRenderer customizado para poder trocar os icones
		menuTreeview.setCellRenderer(new RPAConfigDialogTreeCellRenderer());

		menuTreeview.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		// Adiciona o listener para seleção dos nodos
		menuTreeview.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent evt) {

				JTree treeMenu = (JTree) evt.getSource();

				DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeMenu
													.getLastSelectedPathComponent();

				String opcao = node.toString();

				// Muda a exibição do cardLayout conforme a opção
				CardLayout cardLayout = (CardLayout) centerPanel.getLayout();

				if (node.isLeaf()) {
					cardLayout.show(centerPanel, opcao);
				}
			}
		});
	}

	private List<DefaultMutableTreeNode> getOpcoesMenuTreeview() {

		String[] opcoesMenu = new String[]{"Conta de Email", "Diretórios", "Proxy"};
		
		List<DefaultMutableTreeNode> nodes = new ArrayList<DefaultMutableTreeNode>();

		for(String opcao: opcoesMenu){
			
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(opcao);
			
			nodes.add(node);
		}
		
		return nodes;
	}

	private JFileChooser getFileChooser() {
		if (fileChooser == null) {
			
			fileChooser = new JFileChooser("C:\\");

			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}

		return fileChooser;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if (this.buttonEscolhaPath == evt.getSource()) {
			showFileChooseDialog();
		} else if (this.buttonTestConnection == evt.getSource()) {
			testConnection();
		} else if (this.buttonSalvar == evt.getSource()) {
			salvarConfiguracoes();
		} else if (this.buttonSair == evt.getSource()) {
			this.setVisible(false);
		}
	}
	
	private void showFileChooseDialog(){
		
		int retValue = getFileChooser().showOpenDialog(this);

		if (retValue == JFileChooser.APPROVE_OPTION) {
			String path = getFileChooser().getSelectedFile().toString();

			this.fieldPathGravacao.setText(path);
		}
	}
	
	private void testConnection(){

		Config config = new Config();
		
		config.setProtocolo((Protocolo)comboProtocolo.getSelectedItem());
		config.setServidor(fieldServidor.getText());
		config.setEmail(fieldEmail.getText());
		config.setSenha(new String(fieldSenha.getPassword()));
		config.setDirCaixaEmail((String)comboPastasEmail.getSelectedItem());
		config.setApagarJaProcessados(checkApagarProcessados.isSelected());
		config.setDirGravacao(fieldPathGravacao.getText());

		EmailClient client = new EmailClient(config);

		// Test connection ...
		client.conectar();

		if (client.isConnected()) {
			labelTesteConexao.setForeground(new Color(0, 160, 0));
			labelTesteConexao.setText("Conectado com sucesso!");
		} else {
			labelTesteConexao.setForeground(Color.RED);
			labelTesteConexao.setText("Não foi possível conectar ao servidor");
		}	
	}
	
	private void salvarConfiguracoes(){
		
		Config cfg = new Config();
		
		cfg.setProtocolo((Protocolo)comboProtocolo.getSelectedItem());
		cfg.setServidor(fieldServidor.getText());
		cfg.setEmail(fieldEmail.getText());
		cfg.setSenha(new String(fieldSenha.getPassword()));
		cfg.setDirCaixaEmail((String)comboPastasEmail.getSelectedItem());
		cfg.setApagarJaProcessados(checkApagarProcessados.isSelected());
		cfg.setDirGravacao(fieldPathGravacao.getText());

		// DAO
		ConfigDAO dao = ConfigDAOFactory.create();

		if(dao.save(cfg)){
			WindowManager.showInfoMessage("Alterações gravadas com sucesso.");
		} else {
			WindowManager.showErrorMessage("Erro ao gravar o registro");
		}
	}
}