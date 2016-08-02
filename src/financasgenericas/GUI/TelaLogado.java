/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financasgenericas.GUI;

import financasgenericas.UsuarioLogado;
import financasgenericas.controler.ControlerUsuario;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Otavio
 */
public abstract class TelaLogado extends JFrame {

    private GridBagLayout gbl;
    private GridBagConstraints gbc;

    private JMenuBar barraMenu;

    private JMenu menuUsuario;

    private JMenuItem menuItemNomeUsuario;
    private JMenuItem menuItemLogout;

    public TelaLogado(String nome) {
        super(nome);
        gbl = new GridBagLayout();
        gbc = new GridBagConstraints();
        setLayout(gbl);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        construirTela();
    }

    private void construirTela() {
        barraMenu = new JMenuBar();

        menuUsuario = new JMenu("Usuário");

        menuUsuario.setMnemonic(
                'U');
        
        menuItemNomeUsuario = new JMenuItem(ControlerUsuario.getUsuario(UsuarioLogado.getInstance().getUsuario()).getNome());
        menuItemNomeUsuario.setEnabled(false);
        menuItemLogout = new JMenuItem("Encerrar sessão",
                new ImageIcon(getClass().getResource("/img/sair.png")));

        menuItemLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        menuUsuario.add(menuItemNomeUsuario);
        menuUsuario.addSeparator();
        menuUsuario.add(menuItemLogout);

        barraMenu.add(menuUsuario);

        setJMenuBar(barraMenu);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                sairTela();
            }
        });
    }

    public void logout() {
        int opc = JOptionPane.showConfirmDialog(this, "Realmente deseja encerrar sua sessão?", "Encerrar sessão", JOptionPane.YES_NO_OPTION);
        if (opc == JOptionPane.YES_OPTION) {
            TelaLogin tl = new TelaLogin();
            tl.setVisible(true);
            dispose();

        }
    }

    private void sairTela() {
        int opc = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja fechar Finanças Genericas",
                "Fechar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opc == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    protected void adicionarComponente(Component c,
            int linha, int coluna, int largura, int fill, int anchor) {
        gbc.gridx = coluna;
        gbc.fill = fill;
        gbc.gridy = linha;
        gbc.anchor = anchor;
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.gridwidth = largura;
        gbl.setConstraints(c, gbc);
        add(c);
    }

    protected void adicionarComponente(JPanel painel, Component c,
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

    protected void adicionarComponente(JPanel painel, Component c,
            int linha, int coluna, int largura, int fill, Insets insets) {
        gbc.gridx = coluna;
        gbc.fill = fill;
        gbc.gridy = linha;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = insets;
        gbc.gridwidth = largura;
        gbl.setConstraints(c, gbc);
        painel.add(c);
    }

    protected GridBagLayout getLogadoLayout() {
        return gbl;
    }
}
