/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dcc.ufla.financasgenericas.GUI;

import br.dcc.ufla.financasgenericas.model.DividaItem;
import br.dcc.ufla.financasgenericas.model.ItemDespesaGrupo;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

    private JLabel lblInfo;
    private JLabel lblNomeItem;
    private JLabel lblValorItem;

    private JTextField txtNomeItem;
    private JTextField txtValorItem;

    private JButton btnAdcionar;
    private JButton btnCancelar;
    private boolean isExited = false;

    public TelaAdicionarItemGrupo(JFrame parent, ArrayList<String> participantes) {
        super(parent, "Adicionar item", true);
        gbl = new GridBagLayout();
        gbc = new GridBagConstraints();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
        lblInfo = new JLabel();
        lblInfo.setVisible(false);
        lblNomeItem = new JLabel("Nome");
        lblValorItem = new JLabel("Valor");

        txtValorItem = new JTextField(8);
        txtValorItem.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                gerarValores();
            }

            private int numeroSelecionados() {
                int soma = 0;
                for (int i = 0; i < ckParticipantes.length; i++) {
                    if (ckParticipantes[i].isSelected()) {
                        soma++;
                    }
                }
                return soma;
            }

            public double formataSemArendodamento(double valor) {
                String sValor = Double.toString(valor);
                if (sValor.contains(".") && sValor.substring(sValor.indexOf('.')).length() > 2) {
                    char[] cValor = sValor.toCharArray();
                    char[] cNovoValor = new char[cValor.length + 5];
                    for (int i = 0; i < cValor.length; i++) {
                        if (cValor[i] == '.') {
                            cNovoValor[i] = '.';
                            cNovoValor[i + 1] = cValor[i + 1];
                            cNovoValor[i + 2] = cValor[i + 2];
                            break;
                        } else {
                            cNovoValor[i] = cValor[i];
                        }
                    }
                    return Double.parseDouble(String.valueOf(cNovoValor));
                } else {
                    return valor;
                }
            }

            private void gerarValores() {
                if (!txtValorItem.getText().isEmpty()) {
                    try {
                        String sValor = txtValorItem.getText().replace(',', '.');
                        double valor = Double.parseDouble(sValor);
                        double valorPorPessoa = valor / numeroSelecionados();
                        valorPorPessoa = formataSemArendodamento(valorPorPessoa);

                        double somaSemDiff = (numeroSelecionados() * valorPorPessoa);
                        BigDecimal diff = new BigDecimal("" + valor);
                        diff = diff.subtract(new BigDecimal("" + somaSemDiff));

                        for (int i = 0; i < ckParticipantes.length; i++) {
                            if (ckParticipantes[i].isSelected()) {
                                if (diff.compareTo(BigDecimal.ZERO) > 0) {
                                    txtValorPorParticipante[i].setText(Double.toString(valorPorPessoa + 0.01).replace('.', ','));
                                } else {
                                    txtValorPorParticipante[i].setText(Double.toString(valorPorPessoa).replace('.', ','));
                                }
                                diff = diff.subtract(new BigDecimal(0.01));
                            }
                        }
                    } catch (NumberFormatException nfe) {
                        setInfo("Dê um valor válido ao item");
                    }
                }
            }

        });

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
                if (txtNomeItem.getText().isEmpty()) {
                    txtNomeItem.setBackground(new Color(255, 150, 150));
                    lblInfo.setText("Dê um nome ao item");
                    lblInfo.setVisible(true);
                    validate();
                    repaint();
                    pack();
                } else if (txtValorItem.getText().isEmpty()) {
                    txtValorItem.setBackground(new Color(255, 150, 150));
                    lblInfo.setText("Dê um valor ao item");
                    lblInfo.setVisible(true);
                    validate();
                    repaint();
                    pack();
                } else if (!verificaSomaDosValoresPagos()) {
                    lblInfo.setText("Soma dos pagantes não corresponde ao valor");
                    lblInfo.setVisible(true);
                    for (int i = 0; i < ckParticipantes.length; i++) {
                        txtValorPorParticipante[i].setBackground(new Color(255, 150, 150));
                    }
                    validate();
                    repaint();
                    pack();
                } else {
                    dispose();
                }

            }
        });
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int opc = JOptionPane.showConfirmDialog(TelaAdicionarItemGrupo.this, "Tem certeza que deseja cancelar o cadatro do item", "Cancelamento", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (opc == JOptionPane.YES_OPTION) {
                    isExited = true;
                    dispose();
                }
            }
        });
        JPanel painelItegrantes = new JPanel(gbl);
        for (int i = 0; i < ckParticipantes.length; i++) {
            ckParticipantes[i].setSelected(true);
            adicionarComponente(painelItegrantes, ckParticipantes[i], i, 0, 1, GridBagConstraints.BOTH);
            adicionarComponente(painelItegrantes, txtValorPorParticipante[i], i, 1, 1, GridBagConstraints.BOTH);
        }
        adicionarComponente(lblInfo, 0, 0, 1, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
        adicionarComponente(lblNomeItem, 1, 0, 1, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
        adicionarComponente(txtNomeItem, 1, 1, 2, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
        adicionarComponente(lblValorItem, 2, 0, 1, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
        adicionarComponente(txtValorItem, 2, 1, 2, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
        adicionarComponente(painelItegrantes, 3, 0, 1, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
        adicionarComponente(btnAdcionar, 4, 0, 3, GridBagConstraints.CENTER, GridBagConstraints.LINE_START);

    }

    private boolean verificaSomaDosValoresPagos() {
        try {
            double soma = 0;
            for (int i = 0; i < ckParticipantes.length; i++) {
                if (!txtValorPorParticipante[i].getText().isEmpty()) {
                    soma += Double.parseDouble(txtValorPorParticipante[i].getText().replace(',', '.'));
                }
            }
            return soma == Double.parseDouble(txtValorItem.getText().replace(',', '.'));
        } catch (NumberFormatException nfe) {
            setInfo("Entre com um valor numérico");
            return false;
        }
    }

    public ItemDespesaGrupo retorno() {
        if (!isExited) {
            try {
                HashMap<String, DividaItem> participantesItem = new HashMap<>();
                for (int i = 0; i < ckParticipantes.length; i++) {
                    if (ckParticipantes[i].isSelected()) {
                        txtValorPorParticipante[i].setBackground(new Color(255, 255, 255));
                        DividaItem divida = new DividaItem(Double.parseDouble(txtValorPorParticipante[i].getText().replace(',', '.')));
                        participantesItem.put(ckParticipantes[i].getText(), divida);
                    }
                }
                ItemDespesaGrupo item = new ItemDespesaGrupo(txtNomeItem.getText(), Double.parseDouble(txtValorItem.getText().replace(',', '.')), participantesItem);
                return item;
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Operação cancelada, numero de entrada inválido", "Entrada de número inválida", JOptionPane.ERROR_MESSAGE);
                isExited = true;
                return null;
            }
        } else {
            return null;
        }
    }

    private void setInfo(String mensagem) {
        lblInfo.setText(mensagem);
        lblInfo.setVisible(true);
        validate();
        repaint();
        pack();
    }

    public JCheckBox[] getCheck() {
        return ckParticipantes;
    }

    public JTextField[] getValorPorParticipante() {
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
