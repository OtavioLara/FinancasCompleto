/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dcc.ufla.financasgenericas.GUI;

import br.dcc.ufla.financasgenericas.controler.ControlerUsuario;
import br.dcc.ufla.financasgenericas.exceptions.UsuarioJaExistenteException;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Otavio
 */
class TelaCadastro extends JFrame implements ActionListener, Cadastra {

    private GridBagLayout gbl;
    private GridBagConstraints gbc;

    private JLabel lblNome;
    private JLabel lblUsername;
    private JLabel lblEmail;
    private JLabel lblSenha;
    private JLabel lblSenhaNovamente;

    private JTextField txtNome;
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JPasswordField txtSenhaNovamente;

    private JButton btnCadastrar;
    private JButton btnCancelar;

    public TelaCadastro() {
        super("Cadastro");

        gbl = new GridBagLayout();
        gbc = new GridBagConstraints();

        setSize(400, 250);
        setLayout(gbl);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        construirTela();
        setLocationRelativeTo(null);
    }

    public void construirTela() {
        lblNome = new JLabel("Nome: ");
        lblUsername = new JLabel("Username");
        lblEmail = new JLabel("Email");
        lblSenha = new JLabel("Senha: ");
        lblSenhaNovamente = new JLabel("Digite Novamente a senha");

        txtNome = new JTextField(15);
        txtUsername = new JTextField(15);
        txtEmail = new JTextField(15);
        txtSenha = new JPasswordField(15);
        txtSenhaNovamente = new JPasswordField(15);

        btnCadastrar = new JButton("Cadastrar");
        getRootPane().setDefaultButton(btnCadastrar);
        btnCadastrar.addActionListener(this);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaLogin tl = new TelaLogin();
                tl.setVisible(true);
                dispose();
            }
        });

        JPanel painelCadastro = new JPanel();
        painelCadastro.setLayout(gbl);
        adicionarComponente(painelCadastro, lblNome, 0, 0, 1, GridBagConstraints.NONE);
        adicionarComponente(painelCadastro, txtNome, 0, 1, 3, GridBagConstraints.NONE);
        adicionarComponente(painelCadastro, lblUsername, 1, 0, 1, GridBagConstraints.NONE);
        adicionarComponente(painelCadastro, txtUsername, 1, 1, 3, GridBagConstraints.NONE);
        adicionarComponente(painelCadastro, lblEmail, 2, 0, 1, GridBagConstraints.NONE);
        adicionarComponente(painelCadastro, txtEmail, 2, 1, 3, GridBagConstraints.NONE);
        adicionarComponente(painelCadastro, lblSenha, 3, 0, 1, GridBagConstraints.NONE);
        adicionarComponente(painelCadastro, txtSenha, 3, 1, 3, GridBagConstraints.NONE);
        adicionarComponente(painelCadastro, lblSenhaNovamente, 4, 0, 1, GridBagConstraints.NONE);
        adicionarComponente(painelCadastro, txtSenhaNovamente, 4, 1, 3, GridBagConstraints.NONE);
        painelCadastro.setBorder(javax.swing.BorderFactory.createTitledBorder("Cadatro de usuário"));

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnCancelar);

        adicionarComponente(painelCadastro, 0, 0, 4, GridBagConstraints.BOTH);
        adicionarComponente(painelBotoes, 1, 0, 4, GridBagConstraints.BOTH);

    }

    private void adicionarComponente(Component c,
            int linha, int coluna, int largura, int fill) {
        gbc.gridx = coluna;
        gbc.fill = fill;
        gbc.gridy = linha;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.gridwidth = largura;
        gbl.setConstraints(c, gbc);
        add(c);
    }

    private void adicionarComponente(JPanel painel, Component c,
            int linha, int coluna, int largura, int fill) {
        gbc.gridx = coluna;
        gbc.fill = fill;
        gbc.gridy = linha;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.gridwidth = largura;
        gbl.setConstraints(c, gbc);
        painel.add(c);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        validaCampos();
    }

    @Override
    public void geraObjeto() {
        try {
            ControlerUsuario.cadastrarUsuario(txtNome.getText(), txtEmail.getText(), txtUsername.getText(), txtSenha.getText());
            TelaLogin tl = new TelaLogin();
            tl.setVisible(true);
            setVisible(false);
        } catch (UsuarioJaExistenteException uje) {
            JOptionPane.showMessageDialog(null, uje.getMessage() + "\nTente outro por favor.", "Usuario já existe", JOptionPane.ERROR_MESSAGE);
            txtUsername.setBackground(new Color(255, 150, 150));
            txtUsername.requestFocus();
        }

    }

    @Override
    public void validaCampos() {
        if (txtNome.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha os campos para se cadastrar", "Campo Vazio", JOptionPane.ERROR_MESSAGE);
        } else if (txtUsername.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Você precisará de um username para se autenticar", "Campo Vazio", JOptionPane.ERROR_MESSAGE);
        } else if (txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Informe seu email", "Campo Vazio", JOptionPane.ERROR_MESSAGE);
        } else if (txtSenha.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite uma senha para válida", "Campo Vazio", JOptionPane.ERROR_MESSAGE);
        } else if (txtSenhaNovamente.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Confirme a senha digitada", "Campo Vazio", JOptionPane.ERROR_MESSAGE);
        } else if (!txtSenha.getText().equals(txtSenhaNovamente.getText())) {
            JOptionPane.showMessageDialog(null, "As senha digitadas devem ser iguais", "Campo Vazio", JOptionPane.ERROR_MESSAGE);
        } else {
            geraObjeto();
        }
    }
}
