/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financasgenericas.GUI;

import financasgenericas.model.ItemDespesaGrupo;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Otavio
 */
public class PainelItens extends JPanel {

    private GridBagLayout gbl;

    private ArrayList<JTextField> nomeItens;
    private ArrayList<JTextField> valorItens;
    private ArrayList<JCheckBox[]> checkBoxes;
    private ArrayList<JTextField[]> valorItensPorUsuario;

    private JButton btnAdicionarItem;

    private int totalItens = 0;

    public PainelItens(GridBagLayout layout, ArrayList<String> integrantes, ArrayList<ItemDespesaGrupo> itens, TelaLogado tela) {
        this.gbl = layout;
        setLayout(gbl);
        contruirPainel(tela, integrantes, itens);

    }

    private JCheckBox[] criarCheckBox(ArrayList<String> integrantes) {
        JCheckBox[] ckIntegrantes = new JCheckBox[integrantes.size()];
        for (int i = 0; i < integrantes.size(); i++) {
            ckIntegrantes[i] = new JCheckBox(integrantes.get(i));
        }
        return ckIntegrantes;
    }

    private void contruirPainel(JFrame tela, ArrayList<String> integrantes, ArrayList<ItemDespesaGrupo> itens) {

        nomeItens = new ArrayList<>();
        valorItens = new ArrayList<>();
        checkBoxes = new ArrayList<>();
        valorItensPorUsuario = new ArrayList<>();

        btnAdicionarItem = new JButton("Adicioanar item");
        btnAdicionarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaAdicionarItemGrupo tg = new TelaAdicionarItemGrupo(tela, integrantes);
                tg.setVisible(true);
                if (tg.retorno() != null) {
                    adicionarMaisUmItem(tg, tela, integrantes, itens);
                }
            }

            
        });

        adicionarComponente(btnAdicionarItem, 100, 0, 6, GridBagConstraints.CENTER, GridBagConstraints.CENTER);

    }

    private void adicionarMaisUmItem(TelaAdicionarItemGrupo tg, JFrame tela, ArrayList<String> integrantes, ArrayList<ItemDespesaGrupo> itens) {
        ItemDespesaGrupo item = tg.retorno();
        itens.add(item);

        JLabel lblNomeItem = new JLabel("Nome");
        JLabel lblValorItem = new JLabel("Valor");
        JTextField txtNomeItem = new JTextField(15);
        txtNomeItem.setText(item.getNome());
        JTextField txtValorItem = new JTextField(8);
        txtValorItem.setText("" + item.getValor());
        nomeItens.add(txtNomeItem);
        valorItens.add(txtValorItem);
        JPanel painelItem = new JPanel(gbl);

        adicionarComponente(painelItem, lblNomeItem, 0, 0, 1, GridBagConstraints.BOTH);
        adicionarComponente(painelItem, nomeItens.get(totalItens), 0, 1, 1, GridBagConstraints.BOTH);
        adicionarComponente(painelItem, lblValorItem, 0, 2, 1, GridBagConstraints.BOTH);
        adicionarComponente(painelItem, valorItens.get(totalItens), 0, 3, 1, GridBagConstraints.BOTH);

        JCheckBox[] ckIntegrantes = tg.getCheck();
        JTextField[] txtValWorePorIntegrantes = tg.getValorPorParticipante();

        valorItensPorUsuario.add(txtValWorePorIntegrantes);
        checkBoxes.add(ckIntegrantes);

        for (int i = 0; i < ckIntegrantes.length; i++) {
            if (ckIntegrantes[i].isSelected()) {
                adicionarComponente(painelItem, checkBoxes.get(totalItens)[i], i + 1, 0, 1, GridBagConstraints.BOTH);
                adicionarComponente(painelItem, valorItensPorUsuario.get(totalItens)[i], i + 1, 1, 1, GridBagConstraints.BOTH);
            }
        }
        adicionarComponente(painelItem, totalItens, 0, 1, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
        tela.pack();

        totalItens++;
    }

    protected void adicionarComponente(Component c,
            int linha, int coluna, int largura, int fill, int anchor) {
        GridBagConstraints gbc = new GridBagConstraints();
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
        GridBagConstraints gbc = new GridBagConstraints();
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
