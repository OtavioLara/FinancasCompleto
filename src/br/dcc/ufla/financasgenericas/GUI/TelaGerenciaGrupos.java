/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dcc.ufla.financasgenericas.GUI;

import br.dcc.ufla.financasgenericas.model.Grupo;
import br.dcc.ufla.financasgenericas.controler.ControlerGrupo;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Otavio
 */
class TelaGerenciaGrupos extends TelaLogado {

    private JList<Grupo> lstIntegrantes;
    private DefaultListModel<Grupo> lstModelo;

    private JButton btnRecarregar;
    private JButton btnCriarGrupo;
    private JButton btnVoltar;

    JScrollPane listScroller;

    public TelaGerenciaGrupos() {
        super("Gerenciador de grupos");

        construirTela();
        pack();
        setLocationRelativeTo(null);
    }

    private void criarListaDeGrupos() {
        lstModelo.clear();
        for (Map.Entry<Long, Grupo> en : ControlerGrupo.gruposDoUsuarioLogado().entrySet()) {
            Long key = en.getKey();
            Grupo grupo = en.getValue();
            lstModelo.addElement(grupo);
        }
    }

    private void construirTela() {

        lstModelo = new DefaultListModel<>();
        criarListaDeGrupos();

        lstIntegrantes = new JList<>(lstModelo);
        lstIntegrantes.setLayoutOrientation(JList.VERTICAL);
        lstIntegrantes.setVisibleRowCount(-1);

        listScroller = new JScrollPane(lstIntegrantes);
        listScroller.setPreferredSize(new Dimension(100, 80));

        btnCriarGrupo = new JButton("Criar grupo");
        btnCriarGrupo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaCriarGrupo tl = new TelaCriarGrupo(lstModelo);
                tl.setVisible(true);
            }
        });
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaPrincipal tl = new TelaPrincipal();
                tl.setVisible(true);
                dispose();
            }
        });
        JPanel painelGrupos = new JPanel(getLogadoLayout());
        adicionarComponente(painelGrupos, listScroller, 0, 0, 1, GridBagConstraints.BOTH);
        painelGrupos.setBorder(javax.swing.BorderFactory.createTitledBorder("Seus grupos"));

        JPanel painelBotoes = new JPanel(getLogadoLayout());
        adicionarComponente(painelBotoes, btnCriarGrupo, 0, 0, 1, GridBagConstraints.BOTH);
        adicionarComponente(painelBotoes, btnVoltar, 1, 0, 1, GridBagConstraints.BOTH);

        adicionarComponente(painelGrupos, 0, 0, 1, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
        adicionarComponente(painelBotoes, 0, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
    }
}
