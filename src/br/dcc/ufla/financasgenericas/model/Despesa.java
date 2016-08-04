/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dcc.ufla.financasgenericas.model;

import java.util.Date;

/**
 *
 * @author Otavio
 */
public abstract class Despesa {
    private double valor;
    private String nome;
    private String descricao;
    private Date data;
    
    public Despesa(String nome, double valor, Date data, String descricao){
        this(nome, valor, data);
        this.descricao = descricao;
    }
    public Despesa(String nome, double valor, Date data){
        this.nome = nome;
        this.valor = valor;
        this.data = data;
    }
    /**
     * @return the valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }

    public abstract boolean isParticipante(Usuario usuario);
    
}
