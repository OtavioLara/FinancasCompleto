/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dcc.ufla.financasgenericas.GUI;

import br.dcc.ufla.financasgenericas.controler.ControlerDespesa;
import br.dcc.ufla.financasgenericas.controler.ControlerUsuario;
import br.dcc.ufla.financasgenericas.model.DespesaGrupo;
import br.dcc.ufla.financasgenericas.model.UsuarioLogado;
import br.dcc.ufla.financasgenericas.repositorio.RepositorioUsuario;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Otavio
 */
public class PainelReceberPagamento extends JPanel {

    private GridBagLayout gbl;

    ArrayList<JLabel> lblNomesDespesas;
    ArrayList<JLabel> lblValores;
    ArrayList<JTextField> txtsPagamento;
    ArrayList<Long> ids;
    JPanel painelPrincipal;
    JLabel lblValorAReceber;
    JTextField txtValorAReceber;

    JButton btnVoltar;
    JButton btnDistribuirValor;
    JButton btnConfirmarPagamento;
    HashMap<Long, DespesaGrupo> ourDesp;
    int totalDespesas = 0;
    double valorTotal = 0.0;

    public PainelReceberPagamento(GridBagLayout layout, JFrame tela, String username) {
        gbl = layout;
        setLayout(gbl);
        contruirPainel(tela, username);
    }

    private void contruirPainel(JFrame tela, String username) {
        ids = new ArrayList<>();
        lblNomesDespesas = new ArrayList<>();
        lblValores = new ArrayList<>();
        txtsPagamento = new ArrayList<>();
        btnDistribuirValor = new JButton("Distribuir");
        btnDistribuirValor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                distribuir();
            }
        });
        btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opc = JOptionPane.showConfirmDialog(tela, "Tem certeza que deseja voltar ao menu principal?", "Voltar ao menu principal", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (opc == JOptionPane.YES_OPTION) {
                    TelaPrincipal tl = new TelaPrincipal();
                    tl.setVisible(true);
                    tela.dispose();

                }
            }
        });
        btnConfirmarPagamento = new JButton("Confirmar pagamento");
        btnConfirmarPagamento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gravarDespesas(username);
                JOptionPane.showMessageDialog(tela, "Valor de "+txtValorAReceber.getText()+" de "+username+" recebido com sucesso ", "Valor recebido com sucesso", JOptionPane.INFORMATION_MESSAGE);
                TelaPrincipal tl = new TelaPrincipal();
                tl.setVisible(true);
                tela.dispose();
            }
        });
        tela.getRootPane().setDefaultButton(btnConfirmarPagamento);
        txtValorAReceber = new JTextField(8);
        txtValorAReceber.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtValorAReceber.selectAll();
            }

            @Override
            public void focusLost(FocusEvent e) {
                distribuir();
            }

        });
        lblValorAReceber = new JLabel("Valor recebido");
        painelPrincipal = new JPanel(gbl);
        if (carregarDepesas(tela, username)) {
            adicionarComponente(lblValorAReceber, 0, 0, 1, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
            adicionarComponente(txtValorAReceber, 0, 1, 2, GridBagConstraints.CENTER, GridBagConstraints.LINE_START);
            adicionarComponente(btnDistribuirValor, 1, 0, 1, GridBagConstraints.CENTER, GridBagConstraints.LINE_START);

        } else {
            JLabel info = new JLabel("Não existe dividas desse usuario com você");
            adicionarComponente(info, 0, 0, 1, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
        }
    }

    private boolean carregarDepesas(JFrame tela, String username) {
        ourDesp = ControlerDespesa.getDepesaEntreUsuario(username);
        boolean entrou = false;
        for (Map.Entry<Long, DespesaGrupo> entry : ourDesp.entrySet()) {
            Long key = entry.getKey();
            DespesaGrupo value = entry.getValue();
            if (value.valorAReceberPeloIntegrante(RepositorioUsuario.getUsuario(UsuarioLogado.getInstance().getUsuario())) > 0.0) {
                entrou = true;
                JLabel lblNome = new JLabel(value.getNome());
                System.out.println();
                JLabel lblValor = new JLabel("R$ " + value.valorAReceberPeloIntegrante(RepositorioUsuario.getUsuario(UsuarioLogado.getInstance().getUsuario())));
                valorTotal += value.valorAReceberPeloIntegrante(RepositorioUsuario.getUsuario(UsuarioLogado.getInstance().getUsuario()));
                JTextField txtValor = new JTextField(8);
                lblNomesDespesas.add(lblNome);
                lblValores.add(lblValor);
                txtsPagamento.add(txtValor);
                ids.add(key);
                adicionarComponente(painelPrincipal, lblNomesDespesas.get(totalDespesas), totalDespesas, 0, 1, GridBagConstraints.BOTH);
                adicionarComponente(painelPrincipal, lblValores.get(totalDespesas), totalDespesas, 1, 1, GridBagConstraints.BOTH);
                adicionarComponente(painelPrincipal, txtsPagamento.get(totalDespesas), totalDespesas, 2, 1, GridBagConstraints.BOTH);
                totalDespesas++;
            }
        }
        txtValorAReceber.setText("" + valorTotal);
        if (entrou) {
            adicionarComponente(painelPrincipal, 2, 0, 3, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
            adicionarComponente(btnConfirmarPagamento, 3, 0, 1, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
            adicionarComponente(btnVoltar, 3, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.LINE_START);
            tela.validate();
            tela.repaint();
            tela.pack();
        }
        return entrou;
    }

    private void gravarDespesas(String username) {
        int posAtual = 0;
        for (Map.Entry<Long, DespesaGrupo> entry : ourDesp.entrySet()) {
            Long key = entry.getKey();
            DespesaGrupo value = entry.getValue();
            if (value.valorAReceberPeloIntegrante(ControlerUsuario.getUsuario(UsuarioLogado.getInstance().getUsuario())) > 0.0) {
                value.setValorPagoPeloParticipante(ControlerUsuario.getUsuario(username), Double.parseDouble(txtsPagamento.get(posAtual).getText()));
                value.setValorRecebidoPeloParticipante(ControlerUsuario.getUsuario(UsuarioLogado.getInstance().getUsuario()), Double.parseDouble(txtsPagamento.get(posAtual).getText()));
                ControlerDespesa.atualizarDespesa(key, value);
                posAtual++;
            }
            
        }
    }

    private void distribuir() {
        double valorRecebido = Double.parseDouble(txtValorAReceber.getText());
        for (int i = 0; i < lblValores.size(); i++) {
            String sValor = lblValores.get(i).getText();
            sValor = sValor.substring(3, sValor.length());
            double valorDaDespesa = Double.parseDouble(sValor);
            if (valorRecebido >= valorDaDespesa) {
                txtsPagamento.get(i).setText(valorDaDespesa + "");
                valorRecebido -= valorDaDespesa;
            } else {
                txtsPagamento.get(i).setText(Double.toString(valorRecebido));
                valorRecebido = 0;
            }
        }
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
