/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financasgenericas.GUI;

import financasgenericas.Despesa;
import financasgenericas.DespesaGrupo;
import financasgenericas.DespesaIndividual;
import financasgenericas.UsuarioLogado;
import financasgenericas.controler.ControlerDespesa;
import financasgenericas.controler.ControlerGrupo;
import financasgenericas.controler.ControlerUsuario;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Otavio
 */
public class TelaHistorico extends TelaLogado {

    private JPanel painelPrincipal;
    private JTable tabelaItens;
    private DefaultTableModel modeloTableItens;

    private JButton btnVoltar;

    public TelaHistorico() {
        super("Histórico");
        construirTela();
        pack();
        setLocationRelativeTo(null);
    }

    private void contruirTabela() {
        HashMap<Long, Despesa> despesas = ControlerDespesa.getTotalDespesasDoUsuarioLogado();

        for (Map.Entry<Long, Despesa> entry : despesas.entrySet()) {
            Long key = entry.getKey();
            Despesa value = entry.getValue();
            Object[] obj = new Object[7];
            String data = value.getData().getDate() + "/" + (value.getData().getMonth() + 1) + "/" + (value.getData().getYear() + 1900);
            if (value instanceof DespesaGrupo) {
                obj[0] = value.getNome();
                obj[1] = String.format("R$ %.2f", value.getValor());
                obj[2] = String.format("R$ %.2f", ((DespesaGrupo) value).contribuidoPeloIntegrante(ControlerUsuario.getUsuario(UsuarioLogado.getInstance().getUsuario())));
                obj[3] = String.format("R$ %.2f", ((DespesaGrupo) value).valorAPagarPeloIntegrante(ControlerUsuario.getUsuario(UsuarioLogado.getInstance().getUsuario())));
                obj[4] = String.format("R$ %.2f", ((DespesaGrupo) value).valorAReceberPeloIntegrante(ControlerUsuario.getUsuario(UsuarioLogado.getInstance().getUsuario())));
                obj[5] = data;
                obj[6] = ControlerGrupo.getGrupoById(((DespesaGrupo) value).getIdGrupo()).getNome();
            } else if (value instanceof DespesaIndividual) {
                obj[0] = value.getNome();
                obj[1] = String.format("R$ %.2f", value.getValor());
                obj[2] = "        -   ";
                obj[3] = "        -   ";
                obj[4] = "        -   ";
                obj[5] = data;
                obj[6] = "Individual";
            }
            modeloTableItens.addRow(obj);
        }

    }

    private void construirTela() {

        String colunas[] = {"Nome", "Valor total", "Valor pago", "Valor A pagar", "Valor A receber", "Data", "Grupo"};

        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaPrincipal tp = new TelaPrincipal();
                tp.setVisible(true);
                dispose();
            }
        });
        modeloTableItens = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaItens = new JTable(modeloTableItens);

        JScrollPane listScroller = new JScrollPane(tabelaItens);
        listScroller.setPreferredSize(new Dimension(600, 150));

        contruirTabela();
        painelPrincipal = new JPanel(getLogadoLayout());
        adicionarComponente(painelPrincipal, listScroller, 0, 0, 1, GridBagConstraints.BOTH);
        adicionarComponente(painelPrincipal, btnVoltar, 1, 0, 1, GridBagConstraints.BOTH);
        add(painelPrincipal);
    }

}
