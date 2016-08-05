/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dcc.ufla.financasgenericas.GUI;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Otavio
 */
public class TelaReceberPagamento extends TelaLogado{
    JLabel lblUsuario;
    JTextField txtUsuario;
    
    JButton btnCarregarPagamentos;
    PainelReceberPagamento painelPagamento;
    
    public TelaReceberPagamento() {
        super("Receber pagamento");
        construirTela();
        pack();
        setLocationRelativeTo(null);
    }

    private void construirTela() {
        lblUsuario = new JLabel("Username");
        txtUsuario = new JTextField(15);
        btnCarregarPagamentos = new JButton("Carregar");
        btnCarregarPagamentos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                painelPagamento = new PainelReceberPagamento(getLogadoLayout(), TelaReceberPagamento.this, txtUsuario.getText());
                adicionarComponente(painelPagamento, 2, 0, 3, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
                validate();
                repaint();
                pack();
            }
        });
        getRootPane().setDefaultButton(btnCarregarPagamentos);
        JPanel painelUsername = new JPanel(getLogadoLayout());
        adicionarComponente(painelUsername, lblUsuario, 0, 0, 1, GridBagConstraints.BOTH);
        adicionarComponente(painelUsername, txtUsuario, 0, 1, 2, GridBagConstraints.BOTH);
        adicionarComponente(painelUsername, 0, 0, 1, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
        adicionarComponente(btnCarregarPagamentos, 1, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.LINE_START);
        
    }
}
