/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dcc.ufla.financasgenericas.model;

import br.dcc.ufla.financasgenericas.exceptions.SomaNaoCorrespondeAValorException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Otavio
 */
public class DespesaGrupo extends Despesa{

    private Date dataAlerta;
    private ArrayList<ItemDespesaGrupo> itens;
    private HashMap<String, DividaDespesa> integrantes;
    private long idGrupo;
    
    public DespesaGrupo(String nome, double valor, String descricao, Date data,
            Date dataAlerta,long idGrupo, HashMap<String, DividaDespesa> integrantes,
            ArrayList<ItemDespesaGrupo> itens) {
        super(nome, valor, data, descricao);
        this.idGrupo = idGrupo;
        this.integrantes = integrantes;
        this.dataAlerta = dataAlerta;
        setItens(itens, valor);
    }


    public void removerIntegrante(Usuario u) {
        if (getIntegrantes().containsKey(u)) {
            getIntegrantes().remove(u);
        }
        if (getIntegrantes().isEmpty()) {

        }
    }

    public void setItens(ArrayList<ItemDespesaGrupo> itens, double valor) {
        if (verificaSomaItens(itens, valor)) {
            this.itens = itens;
        } else {
            throw new SomaNaoCorrespondeAValorException(somaItens(itens), valor);
        }
    }

    private boolean verificaSomaItens(ArrayList<ItemDespesaGrupo> itens, double valor) {
        return (somaItens(itens) == valor);
    }

    private double somaItens(ArrayList<ItemDespesaGrupo> itens) {
        double soma = 0;
        for (Item item : itens) {
            soma += item.getValor();
        }
        return soma;
    }
    
    @Override
    public boolean isParticipante(Usuario usuario) {
        return getIntegrantes().containsKey(usuario.getUserName());
    }
    public double contribuidoPeloIntegrante(Usuario usuario){
        return integrantes.get(usuario.getUserName()).getValorPago();
    }
    public double valorAPagarPeloIntegrante(Usuario usuario){
        return integrantes.get(usuario.getUserName()).valorApagar();
    }
    public double valorAReceberPeloIntegrante(Usuario usuario){
        return integrantes.get(usuario.getUserName()).valorAreceber();
    }
    public void setValorPagoPeloParticipante(Usuario usuario, double valorPago){
        integrantes.get(usuario.getUserName()).pagar(valorPago);
    }
    public void setValorRecebidoPeloParticipante(Usuario usuario, double valorRecebido){
        integrantes.get(usuario.getUserName()).receber(valorRecebido);
    }
    
    /**
     * @return the dataAlerta
     */
    public Date getDataAlerta() {
        return dataAlerta;
    }

    /**
     * @return the itens
     */
    public ArrayList<ItemDespesaGrupo> getItens() {
        return itens;
    }

    /**
     * @return the integrantes
     */
    public HashMap<String, DividaDespesa> getIntegrantes() {
        return integrantes;
    }

    /**
     * @return the idGrupo
     */
    public long getIdGrupo() {
        return idGrupo;
    }
}
