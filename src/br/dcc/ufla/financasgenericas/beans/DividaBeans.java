/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dcc.ufla.financasgenericas.beans;

import java.io.Serializable;

/**
 *
 * @author Otavio
 */
public class DividaBeans implements Serializable {

    private double valor;

    /**
     * @return the valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(double valor) {
        this.valor = valor;
    }
}
