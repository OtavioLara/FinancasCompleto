/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dcc.ufla.financasgenericas.model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Otavio
 */
public class DespesaIndividual extends Despesa {

    private String usuario;
    private ArrayList<ItemDespesaIndividual> itens;

    public DespesaIndividual(String nome, double valor, String descricao, Date data, String usuario, ArrayList<ItemDespesaIndividual> itens) {
        super(nome, valor, data, descricao);
        this.usuario = usuario;
        this.itens = itens;
    }

    /**
     * @deprecated
     */
    public void addItem(String nome, double valor) {
        getItens().add(new ItemDespesaIndividual(nome, valor));
    }

    @Override
    public boolean isParticipante(Usuario usuario) {
        return getUsuario().equals(usuario.getUserName());
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @return the itens
     */
    public ArrayList<ItemDespesaIndividual> getItens() {
        return itens;
    }
}
