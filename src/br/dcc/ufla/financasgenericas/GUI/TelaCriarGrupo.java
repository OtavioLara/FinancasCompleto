/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dcc.ufla.financasgenericas.GUI;

import br.dcc.ufla.financasgenericas.model.Grupo;
import br.dcc.ufla.financasgenericas.model.Usuario;
import br.dcc.ufla.financasgenericas.model.UsuarioLogado;
import br.dcc.ufla.financasgenericas.controler.ControlerGrupo;
import br.dcc.ufla.financasgenericas.controler.ControlerUsuario;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Otavio
 */
public class TelaCriarGrupo extends TelaLogado implements Cadastra {

    private JLabel lblNomeGrupo;

    private JList<String> lstIntegrantes;
    private DefaultListModel<String> lstModelo;

    private JTextField txtNomeGrupo;
    private JTextField txtIntegrantes;

    private JButton btnMaisItegrantes;
    private JButton btnCriarGrupo;
    private JButton btnCancelar;
    private DefaultListModel<Grupo> lstModeloGrupos;
    private ArrayList<Usuario> listaUsuarios;

    public TelaCriarGrupo(DefaultListModel<Grupo> lstModeloGrupos) {
        super("Criar grupo");
        construirTela(lstModeloGrupos);
        pack();
        setLocationRelativeTo(null);
    }

    private void criarListaDeGrupos() {
        this.lstModeloGrupos.clear();
        for (Map.Entry<Long, Grupo> en : ControlerGrupo.gruposDoUsuarioLogado().entrySet()) {
            Long key = en.getKey();
            Grupo grupo = en.getValue();
            lstModeloGrupos.addElement(grupo);
        }
    }

    private void construirTela(DefaultListModel<Grupo> lstModeloGrupos) {
        this.lstModeloGrupos = lstModeloGrupos;
        lblNomeGrupo = new JLabel("Nome do grupo");

        lstModelo = new DefaultListModel<>();
        lstModelo.addElement(UsuarioLogado.getInstance().getUsuario());

        lstIntegrantes = new JList<>(lstModelo);
        lstIntegrantes.setLayoutOrientation(JList.VERTICAL_WRAP);
        lstIntegrantes.setVisibleRowCount(-1);

        JScrollPane listScroller = new JScrollPane(lstIntegrantes);
        listScroller.setPreferredSize(new Dimension(100, 80));

        listaUsuarios = new ArrayList<>();
        listaUsuarios.add(ControlerUsuario.getUsuario(UsuarioLogado.getInstance().getUsuario()));
        txtNomeGrupo = new JTextField(15);

        btnMaisItegrantes = new JButton("add integrante");
        btnMaisItegrantes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog(null, "Entre com o username da pessoa\n"
                        + "que deseja cadastrar", "Adicionar integrante", JOptionPane.PLAIN_MESSAGE);
                if (ControlerUsuario.getUsuario(username) != null) {
                    lstModelo.addElement(username);
                    listaUsuarios.add(ControlerUsuario.getUsuario(username));
                } else {
                    JOptionPane.showMessageDialog(null, "Usuário " + username + " não existe", "Usario não existe", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCriarGrupo = new JButton("Criar grupo");
        getRootPane().setDefaultButton(btnCriarGrupo); 
        btnCriarGrupo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtNomeGrupo.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O grupo precisa de um nome", "Grupo sem nome", JOptionPane.ERROR_MESSAGE);
                } else if (listaUsuarios.size() == 1) {
                    JOptionPane.showMessageDialog(null, "Para ser um grupo deve haver mais de uma pessoa", "Tamanho insuficiente", JOptionPane.ERROR_MESSAGE);
                } else {
                    ControlerGrupo.adicionarGrupo(txtNomeGrupo.getText(), ControlerUsuario.getUsuario(UsuarioLogado.getInstance().getUsuario()), listaUsuarios);
                    criarListaDeGrupos();
                    JOptionPane.showMessageDialog(null, "Grupo cadastrado com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            }
        });
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        JPanel painelIntegrantes = new JPanel(getLogadoLayout());
        adicionarComponente(painelIntegrantes, listScroller, 0, 0, 3, GridBagConstraints.LINE_START);
        adicionarComponente(painelIntegrantes, btnMaisItegrantes, 1, 0, 2, GridBagConstraints.LINE_END);
        painelIntegrantes.setBorder(javax.swing.BorderFactory.createTitledBorder("Integrantes"));

        JPanel painelBotoes = new JPanel(getLogadoLayout());
        adicionarComponente(painelBotoes, btnCriarGrupo, 0, 0, 2, GridBagConstraints.BOTH);
        adicionarComponente(painelBotoes, btnCancelar, 0, 2, 1, GridBagConstraints.BOTH);

        adicionarComponente(lblNomeGrupo, 0, 0, 1, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
        adicionarComponente(txtNomeGrupo, 0, 1, 2, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
        adicionarComponente(painelIntegrantes, 1, 0, 3, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
        adicionarComponente(painelBotoes, 2, 0, 3, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);

    }

    @Override
    public void geraObjeto() {
        ControlerGrupo.adicionarGrupo(txtNomeGrupo.getText(), ControlerUsuario.getUsuario(UsuarioLogado.getInstance().getUsuario()), listaUsuarios);
    }
    
    @Override
    public void validaCampos() {
        if (txtNomeGrupo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O grupo precisa de um nome", "Grupo sem nome", JOptionPane.ERROR_MESSAGE);
        } else if (listaUsuarios.size() == 1) {
            JOptionPane.showMessageDialog(null, "Para ser um grupo deve haver mais de uma pessoa", "Tamanho insuficiente", JOptionPane.ERROR_MESSAGE);
        } else {
            geraObjeto();
            criarListaDeGrupos();
            JOptionPane.showMessageDialog(null, "Grupo cadastrado com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
    }

}
