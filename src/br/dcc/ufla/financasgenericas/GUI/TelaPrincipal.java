/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dcc.ufla.financasgenericas.GUI;


import br.dcc.ufla.financasgenericas.controler.ControlerDespesa;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Otavio
 */
public class TelaPrincipal extends TelaLogado {

    private JLabel lblDivida;
    private JLabel lblAReceber;
    private JLabel lblValorDivida;
    private JLabel lblValorAReceber;

    private JButton btnCadastrarDespesaGrupo;
    private JButton btnCadastrarDespesaIndividual;
    private JButton btnContasPendentes;
    private JButton btnHistorico;
    private JButton btnGrupos;
    private JButton btnReceberPagamento;

    public TelaPrincipal() {
        super("Finanças genéricas");
        construirTela();
        pack();
        setLocationRelativeTo(null);
    }

    private void atualizarDespesas() {
        //TODO
    }

    private void construirTela() {

        lblDivida = new JLabel("Preciso pagar");
        lblAReceber = new JLabel("Preciso receber");
        ControlerDespesa cd =new ControlerDespesa();
        double divida = cd.getDividaUsuarioLogado();
        lblValorDivida = new JLabel(String.format("R$ %.2f", divida));

        lblValorAReceber = new JLabel(String.format("R$ %.2f",cd.getAReceberUsuarioLogado()));
        atualizarDespesas();
        btnCadastrarDespesaGrupo = new JButton("Cadastro despesa grupo");
        btnCadastrarDespesaGrupo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaDespesaGrupo tg = new TelaDespesaGrupo();
                tg.setVisible(true);
                dispose();
            }
        });
        btnCadastrarDespesaIndividual = new JButton("Cadastro despesa individual");
        btnCadastrarDespesaIndividual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaDespesaIndividual tl = new TelaDespesaIndividual();
                tl.setVisible(true);
                dispose();
            }
        });
        btnContasPendentes = new JButton("Contas pendentes");
        btnContasPendentes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaContasPendentes tcp = new TelaContasPendentes();
                tcp.setVisible(true);
                dispose();
            }
        });
        btnHistorico = new JButton("Histórico de despesas");
        btnHistorico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaHistorico th = new TelaHistorico();
                th.setVisible(true);
                dispose();
            }
        });
        btnGrupos = new JButton("Administrar grupos");
        btnGrupos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaGerenciaGrupos tl = new TelaGerenciaGrupos();
                tl.setVisible(true);
                dispose();
            }
        });
        btnReceberPagamento = new JButton("Registrar pagamentos");
        btnReceberPagamento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaReceberPagamento trp = new TelaReceberPagamento();
                trp.setVisible(true);
                dispose();
            }
        });
        JPanel painelResumo = new JPanel();
        painelResumo.setLayout(getLogadoLayout());
        adicionarComponente(painelResumo, lblDivida, 0, 0, 2, GridBagConstraints.BOTH, new Insets(3, 3, 3, 50));
        adicionarComponente(painelResumo, lblValorDivida, 1, 0, 2, GridBagConstraints.BOTH, new Insets(3, 3, 3, 50));
        adicionarComponente(painelResumo, lblAReceber, 0, 2, 2, GridBagConstraints.BOTH, new Insets(3, 50, 3, 3));
        adicionarComponente(painelResumo, lblValorAReceber, 1, 2, 2, GridBagConstraints.BOTH, new Insets(3, 50, 3, 3));
        painelResumo.setBorder(javax.swing.BorderFactory.createTitledBorder("Resumo geral"));

        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout());
        painelBotoes.add(btnCadastrarDespesaGrupo);
        painelBotoes.add(btnCadastrarDespesaIndividual);
        painelBotoes.add(btnReceberPagamento);
        painelBotoes.add(btnContasPendentes);
        painelBotoes.add(btnHistorico);
        painelBotoes.add(btnGrupos);
        painelBotoes.add(btnGrupos);

        adicionarComponente(painelResumo, 0, 0, 4, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
        adicionarComponente(painelBotoes, 1, 0, 4, GridBagConstraints.BOTH, GridBagConstraints.LINE_END);
    }

    private void controlaDivida() {

    }
}
