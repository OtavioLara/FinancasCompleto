/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financasgenericas.GUI;

import financasgenericas.controler.ControlerUsuario;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Otavio
 */
public class TelaLogin extends JFrame {

    private GridBagLayout gbl;
    private GridBagConstraints gbc;

    private JLabel lblInfo;
    private JLabel lblUsername;
    private JLabel lblSenha;

    private JTextField txtUsername;
    private JPasswordField txtSenha;

    private JButton btnLogar;
    private JButton btnCadastrar;

    public TelaLogin() {
        super("Login");

        gbl = new GridBagLayout();
        gbc = new GridBagConstraints();

        setLayout(gbl);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        construirTela();
        pack();
        setLocationRelativeTo(null);

    }

    private void construirTela() {
        lblUsername = new JLabel("Username");
        lblSenha = new JLabel("Senha");
        lblInfo = new JLabel("Digite seu username e senha");
        lblInfo.setVisible(false);
        txtUsername = new JTextField(20);
        txtSenha = new JPasswordField(20);

        btnLogar = new JButton("Logar");
        btnLogar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logar();
            }
        });
        getRootPane().setDefaultButton(btnLogar);  
        txtSenha.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtSenha.setBackground(new Color(255, 255, 255));
                lblInfo.setVisible(false);
                validate();
                repaint();
                pack();
                setLocationRelativeTo(null);
            }
            
        });
        txtUsername.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtUsername.setBackground(new Color(255, 255, 255));
                lblInfo.setVisible(false);
                validate();
                repaint();
                pack();
                setLocationRelativeTo(null);
            }
            
        });
        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaCadastro tl = new TelaCadastro();
                tl.setVisible(true);
                dispose();
            }
        });

        JPanel painelLogin = new JPanel();
        painelLogin.setLayout(gbl);

        adicionarComponente(painelLogin, lblUsername, 0, 0, 1, GridBagConstraints.NONE);
        adicionarComponente(painelLogin, txtUsername, 1, 0, 1, GridBagConstraints.NONE);
        adicionarComponente(painelLogin, lblSenha, 2, 0, 1, GridBagConstraints.NONE);
        adicionarComponente(painelLogin, txtSenha, 3, 0, 1, GridBagConstraints.NONE);
        painelLogin.setBorder(javax.swing.BorderFactory.createTitledBorder("Login"));

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnLogar);
        painelBotoes.add(btnCadastrar);

        adicionarComponente(lblInfo, 0, 0, 1, GridBagConstraints.CENTER);
        adicionarComponente(painelLogin, 1, 0, 1, GridBagConstraints.BOTH);
        adicionarComponente(painelBotoes, 2, 0, 1, GridBagConstraints.BOTH);

    }

    private void logar() {
        if (txtUsername.getText().isEmpty()) {
            lblInfo.setText("ensira o seu username");
            txtUsername.setBackground(new Color(255, 150, 150));
            lblInfo.setVisible(true);
            pack();
        } else if (txtSenha.getText().isEmpty()) {
            lblInfo.setText("ensira a sua senha");
            txtSenha.setBackground(new Color(255, 150, 150));
            lblInfo.setVisible(true);
            pack();
        } else if (!ControlerUsuario.logar(txtUsername.getText(), txtSenha.getText())) {
            lblInfo.setText("username ou senha incorretos");
            lblInfo.setVisible(true);
            pack();
        } else {
            TelaPrincipal tl = new TelaPrincipal();
            dispose();
            tl.setVisible(true);
        }
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

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        TelaLogin tp = new TelaLogin();
        tp.setVisible(true);
    }

}
