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

    /**
     * compara duas dividas
     *
     * @param outra
     * @return true se a divida for maior que outra e false se a divida for
     * maior que a outra
     */
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
        valorPago += valor;
    }
}
