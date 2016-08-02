/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financasgenericas.GUI;

import financasgenericas.DividaDespesa;
import financasgenericas.Grupo;
import financasgenericas.ItemDespesaGrupo;
import financasgenericas.Usuario;
import financasgenericas.controler.ControlerDespesa;
import financasgenericas.controler.ControlerGrupo;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Otavio
 */
public class TelaDespesaGrupo extends TelaLogado {

    private JLabel lblNomeDespesa;
    private JLabel lblData;
    private JLabel lblDataAlerta;
    private JLabel lblGrupos;
    private JLabel lblValorTotal;
    private JLabel lblInfoIntegrantes;
    private double valorTotal;

    private JTextField txtNomeDespesa;
    private JTextField txtData;
    private JTextField txtDataAlerta;
    private JTextArea txtDescricao;

    private JComboBox<Grupo> cbGrupos;

    private JButton btnVoltar;
    private JButton btnCadastrar;

    private JPanel painelPrincipal;
    private JPanel painelItens;
    private JScrollPane scrollPane;

    private ArrayList<ItemDespesaGrupo> itens;
    private long idGrupoSelecionado;

    //quem vai pagar:
    private JComboBox<String> cbContribuinte;
    private ArrayList<JTextField> txtsValorContribuido;
    private ArrayList<JTextField> txtsNomeContribuinte;
    private HashMap<String, JTextField> usuariosQuePagaram;
    private int totalContribuintes = 0;

    public TelaDespesaGrupo() {
        super("Despesa grupo");
        construirTela();
        setResizable(true);
        pack();
        setLocationRelativeTo(null);
    }

    private Grupo[] getArrayGrupos() {
        Grupo[] grupos = new Grupo[ControlerGrupo.gruposDoUsuarioLogado().size()];
        int i = 0;
        for (Map.Entry<Long, Grupo> en : ControlerGrupo.gruposDoUsuarioLogado().entrySet()) {
            Long key = en.getKey();
            Grupo grupo = en.getValue();
            grupos[i] = grupo;
            i++;
        }
        return grupos;
    }

    private String[] getArrayParticipantes() {
        Grupo grupo = ControlerGrupo.getGrupoById(idGrupoSelecionado);
        String[] participantes = new String[grupo.getParticipantes().size()];
        int i = 0;
        for (Map.Entry<Usuario, Boolean> entry : grupo.getParticipantes().entrySet()) {
            Usuario key = entry.getKey();
            Boolean value = entry.getValue();
            participantes[i] = key.getUserName();
            i++;
        }
        return participantes;
    }

    private ArrayList<String> getIntegrantesDespesa(Grupo grupo) {
        ArrayList<String> integrantes = new ArrayList<>();
        Set<Usuario> integrantesGrupo = grupo.getParticipantes().keySet();
        for (Usuario itegrante : integrantesGrupo) {
            integrantes.add(itegrante.getUserName());
        }
        return integrantes;
    }

    private void construirTela() {
        lblInfoIntegrantes = new JLabel();
        usuariosQuePagaram = new HashMap<>();
        cbContribuinte = new JComboBox<>();
        cbContribuinte.addItem("Selecione usuários que vão pagar");
        txtsNomeContribuinte = new ArrayList<>();
        txtsValorContribuido = new ArrayList<>();
        itens = new ArrayList<>();
        lblNomeDespesa = new JLabel("Nome");
        lblData = new JLabel("Data criação");
        lblDataAlerta = new JLabel("Data alerta");

        txtNomeDespesa = new JTextField(15);
        txtData = new JTextField(15);
        txtDataAlerta = new JTextField(15);

        txtDescricao = new JTextArea(4, 20);
        txtDescricao.setBorder(javax.swing.BorderFactory.createTitledBorder("Descrição"));
        txtDescricao.setLineWrap(true);
        txtDescricao.setMaximumSize(new Dimension(4, 20));

        lblValorTotal = new JLabel("Valor total: 0");

        lblNomeDespesa = new JLabel("Nome da despesa");
        txtNomeDespesa = new JTextField(15);

        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaPrincipal tp = new TelaPrincipal();
                tp.setVisible(true);
                dispose();
            }
        });
        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date data = new Date();
                HashMap<String, DividaDespesa> participantes = getParticipantes();
                ControlerDespesa.cadastrarDespesaGrupo(txtNomeDespesa.getText(), valorTotal, txtDescricao.getText(), data, data, idGrupoSelecionado, participantes, itens);
                JOptionPane.showMessageDialog(TelaDespesaGrupo.this, "Despesa cadastrada com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                TelaPrincipal tp = new TelaPrincipal();
                tp.setVisible(true);
                dispose();
            }

            private HashMap<String, DividaDespesa> getParticipantes() {
                HashMap<String, DividaDespesa> integrantes = new HashMap<>();
                Set<Usuario> integrantesGrupo = ((Grupo) cbGrupos.getSelectedItem()).getParticipantes().keySet();
                for (Usuario usuario : integrantesGrupo) {
                    double totalDoUsuario = 0;
                    for (ItemDespesaGrupo item : itens) {
                        totalDoUsuario += item.getDividaItem().get(usuario.getUserName()).getValor();
                    }
                    valorTotal += totalDoUsuario;
                    integrantes.put(usuario.getUserName(), new DividaDespesa(totalDoUsuario, getValorPago(usuario.getUserName())));
                }
                return integrantes;
            }

            private double getValorPago(String userName) {
                try {
                    if (usuariosQuePagaram.containsKey(userName)) {
                        return Double.parseDouble(usuariosQuePagaram.get(userName).getText());
                    } else {
                        return 0.0;
                    }
                } catch (NumberFormatException nfe) {
                    lblInfoIntegrantes = new JLabel("Digite um valor válido para " + userName);
                    return 0.0;
                }
            }

        });
        lblGrupos = new JLabel("Grupos");
        cbGrupos = new JComboBox<>(getArrayGrupos());
        JPanel painelQuemPagou = new JPanel(getLogadoLayout());
        cbGrupos.addActionListener((ActionEvent e) -> {
            JComboBox cb = (JComboBox) e.getSource();
            Grupo grupo = (Grupo) cb.getSelectedItem();
            idGrupoSelecionado = grupo.getId();
            painelItens = new PainelItens(getLogadoLayout(), getIntegrantesDespesa(((Grupo) cbGrupos.getSelectedItem())), itens, TelaDespesaGrupo.this);
            scrollPane = new JScrollPane(painelItens);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setPreferredSize(new Dimension(300, 200));
            scrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder("Adicione itens a despesa"));
            String[] participante = getArrayParticipantes();
            for (int i = 0; i < participante.length; i++) {
                cbContribuinte.addItem(participante[i]);
            }
            adicionarComponente(painelQuemPagou, cbContribuinte, 0, 0, 2, GridBagConstraints.BOTH);

            adicionarComponente(painelPrincipal, painelQuemPagou, 5, 0, 3, GridBagConstraints.BOTH);
            adicionarComponente(painelPrincipal, scrollPane, 6, 0, 3, GridBagConstraints.BOTH);
            painelQuemPagou.validate();
            painelQuemPagou.repaint();
            painelItens.validate();
            painelItens.repaint();
            pack();
        });
        cbContribuinte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!usuariosQuePagaram.containsKey((String) cbContribuinte.getSelectedItem()) && cbContribuinte.getSelectedIndex() != 0) {
                    JTextField txtNomeContribuinte = new JTextField((String) cbContribuinte.getSelectedItem());

                    txtNomeContribuinte.setEditable(false);
                    txtNomeContribuinte.setColumns(15);

                    JTextField txtValorContribuido = new JTextField(8);

                    txtsNomeContribuinte.add(txtNomeContribuinte);

                    txtsValorContribuido.add(txtValorContribuido);
                    usuariosQuePagaram.put((String) cbContribuinte.getSelectedItem(), txtsValorContribuido.get(totalContribuintes));
                    adicionarComponente(painelQuemPagou, lblInfoIntegrantes, 1, 0, 1, GridBagConstraints.BOTH);
                    adicionarComponente(painelQuemPagou, txtsNomeContribuinte.get(totalContribuintes), totalContribuintes + 2, 0, 1, GridBagConstraints.BOTH);
                    adicionarComponente(painelQuemPagou, txtsValorContribuido.get(totalContribuintes), totalContribuintes + 2, 1, 1, GridBagConstraints.BOTH);
                    totalContribuintes++;
                    painelQuemPagou.validate();
                    painelQuemPagou.repaint();
                    painelPrincipal.validate();
                    painelPrincipal.repaint();
                    pack();
                    setLocationRelativeTo(null);
                } else {

                }
            }
        });
        JPanel painelBotoes = new JPanel(getLogadoLayout());

        adicionarComponente(painelBotoes, btnCadastrar, 0, 0, 1, GridBagConstraints.BOTH);
        adicionarComponente(painelBotoes, btnVoltar, 0, 1, 1, GridBagConstraints.BOTH);

        painelPrincipal = new JPanel(getLogadoLayout());

        adicionarComponente(painelPrincipal, lblNomeDespesa, 0, 0, 1, GridBagConstraints.BOTH);
        adicionarComponente(painelPrincipal, txtNomeDespesa, 0, 1, 2, GridBagConstraints.BOTH);

        adicionarComponente(painelPrincipal, lblData, 1, 0, 1, GridBagConstraints.BOTH);
        adicionarComponente(painelPrincipal, txtData, 1, 1, 2, GridBagConstraints.BOTH);

        adicionarComponente(painelPrincipal, lblDataAlerta, 2, 0, 1, GridBagConstraints.BOTH);
        adicionarComponente(painelPrincipal, txtDataAlerta, 2, 1, 2, GridBagConstraints.BOTH);

        adicionarComponente(painelPrincipal, txtDescricao, 3, 0, 3, GridBagConstraints.BOTH);

        adicionarComponente(painelPrincipal, lblGrupos, 4, 0, 1, GridBagConstraints.BOTH);
        adicionarComponente(painelPrincipal, cbGrupos, 4, 1, 2, GridBagConstraints.BOTH);

        adicionarComponente(painelPrincipal, painelBotoes, 7, 0, 3, GridBagConstraints.BOTH);
        add(painelPrincipal);
    }

}
