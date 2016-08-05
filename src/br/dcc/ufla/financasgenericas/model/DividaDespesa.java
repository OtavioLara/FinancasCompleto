/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.dcc.ufla.financasgenericas.model;

/**
 *
 * @author Otavio
 */
public class DividaDespesa extends Divida {

    private double valorPago;

    public DividaDespesa(double valorTotal, double valorPago) {
        super(valorTotal);
        this.valorPago = valorPago;
    }

    public double getValorPago() {
        return valorPago;
    }

    public double valorApagar() {
        if (getValor() - valorPago > 0) {
            return (getValor() - valorPago);
        } else {
            return 0.0;
        }
    }

    public double valorAreceber() {
        if (valorPago - getValor() > 0) {
            return valorPago - getValor();
        } else {
            return 0.0;
        }
    }

    public void pagar(double valor) {
        if (valorPago <= getValor()) {
            valorPago += valor;
        }
    }

    public void receber(double valor) {
        if (valor < valorPago) {
            valorPago -= valor;
        }
    }
}
