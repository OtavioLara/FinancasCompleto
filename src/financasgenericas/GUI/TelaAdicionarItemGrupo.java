/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financasgenericas.GUI;

import financasgenericas.model.DividaItem;
import financasgenericas.model.ItemDespesaGrupo;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Otavio
 */
class TelaAdicionarItemGrupo extends JDialog {

    private GridBagLayout gbl;
    private GridBagConstraints gbc;

    private JCheckBox[] ckParticipantes;
    private JTextField[] txtValorPorParticipante;

    private JLabel lblNomeItem;
    private JLabel lblValorItem;

    private JTextField txtNomeItem;
    private JTextField txtValorItem;

    private JButton btnAdcionar;
    private JButton btnCancelar;
    public TelaAdicionarItemGrupo(JFrame parent, ArrayList<String> participantes) {
        super(parent, "Adicionar item", true);
        gbl = new GridBagLayout();
        gbc = new GridBagConstraints();
        setLayout(gbl);
        criarCheckBox(participantes);
        contruirTela();
        setResizable(false);
        pack();
        setLocationRelativeTo(parent);
        
    }

    private void criarCheckBox(ArrayList<String> integrantes) {
        ckParticipantes = new JCheckBox[integrantes.size()];
        txtValorPorParticipante = new JTextField[integrantes.size()];
        int i = 0;
        for (String integrante : integrantes) {
            ckParticipantes[i] = new JCheckBox(integrante);
            txtValorPorParticipante[i] = new JTextField(8);
            i++;
        }
    }

    private void contruirTela() {
        lblNomeItem = new JLabel("Nome");
        lblValorItem = new JLabel("Valor");

        txtValorItem = new JTextField(8);
        txtNomeItem = new JTextField(15);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnAdcionar = new JButton("Adicionar");
        btnAdcionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                dispose();
            }
        });

        JPanel painelItegrantes = new JPanel(gbl);
        for (int i = 0; i < ckParticipantes.length; i++) {
            adicionarComponente(painelItegrantes, ckParticipantes[i], i, 0, 1, GridBagConstraints.BOTH);
            adicionarComponente(painelItegrantes, txtValorPorParticipante[i], i, 1, 1, GridBagConstraints.BOTH);
        }

        adicionarComponente(lblNomeItem, 0, 0, 1, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
        adicionarComponente(txtNomeItem, 0, 1, 2, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
        adicionarComponente(lblValorItem, 1, 0, 1, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
        adicionarComponente(txtValorItem, 1, 1, 2, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
        adicionarComponente(painelItegrantes, 2, 0, 1, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
        adicionarComponente(btnAdcionar, 3, 0, 3, GridBagConstraints.CENTER, GridBagConstraints.LINE_START);

    }

    public ItemDespesaGrupo retorno() {
        
        HashMap<String, DividaItem> participantesItem = new HashMap<>();
        for (int i = 0; i < ckParticipantes.length; i++) {
            if (ckParticipantes[i].isSelected()) {
                DividaItem divida = new DividaItem(Double.parseDouble(txtValorPorParticipante[i].getText()));
                participantesItem.put(ckParticipantes[i].getText(), divida);
            }
        }
        ItemDespesaGrupo item = new ItemDespesaGrupo(txtNomeItem.getText(), Double.parseDouble(txtValorItem.getText()), participantesItem);
        return item;
    }

    public JCheckBox[] getCheck() {
        return ckParticipantes;
    }

    public JTextField[] getValorPorParticipante(){
        return txtValorPorParticipante;
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
}
