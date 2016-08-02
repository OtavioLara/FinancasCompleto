/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financasgenericas.beans;

import java.io.Serializable;

/**
 *
 * @author Otavio
 */
public class DividaDespesaBeans extends DividaBeans implements Serializable {
    private double valorPago;

    /**
     * @return the valorPago
     */
    public double getValorPago() {
        return valorPago;
    }

    /**
     * @param valorPago the valorPago to set
     */
    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }
    
}
